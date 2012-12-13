/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.persistence.base.jpa.JpaRevisionedEntity;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException;

/**
 * This is the implementation of the abstract entity {@link DataObjectView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@MappedSuperclass
@DataClassAnnotation(id = DataObjectView.CLASS_ID, title = DataObjectView.CLASS_TITLE, //
groupId = DataClassGroupRoot.GROUP_ID, groupVersion = DataClassGroupRoot.GROUP_VERSION)
public abstract class AbstractDataObject extends JpaRevisionedEntity<Long> implements
    DataObject {

  /** UID for serialization. */
  private static final long serialVersionUID = 8616371370522165168L;

  /** @see #getTitle() */
  private String title;

  /** @see #getDeletedFlag() */
  private boolean deletedFlag;

  /**
   * The constructor.
   */
  public AbstractDataObject() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param title is the {@link #getTitle() title}.
   */
  public AbstractDataObject(String title) {

    super();
    if (title != null) {
      setTitle(title);
    }
  }

  /**
   * This method allows to implement custom logic to provide read access to
   * {@link net.sf.mmm.data.api.reflection.DataField fields} of dynamically
   * typed {@link net.sf.mmm.data.api.reflection.DataClass classes}.
   * 
   * @see net.sf.mmm.data.api.reflection.access.DataFieldAccessor#getFieldValue(DataObjectView)
   * 
   * @param field is the
   *        {@link net.sf.mmm.data.api.reflection.DataField#getTitle() title} of
   *        the requested {@link net.sf.mmm.data.api.reflection.DataField}.
   * @return the value of the requested field.
   */
  protected Object getFieldValue(String field) {

    throw new PojoPropertyNotFoundException(getClass(), field);
  }

  /**
   * This method allows to implement custom logic to provide write access to
   * {@link net.sf.mmm.data.api.reflection.DataField fields} of dynamically
   * typed {@link net.sf.mmm.data.api.reflection.DataClass classes}.
   * 
   * @see net.sf.mmm.data.api.reflection.access.DataFieldAccessor#setFieldValue(DataObjectView,
   *      Object)
   * 
   * @param field is the
   *        {@link net.sf.mmm.data.api.reflection.DataField#getTitle() title} of
   *        the requested {@link net.sf.mmm.data.api.reflection.DataField}.
   * @param value is the value of the field to set.
   */
  protected void setFieldValue(String field, Object value) {

    throw new PojoPropertyNotFoundException(getClass(), field);
  }

  /**
   * {@inheritDoc}
   */
  @Column(nullable = true)
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  public void setTitle(String title) {

    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  public boolean getDeletedFlag() {

    return this.deletedFlag;
  }

  /**
   * {@inheritDoc}
   */
  public void setDeletedFlag(boolean deleted) {

    this.deletedFlag = deleted;
  }

  /**
   * This method gets the parent of this object.
   * 
   * @return the parent or <code>null</code> if it has no parent.
   */
  @Transient
  protected DataObjectView getParent() {

    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * <b>ATTENTION:</b><br>
   * This field/method is logically
   * {@link net.sf.mmm.data.api.reflection.DataFieldAnnotation#isInheritedFromParent()
   * inherited} but NOT annotated with <code>isInherited = true</code>. This
   * feature is programmatically implemented since it is required at a very low
   * level.
   */
  @Transient
  public boolean isDeleted() {

    if (this.deletedFlag) {
      return true;
    } else {
      DataObjectView parent = getParent();
      if (parent == null) {
        return false;
      } else {
        return parent.isDeleted();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String className = getClass().getSimpleName();
    String objectName = this.title;
    if (objectName == null) {
      objectName = "-";
    }
    int idLength = 0;
    Long id = getId();
    if (id != null) {
      idLength = 8;
    }
    StringBuffer buffer = new StringBuffer(className.length() + objectName.length() + idLength);
    buffer.append(className);
    buffer.append(':');
    buffer.append(objectName);
    if (id != null) {
      buffer.append(" [");
      buffer.append(id);
      buffer.append(']');
    }
    return buffer.toString();
  }

}
