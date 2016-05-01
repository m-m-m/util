/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Objects;

import net.sf.mmm.util.lang.api.Id;

/**
 * This is the abstract base implementation of {@link Id}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 7.1.0
 */
public abstract class AbstractId<E> implements Id<E> {

  private long objectId;

  private Number revision;

  private Class<E> type;

  /**
   * The constructor.
   */
  protected AbstractId() {
    super();
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param objectId - see {@link #getId()}.
   */
  public AbstractId(Class<E> type, long objectId) {
    this(type, objectId, null);
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param objectId - see {@link #getId()}.
   * @param revision - see {@link #getRevision()}.
   */
  public AbstractId(Class<E> type, long objectId, Number revision) {
    super();
    this.type = type;
    this.objectId = objectId;
    this.revision = revision;
  }

  @Override
  public long getId() {

    return this.objectId;
  }

  @Override
  public Class<E> getType() {

    return this.type;
  }

  @Override
  public Number getRevision() {

    return this.revision;
  }

  @Override
  public final int hashCode() {

    return Objects.hash(Long.valueOf(this.objectId), this.type, this.revision);
  }

  @Override
  public final boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if ((obj == null) || !(obj instanceof AbstractId)) {
      return false;
    }
    AbstractId<?> other = (AbstractId<?>) obj;
    if (this.objectId != other.objectId) {
      return false;
    }
    if (!Objects.equals(this.type, other.type)) {
      return false;
    }
    if (!Objects.equals(this.revision, other.revision)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder(32);
    toString(buffer);
    return buffer.toString();
  }

  /**
   * @see #toString()
   * @param buffer the {@link StringBuilder} where to {@link StringBuilder#append(CharSequence) append} the string
   *        representation to.
   */
  protected void toString(StringBuilder buffer) {

    if (this.type != null) {
      buffer.append(this.type.getSimpleName());
      buffer.append(':');
    }
    buffer.append(this.objectId);
    if (this.revision != null) {
      buffer.append(':');
      buffer.append(this.revision);
    }
  }

}
