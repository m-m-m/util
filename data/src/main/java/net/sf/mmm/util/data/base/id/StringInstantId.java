/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.base.id;

import java.time.Instant;
import java.util.Objects;

import net.sf.mmm.util.data.api.id.Id;
import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the implementation of {@link Id} that holds its {@link #getId() primary key} as a {@code String} value. This
 * is the most generic type of {@link Id}. However {@link LongVersionId} and {@link UuidVersionId} will be more
 * efficient.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class StringInstantId<E> extends AbstractInstantId<E, String> implements AttributeReadId<String> {

  private final String id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   */
  public StringInstantId(Class<E> type, String id, Instant version) {
    super(type, version);
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  @Override
  public String getId() {

    return this.id;
  }

  @Override
  protected <T> StringInstantId<T> newId(Class<T> newType, Instant version) {

    return new StringInstantId<>(newType, this.id, version);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link StringInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> StringInstantId<E> of(Class<E> type, String id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link StringInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> StringInstantId<E> of(Class<E> type, String id, Instant version) {

    if (id == null) {
      return null;
    }
    return new StringInstantId<>(type, id, version);
  }

}
