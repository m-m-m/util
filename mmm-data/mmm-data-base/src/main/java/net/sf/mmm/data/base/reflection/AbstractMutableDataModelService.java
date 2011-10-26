/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.api.reflection.DataModifiers;
import net.sf.mmm.data.api.reflection.DataReflectionEvent;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.api.reflection.DataReflectionNotEditableException;
import net.sf.mmm.data.api.reflection.DataReflectionObject;
import net.sf.mmm.data.api.reflection.DataSystemModifyException;
import net.sf.mmm.data.api.reflection.MutableDataReflectionService;
import net.sf.mmm.data.trash.MutableMetaData;
import net.sf.mmm.util.event.api.ChangeType;
import net.sf.mmm.util.reflect.api.ClassResolver;
import net.sf.mmm.util.reflect.base.MappedClassResolver;

/**
 * This an the abstract base implementation of the
 * {@link net.sf.mmm.data.api.reflection.DataReflectionService
 * ContentModelService} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractMutableDataModelService extends AbstractDataReflectionService
    implements MutableDataReflectionService, ClassResolver {

  /** @see #getClassResolver() */
  private ClassResolver classResolver;

  /** @see #getName2JavaClassMap() */
  private Map<String, Class> name2JavaClassMap;

  /**
   * The constructor.
   */
  public AbstractMutableDataModelService() {

    super();
  }

  /**
   * @return the classResolver
   */
  public ClassResolver getClassResolver() {

    return this.classResolver;
  }

  /**
   * @param classResolver the classResolver to set
   */
  @Resource
  public void setClassResolver(ClassResolver classResolver) {

    this.classResolver = classResolver;
  }

  /**
   * @return the name2JavaClassMap
   */
  public Map<String, Class> getName2JavaClassMap() {

    return this.name2JavaClassMap;
  }

  /**
   * @param name2JavaClassMap the name2JavaClassMap to set
   */
  public void setName2JavaClassMap(Map<String, Class> name2JavaClassMap) {

    this.name2JavaClassMap = name2JavaClassMap;
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   * 
   * @throws Exception if an exception was caused during initialization.
   */
  @PostConstruct
  public void initialize() throws Exception {

    if (this.name2JavaClassMap == null) {
      Map<String, Class> name2javaClass = new HashMap<String, Class>();
      initializeJavaClassMap(name2javaClass);
      this.name2JavaClassMap = name2javaClass;
    }
    if (this.classResolver == null) {
      MappedClassResolver mappedClassResolver = new MappedClassResolver();
      initializeClassResolver(mappedClassResolver);
      this.classResolver = mappedClassResolver;
    }
  }

  /**
   * This method initializes the default {@link #getName2JavaClassMap()
   * name2JavaClassMap}.
   * 
   * @param name2javaClass is the map to initialize.
   */
  protected void initializeJavaClassMap(Map<String, Class> name2javaClass) {

    name2javaClass.put(DataObject.CLASS_NAME, DataObject.class);
    name2javaClass.put(DataReflectionObject.CLASS_NAME, DataReflectionObject.class);
    name2javaClass.put(DataClass.CLASS_NAME, DataClass.class);
    name2javaClass.put(DataField.CLASS_NAME, DataField.class);
  }

  /**
   * This method initializes the default {@link #getClassResolver()
   * class-resolver}.
   * 
   * @param mappedClassResolver is the resolver to initialize.
   */
  protected void initializeClassResolver(MappedClassResolver mappedClassResolver) {

    mappedClassResolver.addClassMapping(String.class);
    mappedClassResolver.addClassMapping(Boolean.class);
    mappedClassResolver.addClassMapping(Integer.class);
    mappedClassResolver.addClassMapping(Long.class);
    mappedClassResolver.addClassMapping(Double.class);
    mappedClassResolver.addClassMapping(Date.class);
    mappedClassResolver.addClassMapping(Float.class);
    mappedClassResolver.addClassMapping(Short.class);
    mappedClassResolver.addClassMapping(Byte.class);
    // collections
    mappedClassResolver.addClassMapping(List.class);
    mappedClassResolver.addClassMapping(Set.class);
    mappedClassResolver.addClassMapping(Collection.class);
    mappedClassResolver.addClassMapping(Map.class);
    // mmm values
    mappedClassResolver.addClassMapping(DataModifiers.class);
    mappedClassResolver.addClassMapping(DataFieldModifiers.class);
    mappedClassResolver.addClassMapping(DataClassModifiers.class);
    mappedClassResolver.addClassMapping(DataId.VALUE_NAME, DataId.class);
    mappedClassResolver.addClassMapping(MutableMetaData.VALUE_NAME, MutableMetaData.class);
  }

  /**
   * {@inheritDoc}
   */
  public Class resolveClass(String name) {

    Class result;
    DataClass contentClass = getDataClass(name);
    if (contentClass == null) {
      result = this.classResolver.resolveClass(name);
    } else {
      //
      result = contentClass.getJavaClass();
    }
    assert (result != null);
    return result;
  }

  /**
   * This method verifies that this model is {@link #isEditable() editable}.
   * 
   * @throws DataReflectionNotEditableException if this model is NOT
   *         {@link #isEditable() editable}.
   */
  protected void requireEditableModel() throws DataReflectionNotEditableException {

    if (!isEditable()) {
      throw new DataReflectionNotEditableException();
    }
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass createDataClass(DataClass superClass, String name,
      DataClassModifiers modifiers) throws DataReflectionException {

    requireEditableModel();
    assert (superClass == getDataClass(superClass.getContentId()));
    DataId id = getIdManager().createUniqueClassId();
    AbstractDataClass parentClass = (AbstractDataClass) superClass;
    AbstractDataClass newClass = createNewClass(id, name, parentClass, modifiers,
        superClass.getJavaClass(), false);
    // TODO
    syncClassRecursive(newClass, parentClass);
    // fire update event on super-class?
    // fireEvent(new ContentModelEvent(newClass, ChangeEvent.Type.ADD));
    return newClass;
  }

  /**
   * {@inheritDoc}
   */
  public DataField createContentField(DataClass declaringClass, String name, Type type,
      DataFieldModifiers modifiers) throws DataReflectionException {

    requireEditableModel();
    assert (declaringClass == getDataClass(declaringClass.getContentId()));
    DataId id = getIdManager().createUniqueFieldId();
    AbstractDataClass contentClass = (AbstractDataClass) declaringClass;
    AbstractDataField field = createNewField(id, name, contentClass, type, modifiers, false);
    syncField(contentClass, field);
    // TODO: persist here...
    // contentClass.addField(field);
    // fireEvent(new ContentModelEvent(field, ChangeEvent.Type.ADD));
    return field;
  }

  /**
   * {@inheritDoc}
   */
  public void setDeleted(DataReflectionObject classOrField, boolean newDeletedFlag)
      throws DataReflectionException {

    requireEditableModel();
    if (classOrField.getModifiers().isSystem()) {
      throw new DataSystemModifyException(classOrField);
    }
    // TODO: persist
    AbstractDataReflectionObject reflectionObject = (AbstractDataReflectionObject) classOrField;
    reflectionObject.setDeletedFlag(newDeletedFlag);
    if (classOrField.isContentClass()) {
      fireEvent(new DataReflectionEvent((DataClass) classOrField, ChangeType.UPDATE));
    } else {
      fireEvent(new DataReflectionEvent((DataField) classOrField, ChangeType.UPDATE));
    }
  }

}
