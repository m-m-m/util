/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.id;

import java.util.UUID;

import net.sf.mmm.util.exception.api.ObjectMismatchException;

/**
 * This is the implementation of {@link Id} for a {@link #getId() long based} <em>primary key</em>.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class GenericId<E> extends AbstractId<E> {

  private static final long serialVersionUID = 1L;

  private UUID uuid;

  private long id;

  private long version;

  /**
   * The constructor.
   */
  protected GenericId() {
    super();
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @param version - see {@link #getVersion()}.
   */
  public GenericId(Class<E> type, long id, long version) {
    this(type, null, id, version);
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param uuid - see {@link #getUuid()}.
   * @param version - see {@link #getVersion()}.
   */
  public GenericId(Class<E> type, UUID uuid, long version) {
    this(type, uuid, ID_UUID, version);
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param uuid - see {@link #getUuid()}.
   * @param id - see {@link #getId()}.
   * @param version - see {@link #getVersion()}.
   */
  private GenericId(Class<E> type, UUID uuid, long id, long version) {
    super(type);
    this.id = id;
    this.uuid = uuid;
    this.version = version;
  }

  @Override
  public UUID getUuid() {

    return this.uuid;
  }

  @Override
  public long getId() {

    return this.id;
  }

  @Override
  public long getVersion() {

    return this.version;
  }

  @SuppressWarnings("unchecked")
  @Override
  public GenericId<E> withType(Class<?> type) {

    Class<E> currentType = getType();
    if (currentType == null) {
      return new GenericId<>((Class<E>) type, getId(), this.version);
    } else if (currentType != type) {
      throw new ObjectMismatchException(type, currentType, this);
    }
    return this;
  }

  @Override
  public Id<E> withLatestVersion() {

    if (this.version == VERSION_LATEST) {
      return this;
    }
    return new GenericId<>(getType(), this.uuid, this.id, VERSION_LATEST);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @param version - see {@link #getVersion()}.
   * @return a new instance of {@link GenericId}.
   */
  public static <E> GenericId<E> of(Class<E> type, Long id, long version) {

    if (id == null) {
      return null;
    }
    return of(type, id.longValue(), version);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @return a new instance of {@link GenericId}.
   */
  public static <E> GenericId<E> of(Class<E> type, Long id) {

    if (id == null) {
      return null;
    }
    return of(type, id.longValue(), VERSION_LATEST);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @return a new instance of {@link GenericId}.
   */
  public static <E> GenericId<E> of(Class<E> type, long id) {

    return of(type, id, VERSION_LATEST);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @param version - see {@link #getVersion()}.
   * @return a new instance of {@link GenericId}.
   */
  public static <E> GenericId<E> of(Class<E> type, long id, long version) {

    return new GenericId<>(type, id, version);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param uuid - see {@link #getUuid()}.
   * @return a new instance of {@link GenericId}.
   */
  public static <E> GenericId<E> of(Class<E> type, UUID uuid) {

    return of(type, uuid, VERSION_LATEST);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param uuid - see {@link #getUuid()}.
   * @param version - see {@link #getVersion()}.
   * @return a new instance of {@link GenericId}.
   */
  public static <E> GenericId<E> of(Class<E> type, UUID uuid, long version) {

    if (uuid == null) {
      return null;
    }
    return new GenericId<>(type, uuid, version);
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param idAsString the {@link #toString() string representation of the ID}.
   * @return a new instance of {@link GenericId}.
   */
  public static <E> GenericId<E> of(Class<E> type, String idAsString) {

    if (idAsString == null) {
      return null;
    }
    int versionSeparatorIndex = idAsString.indexOf(VERSION_SEPARATOR);
    String idString;
    long version;
    if (versionSeparatorIndex < 0) {
      idString = idAsString;
      version = VERSION_LATEST;
    } else {
      idString = idAsString.substring(0, versionSeparatorIndex);
      version = Long.parseLong(idAsString.substring(versionSeparatorIndex + 1));
    }
    UUID uuid = parseUuid(idString);
    if (uuid != null) {
      return of(type, uuid, version);
    } else {
      return of(type, Long.parseLong(idString), version);
    }
  }

}
