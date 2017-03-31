/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.base.id;

import java.time.Instant;
import java.util.Objects;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the implementation of {@link AbstractInstantId} using {@link Long} as type for the {@link #getId() primary
 * key}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class LongInstantId<E> extends AbstractInstantId<E, Long> implements AttributeReadId<Long> {

  private final Long id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  public LongInstantId(Class<E> type, long id, Instant version) {
    this(type, Long.valueOf(id), version);
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  protected LongInstantId(Class<E> type, Long id, Instant version) {
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
  protected <T> LongInstantId<T> newId(Class<T> newType, Instant newVersion) {

    return new LongInstantId<>(newType, this.id, newVersion);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link LongInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> LongInstantId<E> of(Class<E> type, Long id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link LongInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> LongInstantId<E> of(Class<E> type, Long id, Instant version) {

    if (id == null) {
      return null;
    }
    return new LongInstantId<>(type, id, version);
  }

}
