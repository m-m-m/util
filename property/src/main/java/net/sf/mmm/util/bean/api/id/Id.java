/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.id;

import java.util.UUID;

import net.sf.mmm.util.bean.api.entity.Entity;
import net.sf.mmm.util.bean.api.entity.EntityBean;
import net.sf.mmm.util.lang.api.Datatype;

/**
 * This is the interface for an ID that uniquely identifies an {@link Entity} of a particular {@link #getType() type}.
 * <br>
 * An {@link Id} is build out of the following parts:
 * <ul>
 * <li>{@link #getId() object-id} - the ID that identifies the entity and is unique for a specific {@link #getType()
 * type}.</li>
 * <li>{@link #getType() type} - is the ID of the type of the identified entity.</li>
 * <li>{@link #getVersion() revision} - the optional {@link #getVersion() revision} of the entity.</li>
 * </ul>
 * Just like the {@link #getId() primary key} the {@link #getVersion() revision} and {@link #getType() type} of an
 * object do not change. This allows to create an instance of the identified object without additional costs (e.g.
 * database lookup) by a dynamic proxy using lazy loading.<br>
 * An {@link Id} has a compact {@link #toString() string representation} that can be converted back to an {@link Id}.
 * Therefore, the implementation shall provide a {@link String}-arg constructor and a static {@code valueOf(String)}
 * method.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @see GenericId
 * @author hohwille
 * @since 8.0.0
 */
public interface Id<E> extends Datatype {

  /**
   * The value used as {@link #getVersion() version} if it unspecified. If you are using an {@link Id} as link to an
   * {@link Entity} you will use this value to point to the recent version of the {@link Entity}.
   */
  long VERSION_LATEST = -1;

  /** The value used as {@link #getId() id} if a {@link #getUuid() UUID} is used as <em>primary key</em> instead. */
  long ID_UUID = -1;

  /** The separator for the {@link #getVersion() version}. */
  char VERSION_SEPARATOR = '@';

  /**
   * @return the <em>primary key</em> of the identified {@link Entity} as {@code long} value. It is only unique for a
   *         particular {@link #getType() type} of an <em>entity</em>. May also be {@link #ID_UUID}.
   */
  long getId();

  /**
   * Gets the <em>primary key</em> as {@link UUID}. Most data stores will use a {@code long} provided by
   * {@link #getId()}. However a {@link UUID} can be used in the following scenarios:
   * <ul>
   * <li>Your data store uses {@link UUID}s natively as <em>primary key</em> (e.g. apache cassandra supports this). In
   * such case {@link #getId()} will always return {@link #ID_UUID} and {@link #getUuid()} holds the actual
   * <em>primary key</em>.</li>
   * <li>You may need to express a link to a transient entity. Then you can temporary assign a {@link UUID} to the
   * entity on the client and link it via such ID. On the server-side the actual {@link UUID} based {@link Id} can then
   * be replaced with the actual {@link #getId() ID} while persisting the data.</li>
   * </ul>
   * In other cases this method will simply return {@code null}.
   *
   * @return the <em>primary key</em> of the identified {@link Entity} as {@link UUID} value or {@code null} if
   *         {@link #getId()} is used.
   */
  UUID getUuid();

  /**
   * @return the {@link Class} reflecting the <em>type</em> of the referenced <em>entity</em>.
   */
  Class<E> getType();

  /**
   * @param type the value of {@link #getType()}. Exact type should actually be {@link Class}{@literal <E>} but this
   *        prevents simple usage. As the {@link #getType() type} can not actually be changed with this method, this
   *        should be fine.
   * @return an instance of {@link Id} ensured to carry the {@link #getType() type}.
   */
  Id<E> withType(Class<?> type);

  /**
   * @return this {@link Id} with a {@link #getVersion() version} of {@link #VERSION_LATEST} e.g. to use the {@link Id}
   *         as regular <em>foreign key</em> (pointing to the latest revision and not a historic revision).
   */
  Id<E> withLatestVersion();

  /**
   * @return the {@code version} of this entity. Whenever the {@link Entity} gets updated (a modification is saved and
   *         the transaction is committed), this counter is increased. The initial value of a new {@link EntityBean} is
   *         {@code 0}. The version acts as a modification counter for optimistic locking. On each update it will be
   *         verified that the version has not been increased already by another transaction. When linking an
   *         {@link Entity} ({@link Id} used as foreign key) the version can act as revision for auditing. If it is
   *         {@link #VERSION_LATEST} it points to the latest revision of the {@link Entity}. Otherwise it points to a
   *         specific historic revision of the {@link Entity}. Depending on the database technology (e.g. when using
   *         hibernate envers) the version and the revision can be semantically different. In such case a
   *         {@link Entity#getId() primary key} can not directly be used as revisioned foreign key {@link Id}.
   */
  long getVersion();

  /**
   * @return the {@link String} representation of this {@link Id}. Will consist of {@link #getId() object-id},
   *         {@link #getType() type} and {@link #getVersion() revision} separated with a specific separator. Segments
   *         that are {@code null} will typically be omitted in the {@link String} representation.
   */
  @Override
  String toString();

}
