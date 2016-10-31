/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.entity;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.data.api.entity.Entity;
import net.sf.mmm.util.data.api.id.Id;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for an {@link Entity} as a {@link Bean}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface EntityBean extends Bean, Entity {

  /**
   * @return the {@link WritableProperty property} containing the {@link #getId() primary key}.
   */
  WritableProperty<Id<?>> Id();

}
