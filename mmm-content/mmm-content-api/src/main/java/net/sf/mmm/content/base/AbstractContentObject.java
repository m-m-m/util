/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.sf.mmm.content.api.ContentFieldAnnotation;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.datatype.api.ContentId;

/**
 * This is the implementation of the abstract entity {@link ContentObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class AbstractContentObject implements ContentObject {

  /** UID for serialization. */
  private static final long serialVersionUID = 8616371370522165168L;

  /** @see #getId() */
  private Long id;

  /** @see #getContentId() */
  private ContentId contentId;

  /** @see #getTitle() */
  private String title;

  /** @see #getRevision() */
  private Integer revision;

  /** @see #getModificationCount() */
  private int modificationCount;

  /**
   * The constructor.
   */
  public AbstractContentObject() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getTitle() name}.
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
   * @param name is the {@link #getTitle() name}.
   * @param contentId is the {@link #getContentId() ID}.
   */
  public AbstractContentObject(String name, ContentId contentId) {

    this(name);
    setContentId(contentId);
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public final ContentId getContentId() {

    return this.contentId;
  }

  /**
   * {@inheritDoc}
   */
  public abstract int getContentClassId();

  /**
   * @param contentId is the contentId to set
   */
  protected void setContentId(ContentId contentId) {

    this.contentId = contentId;
    if (this.id == null) {
      this.id = Long.valueOf(contentId.getObjectId());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Id
  @GeneratedValue
  public Long getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isPersistent() {

    return (this.id != null);
  }

  /**
   * {@inheritDoc}
   */
  @Column(nullable = true)
  public String getTitle() {

    return this.title;
  }

  /**
   * This method sets the {@link #getTitle() name} of this object.<br>
   * <b>ATTENTION:</b><br>
   * This method should only be used internally. Especially this method can NOT
   * be used to rename this entity. Therefore you have to use the
   * content-repository.
   * 
   * @param name the name to set
   */
  protected void setName(String name) {

    this.title = name;
  }

  /**
   * This method gets the modification counter. It can be used to detect a
   * conflict on an update of the entity if optimistic locking is used.
   * 
   * @return the modificationCount is the modification counter.
   */
  @ContentFieldAnnotation(id = 11)
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
  @ContentFieldAnnotation(id = 12)
  public Integer getRevision() {

    return this.revision;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {

    if (this.contentId == null) {
      // ATTENTION: if the ID is set the hash-code changes...
      return super.hashCode();
    } else {
      return ~this.contentId.hashCode();
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
      if (this.contentId != null) {
        return this.contentId.equals(((ContentObject) other).getContentId());
      }
    }
    return false;
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
    if (this.contentId != null) {
      idLength = 10;
    }
    StringBuffer buffer = new StringBuffer(className.length() + objectName.length() + idLength);
    buffer.append(className);
    buffer.append(':');
    buffer.append(objectName);
    if (this.contentId != null) {
      buffer.append(" [");
      buffer.append(this.contentId);
      buffer.append(']');
    }
    return buffer.toString();
  }

}
