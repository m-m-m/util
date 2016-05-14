/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.id;

import java.util.Objects;

/**
 * This is the abstract base implementation of {@link Id}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractId<E> implements Id<E> {

  private static final long serialVersionUID = 1L;

  private long id;

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
   * @param id - see {@link #getId()}.
   */
  public AbstractId(Class<E> type, long id) {
    super();
    this.type = type;
    this.id = id;
  }

  @Override
  public long getId() {

    return this.id;
  }

  @Override
  public Class<E> getType() {

    return this.type;
  }

  @Override
  public final int hashCode() {

    return ~((int) this.id);
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
    if (this.id != other.id) {
      return false;
    }
    if (!Objects.equals(this.type, other.type)) {
      return false;
    }
    if (getVersion() != other.getVersion()) {
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
   * @param buffer the {@link StringBuilder} where to {@link StringBuilder#append(CharSequence) append} the
   *        string representation to.
   */
  protected void toString(StringBuilder buffer) {

    if (this.type != null) {
      buffer.append(this.type.getSimpleName());
      buffer.append(':');
    }
    buffer.append(this.id);
    long version = getVersion();
    if (version != VERSION_LATEST) {
      buffer.append('@');
      buffer.append(version);
    }
  }

}
