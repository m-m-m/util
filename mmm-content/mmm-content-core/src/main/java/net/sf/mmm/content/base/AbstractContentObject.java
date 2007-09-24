/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.base;

import java.util.Collections;
import java.util.List;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.FieldNotExistsException;
import net.sf.mmm.content.security.api.PermissionDeniedException;
import net.sf.mmm.content.value.api.Lock;
import net.sf.mmm.content.value.api.MetaDataSet;
import net.sf.mmm.content.value.api.MutableMetaData;
import net.sf.mmm.content.value.api.RevisionHistory;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the implementation of the abstract entity {@link ContentObject}.
 * Based on this implementation each sub-class has to be annotated with
 * {@link ClassAnnotation} in order to be an entity-type.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ClassAnnotation(id = ContentObject.CLASS_ID, name = ContentObject.CLASS_NAME, isExtendable = false, revisionControl = RevisionControl.NO)
public abstract class AbstractContentObject implements ContentObject, MutableMetaData {

  /** @see #getId() */
  private SmartId id;

  /** @see #getName() */
  private String name;

  /** @see #isDeleted() */
  private boolean deletedFlag;

  /** @see #getRevision() */
  private int revision;

  /** @see #getModificationCount() */
  private int modificationCount;

  /** @see #getLock() */
  private Lock lock;

  /** @see #getMetaDataSet() */
  private MetaDataSet metaDataSet;

  /** @see #getContentClass() */
  private ContentClassReadAccessByContentObject classAccess;

  /**
   * The constructor.
   */
  public AbstractContentObject() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name}.
   */
  public AbstractContentObject(String name) {

    super();
    if (name != null) {
      setName(name);
    }
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name}.
   * @param id is the {@link #getId() ID}.
   */
  public AbstractContentObject(String name, SmartId id) {

    this(name);
    if (id != null) {
      setId(id);
    }
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 0)
  public SmartId getId() {

    return this.id;
  }

  /**
   * This method sets the {@link #getId() ID} of this object.<br>
   * <b>ATTENTION:</b><br>
   * This method should only be used internally. To create a new object you have
   * to use the content-repository or if you directly construct a custom entity,
   * you leave this field blank (<code>null</code>).
   * 
   * @param id the id to set
   */
  private void setId(SmartId id) {

    this.id = id;
    this.revision = id.getRevision();
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 1)
  public String getName() {

    return this.name;
  }

  /**
   * This method sets the {@link #getName() name} of this object.<br>
   * <b>ATTENTION:</b><br>
   * This method should only be used internally. Especially this method can NOT
   * be used to rename this entity. Therefore you have to use the
   * content-repository.
   * 
   * @param name the name to set
   */
  private void setName(String name) {

    this.name = name;
  }

  /**
   * @return the classAccess
   */
  private ContentClassReadAccessByContentObject getClassAccess() {

    if (this.classAccess == null) {
      AbstractContentObject parent = getParent();
      if (parent != null) {
        return parent.getClassAccess();
      }
    }
    return this.classAccess;
  }

  /**
   * {@inheritDoc}
   * 
   * Override this method if you implement a generic entity that can represent
   * multiple content-classes.
   */
  @FieldAnnotation(id = 2, isTransient = true, isFinal = true)
  public ContentClass getContentClass() {

    if (this.classAccess == null) {
      this.classAccess = getClassAccess();
    }
    if (this.classAccess == null) {
      throw new IllegalStateException("The system is NOT yet initialized!");
    }
    ContentClass contentClass = this.classAccess.getContentClass(this);
    if (contentClass == null) {
      throw new IllegalStateException("The system is NOT yet initialized!");
    }
    return contentClass;
  }

  /**
   * This method sets the accessor used to determines the
   * {@link #getContentClass() content-class}. It has to be set to the
   * root-folder once during the bootstrap of the system.
   * 
   * @param contentClassAccess is the classAccess to set.
   */
  private void setClassAccess(ContentClassReadAccessByContentObject contentClassAccess) {

    assert (this.classAccess == null);
    assert (contentClassAccess != null);
    this.classAccess = contentClassAccess;
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 3)
  public abstract AbstractContentObject getParent();

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 4, inverseRelationFieldId = 3)
  public List<? extends AbstractContentObject> getChildren() {

    if (isFolder()) {
      throw new ContentModelException(
          "An entity that is a folder has to implement (override) this method!");
    }
    return Collections.emptyList();
  }

  /**
   * This method gets the {@link #getChildren() child} with the given
   * <code>childName</code>.<br>
   * <b>ATTENTION:</b><br>
   * This is an internal method. For the most implementations it only looks if
   * the child is already available in a cache. TODO !?!?
   * 
   * @param childName is the {@link #getName() name} of the requested child.
   * @return the child with the given <code>childName</code> or
   *         <code>null</code> if NOT available.
   */
  public AbstractContentObject getChild(String childName) {

    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * This method is <code>transient</code> instead of <code>static</code>
   * because it is a build in feature.
   */
  @FieldAnnotation(id = 5, isTransient = true)
  public boolean isFolder() {

    return getContentClass().isFolderClass();
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 6)
  public final String getPath() {

    AbstractContentObject parent = getParent();
    if (parent == null) {
      // root folder
      return PATH_SEPARATOR;
    } else {
      StringBuilder pathBuilder = new StringBuilder();
      buildPathRecursive(pathBuilder);
      return pathBuilder.toString();
    }
  }

  /**
   * This method is used to build the {@link #getPath() path} using the given
   * <code>stringBuilder</code>.
   * 
   * @param stringBuilder is the {@link StringBuilder} where the path is
   *        appended to.
   */
  private void buildPathRecursive(StringBuilder stringBuilder) {

    AbstractContentObject parent = getParent();
    if (parent != null) {
      parent.buildPathRecursive(stringBuilder);
      stringBuilder.append(PATH_SEPARATOR);
      stringBuilder.append(getName());
    }
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 7)
  public MetaDataSet getMetaDataSet() {

    return this.metaDataSet;
  }

  /**
   * @param metaDataSet the metaDataSet to set
   */
  protected void setMetaDataSet(MetaDataSet metaDataSet) {

    this.metaDataSet = metaDataSet;
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 8)
  public Lock getLock() {

    return this.lock;
  }

  /**
   * @param lock the lock to set
   */
  protected void setLock(Lock lock) {

    this.lock = lock;
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 9)
  public final boolean getDeletedFlag() {

    return this.deletedFlag;
  }

  /**
   * This method sets the {@link #isDeleted() deleted} flag.
   * 
   * @param deleted - if <code>true</code> the object will be marked as
   *        deleted.
   */
  protected void setDeletedFlag(boolean deleted) {

    this.deletedFlag = deleted;
  }

  /**
   * {@inheritDoc}
   * 
   * <b>ATTENTION:</b><br>
   * This field/method is logically
   * {@link FieldAnnotation#isInherited() inherited} but NOT annotated with
   * <code>isInherited = true</code>. This feature is programmatically
   * implemented since it is required at a very low level.
   */
  @FieldAnnotation(id = 10, isTransient = true)
  public boolean isDeleted() {

    if (this.deletedFlag) {
      return true;
    } else {
      AbstractContentObject parent = getParent();
      if (parent == null) {
        return false;
      } else {
        return parent.isDeleted();
      }
    }
  }

  /**
   * This method gets the modification counter. It can be used to detect a
   * conflict on an update of the entity if optimistic locking is used.
   * 
   * @return the modificationCount is the modification counter.
   */
  @FieldAnnotation(id = 11)
  public int getModificationCount() {

    return this.modificationCount;
  }

  /**
   * This method sets the {@link #getModificationCount() modification counter}.
   * 
   * @param modificationCount the counter to set.
   */
  protected void setModificationCount(int modificationCount) {

    this.modificationCount = modificationCount;
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 12)
  public int getRevision() {

    return this.revision;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isRevisionClosed() {

    if (this.id != null) {
      return (this.id.getRevision() == 0);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 14)
  public RevisionHistory getRevisionHistory() {

    // TODO
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public final Object getValue(String fieldName) throws ContentException {

    return getValue(fieldName, Object.class);
  }

  /**
   * {@inheritDoc}
   */
  public final <V> V getValue(String fieldName, Class<V> type) throws ContentException {

    ContentField field = getContentClass().getField(fieldName);
    if (field == null) {
      throw new FieldNotExistsException(fieldName, getContentClass());
    }
    if (!field.getFieldClass().isAssignableFrom(type)) {
      throw new ContentCastException(field.getFieldClass(), type);
    }
    Object result = field.getAccessor().get(this);
    return type.cast(result);
  }

  /**
   * {@inheritDoc}
   */
  public Object removeValue(String key) {

    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String fieldName, Object value) throws ContentException {

    ContentField field = getContentClass().getField(fieldName);
    if (field == null) {
      throw new FieldNotExistsException(fieldName, getContentClass());
    }
    if (field.getModifiers().isReadOnly()) {
      // TODO: create exception type
      // throw new FieldNotEditableException(fieldName, getContentClass());
      throw new PermissionDeniedException("", "write " + field, "");
    }
    try {
      field.getAccessor().set(this, value);
    } catch (ClassCastException e) {
      if ((value != null) && field.getFieldClass().isAssignableFrom(value.getClass())) {
        throw new ContentCastException(value.getClass(), field.getFieldClass());
      }
      throw new IllegalStateException("Internal Error!");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {

    if (this.id == null) {
      // ATTENTION: if the ID is set the hash-code changes...
      return super.hashCode();
    } else {
      return ~this.id.hashCode();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean equals(Object other) {

    if (this == other) {
      return true;
    }
    if ((other != null) && (other instanceof AbstractContentObject)) {
      if (this.id != null) {
        return this.id.equals(((ContentObject) other).getId());
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String className;
    ContentClass contentClass = getContentClass();
    if (contentClass == null) {
      className = "-";
    } else {
      className = contentClass.getName();
    }
    StringBuffer buffer = new StringBuffer(className.length() + this.name.length() + 10);
    buffer.append(className);
    buffer.append(':');
    buffer.append(getName());
    buffer.append(" [");
    buffer.append(this.id);
    buffer.append(']');
    return buffer.toString();
  }

  /**
   * 
   */
  public abstract static class AbstractContentObjectModifier {

    /**
     * Sets the <code>{@link ContentObject#getName() name}</code> of the given
     * <code>object</code>.
     * 
     * @param object is the object to modify.
     * @param name is the {@link ContentObject#getName() name} to set.
     */
    protected void setContentObjectName(AbstractContentObject object, String name) {

      object.setName(name);
    }

    /**
     * Sets the <code>{@link ContentObject#getId() ID}</code> of the given
     * <code>object</code>.
     * 
     * @param object is the object to modify.
     * @param id is the {@link ContentObject#getId() ID} to set.
     */
    protected void setContentObjectId(AbstractContentObject object, SmartId id) {

      object.setId(id);
    }

    /**
     * Sets the <code>{@link ContentObject#getDeletedFlag() deleted-flag}</code>
     * of the given <code>object</code>.
     * 
     * @param object is the object to modify.
     * @param deleted is the {@link ContentObject#getDeletedFlag() deleted-flag}
     *        to set.
     */
    protected void setContentObjectDeletedFlag(AbstractContentObject object, boolean deleted) {

      object.setDeletedFlag(deleted);
    }

    /**
     * Sets the
     * <code>{@link AbstractContentObject#getClassAccess() class-access}</code>
     * of the given <code>object</code>.
     * 
     * @param object is the object to modify.
     * @param access gives access to determine the
     *        {@link AbstractContentObject#getContentClass() content-class}.
     */
    protected void setContentObjectClassAccess(AbstractContentObject object,
        ContentClassReadAccessByContentObject access) {

      object.setClassAccess(access);
    }

  }

}
