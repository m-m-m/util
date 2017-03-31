/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.api.id;

import java.util.Objects;
import java.util.UUID;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.ObjectMismatchException;

/**
 * This is the abstract base implementation of {@link Id}.
 *
 * @param <E> the generic type of the identified entity.
 * @param <I> the generic type of the {@link #getId() ID}.
 * @param <V> the generic type of the {@link #getVersion() Version}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class AbstractId<E, I, V extends Comparable<?>> implements Id<E> {

  private static final long serialVersionUID = 1L;

  private final Class<E> type;

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   */
  public AbstractId(Class<E> type) {
    super();
    this.type = type;
  }

  @Override
  public Class<E> getType() {

    return this.type;
  }

  @Override
  public abstract I getId();

  @Override
  public abstract V getVersion();

  @Override
  public Id<E> withLatestVersion() {

    return withVersion(null);
  }

  /**
   * @param newVersion the new value of {@link #getVersion()}.
   * @return a copy of this {@link Id} with the given {@link #getVersion() version} or this {@link Id} itself if already
   *         satisfying.
   */
  public Id<E> withVersion(V newVersion) {

    if (getVersion() == newVersion) {
      return this;
    }
    return newId(this.type, newVersion);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public final Id<E> withType(Class<?> newType) {

    if (this.type == null) {
      return (Id) newId(newType, getVersion());
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
  protected abstract <T> AbstractId<T, I, V> newId(Class<T> newType, V newVersion);

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
    AbstractId<?, ?, ?> other = (AbstractId<?, ?, ?>) obj;
    if (!Objects.equals(getId(), other.getId())) {
      return false;
    }
    if ((this.type != null) && (other.type != null) && !this.type.equals(other.type)) {
      return false;
    }
    if (!Objects.equals(getVersion(), other.getVersion())) {
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
    V version = getVersion();
    if (version != null) {
      buffer.append(VERSION_SEPARATOR);
      buffer.append(version);
    }
  }

  /**
   * @param <E> the type of the identified entity.
   * @param id the existing {@link Id} or {@code null}.
   * @param type the {@link #getType() type} to {@link #withType(Class) ensure}.
   * @return the result of <code>id.{@link Id#withType(Class) withType}(type)</code> or {@code null} if the given
   *         {@link Id} is {@code null}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <E> Id<E> getWithType(Id<?> id, Class<E> type) {

    if (id == null) {
      return null;
    }
    return (Id) id.withType(type);
  }

  /**
   * @param <I> the generic type expected for {@link Id#getId()}.
   * @param id the actual {@link Id} instance.
   * @param type the {@link Class} reflecting the expected type of {@link Id#getId()}.
   * @return the {@link #getId() primary key} of the given {@link Id}. May be {@code null} if the given {@link Id} is
   *         {@code null}.
   * @throws IllegalArgumentException if the given {@code type} does not match.
   */
  @SuppressWarnings("unchecked")
  public static <I> I getIdAs(Id<?> id, Class<I> type) throws IllegalArgumentException {

    return getAs(id, type, PROPERTY_ID);
  }

  /**
   * @param <V> the generic type expected for {@link Id#getVersion()}.
   * @param id the actual {@link Id} instance.
   * @param type the {@link Class} reflecting the expected type of {@link Id#getVersion()}.
   * @return the {@link #getVersion() version} of the given {@link Id}. May be {@code null} if the given {@link Id}
   *         itself or its {@link #getVersion() version} is {@code null}.
   * @throws IllegalArgumentException if the given {@code type} does not match.
   */
  public static <V> V getVersionAs(Id<?> id, Class<V> type) throws IllegalArgumentException {

    return getAs(id, type, PROPERTY_VERSION);
  }

  /**
   * @param id the actual {@link Id} instance.
   * @return the {@link #getVersion() version} of the given {@link Id}. May be {@code null} if the given {@link Id}
   *         itself or its {@link #getVersion() version} is {@code null}.
   * @throws IllegalArgumentException if the given {@code type} does not match.
   */
  public static long getVersionAsLong(Id<?> id) throws IllegalArgumentException {

    Long version = getVersionAs(id, Long.class);
    if (version == null) {
      return 0;
    }
    return version.longValue();
  }

  @SuppressWarnings("unchecked")
  private static <V> V getAs(Id<?> id, Class<V> type, String property) throws IllegalArgumentException {

    if (id == null) {
      return null;
    }
    Object value;
    if (PROPERTY_ID.equals(property)) {
      value = id.getId();
    } else if (PROPERTY_VERSION.equals(property)) {
      value = id.getVersion();
    } else {
      throw new IllegalCaseException(property);
    }
    if (value == null) {
      return null;
    } else if (type.isInstance(value)) {
      return (V) value;
    } else {
      throw new IllegalArgumentException("Expected type " + type.getSimpleName() + " for property " + property + " but found " + value + " of type "
          + value.getClass().getSimpleName() + " from ID " + id + " of type " + id.getClass().getSimpleName());
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
