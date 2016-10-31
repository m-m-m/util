/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.api.id;

import java.util.Objects;
import java.util.UUID;

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
  public final int hashCode() {

    long id = getId();
    if (id == ID_UUID) {
      return ~getUuid().hashCode();
    } else {
      return ~((int) id);
    }
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
    if (getId() != other.getId()) {
      return false;
    }
    if (!Objects.equals(getUuid(), other.getUuid())) {
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
   * @param buffer the {@link StringBuilder} where to {@link StringBuilder#append(CharSequence) append} the string
   *        representation to.
   */
  protected void toString(StringBuilder buffer) {

    UUID uuid = getUuid();
    if (uuid == null) {
      long id = getId();
      buffer.append(id);
      assert (id != ID_UUID);
    } else {
      buffer.append(uuid);
      assert (getId() == ID_UUID);
    }
    long version = getVersion();
    if (version != VERSION_LATEST) {
      buffer.append(VERSION_SEPARATOR);
      buffer.append(version);
    }
  }

  /**
   * @param idString the id (without version) as {@link String}.
   * @return the {@code idString} as {@link UUID} or {@code null} if not a {@link #getUuid() UUID} (but {@link #getId()
   *         ID} instead).
   */
  protected static UUID parseUuid(String idString) {

    if ((idString.length() > 29) && (idString.indexOf('-') > 0)) {
      return UUID.fromString(idString);
    } else {
      return null;
    }
  }

}
