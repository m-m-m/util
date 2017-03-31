/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.base.id;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import net.sf.mmm.util.data.api.id.Id;
import net.sf.mmm.util.lang.api.attribute.AttributeReadId;
import net.sf.mmm.util.lang.api.attribute.AttributeReadUuid;

/**
 * This is the implementation of {@link Id} that holds its {@link #getId() primary key} as a {@code UUID} value. Can be
 * used in two scenarios:
 * <ul>
 * <li>Your data store uses {@link UUID}s natively as <em>primary key</em> (e.g. apache cassandra supports this). In
 * such case you will always directly use a {@link UUID} as the actual <em>primary key</em>.</li>
 * <li>You may need to express a link to a transient entity. Then you can temporary assign a {@link UUID} to the entity
 * on the client and link it via such ID. On the server-side the actual {@link UuidInstantId} will then be replaced with
 * the actual {@link #getId() ID} while persisting the data.</li>
 * </ul>
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class UuidInstantId<E> extends AbstractInstantId<E, UUID> implements AttributeReadId<UUID>, AttributeReadUuid {

  private final UUID id;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getUuid()}.
   * @param version the {@link #getVersion() version}.
   */
  public UuidInstantId(Class<E> type, UUID id, Instant version) {
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
  protected <T> UuidInstantId<T> newId(Class<T> newType, Instant newVersion) {

    return new UuidInstantId<>(newType, this.id, newVersion);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getUuid()}.
   * @return the new {@link UuidInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> UuidInstantId<E> of(Class<E> type, UUID id) {

    return of(type, id, null);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type the {@link #getType() type}.
   * @param id the {@link #getId() primary key}. See {@link #getUuid()}.
   * @param version the {@link #getVersion() version}.
   * @return the new {@link UuidInstantId} or {@code null} if the given {@code id} was {@code null}.
   */
  public static <E> UuidInstantId<E> of(Class<E> type, UUID id, Instant version) {

    if (id == null) {
      return null;
    }
    return new UuidInstantId<>(type, id, version);
  }

}
