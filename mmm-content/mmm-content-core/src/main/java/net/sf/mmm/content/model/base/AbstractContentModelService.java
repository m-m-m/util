/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentModelEvent;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.ContentModelFeatureUnsupportedException;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.base.DuplicateClassException;
import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.base.SmartIdManager;
import net.sf.mmm.util.event.AbstractSynchronizedEventSource;
import net.sf.mmm.util.event.ChangeEvent;
import net.sf.mmm.util.event.EventListener;
import net.sf.mmm.util.event.EventSource;

/**
 * This is the abstract base implementation of the {@link ContentModelService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentModelService extends
    AbstractSynchronizedEventSource<ContentModelEvent, EventListener<ContentModelEvent>> implements
    ContentModelService, ContentReflectionFactory {

  /** @see #getContentClass(String) */
  private final Map<String, AbstractContentClass> name2class;

  /** @see #getContentClass(ContentId) */
  private final Map<ContentId, AbstractContentClass> id2class;

  /** @see #getContentClasses() */
  private final List<AbstractContentClass> classes;

  /** @see #getContentClasses() */
  private final List<AbstractContentClass> classesView;

  /** @see #getContentField(ContentId) */
  private final Map<ContentId, AbstractContentField> id2field;

  /** @see #getRootContentClass() */
  private AbstractContentClass rootClass;

  /** @see #getIdManager() */
  private SmartIdManager idManager;

  /**
   * The constructor.
   */
  public AbstractContentModelService() {

    super();
    // TODO: use concurrent map?
    this.name2class = new HashMap<String, AbstractContentClass>();
    this.id2class = new HashMap<ContentId, AbstractContentClass>();
    this.classes = new ArrayList<AbstractContentClass>();
    this.classesView = Collections.unmodifiableList(this.classes);
    this.id2field = new HashMap<ContentId, AbstractContentField>();
  }

  /**
   * {@inheritDoc}
   */
  public SmartIdManager getIdManager() {

    return this.idManager;
  }

  /**
   * @param idManager the idManager to set
   */
  @Resource
  public void setIdManager(SmartIdManager idManager) {

    this.idManager = idManager;
  }

  /**
   * {@inheritDoc}
   */
  public EventSource<ContentModelEvent, EventListener<ContentModelEvent>> getEventRegistrar() {

    return this;
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
  public AbstractContentClass getContentClass(ContentId id) {

    return this.id2class.get(id);
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
  public List<AbstractContentClass> getContentClasses() {

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
  }

  /**
   * This method registers the given <code>contentClass</code> to this
   * service.<br>
   * It does NOT {@link #fireEvent(ContentModelEvent) fire} the according event.
   * 
   * @param contentClass is the class to add.
   * @throws ContentModelException if the class is already registered.
   */
  protected void addClass(AbstractContentClass contentClass) throws ContentModelException {

    ContentId id = contentClass.getId();
    AbstractContentClass duplicate = this.id2class.get(id);
    if (duplicate != null) {
      throw new DuplicateClassException(id);
    }
    String name = contentClass.getName();
    duplicate = this.name2class.get(name);
    if (duplicate != null) {
      throw new DuplicateClassException(name);
    }
    for (AbstractContentField field : contentClass.getDeclaredFields()) {
      // TODO:
      //addField(field);
    }
    this.name2class.put(name, contentClass);
    this.id2class.put(id, contentClass);
    // TODO: sort list!
    this.classes.add(contentClass);
  }

  /**
   * This method {@link #addClass(AbstractContentClass) registers} the given
   * <code>contentClass</code> recursive. Here recursive means that all
   * {@link net.sf.mmm.content.model.api.ContentClass#getSubClasses() sub-classes}
   * are also {@link #addClassRecursive(AbstractContentClass) registered}
   * recursively.
   * 
   * @param contentClass is the class to add.
   * @throws ContentModelException if the class or one of its sub-classes could
   *         NOT be registered.
   */
  protected void addClassRecursive(AbstractContentClass contentClass) throws ContentModelException {

    addClass(contentClass);
    for (AbstractContentClass subClass : contentClass.getSubClasses()) {
      addClassRecursive(subClass);
    }
  }

  /**
   * This method registers the <code>contentField</code> to this service.
   * 
   * @param contentField is the field to add.
   * @throws ContentModelException if a field with the same ID is already
   *         registered.
   */
  protected void addField(AbstractContentField contentField) throws ContentModelException {

    ContentId id = contentField.getId();
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
   * @throws ContentModelException if the operation fails (e.g. the class is
   *         required by the system).
   */
  protected void removeClass(AbstractContentClass contentClass) throws ContentModelException {

    if (contentClass.getModifiers().isSystem()) {
      throw new ContentModelSystemModifyException(contentClass);
    }
    AbstractContentClass old = this.id2class.remove(contentClass.getId());
    assert (old == contentClass);
    old = this.name2class.remove(contentClass.getName());
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
   * @throws ContentModelException if the operation fails (e.g. the field is
   *         required by the system).
   */
  protected void removeField(AbstractContentField contentField) throws ContentModelException {

    if (contentField.getModifiers().isSystem()) {
      throw new ContentModelSystemModifyException(contentField);
    }
  }

  protected void syncClassRecursive(AbstractContentClass contentClass,
      AbstractContentClass superClass) {

    SmartId id = contentClass.getId();
    AbstractContentClass existingClass = this.id2class.get(id);
    if (existingClass == null) {
      // new content class

      // TODO: verification
      superClass.addSubClass(contentClass);
      addClass(contentClass);
      fireEvent(new ContentModelEvent(contentClass, ChangeEvent.Type.ADD));
    } else {
      ClassModifiers existingModifiers = existingClass.getModifiers();
      boolean modified = false;
      if (!existingClass.getName().equals(contentClass.getName())) {
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
          throw new ContentModelException("Changing modifiers of system class is NOT permitted!");
        }
        // TODO:
        throw new ContentModelFeatureUnsupportedException(
            "Changing the modifiers of a content-class is currently NOT supported!");
      }
      for (AbstractContentField field : contentClass.getDeclaredFields()) {
        // syncField();
      }
      for (AbstractContentClass subClass : contentClass.getSubClasses()) {
        syncClassRecursive(subClass, contentClass);
      }
      if (modified) {
        fireEvent(new ContentModelEvent(contentClass, ChangeEvent.Type.UPDATE));
      }
    }

  }

  /**
   * This method creates a new
   * {@link net.sf.mmm.content.model.api.ContentClass class} with the given
   * arguments,
   * {@link AbstractContentModelService#addClass(AbstractContentClass) registers}
   * it to the content-model and
   * {@link AbstractContentClass#addSubClass(AbstractContentClass) adds} it to
   * the <code> super-class</code>. If a class with the given <code>id</code>
   * already exists, no new class is created but the existing one is updated to
   * the values given as arguments. <br>
   * <b>ATTENTION:</b><br>
   * Updating an existing class is a very sensible operation. E.g. you can NOT
   * make a class abstract that already has instances or final if it already has
   * subclasses. Further changing the super-class may NOT be supported by the
   * content-repository.
   * 
   * @param id is the unique
   *        {@link net.sf.mmm.content.model.api.ContentClass#getId() ID} of the
   *        class.
   * @param name is the
   *        {@link net.sf.mmm.content.model.api.ContentClass#getName() name} of
   *        the class.
   * @param superClass is the
   *        {@link net.sf.mmm.content.model.api.ContentClass#getSuperClass() super-class}
   *        of the class. May be <code>null</code> if the root-class is to be
   *        created/updated.
   * @param modifiers are the
   *        {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   *        of the class.
   * @param deleted is the
   *        {@link net.sf.mmm.content.model.api.ContentClass#isDeleted() deleted flag}
   *        of the class.
   * @param javaClass is the associated
   *        {@link net.sf.mmm.content.model.api.ContentClass#getJavaClass() java-class}
   *        or <code>null</code>.
   * @return the new or updated class.
   */
  protected AbstractContentClass createOrUpdateClass(SmartId id, String name,
      AbstractContentClass superClass, ClassModifiers modifiers, boolean deleted,
      Class<? extends ContentObject> javaClass) {

    AbstractContentClass contentClass = getContentClass(id);
    if (getIdManager().getRootClassId().equals(id)) {
      if (superClass != null) {
        // TODO: NLS
        throw new ContentModelException("Root-class can NOT have super-class!");
      }
    } else if (superClass == null) {
      // TODO: NLS
      throw new ContentModelException("Only root-class has NO super-class!");
    }
    boolean create = (contentClass == null);
    if (create) {
      contentClass = createNewClass(id);
    } else {
      boolean isSystem = contentClass.getModifiers().isSystem();
      if (superClass != contentClass.getSuperClass()) {
        // class-hierarchy has been modified...
        if (isSystem) {
          // TODO: NLS
          throw new ContentModelException(
              "Changing class-hierarchy of system class is NOT permitted!");
        }
      }
      if (!contentClass.getName().equals(name)) {
        // TODO:
        throw new ContentModelFeatureUnsupportedException(
            "Changing the name of a content-class is currently NOT supported!");
      }
      if (!modifiers.equals(contentClass.getModifiers())) {
        if (isSystem) {
          // TODO: NLS
          throw new ContentModelException("Changing modifiers of system class is NOT permitted!");
        }
        // TODO:
        throw new ContentModelFeatureUnsupportedException(
            "Changing the modifiers of a content-class is currently NOT supported!");
      }
      if ((deleted != contentClass.isDeleted()) && (isSystem)) {
        // TODO: NLS
        throw new ContentModelException("Changing deleted-flag of system class is NOT permitted!");
      }
    }
    contentClass.setName(name);
    contentClass.setSuperClass(superClass);
    contentClass.setModifiers(modifiers);
    contentClass.setDeletedFlag(deleted);
    if (javaClass != null) {
      contentClass.setJavaClass(javaClass);
    }
    if (create) {
      addClass(contentClass);
    }
    if (superClass != null) {
      superClass.addSubClass(contentClass);
    }
    return contentClass;
  }

  /**
   * This method creates a new
   * {@link net.sf.mmm.content.model.api.ContentField field} with the given
   * arguments,
   * {@link AbstractContentModelService#addField(AbstractContentField) registers}
   * it to the content-model and
   * {@link AbstractContentClass#addField(AbstractContentField) adds} it to the
   * <code>declaringClass</code>. If a field with the given <code>id</code>
   * already exists, no new field is created but the existing one is updated to
   * the values given as arguments. <br>
   * <b>ATTENTION:</b><br>
   * Updating an existing field is a very sensible operation. E.g. you can NOT
   * make a field final if it is already overridden in a subclasses.
   * 
   * @param id is the unique
   *        {@link net.sf.mmm.content.model.api.ContentField#getId() ID} of the
   *        field.
   * @param name is the
   *        {@link net.sf.mmm.content.model.api.ContentField#getName() name} of
   *        the field.
   * @param declaringClass is the class
   *        {@link net.sf.mmm.content.model.api.ContentField#getDeclaringClass() declaring}
   *        the field.
   * @param modifiers are the
   *        {@link net.sf.mmm.content.model.api.ContentField#getModifiers() modifiers}
   *        of the field.
   * @param type is the
   *        {@link net.sf.mmm.content.model.api.ContentField#getFieldType() type}
   *        of the field.
   * @param typeSpecification is the
   *        {@link net.sf.mmm.content.model.api.ContentField#getFieldTypeSpecification() specification}
   *        of the fields type.
   * @param deleted is the
   *        {@link net.sf.mmm.content.model.api.ContentField#isDeleted() deleted flag}
   *        of the field.
   * @return the new or updated field.
   */
  protected AbstractContentField createOrUpdateField(SmartId id, String name,
      AbstractContentClass declaringClass, FieldModifiers modifiers, Type type,
      String typeSpecification, boolean deleted) {

    AbstractContentField contentField = getContentField(id);
    boolean create = (contentField == null);
    if (create) {
      contentField = createNewField(id);
    } else {
      if (!contentField.getName().equals(name)) {
        // TODO
        throw new ContentModelFeatureUnsupportedException(
            "Changing the name of a content-field is currently NOT supported!");
      }
      if (!contentField.getModifiers().equals(modifiers)) {
        // TODO
        throw new ContentModelFeatureUnsupportedException(
            "Changing the modifiers of a content-field is currently NOT supported!");
      }
      if (!contentField.getFieldType().equals(type)) {
        // TODO
        throw new ContentModelFeatureUnsupportedException(
            "Changing the type of a content-field is currently NOT supported!");
      }
    }
    contentField.setName(name);
    contentField.setDeclaringClass(declaringClass);
    contentField.setModifiers(modifiers);
    if (typeSpecification != null) {
      contentField.setFieldTypeSpecification(typeSpecification);
    }
    contentField.setFieldTypeAndClass(type);
    if (create) {
      addField(contentField);
      declaringClass.addField(contentField);
    }
    return contentField;
  }

}
