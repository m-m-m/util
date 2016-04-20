/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api;

import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.WritableIntegerProperty;

/**
 * This is the interface for an entity that may be loaded from or saved to a database. Each such entity has the
 * following properties:
 * <ul>
 * <li>A {@link #Id() primary key}.</li>
 * <li>A {@link #Version() version}.</li>
 * </ul>
 *
 * @param <ID> the generic type of the {@link #Id() primary key}.
 * @author hohwille
 * @since 8.0.0
 */
public interface EntityBean<ID> extends Bean, Entity<ID> {

  /**
   * @return the {@link WritableProperty property} containing the {@link #getId() unique ID}.
   */
  WritableProperty<ID> Id();

  /**
   * @return the {@link #getId() ID}.
   */
  ID getId();

  /**
   * @param id the {@link #getId() ID}.
   */
  void setId(ID id);

  /**
   * @return the {@link WritableProperty property} containing the {@link #getVersion() version}.
   */
  WritableIntegerProperty Version();

  /**
   * @return the {@link #Version() version}.
   */
  int getVersion();

  /**
   * @param version the {@link #Version() version}.
   */
  void setVersion(int version);

}
