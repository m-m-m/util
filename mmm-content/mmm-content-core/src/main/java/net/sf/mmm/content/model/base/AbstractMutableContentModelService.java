/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.lang.reflect.Type;
import javax.annotation.Resource;

import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelEvent;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.ContentModelNotEditableException;
import net.sf.mmm.content.model.api.ContentReflectionObject;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.api.MutableContentModelService;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.util.event.ChangeEvent;
import net.sf.mmm.util.reflect.ClassResolver;

/**
 * This an the abstract base implementation of the
 * {@link net.sf.mmm.content.model.api.ContentModelService ContentModelService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractMutableContentModelService extends AbstractContentModelService
    implements MutableContentModelService, ClassResolver {

  /** @see #getClassResolver() */
  private ClassResolver classResolver;

  /**
   * The constructor.
   */
  public AbstractMutableContentModelService() {

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
   * {@inheritDoc}
   */
  public Class resolveClass(String name) throws ClassNotFoundException {

    Class result;
    ContentClass contentClass = getClass(name);
    if (contentClass == null) {
      result = this.classResolver.resolveClass(name);
    } else {
      result = contentClass.getJavaClass();
    }
    assert (result != null);
    return result;
  }

  /**
   * This method verifies that this model is {@link #isEditable() editable}.
   * 
   * @throws ContentModelNotEditableException if this model is NOT
   *         {@link #isEditable() editable}.
   */
  protected void requireEditableModel() throws ContentModelNotEditableException {

    if (!isEditable()) {
      throw new ContentModelNotEditableException();
    }
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass createClass(ContentClass superClass, String name,
      ClassModifiers modifiers) throws ContentModelException {

    requireEditableModel();
    assert (superClass == getClass(superClass.getId()));
    SmartId id = getIdManager().createUniqueClassId();
    AbstractContentClass newClass = createOrUpdateClass(id, name, (AbstractContentClass) superClass, modifiers, false,
        superClass.getJavaClass());
    // fire update event on super-class?
    fireEvent(new ContentModelEvent(newClass, ChangeEvent.Type.ADD));
    return newClass;
  }

  /**
   * {@inheritDoc}
   */
  public ContentField createField(ContentClass declaringClass, String name, Type type,
      FieldModifiers modifiers) throws ContentModelException {

    requireEditableModel();
    assert (declaringClass == getClass(declaringClass.getId()));
    SmartId id = getIdManager().createUniqueFieldId();
    return newField(id, name, (AbstractContentClass) declaringClass, modifiers, type);
  }

  /**
   * {@inheritDoc}
   */
  public void setDeleted(ContentReflectionObject classOrField, boolean newDeletedFlag)
      throws ContentModelException {

  // TODO Auto-generated method stub

  }

  /**
   * This method creates a new un-initialized content-field instance.
   * 
   * @param id
   * @param name
   * @param declaringClass
   * @param modifiers
   * @param type
   * @return the new field.
   */
  protected AbstractContentField newField(SmartId id, String name,
      AbstractContentClass declaringClass, FieldModifiers modifiers, Type type) {

    AbstractContentField contentField = newField();
    contentField.setId(id);
    contentField.setName(name);
    contentField.setDeclaringClass(declaringClass);
    contentField.setModifiers(modifiers);
    contentField.setFieldTypeAndClass(type);
    return contentField;
  }

}
