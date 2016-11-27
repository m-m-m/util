/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.api.id;

import java.util.Objects;
import java.util.UUID;

import net.sf.mmm.util.exception.api.ObjectMismatchException;

/**
 * This is the abstract base implementation of {@link Id}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class AbstractId<E> implements Id<E> {

  private static final long serialVersionUID = 1L;

  private final Class<E> type;

  private final long version;

  /**
   * The constructor.
   */
  protected AbstractId() {
    super();
    this.type = null;
    this.version = VERSION_LATEST;
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   */
  public AbstractId(Class<E> type) {
    this(type, VERSION_LATEST);
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param version - see {@link #getVersion()}.
   */
  public AbstractId(Class<E> type, long version) {
    super();
    this.type = type;
    this.version = version;
  }

  @Override
  public Class<E> getType() {

    return this.type;
  }

  @Override
  public long getVersion() {

    return this.version;
  }

  @Override
  public Id<E> withVersion(long newVersion) {

    if (this.version == newVersion) {
      return this;
    }
    return newId(this.type, newVersion);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final Id<E> withType(Class<?> newType) {

    if (this.type == null) {
      return (Id) newId(newType, this.version);
    } else if (this.type != newType) {
      throw new ObjectMismatchException(newType, this.type, this);
    }
    return this;
  }

  /**
   * Internal method called from {@link #withType(Class)} or {@link #withType(Class)} to create a new instance.
   *
   * @param <T> the generic type of {@code newType}.
   * @param newType the new {@link #getType() type}.
   * @param newVersion the new {@link #getVersion() version}.
   * @return the new instance of the {@link Id} implementation.
   */
  protected abstract <T> AbstractId<T> newId(Class<T> newType, long newVersion);

  @Override
  public final int hashCode() {

    return ~getId().hashCode();
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
    if (!Objects.equals(getId(), other.getId())) {
      return false;
    }
    if (!Objects.equals(this.type, other.type)) {
      return false;
    }
    if (this.version != other.version) {
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

    buffer.append(getId());
    if (this.version != VERSION_LATEST) {
      buffer.append(VERSION_SEPARATOR);
      buffer.append(this.version);
    }
  }

  /**
   * @param idString the id (without version) as {@link String}.
   * @return the {@code idString} as {@link UUID} or {@code null} if not a {@link UUID}.
   */
  protected static UUID parseUuid(String idString) {

    if ((idString.length() > 29) && (idString.indexOf('-') > 0)) {
      return UUID.fromString(idString);
    } else {
      return null;
    }
  }

}
