/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.entity.api;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This is the interface for an ID that uniquely identifies an object of a particular type. The referenced object is
 * typically (but NOT strictly necessary) an {@link GenericEntity entity}. <br>
 * An {@link EntityId} is build out of the following parts:
 * <ul>
 * <li>{@link #getObjectId() object-id} - unique ID of an object of a particular type.</li>
 * <li>{@link #getTypeId() type-id} - is the ID of the type of the object identified by this {@link EntityId} .</li>
 * <li>{@link #getRevision() revision} - a object can potentially have multiple revisions (as history of changes). A
 * {@link EntityId} uniquely identifies the specific {@link #getRevision() revision}.</li>
 * </ul>
 * Just like the {@link #getObjectId() primary key} the {@link #getRevision() revision} and {@link #getTypeId() type} of
 * an object do not change. This allows to create an instance of the identified object without additional costs (e.g.
 * database lookup) by a dynamic proxy using lazy loading. <br>
 * An {@link EntityId} has a compact {@link #toString() string representation} that can be converted back to an
 * {@link EntityId}. Therefore, the implementation shall provide a {@link String}-arg constructor.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface EntityId extends SimpleDatatype<String> {

  /** This radix is used to convert numbers to/from strings. */
  int RADIX = 32;

  /**
   * An illegal value for the {@link #getObjectId() object-id}. This is reserved for undefined values.
   */
  long OBJECT_ID_ILLEGAL = -1;

  /**
   * The minimum {@link #getObjectId() object-id} that can be used for custom {@link GenericEntity entities}. All
   * object-IDs lower than this are reserved for master-data entities provided with the application. <br>
   * <b>NOTE:</b><br>
   * If you want to create a reusable and distributed extension for mmm please get in contact with us and we can try to
   * find a reserved ID space.
   */
  long OBJECT_ID_MINIMUM_CUSTOM = 1048576;

  /** The delimiter used in the string representations of an id. */
  char SEPARATOR_CHAR = '.';

  /** the delimiter used in the string representations of an id */
  String SEPARATOR = String.valueOf(SEPARATOR_CHAR);

  /**
   * This method gets the {@link net.sf.mmm.util.lang.api.attribute.AttributeReadId#getId() unique identifier} of an
   * {@link GenericEntity entity} (or any other object).
   *
   * @see GenericEntity#getId()
   *
   * @return the object-id.
   */
  long getObjectId();

  /**
   * This method gets the identifier representing the <em>type</em> of the referenced {@link GenericEntity entity} or
   * object. The type is typically directly related to the {@link Class} reflecting the object. However, in dynamically
   * or mixed typed object-worlds (often used in content-management-systems) two instances of the same java
   * {@link Class} may be considered to have different types that can only be determined at runtime. To resolve an
   * {@link EntityId} a central component is required that can map the {@link #getTypeId()} to the actual type (e.g.
   * {@link Class} object) and vice versa.
   *
   * @return the classId.
   */
  long getTypeId();

  /**
   * This method gets the number of the specific revision of the object referenced by this {@link EntityId}. If the
   * associated object is NOT version controlled (e.g. a class or field), then the revision will always be
   * {@link RevisionedEntity#LATEST_REVISION null}. <br>
   * Further a revision of {@link RevisionedEntity#LATEST_REVISION null} always points to the latest revision of an
   * object.
   *
   * @see RevisionedEntity#getRevision()
   * @see RevisionedEntity#LATEST_REVISION
   *
   * @return the revision or {@link RevisionedEntity#LATEST_REVISION null} for the latest revision.
   */
  Number getRevision();

  /**
   * The string representation of the {@link EntityId} in the following form:
   *
   * <pre>
   * &lt;{@link #getObjectId() objectId}&gt;.&lt;{@link #getTypeId()
   * typeId}&gt;[.&lt;{@link #getRevision() revision}&gt;]
   * </pre>
   *
   * The {@link #getRevision() revision} can be omitted if zero. All number values are {@link Long#toString(long, int)
   * encoded} using the {@link #RADIX} constant. <br>
   *
   * {@inheritDoc}
   */
  @Override
  String toString();

}
