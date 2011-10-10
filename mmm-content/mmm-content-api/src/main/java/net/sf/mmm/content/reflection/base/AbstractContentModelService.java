/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.base;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.mmm.content.api.ContentIdManager;
import net.sf.mmm.content.api.ContentModelSystemModifyException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.api.ContentClassModifiers;
import net.sf.mmm.content.reflection.api.ContentFieldModifiers;
import net.sf.mmm.content.reflection.api.ContentReflectionEvent;
import net.sf.mmm.content.reflection.api.ContentReflectionException;
import net.sf.mmm.content.reflection.api.ContentReflectionService;
import net.sf.mmm.content.reflection.api.access.ContentClassReadAccessByInstance;
import net.sf.mmm.util.event.api.EventListener;
import net.sf.mmm.util.event.api.EventSource;
import net.sf.mmm.util.event.base.AbstractSynchronizedEventSource;

/**
 * This is the abstract base implementation of the
 * {@link ContentReflectionService} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractContentModelService implements ContentReflectionService {

  /** @see #getContentClass(String) */
  private final Map<String, AbstractContentClass> name2class;

  /** @see #getContentClass(ContentId) */
  private final Map<ContentId, AbstractContentClass> id2class;

  /** @see #getContentClass(Class) */
  private final Map<Class, AbstractContentClass> class2class;

  /** @see #getContentClasses() */
  private final List<AbstractContentClass<? extends ContentObject>> classes;

  /** @see #getContentClasses() */
  private final List<AbstractContentClass<? extends ContentObject>> classesView;

  /** @see #getContentField(ContentId) */
  private final Map<ContentId, AbstractContentField> id2field;

  /** @see #getRootContentClass() */
  private AbstractContentClass rootClass;

  /** @see #getIdManager() */
  private ContentIdManager idManager;

  /** @see #getEventRegistrar() */
  private final ContentModelEventSource eventSource;

  /**
   * The constructor.
   */
  public AbstractContentModelService() {

    super();
    // TODO: use concurrent map?
    this.name2class = new HashMap<String, AbstractContentClass>();
    this.id2class = new HashMap<ContentId, AbstractContentClass>();
    this.class2class = new HashMap<Class, AbstractContentClass>();
    this.classes = new ArrayList<AbstractContentClass<? extends ContentObject>>();
    this.classesView = Collections.unmodifiableList(this.classes);
    this.id2field = new HashMap<ContentId, AbstractContentField>();
    // AbstractContentObject.setClassAccess(this);
    this.eventSource = new ContentModelEventSource();
  }

  /**
   * {@inheritDoc}
   */
  public ContentIdManager getIdManager() {

    return this.idManager;
  }

  /**
   * @param idManager the idManager to set
   */
  @Resource
  public void setIdManager(ContentIdManager idManager) {

    this.idManager = idManager;
  }

  /**
   * {@inheritDoc}
   */
  public EventSource<ContentReflectionEvent, EventListener<ContentReflectionEvent>> getEventRegistrar() {

    return this.eventSource;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass getContentClass(String name) {

    return this.name2class.get(name);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass<? extends ContentObject> getContentClass(ContentId id) {

    return this.id2class.get(id);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass getContentClass(Class<? extends ContentObject> javaClass) {

    return this.class2class.get(javaClass);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass getContentClass(AbstractContentObject contentObject) {

    ContentId id = contentObject.getContentId();
    if (id == null) {
      return getContentClass(contentObject.getClass());
    } else {
      return getContentClass(id.getContentClassId());
    }
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentField getContentField(ContentId id) {

    return this.id2field.get(id);
  }

  /**
   * {@inheritDoc}
   */
  public List<AbstractContentClass<? extends ContentObject>> getContentClasses() {

    return this.classesView;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass getRootContentClass() {

    return this.rootClass;
  }

  /**
   * This method set the {@link #getRootContentClass() root-class}.
   * 
   * @param rootClass is the root-class to set.
   */
  protected void setRootClass(AbstractContentClass rootClass) {

    this.rootClass = rootClass;
    setContentObjectClassAccess(this.rootClass, this);
  }

  /**
   * This method registers the given <code>contentClass</code> to this service.<br>
   * It does NOT {@link #fireEvent(ContentReflectionEvent) fire} the according
   * event.
   * 
   * @param contentClass is the class to add.
   * @throws ContentReflectionException if the class is already registered.
   */
  protected void addClass(AbstractContentClass contentClass) throws ContentReflectionException {

    ContentId id = contentClass.getContentId();
    AbstractContentClass duplicate = this.id2class.get(id);
    if (duplicate != null) {
      throw new DuplicateClassException(contentClass, duplicate);
    }
    String name = contentClass.getTitle();
    duplicate = this.name2class.get(name);
    if (duplicate != null) {
      throw new DuplicateClassException(contentClass, duplicate);
    }
    for (AbstractContentField field : contentClass.getDeclaredFields()) {
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
   * This method {@link #addClass(AbstractContentClass) registers} the given
   * <code>contentClass</code> recursive. Here recursive means that all
   * {@link net.sf.mmm.content.reflection.api.ContentClass#getSubClasses()
   * sub-classes} are also {@link #addClassRecursive(AbstractContentClass)
   * registered} recursively.
   * 
   * @param contentClass is the class to add.
   * @throws ContentReflectionException if the class or one of its sub-classes
   *         could NOT be registered.
   */
  protected void addClassRecursive(AbstractContentClass contentClass)
      throws ContentReflectionException {

    addClass(contentClass);
    for (AbstractContentClass subClass : contentClass.getSubClasses()) {
      addClassRecursive(subClass);
    }
  }

  /**
   * This method registers the <code>contentField</code> to this service.
   * 
   * @param contentField is the field to add.
   * @throws ContentReflectionException if a field with the same ID is already
   *         registered.
   */
  protected void addField(AbstractContentField contentField) throws ContentReflectionException {

    ContentId id = contentField.getContentId();
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
   * @throws ContentReflectionException if the operation fails (e.g. the class
   *         is required by the system).
   */
  protected void removeClass(AbstractContentClass contentClass) throws ContentReflectionException {

    if (contentClass.getContentModifiers().isSystem()) {
      throw new ContentModelSystemModifyException(contentClass);
    }
    AbstractContentClass old = this.id2class.remove(contentClass.getContentId());
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
   * @throws ContentReflectionException if the operation fails (e.g. the field
   *         is required by the system).
   */
  protected void removeField(AbstractContentField contentField) throws ContentReflectionException {

    if (contentField.getContentModifiers().isSystem()) {
      throw new ContentModelSystemModifyException(contentField);
    }
  }

  /**
   * @see ContentModelEventSource#fireEvent(ContentReflectionEvent)
   * 
   * @param event is the event to send.
   */
  protected void fireEvent(ContentReflectionEvent event) {

    this.eventSource.fireEvent(event);
  }

  /**
   * This method synchronizes the given field that has been added or modified.
   * 
   * @param contentClass
   * @param field
   */
  protected void syncField(AbstractContentClass contentClass, AbstractContentField field) {

    ContentId id = field.getContentId();
    AbstractContentField existingField = this.id2field.get(id);
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
  protected void syncClassRecursive(AbstractContentClass contentClass,
      AbstractContentClass superClass) {

    ContentId id = contentClass.getContentId();
    AbstractContentClass existingClass = this.id2class.get(id);
    if (existingClass == null) {
      // new content class

      // TODO: verification
      superClass.addSubClass(contentClass);
      addClass(contentClass);
      fireEvent(new ContentReflectionEvent(contentClass, ChangeEventType.ADD));
    } else {
      ContentClassModifiers existingModifiers = existingClass.getContentModifiers();
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
      if (!existingModifiers.equals(contentClass.getContentModifiers())) {
        if (existingModifiers.isSystem()) {
          // TODO: NLS
          throw new ContentReflectionException(
              "Changing modifiers of system class is NOT permitted!");
        }
        // TODO:
        throw new ContentModelFeatureUnsupportedException(
            "Changing the modifiers of a content-class is currently NOT supported!");
      }
      for (AbstractContentField field : contentClass.getDeclaredFields()) {
        syncField(contentClass, field);
      }
      for (AbstractContentClass subClass : contentClass.getSubClasses()) {
        syncClassRecursive(subClass, contentClass);
      }
      if (modified) {
        fireEvent(new ContentReflectionEvent(contentClass, ChangeEventType.UPDATE));
      }
    }

  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass createNewClass(ContentId id, String name,
      AbstractContentClass superClass, ContentClassModifiers modifiers,
      Class<? extends ContentObject> javaClass, boolean deleted) {

    AbstractContentClass newClass = createNewClass(id, name);
    newClass.setSuperClass(superClass);
    newClass.setModifiers(modifiers);
    newClass.setJavaClass(javaClass);
    newClass.setDeletedFlag(deleted);
    return newClass;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentField createNewField(ContentId id, String name,
      ContentClass declaringClass, Type type, ContentFieldModifiers modifiers, boolean deleted) {

    AbstractContentField newField = createNewField(id, name);
    newField.setDeclaringClass((AbstractContentClass) declaringClass);
    newField.setFieldTypeAndClass(type);
    newField.setModifiers(modifiers);
    return newField;
  }

  /**
   * @see AbstractContentModelService#getEventRegistrar()
   */
  private static class ContentModelEventSource
      extends
      AbstractSynchronizedEventSource<ContentReflectionEvent, EventListener<ContentReflectionEvent>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireEvent(ContentReflectionEvent event) {

      super.fireEvent(event);
    }

  }

}
