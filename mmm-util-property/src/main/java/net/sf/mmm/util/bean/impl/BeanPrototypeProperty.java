/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.AbstractProperty;
import net.sf.mmm.util.property.api.GenericProperty;

/**
 * This class is a container for a {@link GenericProperty} and its according {@link #getIndex() index} for a
 * specific {@link Bean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BeanPrototypeProperty {

  private final AbstractProperty<?> property;

  private final int index;

  /**
   * The constructor.
   *
   * @param property - see {@link #getProperty()}.
   * @param index - see {@link #getIndex()}.
   */
  public BeanPrototypeProperty(AbstractProperty<?> property, int index) {

    super();
    this.property = property;
    this.index = index;
  }

  /**
   * @return the property
   */
  public AbstractProperty<?> getProperty() {

    return this.property;
  }

  /**
   * @return the array index of the property in the {@link BeanAccessInstance}.
   */
  public int getIndex() {

    return this.index;
  }

}
