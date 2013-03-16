/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.entity.base;

import net.sf.mmm.util.entity.api.MutableGenericEntity;

/**
 * This is the abstract base-class that implementations of {@link MutableGenericEntity} should inherit from
 * (if possible).
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractGenericEntity<ID> implements MutableGenericEntity<ID> {

  /** UID for serialization. */
  private static final long serialVersionUID = 3293773825737626494L;

  /** @see #getId() */
  private ID id;

  /** @see #getModificationCounter() */
  private int modificationCounter;

  /**
   * The constructor.
   */
  public AbstractGenericEntity() {

    super();
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
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + this.modificationCounter;
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
    if (obj == null) {
      return false;
    }
    // TODO hohwille: This might be dangerous due to JPA/hibernate may generating sub classes for lazy
    // loading...
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractGenericEntity<?> other = (AbstractGenericEntity<?>) obj;
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
  }

}
