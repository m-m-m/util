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
import javax.inject.Inject;

import net.sf.mmm.data.api.DataIdManager;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.api.reflection.DataReflectionEvent;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.api.reflection.DataReflectionService;
import net.sf.mmm.data.api.reflection.DataSystemModifyException;
import net.sf.mmm.data.api.reflection.access.DataClassReadAccessByInstance;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.util.event.api.ChangeType;
import net.sf.mmm.util.event.api.EventListener;
import net.sf.mmm.util.event.api.EventSource;
import net.sf.mmm.util.event.base.AbstractSynchronizedEventSource;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of the {@link DataReflectionService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDataReflectionService implements DataReflectionService {

  /** @see #getDataClass(String) */
  private final Map<String, AbstractDataClass<? extends DataObject>> name2class;

  /** @see #getContentClass(DataId) */
  private final Map<Long, AbstractDataClass<? extends DataObject>> id2class;

  /** @see #getContentClass(Class) */
  private final Map<Class<? extends DataObject>, AbstractDataClass<? extends DataObject>> class2class;

  /** @see #getDataClasses() */
  private final List<AbstractDataClass<? extends DataObject>> classes;

  /** @see #getDataClasses() */
  private final List<AbstractDataClass<? extends DataObject>> classesView;

  /** @see #getField(DataId) */
  private final Map<Long, AbstractDataField<? extends DataObject, ?>> id2field;

  /** @see #getRootDataClass() */
  private AbstractDataClass<? extends DataObject> rootClass;

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
    this.name2class = new HashMap<String, AbstractDataClass<? extends DataObject>>();
    this.id2class = new HashMap<Long, AbstractDataClass<? extends DataObject>>();
    this.class2class = new HashMap<Class<? extends DataObject>, AbstractDataClass<? extends DataObject>>();
    this.classes = new ArrayList<AbstractDataClass<? extends DataObject>>();
    this.classesView = Collections.unmodifiableList(this.classes);
    this.id2field = new HashMap<Long, AbstractDataField<? extends DataObject, ?>>();
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
  @Inject
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
  public AbstractDataClass<? extends DataObject> getDataClass(String title) {

    AbstractDataClass<? extends DataObject> dataClass = this.name2class.get(title);
    if (dataClass == null) {
      throw new ObjectNotFoundException(DataClass.class, title);
    }
    return dataClass;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass<? extends DataObject> getDataClass(DataId id) {

    return getDataClass(id.getObjectId());
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass<? extends DataObject> getDataClass(long id) {

    AbstractDataClass<? extends DataObject> dataClass = this.id2class.get(Long.valueOf(id));
    if (dataClass == null) {
      throw new ObjectNotFoundException(DataClass.class, id);
    }
    return dataClass;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <O extends DataObject> AbstractDataClass<O> getDataClass(O dataObject) {

    NlsNullPointerException.checkNotNull(DataObject.class, dataObject);
    AbstractDataObject abstractDataObject = (AbstractDataObject) dataObject;
    return (AbstractDataClass<O>) getDataClass(abstractDataObject.getDataClassId());
  }

  /**
   * {@inheritDoc}
   */
  public <CLASS extends DataObject> AbstractDataClass<CLASS> getDataClass(Class<CLASS> javaClass)
      throws ObjectNotFoundException {

    return (AbstractDataClass<CLASS>) this.class2class.get(javaClass);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataField<? extends DataObject, ?> getDataField(DataId id) {

    NlsNullPointerException.checkNotNull(DataId.class, id);
    if (id.getClassId() != DataField.CLASS_ID) {
      // TODO: dedicated exception
      throw new NlsIllegalArgumentException(id);
    }
    return this.id2field.get(Long.valueOf(id.getObjectId()));
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
  public AbstractDataClass<? extends DataObject> getRootDataClass() {

    return this.rootClass;
  }

  /**
   * This method set the {@link #getRootDataClass() root-class}.
   * 
   * @param rootClass is the root-class to set.
   */
  protected void setRootClass(AbstractDataClass<? extends DataObject> rootClass) {

    this.rootClass = rootClass;
  }

  /**
   * This method registers the given <code>contentClass</code> to this service.<br>
   * It does NOT {@link #fireEvent(DataReflectionEvent) fire} the according
   * event.
   * 
   * @param contentClass is the class to add.
   * @throws DataReflectionException if the class is already registered.
   */
  protected void addClass(AbstractDataClass<? extends DataObject> contentClass)
      throws DataReflectionException {

    Long id = contentClass.getId();
    AbstractDataClass<? extends DataObject> duplicate = this.id2class.get(id);
    if (duplicate != null) {
      throw new DuplicateObjectException(contentClass, duplicate);
    }
    String name = contentClass.getTitle();
    duplicate = this.name2class.get(name);
    if (duplicate != null) {
      throw new DuplicateObjectException(contentClass, name, duplicate);
    }
    for (AbstractDataField<? extends DataObject, ?> field : contentClass.getDeclaredFields()) {
      if (!this.id2field.containsKey(field.getId())) {
        addField(field);
      }
    }
    this.name2class.put(name, contentClass);
    this.id2class.put(id, contentClass);
    Class<? extends DataObject> javaClass = contentClass.getJavaClass();
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
  protected void addClassRecursive(AbstractDataClass<? extends DataObject> contentClass)
      throws DataReflectionException {

    addClass(contentClass);
    for (AbstractDataClass<? extends DataObject> subClass : contentClass.getSubClasses()) {
      addClassRecursive(subClass);
    }
  }

  /**
   * This method registers the <code>dataField</code> to this service.
   * 
   * @param contentField is the field to add.
   * @throws DataReflectionException if a field with the same ID is already
   *         registered.
   */
  protected void addField(AbstractDataField<? extends DataObject, ?> contentField)
      throws DataReflectionException {

    Long id = contentField.getId();
    AbstractDataField<? extends DataObject, ?> duplicate = this.id2field.get(id);
    if (duplicate == null) {
      throw new DuplicateObjectException(contentField, id, duplicate);
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
  protected void removeClass(AbstractDataClass<? extends DataObject> dataClass)
      throws DataReflectionException {

    if (dataClass.getModifiers().isSystem()) {
      throw new DataSystemModifyException(dataClass);
    }
    AbstractDataClass<? extends DataObject> old = this.id2class.remove(dataClass.getId());
    assert (old == dataClass);
    old = this.name2class.remove(dataClass.getTitle());
    assert (old == dataClass);
    old = this.class2class.get(dataClass.getJavaClass());
    if (old == dataClass) {
      this.class2class.remove(dataClass.getJavaClass());
    }
    boolean removed = this.classes.remove(dataClass);
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
  protected void removeField(AbstractDataField<? extends DataObject, ?> contentField)
      throws DataReflectionException {

    if (contentField.getModifiers().isSystem()) {
      throw new DataSystemModifyException(contentField);
    }
    // TODO
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

    Long id = field.getId();
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
  protected void syncClassRecursive(AbstractDataClass<? extends DataObject> contentClass,
      AbstractDataClass superClass) {

    Long id = contentClass.getId();
    AbstractDataClass existingClass = this.id2class.get(id);
    if (existingClass == null) {
      // new content class

      // TODO: verification
      superClass.addSubClass(contentClass);
      addClass(contentClass);
      fireEvent(new DataReflectionEvent(contentClass, ChangeType.ADD));
    } else {
      DataClassModifiers existingModifiers = existingClass.getModifiers();
      boolean modified = false;
      if (!existingClass.getTitle().equals(contentClass.getTitle())) {
        // TODO I18N
        throw new UnsupportedOperationException(
            "Changing the name of a content-class is currently NOT supported!");
      }
      if (existingClass.getJavaClass() != contentClass.getJavaClass()) {
        // TODO I18N
        throw new UnsupportedOperationException(
            "Changing the java-class of a content-class is currently NOT supported!");
      }
      if (!existingModifiers.equals(contentClass.getModifiers())) {
        if (existingModifiers.isSystem()) {
          // TODO: NLS
          throw new DataReflectionException("Changing modifiers of system class is NOT permitted!");
        }
        // TODO I18N
        throw new UnsupportedOperationException(
            "Changing the modifiers of a content-class is currently NOT supported!");
      }
      for (AbstractDataField<? extends DataObject, ?> field : contentClass.getDeclaredFields()) {
        syncField(contentClass, field);
      }
      for (AbstractDataClass subClass : contentClass.getSubClasses()) {
        syncClassRecursive(subClass, contentClass);
      }
      if (modified) {
        fireEvent(new DataReflectionEvent(contentClass, ChangeType.UPDATE));
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
