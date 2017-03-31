/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.base.id;

import java.util.Objects;

import net.sf.mmm.util.data.api.id.AbstractId;
import net.sf.mmm.util.data.api.id.Id;

/**
 * This is a generic implementation of {@link Id}. Due to erasure generic code may be able to set invalid values. Hence,
 * the explicitly typed implementations are often preferable.
 *
 * @param <E> the generic type of the identified entity.
 * @param <I> the generic type of the {@link #getId() ID}.
 * @param <V> the generic type of the {@link #getVersion() Version}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class GenericId<E, I, V extends Comparable<?>> extends AbstractId<E, I, V> {

  private final I id;

  private final V version;

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @param version - see {@link #getVersion()}.
   */
  protected GenericId(Class<E> type, I id, V version) {
    super(type);
    Objects.requireNonNull(id, "id");
    this.id = id;
    this.version = version;
  }

  @Override
  public V getVersion() {

    return this.version;
  }

  @Override
  public I getId() {

    return this.id;
  }

  @Override
  protected <T> GenericId<T, I, V> newId(Class<T> newType, V newVersion) {

    return new GenericId<>(newType, this.id, newVersion);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param <I> the generic type of the {@link #getId() ID}.
   * @param <V> the generic type of the {@link #getVersion() Version}.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link LongInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E, I, V extends Comparable<?>> GenericId<E, I, V> of(Class<E> type, I id, V version) {

    if (id == null) {
      return null;
    }
    return new GenericId<>(type, id, version);
  }

}
