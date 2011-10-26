/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.mmm.data.api.DataIdManager;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.api.reflection.DataReflectionEvent;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.api.reflection.DataReflectionService;
import net.sf.mmm.data.api.reflection.DataSystemModifyException;
import net.sf.mmm.data.api.reflection.access.DataClassReadAccessByInstance;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.util.event.api.EventListener;
import net.sf.mmm.util.event.api.EventSource;
import net.sf.mmm.util.event.base.AbstractSynchronizedEventSource;
import net.sf.mmm.util.nls.api.DuplicateObjectException;

/**
 * This is the abstract base implementation of the {@link DataReflectionService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDataReflectionService implements DataReflectionService {

  /** @see #getDataClass(String) */
  private final Map<String, AbstractDataClass> name2class;

  /** @see #getContentClass(DataId) */
  private final Map<Integer, AbstractDataClass> id2class;

  /** @see #getContentClass(Class) */
  private final Map<Class, AbstractDataClass> class2class;

  /** @see #getDataClasses() */
  private final List<AbstractDataClass<? extends DataObject>> classes;

  /** @see #getDataClasses() */
  private final List<AbstractDataClass<? extends DataObject>> classesView;

  /** @see #getContentField(DataId) */
  private final Map<DataId, AbstractDataField> id2field;

  /** @see #getRootDataClass() */
  private AbstractDataClass rootClass;

  /** @see #getIdManager() */
  private DataIdManager idManager;

  /** @see #getEventRegistrar() */
  private final ContentModelEventSource eventSource;

  /**
   * The constructor.
   */
  public AbstractDataReflectionService() {

    super();
    // TODO: use concurrent map?
    this.name2class = new HashMap<String, AbstractDataClass>();
    this.id2class = new HashMap<DataId, AbstractDataClass>();
    this.class2class = new HashMap<Class, AbstractDataClass>();
    this.classes = new ArrayList<AbstractDataClass<? extends DataObject>>();
    this.classesView = Collections.unmodifiableList(this.classes);
    this.id2field = new HashMap<DataId, AbstractDataField>();
    // AbstractContentObject.setClassAccess(this);
    this.eventSource = new ContentModelEventSource();
  }

  /**
   * {@inheritDoc}
   */
  public DataIdManager getIdManager() {

    return this.idManager;
  }

  /**
   * @param idManager the idManager to set
   */
  @Resource
  public void setIdManager(DataIdManager idManager) {

    this.idManager = idManager;
  }

  /**
   * {@inheritDoc}
   */
  public EventSource<DataReflectionEvent, EventListener<DataReflectionEvent>> getEventRegistrar() {

    return this.eventSource;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass getDataClass(String name) {

    return this.name2class.get(name);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass<? extends DataObject> getDataClass(DataId id) {

    return this.id2class.get(id.getObjectId());
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass<? extends DataObject> getDataClass(Class<? extends DataObject> javaClass) {

    return this.class2class.get(javaClass);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass getContentClass(AbstractDataObject contentObject) {

    DataId id = contentObject.getContentId();
    if (id == null) {
      return getContentClass(contentObject.getClass());
    } else {
      return getContentClass(id.getContentClassId());
    }
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataField getContentField(DataId id) {

    return this.id2field.get(id);
  }

  /**
   * {@inheritDoc}
   */
  public List<AbstractDataClass<? extends DataObject>> getDataClasses() {

    return this.classesView;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass getRootDataClass() {

    return this.rootClass;
  }

  /**
   * This method set the {@link #getRootDataClass() root-class}.
   * 
   * @param rootClass is the root-class to set.
   */
  protected void setRootClass(AbstractDataClass rootClass) {

    this.rootClass = rootClass;
    setContentObjectClassAccess(this.rootClass, this);
  }

  /**
   * This method registers the given <code>contentClass</code> to this service.<br>
   * It does NOT {@link #fireEvent(DataReflectionEvent) fire} the according
   * event.
   * 
   * @param contentClass is the class to add.
   * @throws DataReflectionException if the class is already registered.
   */
  protected void addClass(AbstractDataClass contentClass) throws DataReflectionException {

    DataId id = getDataId(contentClass);
    AbstractDataClass duplicate = this.id2class.get(id);
    if (duplicate != null) {
      throw new DuplicateObjectException(contentClass, duplicate);
    }
    String name = contentClass.getTitle();
    duplicate = this.name2class.get(name);
    if (duplicate != null) {
      throw new DuplicateClassException(contentClass, duplicate);
    }
    for (AbstractDataField field : contentClass.getDeclaredFields()) {
      if (!this.id2field.containsKey(field.getContentId())) {
        addField(field);
      }
    }
    this.name2class.put(name, contentClass);
    this.id2class.put(id, contentClass);
    Class javaClass = contentClass.getJavaClass();
    if (!this.class2class.containsKey(javaClass)) {
      this.class2class.put(javaClass, contentClass);
    }
    // TODO: sorted list?!
    this.classes.add(contentClass);
  }

  /**
   * This method {@link #addClass(AbstractDataClass) registers} the given
   * <code>contentClass</code> recursive. Here recursive means that all
   * {@link net.sf.mmm.data.api.reflection.DataClass#getSubClasses()
   * sub-classes} are also {@link #addClassRecursive(AbstractDataClass)
   * registered} recursively.
   * 
   * @param contentClass is the class to add.
   * @throws DataReflectionException if the class or one of its sub-classes
   *         could NOT be registered.
   */
  protected void addClassRecursive(AbstractDataClass contentClass) throws DataReflectionException {

    addClass(contentClass);
    for (AbstractDataClass subClass : contentClass.getSubClasses()) {
      addClassRecursive(subClass);
    }
  }

  /**
   * This method registers the <code>contentField</code> to this service.
   * 
   * @param contentField is the field to add.
   * @throws DataReflectionException if a field with the same ID is already
   *         registered.
   */
  protected void addField(AbstractDataField contentField) throws DataReflectionException {

    DataId id = contentField.getContentId();
    if (this.id2field.containsKey(id)) {
      throw new DuplicateFieldException(id);
    }
    this.id2field.put(id, contentField);
  }

  /**
   * This method removes the given <code>contentClass</code> from the model.<br>
   * <b>WARNING:</b><br>
   * Use this feature with extreme care.
   * 
   * @param contentClass is the class to remove.
   * @throws DataReflectionException if the operation fails (e.g. the class is
   *         required by the system).
   */
  protected void removeClass(AbstractDataClass contentClass) throws DataReflectionException {

    if (contentClass.getModifiers().isSystem()) {
      throw new DataSystemModifyException(contentClass);
    }
    AbstractDataClass old = this.id2class.remove(contentClass.getContentId());
    assert (old == contentClass);
    old = this.name2class.remove(contentClass.getTitle());
    assert (old == contentClass);
    boolean removed = this.classes.remove(contentClass);
    assert (removed);
  }

  /**
   * This method removes the given <code>contentField</code> from the model.<br>
   * <b>WARNING:</b><br>
   * Use this feature with extreme care.
   * 
   * @param contentField is the field to remove.
   * @throws DataReflectionException if the operation fails (e.g. the field is
   *         required by the system).
   */
  protected void removeField(AbstractDataField contentField) throws DataReflectionException {

    if (contentField.getModifiers().isSystem()) {
      throw new DataSystemModifyException(contentField);
    }
  }

  /**
   * @see ContentModelEventSource#fireEvent(DataReflectionEvent)
   * 
   * @param event is the event to send.
   */
  protected void fireEvent(DataReflectionEvent event) {

    this.eventSource.fireEvent(event);
  }

  /**
   * This method synchronizes the given field that has been added or modified.
   * 
   * @param contentClass
   * @param field
   */
  protected void syncField(AbstractDataClass contentClass, AbstractDataField field) {

    DataId id = field.getContentId();
    AbstractDataField existingField = this.id2field.get(id);
    if (existingField == null) {
      contentClass.addField(field);
      addField(field);
    } else {
      // TODO: remove existingField from contentClass and add new field
    }
  }

  /**
   * TODO: javadoc
   * 
   * @param contentClass
   * @param superClass
   */
  protected void syncClassRecursive(AbstractDataClass contentClass, AbstractDataClass superClass) {

    DataId id = contentClass.getContentId();
    AbstractDataClass existingClass = this.id2class.get(id);
    if (existingClass == null) {
      // new content class

      // TODO: verification
      superClass.addSubClass(contentClass);
      addClass(contentClass);
      fireEvent(new DataReflectionEvent(contentClass, ChangeEventType.ADD));
    } else {
      DataClassModifiers existingModifiers = existingClass.getModifiers();
      boolean modified = false;
      if (!existingClass.getTitle().equals(contentClass.getTitle())) {
        // Class has been renamed!
        throw new ContentModelFeatureUnsupportedException(
            "Changing the name of a content-class is currently NOT supported!");
      }
      if (existingClass.getJavaClass() != contentClass.getJavaClass()) {
        // Java-Class has changed!
        throw new ContentModelFeatureUnsupportedException(
            "Changing the java-class of a content-class is currently NOT supported!");
      }
      if (!existingModifiers.equals(contentClass.getModifiers())) {
        if (existingModifiers.isSystem()) {
          // TODO: NLS
          throw new DataReflectionException("Changing modifiers of system class is NOT permitted!");
        }
        // TODO:
        throw new ContentModelFeatureUnsupportedException(
            "Changing the modifiers of a content-class is currently NOT supported!");
      }
      for (AbstractDataField field : contentClass.getDeclaredFields()) {
        syncField(contentClass, field);
      }
      for (AbstractDataClass subClass : contentClass.getSubClasses()) {
        syncClassRecursive(subClass, contentClass);
      }
      if (modified) {
        fireEvent(new DataReflectionEvent(contentClass, ChangeEventType.UPDATE));
      }
    }

  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass createNewClass(DataId id, String name, AbstractDataClass superClass,
      DataClassModifiers modifiers, Class<? extends DataObject> javaClass, boolean deleted) {

    AbstractDataClass newClass = createNewClass(id, name);
    newClass.setSuperClass(superClass);
    newClass.setModifiers(modifiers);
    newClass.setJavaClass(javaClass);
    newClass.setDeletedFlag(deleted);
    return newClass;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataField createNewField(DataId id, String name, DataClass declaringClass,
      Type type, DataFieldModifiers modifiers, boolean deleted) {

    AbstractDataField newField = createNewField(id, name);
    newField.setDeclaringClass((AbstractDataClass) declaringClass);
    newField.setFieldTypeAndClass(type);
    newField.setModifiers(modifiers);
    return newField;
  }

  /**
   * @see AbstractDataReflectionService#getEventRegistrar()
   */
  private static class ContentModelEventSource extends
      AbstractSynchronizedEventSource<DataReflectionEvent, EventListener<DataReflectionEvent>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireEvent(DataReflectionEvent event) {

      super.fireEvent(event);
    }

  }

}
