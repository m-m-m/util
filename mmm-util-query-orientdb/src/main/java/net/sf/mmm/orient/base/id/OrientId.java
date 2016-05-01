/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.base.id;

import java.util.Objects;

import com.orientechnologies.orient.core.id.ORID;

import net.sf.mmm.util.lang.api.Id;
import net.sf.mmm.util.lang.base.AbstractId;

/**
 * This is the implementation of {@link Id} for a native {@link ORID}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class OrientId<E> implements Id<E> {

  private final Class<E> type;

  private final ORID orid;

  private final Number revision;

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param orid - see {@link #getOrid()}.
   * @param revision - see {@link #getRevision()}.
   */
  protected OrientId(Class<E> type, ORID orid, Number revision) {
    super();
    this.type = type;
    this.orid = orid;
    this.revision = revision;
  }

  @Override
  public long getId() {

    return this.orid.getClusterPosition();
  }

  @Override
  public Class<E> getType() {

    return this.type;
  }

  @Override
  public Number getRevision() {

    return this.revision;
  }

  /**
   * @return the {@link ORID}.
   */
  public ORID getOrid() {

    return this.orid;
  }

  @Override
  public final int hashCode() {

    return Objects.hash(this.orid, this.revision);
  }

  @Override
  public final boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if ((obj == null) || !(obj instanceof AbstractId)) {
      return false;
    }
    OrientId<?> other = (OrientId<?>) obj;
    if (this.orid != other.orid) {
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

    return this.orid.toString();
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param orid - see {@link #getOrid()}.
   * @return a new instance of {@link OrientId}.
   */
  public static <E> OrientId<E> valueOf(Class<E> type, ORID orid) {

    return valueOf(type, orid, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param orid - see {@link #getOrid()}.
   * @param revision - see {@link #getRevision()}.
   * @return a new instance of {@link OrientId}.
   */
  public static <E> OrientId<E> valueOf(Class<E> type, ORID orid, Number revision) {

    if (orid == null) {
      return null;
    } else {
      return new OrientId<>(type, orid, revision);
    }
  }
}
