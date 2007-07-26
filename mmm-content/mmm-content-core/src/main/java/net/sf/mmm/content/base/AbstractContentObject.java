/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.base;

import java.util.Map;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.FieldNotExistsException;
import net.sf.mmm.content.security.api.PermissionDeniedException;
import net.sf.mmm.content.value.api.MetaData;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.impl.MetaDataImpl;

/**
 * This is the implementation of the abstract entity {@link ContentObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentObject implements ContentObject, MetaData {

  /** @see #getId() */
  private SmartId id;

  /** @see #getName() */
  private String name;

  /** @see #isDeleted() */
  private boolean deleted;

  /** @see #getMetaData(String) */
  private Map<String, MetaData> metadataMap;

  /** @see #getModificationCount() */
  // @javax.persistence.Version
  private int modificationCount;

  /**
   * The constructor.
   */
  public AbstractContentObject() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public final Object getValue(String fieldName) throws ContentException {

    ContentField field = getContentClass().getField(fieldName);
    if (field == null) {
      throw new FieldNotExistsException(fieldName, getContentClass());
    }
    return getFieldValue(field, fieldName);
  }

  /**
   * This method gets the value of the given <code>field</code>.
   * 
   * @see #getValue(String)
   * 
   * @param field is the reflecting field.
   * @param fieldName is the name of the field.
   * @return the value of the field or <code>null</code> if the field exists
   *         but has no value.
   * @throws ContentException if the operation fails.
   */
  protected Object getFieldValue(ContentField field, String fieldName) throws ContentException {

    if (fieldName.equals(FIELD_NAME_ID)) {
      return getId();
    } else if (fieldName.equals(FIELD_NAME_CONTENT_CLASS)) {
      return getContentClass();
    } else if (fieldName.equals(FIELD_NAME_NAME)) {
      return getName();
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getId() {

    return this.id;
  }

  /**
   * This method sets the {@link #getId() ID}.
   * 
   * @param id the id to set
   */
  protected void setId(SmartId id) {

    this.id = id;
  }

  /**
   * {@inheritDoc}
   */
  public MetaData getMetaData(String namespace) {

    if (METADATA_NAMESPACE_NONE.equals(namespace)) {
      return this;
    }
    // TODO: synchronize / semaphore!
    // TODO: make metadata immutable if object locked!
    MetaData metadata = this.metadataMap.get(namespace);
    if (metadata == null) {
      metadata = loadMetaData(namespace);
      this.metadataMap.put(namespace, metadata);
    }
    return metadata;
  }

  /**
   * This method creates the metadata for the given namespace.
   * 
   * @param namespace is the namespace for which the metadata is requested.
   * @return the metadata for the given namespace.
   */
  protected MetaData loadMetaData(String namespace) {

    // TODO: here we need to load the metadata from db...
    return new MetaDataImpl();
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return this.name;
  }

  /**
   * This method sets the {@link #getName() name} of this object.
   * 
   * @param name the name to set
   */
  protected void setName(String name) {

    this.name = name;
  }

  /**
   * This method gets the deleted flag of this object. This method gives direct
   * access to the deleted flag and can be used if the method
   * {@link AbstractContentObject#isDeleted()}has been overridden.
   * 
   * @see ContentObject#isDeleted()
   * 
   * @return the deleted flag of this object.
   */
  protected final boolean getDeletedFlag() {

    return this.deleted;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDeleted() {

    return this.deleted;
  }

  /**
   * This method sets the {@link #isDeleted() deleted} flag.
   * 
   * @param deleted - if <code>true</code> the object will be marked as
   *        deleted.
   */
  protected void setDeleted(boolean deleted) {

    this.deleted = deleted;
  }

  /**
   * This method gets the modification counter. It can be used to detect a
   * conflict on an update of the entity if optimistic locking is used.
   * 
   * @return the modificationCount is the modification counter.
   */
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
      setValue(field, fieldName, value);
    } catch (ClassCastException e) {
      if ((value != null) && field.getFieldClass().isAssignableFrom(value.getClass())) {
        // TODO: create exception type
        // throw new FieldTypeNotCompatibleException(e, field, value.getClass(),
        // field.getFieldType());
        throw new IllegalStateException("Field type NOT compatible!");
      }
      throw new IllegalStateException("Internal Error");
    }
  }

  /**
   * This method sets the given <code>field</code> to the given
   * <code>value</code>.
   * 
   * @param field is the reflecting field.
   * @param fieldName is the name of the field.
   * @param value is the new value to set.
   * @throws ContentException if the operation failed.
   */
  protected void setValue(ContentField field, String fieldName, Object value)
      throws ContentException {

    if (fieldName.equals(FIELD_NAME_NAME)) {
      setName((String) value);
    } else {
      throw new IllegalStateException("Internal Error!");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {

    if (this.id == null) {
      // this is actually a bug
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

    if ((other != null) && (other instanceof AbstractContentObject)) {
      if (this.id == null) {
        return (this == other);
      } else {
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

}
