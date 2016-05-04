/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.entity.base;

import net.sf.mmm.util.entity.api.MutableRevisionedEntity;

/**
 * This is the abstract base-class that implementations of {@link MutableRevisionedEntity} should inherit from (if
 * possible).
 *
 * @param <ID> is the type of the {@link #getId() primary key}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractRevisionedEntity<ID> extends AbstractGenericEntity<ID>
    implements MutableRevisionedEntity<ID> {

  private static final long serialVersionUID = -3138296535416100839L;

  private Number revision;

  /**
   * The constructor.
   */
  public AbstractRevisionedEntity() {

    super();
    this.revision = LATEST_REVISION;
  }

  @Override
  public Number getRevision() {

    return this.revision;
  }

  @Override
  public void setRevision(Number revision) {

    this.revision = revision;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.revision == null) ? 0 : this.revision.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    AbstractRevisionedEntity<?> other = (AbstractRevisionedEntity<?>) obj;
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
   * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(Object) append} the string
   *        representation.
   */
  @Override
  protected void toString(StringBuilder buffer) {

    super.toString(buffer);
    if (this.revision != null) {
      buffer.append("[rev=");
      buffer.append(this.revision);
      buffer.append("]");
    }
  }

}
