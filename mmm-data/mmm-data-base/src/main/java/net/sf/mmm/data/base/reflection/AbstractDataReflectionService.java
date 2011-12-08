/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.data.api.DataIdManager;
import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.api.reflection.DataReflectionEvent;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.api.reflection.DataReflectionService;
import net.sf.mmm.data.api.reflection.DataSystemModifyException;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.event.api.ChangeType;
import net.sf.mmm.util.event.api.EventListener;
import net.sf.mmm.util.event.api.EventSource;
import net.sf.mmm.util.event.base.AbstractSynchronizedEventSource;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the abstract base implementation of the {@link DataReflectionService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDataReflectionService extends AbstractLoggableComponent implements
    DataReflectionService {

  /** @see #getDataClass(String) */
  private final Map<String, AbstractDataClass<? extends DataObjectView>> name2class;

  /** @see #getDataClass(long) */
  private final Map<Long, AbstractDataClass<? extends DataObjectView>> id2class;

  /** @see #getDataClass(Class) */
  private final Map<Class<? extends DataObjectView>, AbstractDataClass<? extends DataObjectView>> class2class;

  /** @see #getDataClasses() */
  private final List<AbstractDataClass<? extends DataObjectView>> classes;

  /** @see #getDataClasses() */
  private final List<AbstractDataClass<? extends DataObjectView>> classesView;

  /** @see #getDataField(long) */
  private final Map<Long, AbstractDataField<? extends DataObjectView, ?>> id2field;

  /** @see #getRootDataClass() */
  private AbstractDataClass<? extends DataObjectView> rootClass;

  /** @see #getEventSource() */
  private final ContentModelEventSource eventSource;

  /** @see #getDataIdManager() */
  private DataIdManager dataIdManager;

  /**
   * The constructor.
   */
  public AbstractDataReflectionService() {

    super();
    // TODO: use concurrent map?
    this.name2class = new HashMap<String, AbstractDataClass<? extends DataObjectView>>();
    this.id2class = new HashMap<Long, AbstractDataClass<? extends DataObjectView>>();
    this.class2class = new HashMap<Class<? extends DataObjectView>, AbstractDataClass<? extends DataObjectView>>();
    this.classes = new ArrayList<AbstractDataClass<? extends DataObjectView>>();
    this.classesView = Collections.unmodifiableList(this.classes);
    this.id2field = new HashMap<Long, AbstractDataField<? extends DataObjectView, ?>>();
    // AbstractContentObject.setClassAccess(this);
    this.eventSource = new ContentModelEventSource();
  }

  /**
   * @return the dataIdManager
   */
  public DataIdManager getDataIdManager() {

    return this.dataIdManager;
  }

  /**
   * @param dataIdManager is the dataIdManager to set
   */
  public void setDataIdManager(DataIdManager dataIdManager) {

    getInitializationState().requireNotInitilized();
    this.dataIdManager = dataIdManager;
  }

  /**
   * {@inheritDoc}
   */
  public DataId getDataId(DataObjectView dataObject) {

    NlsNullPointerException.checkNotNull(DataObjectView.class, dataObject);
    Long objectId = dataObject.getId();
    if (objectId == null) {
      return null;
    }
    AbstractDataObject abstractDataObject = (AbstractDataObject) dataObject;
    long classId = abstractDataObject.getDataClassId();
    Number revision = dataObject.getRevision();
    if (revision == null) {
      return getDataIdManager().getId(objectId.longValue(), classId);
    } else {
      return getDataIdManager().getId(objectId.longValue(), classId, revision.intValue());
    }
  }

  /**
   * @see net.sf.mmm.data.api.reflection.MutableDataReflectionService#getEventSource()
   * @return the {@link EventSource}.
   */
  public EventSource<DataReflectionEvent<? extends DataObjectView>, EventListener<DataReflectionEvent<? extends DataObjectView>>> getEventSource() {

    return this.eventSource;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass<? extends DataObjectView> getDataClass(String title) {

    AbstractDataClass<? extends DataObjectView> dataClass = this.name2class.get(title);
    if (dataClass == null) {
      throw new ObjectNotFoundException(DataClass.class, title);
    }
    return dataClass;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass<? extends DataObjectView> getDataClass(DataId id) {

    return getDataClass(id.getObjectId());
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass<? extends DataObjectView> getDataClass(long id) {

    AbstractDataClass<? extends DataObjectView> dataClass = this.id2class.get(Long.valueOf(id));
    if (dataClass == null) {
      throw new ObjectNotFoundException(DataClass.class, Long.valueOf(id));
    }
    return dataClass;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <O extends DataObjectView> AbstractDataClass<O> getDataClass(O dataObject) {

    NlsNullPointerException.checkNotNull(DataObjectView.class, dataObject);
    AbstractDataObject abstractDataObject = (AbstractDataObject) dataObject;
    return (AbstractDataClass<O>) getDataClass(abstractDataObject.getDataClassId());
  }

  /**
   * {@inheritDoc}
   */
  public <CLASS extends DataObjectView> AbstractDataClass<CLASS> getDataClass(Class<CLASS> javaClass)
      throws ObjectNotFoundException {

    return (AbstractDataClass<CLASS>) this.class2class.get(javaClass);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataField<? extends DataObjectView, ?> getDataField(DataId id) {

    NlsNullPointerException.checkNotNull(DataId.class, id);
    if (id.getClassId() != DataField.CLASS_ID) {
      throw new ObjectMismatchException(Long.valueOf(id.getClassId()),
          Long.valueOf(DataField.CLASS_ID), id);
    }
    return getDataField(id.getObjectId());
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataField<? extends DataObjectView, ?> getDataField(long id)
      throws ObjectNotFoundException {

    AbstractDataField<? extends DataObjectView, ?> field = this.id2field.get(Long.valueOf(id));
    if (field == null) {
      throw new ObjectNotFoundException(DataField.class, Long.valueOf(id));
    }
    return field;
  }

  /**
   * {@inheritDoc}
   */
  public List<AbstractDataClass<? extends DataObjectView>> getDataClasses() {

    return this.classesView;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass<? extends DataObjectView> getRootDataClass() {

    return this.rootClass;
  }

  /**
   * This method set the {@link #getRootDataClass() root-class}.
   * 
   * @param rootClass is the root-class to set.
   */
  protected void setRootClass(AbstractDataClass<? extends DataObjectView> rootClass) {

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
  protected void addClass(AbstractDataClass<? extends DataObjectView> contentClass)
      throws DataReflectionException {

    Long id = contentClass.getId();
    AbstractDataClass<? extends DataObjectView> duplicate = this.id2class.get(id);
    if (duplicate != null) {
      throw new DuplicateObjectException(contentClass, duplicate);
    }
    String name = contentClass.getTitle();
    duplicate = this.name2class.get(name);
    if (duplicate != null) {
      throw new DuplicateObjectException(contentClass, name, duplicate);
    }
    for (AbstractDataField<? extends DataObjectView, ?> field : contentClass.getDeclaredFields()) {
      if (!this.id2field.containsKey(field.getId())) {
        addField(field);
      }
    }
    this.name2class.put(name, contentClass);
    this.id2class.put(id, contentClass);
    Class<? extends DataObjectView> javaClass = contentClass.getJavaClass();
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
   * @param dataClass is the class to add.
   * @throws DataReflectionException if the class or one of its sub-classes
   *         could NOT be registered.
   */
  protected void addClassRecursive(AbstractDataClass<? extends DataObjectView> dataClass)
      throws DataReflectionException {

    addClass(dataClass);
    for (AbstractDataClass<? extends DataObjectView> subClass : dataClass.getSubClasses()) {
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
  protected void addField(AbstractDataField<? extends DataObjectView, ?> contentField)
      throws DataReflectionException {

    Long id = contentField.getId();
    AbstractDataField<? extends DataObjectView, ?> duplicate = this.id2field.get(id);
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
   * @param dataClass is the class to remove.
   * @throws DataReflectionException if the operation fails (e.g. the class is
   *         required by the system).
   */
  protected void removeClass(AbstractDataClass<? extends DataObjectView> dataClass)
      throws DataReflectionException {

    if (dataClass.getModifiers().isSystem()) {
      throw new DataSystemModifyException(dataClass);
    }
    AbstractDataClass<? extends DataObjectView> old = this.id2class.remove(dataClass.getId());
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
  protected void removeField(AbstractDataField<? extends DataObjectView, ?> contentField)
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
  protected void fireEvent(DataReflectionEvent<? extends DataObjectView> event) {

    this.eventSource.fireEvent(event);
  }

  /**
   * This method synchronizes the given field that has been added or modified.
   * 
   * @param <CLASS> is the generic type of the {@link DataClass#getJavaClass()}.
   * @param dataClass is the {@link DataField#getDeclaringClass() declaring
   *        class}.
   * @param dataField is the {@link DataField} to synchronize.
   */
  protected <CLASS extends DataObjectView> void syncField(AbstractDataClass<CLASS> dataClass,
      AbstractDataField<CLASS, ?> dataField) {

    Long id = dataField.getId();
    AbstractDataField<? extends DataObjectView, ?> existingField = this.id2field.get(id);
    if (existingField == null) {
      dataClass.addDeclaredField(dataField);
      addField(dataField);
    } else {
      // TODO: remove existingField from contentClass and add new field
    }
  }

  /**
   * This method synchronizes the given class that has been added or modified.
   * 
   * @param <SUPERCLASS> is the generic type of {@link DataClass#getJavaClass()}
   *        for the given <code>superClass</code>.
   * @param <CLASS> is the generic type of {@link DataClass#getJavaClass()} for
   *        the given <code>dataClass</code>.
   * @param dataClass is the {@link DataClass} that has changed.
   * @param superClass is the {@link DataClass#getSuperClass() super class}.
   */
  protected <SUPERCLASS extends DataObjectView, CLASS extends SUPERCLASS> void syncClassRecursive(
      AbstractDataClass<CLASS> dataClass, AbstractDataClass<SUPERCLASS> superClass) {

    Long id = dataClass.getId();
    AbstractDataClass<? extends DataObjectView> existingClass = this.id2class.get(id);
    if (existingClass == null) {
      // new content class

      // TODO: verification
      superClass.addSubClass(dataClass);
      addClass(dataClass);
      fireEvent(new DataReflectionEvent<CLASS>(dataClass, ChangeType.ADD));
    } else {
      DataClassModifiers existingModifiers = existingClass.getModifiers();
      boolean modified = false;
      if (!existingClass.getTitle().equals(dataClass.getTitle())) {
        // TODO I18N
        throw new UnsupportedOperationException(
            "Changing the name of a content-class is currently NOT supported!");
      }
      if (existingClass.getJavaClass() != dataClass.getJavaClass()) {
        // TODO I18N
        throw new UnsupportedOperationException(
            "Changing the java-class of a content-class is currently NOT supported!");
      }
      if (!existingModifiers.equals(dataClass.getModifiers())) {
        if (existingModifiers.isSystem()) {
          // TODO: NLS
          throw new DataReflectionException("Changing modifiers of system class is NOT permitted!");
        }
        // TODO I18N
        throw new UnsupportedOperationException(
            "Changing the modifiers of a content-class is currently NOT supported!");
      }
      for (AbstractDataField<CLASS, ?> field : dataClass.getDeclaredFields()) {
        syncField(dataClass, field);
      }
      for (AbstractDataClass<? extends CLASS> subClass : dataClass.getSubClasses()) {
        syncClassRecursive(subClass, dataClass);
      }
      if (modified) {
        fireEvent(new DataReflectionEvent<CLASS>(dataClass, ChangeType.UPDATE));
      }
    }

  }

  /**
   * This method creates a new instance of {@link AbstractDataClass}.
   * 
   * @param <CLASS> is the generic type of {@link DataClass#getJavaClass()}.
   * @return the new, uninitialized instance.
   */
  protected abstract <CLASS extends DataObjectView> AbstractDataClass<CLASS> createDataClass();

  /**
   * This method instantiates a {@link DataClass}.
   * 
   * @param <CLASS> is the generic type of the <code>javaClass</code>.
   * @param id is the unique {@link DataClass#getId() ID} of the new class. May
   *        be <code>null</code>.
   * @param title is the {@link DataClass#getTitle() title} of the new class.
   * @param superClass is the {@link DataClass#getSuperClass() super class} of
   *        the new class.
   * @param modifiers are the {@link DataClass#getModifiers() modifiers} of the
   *        new class.
   * @param javaClass is the {@link DataClass#getJavaClass() java class} of the
   *        new class.
   * @param deleted is the {@link DataClass#isDeleted() deleted flag} of the new
   *        class.
   * @return the created class.
   */
  public <CLASS extends DataObjectView> AbstractDataClass<CLASS> createDataClass(Long id, String title,
      AbstractDataClass<? extends DataObjectView> superClass, DataClassModifiers modifiers,
      Class<CLASS> javaClass, boolean deleted) {

    AbstractDataClass<CLASS> newClass = createDataClass();
    newClass.setId(id);
    newClass.setSuperClass(superClass);
    newClass.setModifiers(modifiers);
    newClass.setJavaClass(javaClass);
    newClass.setDeletedFlag(deleted);
    return newClass;
  }

  /**
   * This method creates a new instance of {@link AbstractDataField}.
   * 
   * @param <CLASS> is the generic type of {@link DataClass#getJavaClass()}.
   * @param <FIELD> is the generic type of {@link DataField#getFieldType()}.
   * @return the new, uninitialized instance.
   */
  protected abstract <CLASS extends DataObjectView, FIELD> AbstractDataField<CLASS, FIELD> createDataField();

  /**
   * This method creates a new {@link DataField}.
   * 
   * @param <CLASS> is the generic type of {@link DataClass#getJavaClass()}.
   * @param <FIELD> is the generic type of {@link DataField#getFieldType()}.
   * @param id is the unique {@link DataField#getId() ID} of the new field. May
   *        be <code>null</code>.
   * @param title is the {@link DataField#getTitle() title} of the new field.
   * @param declaringClass is the {@link DataClass}
   *        {@link DataField#getDeclaringClass() declaring} the new field.
   * @param fieldType is the {@link DataField#getFieldType()} of the new field.
   * @param modifiers are the {@link DataField#getModifiers() modifiers} of the
   *        new field.
   * @param isDeleted is the {@link DataField#getDeletedFlag() deleted flag}.
   * @return the new {@link DataField}.
   */
  public <CLASS extends DataObjectView, FIELD> AbstractDataField<CLASS, FIELD> createDataField(Long id,
      String title, DataClass<CLASS> declaringClass, GenericType<FIELD> fieldType,
      DataFieldModifiers modifiers, boolean isDeleted) {

    AbstractDataField<CLASS, FIELD> newField = createDataField();
    if (id != null) {
      newField.setId(id);
    }
    newField.setTitle(title);
    newField.setDeclaringClass((AbstractDataClass<CLASS>) declaringClass);
    newField.setFieldType(fieldType);
    newField.setModifiers(modifiers);
    newField.setDeletedFlag(isDeleted);
    return newField;
  }

  /**
   * @see AbstractDataReflectionService#getEventSource()
   */
  private static class ContentModelEventSource
      extends
      AbstractSynchronizedEventSource<DataReflectionEvent<? extends DataObjectView>, EventListener<DataReflectionEvent<? extends DataObjectView>>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireEvent(DataReflectionEvent<? extends DataObjectView> event) {

      super.fireEvent(event);
    }

  }

}
