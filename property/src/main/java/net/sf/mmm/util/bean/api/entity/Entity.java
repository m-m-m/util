/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.entity;

import net.sf.mmm.util.bean.api.id.Id;

/**
 * This is the interface for an entity that can be loaded from or saved to a database. Typically you may want
 * to use {@link EntityBean} but for legacy technologies such as JPA you have to use standard Java Beans that
 * can directly implement this interface without implementing {@link EntityBean}. Each such {@link Entity} can
 * be identified uniquely by its {@link #getId() primary key}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface Entity {

  /** {@link net.sf.mmm.util.property.api.WritableProperty#getName() Property name} of {@link #getId() ID}. */
  String PROPERTY_NAME_ID = "Id";

  /**
   * @return the unique ID (primary key) of this entity or {@code null} if not available (e.g. entity is not
   *         persistent).
   */
  Id<?> getId();

  /**
   * @param id the new {@link #getId() ID}.
   */
  void setId(Id<?> id);

}
