/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.id;

import net.sf.mmm.util.bean.api.entity.Entity;
import net.sf.mmm.util.bean.api.entity.EntityBean;
import net.sf.mmm.util.lang.api.Datatype;

/**
 * This is the interface for an ID that uniquely identifies an {@link Entity} of a particular
 * {@link #getType() type}.<br>
 * An {@link Id} is build out of the following parts:
 * <ul>
 * <li>{@link #getId() object-id} - the ID that identifies the entity and is unique for a specific
 * {@link #getType() type}.</li>
 * <li>{@link #getType() type} - is the ID of the type of the identified entity.</li>
 * <li>{@link #getVersion() revision} - the optional {@link #getVersion() revision} of the entity.</li>
 * </ul>
 * Just like the {@link #getId() primary key} the {@link #getVersion() revision} and {@link #getType() type}
 * of an object do not change. This allows to create an instance of the identified object without additional
 * costs (e.g. database lookup) by a dynamic proxy using lazy loading.<br>
 * An {@link Id} has a compact {@link #toString() string representation} that can be converted back to an
 * {@link Id}. Therefore, the implementation shall provide a {@link String}-arg constructor and a static
 * {@code valueOf(String)} method.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @see net.sf.mmm.util.bean.api.id.PrimaryKey
 * @author hohwille
 * @since 8.0.0
 */
public interface Id<E> extends Datatype {

  /**
   * The value used as {@link #getVersion() version} if it unspecified. If you are using an {@link Id} as link
   * to an {@link Entity} you will use this value to point to the recent version of the {@link Entity}.
   */
  long VERSION_LATEST = -1;

  /**
   * @return the the <em>primary key</em> of the <em>entity</em>. It is only unique for a particular
   *         {@link #getType() type} of an <em>entity</em>.
   */
  long getId();

  /**
   * @return the {@link Class} reflecting the <em>type</em> of the referenced <em>entity</em>.
   */
  Class<E> getType();

  /**
   * @return the {@code version} of this entity. Whenever the {@link Entity} gets updated (a modification is
   *         saved and the transaction is committed), this counter is increased. The initial value of a new
   *         {@link EntityBean} is {@code 0}. The version acts as a modification counter for optimistic
   *         locking. On each update it will be verified that the version has not been increased already by
   *         another transaction. When linking an {@link Entity} ({@link Id} used as foreign key) the version
   *         can act as revision for auditing. If it is {@link #VERSION_LATEST} it points to the latest
   *         revision of the {@link Entity}. Otherwise it points to a specific historic revision of the
   *         {@link Entity}. Depending on the database technology (e.g. when using hibernate envers) the
   *         version and the revision can be semantically different. In such case a {@link Entity#getId()
   *         primary key} can not directly be used as revisioned foreign key {@link Id}.
   */
  long getVersion();

  /**
   * @return the {@link String} representation of this {@link Id}. Will consist of {@link #getId() object-id},
   *         {@link #getType() type} and {@link #getVersion() revision} separated with a specific separator.
   *         Segments that are {@code null} will typically be omitted in the {@link String} representation.
   */
  @Override
  String toString();

}
