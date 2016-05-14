/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.id;

/**
 * This is a simple implementation of {@link Id}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ForeignKey<E> extends AbstractId<E> {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  protected ForeignKey() {
    super();
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   */
  public ForeignKey(Class<E> type, long id) {
    super(type, id);
  }

  @Override
  public long getVersion() {

    return VERSION_LATEST;
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @return a new instance of {@link ForeignKey}.
   */
  public static <E> ForeignKey<E> valueOf(Class<E> type, Long id) {

    if (id == null) {
      return null;
    } else {
      return valueOf(type, id.longValue());
    }
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @return a new instance of {@link ForeignKey}.
   */
  public static <E> ForeignKey<E> valueOf(Class<E> type, long id) {

    return new ForeignKey<>(type, id);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param id the {@link Id} to convert. May be {@code null}.
   * @return the {@link Id} as {@link ForeignKey} or {@code null} if the given {@link Id} was {@code null}.
   */
  public static <E> ForeignKey<E> valueOf(Id<E> id) {

    if (id == null) {
      return null;
    } else if (id instanceof ForeignKey) {
      return (ForeignKey<E>) id;
    } else {
      return new ForeignKey<>(id.getType(), id.getId());
    }
  }

}
