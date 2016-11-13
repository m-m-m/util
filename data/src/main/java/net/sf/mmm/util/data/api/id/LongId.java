/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.api.id;

import java.util.Objects;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the implementation of {@link Id} using a {@link Long} as {@link #getId() primary key}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class LongId<E> extends AbstractId<E> implements AttributeReadId<Long> {

  private final Long id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  public LongId(Class<E> type, long id, long version) {
    this(type, Long.valueOf(id), version);
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  protected LongId(Class<E> type, Long id, long version) {
    super(type, version);
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  @Override
  public Long getId() {

    return this.id;
  }

  /**
   * @return the {@link #getId() primary key} as primitve {@code long} value.
   */
  public long getIdAsLong() {

    return this.id.longValue();
  }

  @Override
  protected <T> AbstractId<T> newId(Class<T> newType, long version) {

    return new LongId<>(newType, this.id, version);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link LongId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> LongId<E> of(Class<E> type, Long id) {

    return of(type, id, VERSION_LATEST);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link LongId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> LongId<E> of(Class<E> type, Long id, long version) {

    if (id == null) {
      return null;
    }
    return new LongId<>(type, id, version);
  }

}
