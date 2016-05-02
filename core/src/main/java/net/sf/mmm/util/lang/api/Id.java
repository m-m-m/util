/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for an ID that uniquely identifies an <em>entity</em> of a particular type.<br>
 * An {@link Id} is build out of the following parts:
 * <ul>
 * <li>{@link #getId() object-id} - the ID that identifies the entity and is unique for a specific {@link #getType()
 * type}.</li>
 * <li>{@link #getType() type} - is the ID of the type of the identified entity.</li>
 * <li>{@link #getRevision() revision} - the optional {@link #getRevision() revision} of the entity.</li>
 * </ul>
 * Just like the {@link #getId() primary key} the {@link #getRevision() revision} and {@link #getType() type} of an
 * object do not change. This allows to create an instance of the identified object without additional costs (e.g.
 * database lookup) by a dynamic proxy using lazy loading.<br>
 * An {@link Id} has a compact {@link #toString() string representation} that can be converted back to an {@link Id}.
 * Therefore, the implementation shall provide a {@link String}-arg constructor and a static {@code valueOf(String)}
 * method.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @see net.sf.mmm.util.lang.base.SimpleId
 * @author hohwille
 * @since 7.1.0
 */
public interface Id<E> extends Datatype {

  /**
   * @return the the <em>primary key</em> of the <em>entity</em>. It is only unique for a particular {@link #getType()
   *         type} of an <em>entity</em>.
   */
  long getId();

  /**
   * @return the {@link Class} reflecting the <em>type</em> of the referenced <em>entity</em>.
   */
  Class<E> getType();

  /**
   * @return the optional revision of the <em>entity</em> referenced by this {@link Id} or {@code null} for the latest
   *         revision. In case entities of this {@link #getType() type} will not be revision controlled, this method
   *         will always return {@code null}.
   */
  Number getRevision();

  /**
   * @return the {@link String} representation of this {@link Id}. Will consist of {@link #getId() object-id},
   *         {@link #getType() type} and {@link #getRevision() revision} separated with a specific separator. Segments
   *         that are {@code null} will typically be omitted in the {@link String} representation.
   */
  @Override
  String toString();

}
