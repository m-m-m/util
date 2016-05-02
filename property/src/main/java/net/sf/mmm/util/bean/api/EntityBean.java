/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api;

import net.sf.mmm.util.lang.api.Id;
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
 * @author hohwille
 * @since 8.0.0
 */
public interface EntityBean extends Bean, Entity {

  /**
   * @return the {@link WritableProperty property} containing the {@link #getId() unique ID}.
   */
  WritableProperty<Id<?>> Id();

  /**
   * @return the {@link WritableProperty property} containing the {@link #getVersion() version}.
   */
  WritableIntegerProperty Version();

}
