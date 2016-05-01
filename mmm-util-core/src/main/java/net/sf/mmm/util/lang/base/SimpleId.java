/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import javax.persistence.Id;

/**
 * This is a simple implementation of {@link Id}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class SimpleId<E> extends AbstractId<E> {

  /**
   * The constructor.
   */
  protected SimpleId() {
    super();
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param objectId - see {@link #getId()}.
   */
  public SimpleId(Class<E> type, long objectId) {
    this(type, objectId, null);
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param objectId - see {@link #getId()}.
   * @param revision - see {@link #getRevision()}.
   */
  public SimpleId(Class<E> type, long objectId, Number revision) {
    super(type, objectId, revision);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @return a new instance of {@link SimpleId}.
   */
  public static <E> SimpleId<E> valueOf(Class<E> type, Long id) {

    return valueOf(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @param revision - see {@link #getRevision()}.
   * @return a new instance of {@link SimpleId}.
   */
  public static <E> SimpleId<E> valueOf(Class<E> type, Long id, Number revision) {

    if (id == null) {
      return null;
    } else {
      return valueOf(type, id.longValue(), revision);
    }
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @return a new instance of {@link SimpleId}.
   */
  public static <E> SimpleId<E> valueOf(Class<E> type, long id) {

    return valueOf(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @param revision - see {@link #getRevision()}.
   * @return a new instance of {@link SimpleId}.
   */
  public static <E> SimpleId<E> valueOf(Class<E> type, long id, Number revision) {

    return new SimpleId<>(type, id, revision);
  }

}
