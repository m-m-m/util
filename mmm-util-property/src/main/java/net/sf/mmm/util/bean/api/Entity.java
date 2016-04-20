/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api;

/**
 * This is the interface for an entity that may be loaded from or saved to a database. Typically you may want to use
 * {@link EntityBean} but for legacy technologies such as JPA you have to use standard Java Beans that can directly
 * implement this interface without implementing {@link EntityBean}. Each such entity has the following properties:
 * <ul>
 * <li>A {@link #getId() primary key}.</li>
 * <li>A {@link #getVersion() version}.</li>
 * </ul>
 *
 * @param <ID> the generic type of the {@link #getId() primary key}.
 * @author hohwille
 * @since 8.0.0
 */
public interface Entity<ID> {

  /** {@link net.sf.mmm.util.property.api.WritableProperty#getName() Property name} of {@link #getId() ID}. */
  String PROPERTY_NAME_ID = "Id";

  /** {@link net.sf.mmm.util.property.api.WritableProperty#getName() Property name} of {@link #getVersion() Version}. */
  String PROPERTY_NAME_VERSION = "Version";

  /**
   * @return the unique ID (primary key) of this entity or {@code null} if not available (e.g. entity is not
   *         persistent).
   */
  ID getId();

  /**
   * @param id the new {@link #getId() ID}.
   */
  void setId(ID id);

  /**
   * @return the {@code version} of this entity. Whenever the {@link Entity} gets updated (a modification is saved and
   *         the transaction is committed), this counter is increased. The initial value of a new {@link EntityBean} is
   *         <code>0</code>. The version acts as a modification counter for optimistic locking. On each update it will
   *         be verified that the version has not been increased already by another transaction. The version may also
   *         act as revision for auditing.
   */
  int getVersion();

  /**
   * @param version the new {@link #getVersion() version}.
   */
  void setVersion(int version);

}
