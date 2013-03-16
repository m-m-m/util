/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

import net.sf.mmm.util.entity.api.MutableRevisionedEntity;
import net.sf.mmm.util.entity.api.RevisionedEntity;

/**
 * This is the abstract base class for an {@link AbstractTransferObject} corresponding to an
 * {@link net.sf.mmm.util.entity.api.PersistenceEntity}. Unlike for the
 * {@link net.sf.mmm.util.entity.api.PersistenceEntity} there are no restrictions and you can treat the
 * instance as a regular java-bean or {@link net.sf.mmm.util.pojo.api.Pojo}.<br/>
 * For an example see also JavaDoc of {@link CompositeTo}.
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class EntityTo<ID> extends AbstractTransferObject implements MutableRevisionedEntity<ID> {

  /** UID for serialization. */
  private static final long serialVersionUID = -3039958170310580721L;

  /** @see #getId() */
  private ID id;

  /** @see #getModificationCounter() */
  private int modificationCounter;

  /** @see #getRevision() */
  private Number revision;

  /**
   * The constructor.
   */
  public EntityTo() {

    super();
    this.revision = LATEST_REVISION;
  }

  /**
   * The constructor.
   * 
   * @param template is the object to create a deep-copy from.
   */
  public EntityTo(RevisionedEntity<ID> template) {

    super(template);
    if (template == null) {
      this.revision = LATEST_REVISION;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ID getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(ID id) {

    this.id = id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getModificationCounter() {

    return this.modificationCounter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModificationCounter(int version) {

    this.modificationCounter = version;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Number getRevision() {

    return this.revision;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRevision(Number revision) {

    this.revision = revision;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String toString() {

    StringBuilder buffer = new StringBuilder();
    toString(buffer);
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void copyFrom(Object source, boolean overwrite) {

    super.copyFrom(source, overwrite);
    @SuppressWarnings("unchecked")
    RevisionedEntity<ID> sourceEntity = (RevisionedEntity<ID>) source;
    if ((this.id == null) || (overwrite)) {
      this.id = sourceEntity.getId();
    }
    if ((this.modificationCounter == 0) || (overwrite)) {
      this.modificationCounter = sourceEntity.getModificationCounter();
    }
    if ((this.revision == null) || (overwrite)) {
      this.revision = sourceEntity.getRevision();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EntityTo<ID> clone() {

    return (EntityTo<ID>) super.clone();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + this.modificationCounter;
    result = prime * result + ((this.revision == null) ? 0 : this.revision.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    EntityTo<?> other = (EntityTo<?>) obj;
    if (this.id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!this.id.equals(other.id)) {
      return false;
    }
    if (this.modificationCounter != other.modificationCounter) {
      return false;
    }
    if (this.revision == null) {
      if (other.revision != null) {
        return false;
      }
    } else if (!this.revision.equals(other.revision)) {
      return false;
    }
    return true;
  }

  /**
   * Method to extend {@link #toString()} logic.
   * 
   * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(Object) append} the
   *        string representation.
   */
  protected void toString(StringBuilder buffer) {

    buffer.append(getClass().getSimpleName());
    if (this.id != null) {
      buffer.append("[id=");
      buffer.append(this.id);
      buffer.append("]");
    }
    if (this.revision != null) {
      buffer.append("[rev=");
      buffer.append(this.revision);
      buffer.append("]");
    }
  }

}
