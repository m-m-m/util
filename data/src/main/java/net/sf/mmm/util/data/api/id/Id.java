/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.api.id;

import net.sf.mmm.util.data.api.entity.Entity;
import net.sf.mmm.util.data.base.id.LongVersionId;
import net.sf.mmm.util.data.base.id.StringVersionId;
import net.sf.mmm.util.data.base.id.UuidVersionId;
import net.sf.mmm.util.lang.api.Datatype;

/**
 * This is the interface for an ID that uniquely identifies an {@link net.sf.mmm.util.data.api.entity.Entity} of a
 * particular {@link #getType() type}. <br>
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
 * @see AbstractId
 * @author hohwille
 * @since 8.5.0
 */
public interface Id<E> extends Datatype {

  /** The name of the {@link #getId() ID} property (e.g. for JSON or XML). */
  String PROPERTY_ID = "id";

  /** The name of the {@link #getVersion() version} property (e.g. for JSON or XML). */
  String PROPERTY_VERSION = "version";

  /**
   * The value used as {@link #getVersion() version} if it unspecified. If you are using an {@link Id} as link to an
   * {@link Entity} you will use this value to point to the recent version of the
   * {@link net.sf.mmm.util.data.api.entity.Entity}.
   */
  Comparable<?> VERSION_LATEST = null;

  /** The separator for the {@link #getVersion() version}. */
  char VERSION_SEPARATOR = '@';

  /**
   * @see LongVersionId
   * @see UuidVersionId
   * @see StringVersionId
   *
   * @return the <em>primary key</em> of the identified {@link Entity} as {@link Object} value. It is only unique for a
   *         particular {@link #getType() type} of an <em>entity</em>.
   */
  Object getId();

  /**
   * @return the {@link Class} reflecting the <em>type</em> of the referenced <em>entity</em>.
   */
  Class<E> getType();

  /**
   * @param type the new value of {@link #getType()}. Exact type should actually be {@link Class}{@literal <E>} but this
   *        prevents simple usage. As the {@link #getType() type} can not actually be changed with this method, this
   *        should be fine.
   * @return a copy of this {@link Id} with the given {@link #getType() type} or this {@link Id} itself if already
   *         satisfying.
   */
  Id<E> withType(Class<?> type);

  /**
   * @return a copy of this {@link Id} with a {@link #getVersion() version} of {@link #VERSION_LATEST} e.g. to use the
   *         {@link Id} as regular <em>foreign key</em> (pointing to the latest revision and not a historic revision) or
   *         this {@link Id} itself if already satisfying.
   */
  Id<E> withLatestVersion();

  /**
   * @return the {@code version} of this entity. Whenever the {@link net.sf.mmm.util.data.api.entity.Entity} gets
   *         updated (a modification is saved and the transaction is committed), this version is increased. Typically
   *         the version is a {@link Number} starting with {@code 0} for a new
   *         {@link net.sf.mmm.util.data.api.entity.Entity} that is increased whenever a modification is committed.
   *         However, it may also be an {@link java.time.Instant}. The version acts as a modification sequence for
   *         optimistic locking. On each update it will be verified that the version has not been increased already by
   *         another transaction. When linking an {@link net.sf.mmm.util.data.api.entity.Entity} ({@link Id} used as
   *         foreign key) the version can act as revision for auditing. If it is {@link #VERSION_LATEST} ({@code null})
   *         it points to the latest revision of the {@link net.sf.mmm.util.data.api.entity.Entity}. Otherwise it points
   *         to a specific historic revision of the {@link net.sf.mmm.util.data.api.entity.Entity}. Depending on the
   *         database technology (e.g. when using hibernate envers) the version and the revision can be semantically
   *         different. In such case a {@link net.sf.mmm.util.data.api.entity.Entity#getId() primary key} can not be
   *         converted 1:1 as revisioned foreign key {@link Id}.
   */
  Comparable<?> getVersion();

  /**
   * @return the {@link String} representation of this {@link Id}. Will consist of {@link #getId() object-id},
   *         {@link #getType() type} and {@link #getVersion() revision} separated with a specific separator. Segments
   *         that are {@code null} will typically be omitted in the {@link String} representation.
   */
  @Override
  String toString();

}
