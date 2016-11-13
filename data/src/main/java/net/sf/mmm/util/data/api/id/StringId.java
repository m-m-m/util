/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.api.id;

import java.util.Objects;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the implementation of {@link Id} that holds its {@link #getId() primary key} as a {@code String} value. This
 * is the most generic type of {@link Id}. However {@link LongId} and {@link UuidId} will be more efficient.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class StringId<E> extends AbstractId<E> implements AttributeReadId<String> {

  private final String id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   */
  public StringId(Class<E> type, String id, long version) {
    super(type, version);
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  @Override
  public String getId() {

    return this.id;
  }

  @Override
  protected <T> AbstractId<T> newId(Class<T> newType, long version) {

    return new StringId<>(newType, this.id, version);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @return the new {@link StringId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> StringId<E> of(Class<E> type, String id) {

    return of(type, id, VERSION_LATEST);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link StringId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> StringId<E> of(Class<E> type, String id, long version) {

    if (id == null) {
      return null;
    }
    return new StringId<>(type, id, version);
  }

}
