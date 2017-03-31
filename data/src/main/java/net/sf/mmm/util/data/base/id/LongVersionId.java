/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.base.id;

import java.util.Objects;

import net.sf.mmm.util.data.api.id.Id;
import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the implementation of {@link Id} using a {@link Long} as {@link #getId() primary key}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class LongVersionId<E> extends AbstractVersionId<E, Long> implements AttributeReadId<Long> {

  private final Long id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  public LongVersionId(Class<E> type, long id, long version) {
    this(type, Long.valueOf(id), Long.valueOf(version));
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getIdAsLong()}.
   * @param version the {@link #getVersion() version}.
   */
  protected LongVersionId(Class<E> type, Long id, Long version) {
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
  protected <T> LongVersionId<T> newId(Class<T> newType, Long version) {

    return new LongVersionId<>(newType, this.id, version);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link LongVersionId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> LongVersionId<E> of(Class<E> type, Long id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link LongVersionId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> LongVersionId<E> of(Class<E> type, Long id, Long version) {

    if (id == null) {
      return null;
    }
    return new LongVersionId<>(type, id, version);
  }

}
