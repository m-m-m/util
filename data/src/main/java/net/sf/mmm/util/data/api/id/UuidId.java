/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.api.id;

import java.util.Objects;
import java.util.UUID;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;
import net.sf.mmm.util.lang.api.attribute.AttributeReadUuid;

/**
 * This is the implementation of {@link Id} that holds its {@link #getId() primary key} as a {@code UUID} value. Can be
 * used in two scenarios:
 * <ul>
 * <li>Your data store uses {@link UUID}s natively as <em>primary key</em> (e.g. apache cassandra supports this). In
 * such case you will always directly use a {@link UUID} as the actual <em>primary key</em>.</li>
 * <li>You may need to express a link to a transient entity. Then you can temporary assign a {@link UUID} to the entity
 * on the client and link it via such ID. On the server-side the actual {@link UuidId} will then be replaced with the
 * actual {@link #getId() ID} while persisting the data.</li>
 * </ul>
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class UuidId<E> extends AbstractId<E> implements AttributeReadId<UUID>, AttributeReadUuid {

  private final UUID id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getUuid()}.
   * @param version the {@link #getVersion() version}.
   */
  public UuidId(Class<E> type, UUID id, long version) {
    super(type, version);
    Objects.requireNonNull(id, "id");
    this.id = id;
  }

  @Override
  public UUID getId() {

    return this.id;
  }

  @Override
  public UUID getUuid() {

    return this.id;
  }

  @Override
  protected <T> AbstractId<T> newId(Class<T> newType, long version) {

    return new UuidId<>(newType, this.id, version);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getUuid()}.
   * @return the new {@link UuidId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> UuidId<E> of(Class<E> type, UUID id) {

    return of(type, id, VERSION_LATEST);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getUuid()}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link UuidId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> UuidId<E> of(Class<E> type, UUID id, long version) {

    if (id == null) {
      return null;
    }
    return new UuidId<>(type, id, version);
  }

}
