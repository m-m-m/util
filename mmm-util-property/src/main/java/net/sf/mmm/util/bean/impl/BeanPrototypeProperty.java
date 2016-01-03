/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import net.sf.mmm.util.property.impl.GenericPropertyImpl;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public class BeanPrototypeProperty {

  private final GenericPropertyImpl<?> property;

  private final int index;

  /**
   * The constructor.
   *
   * @param property - see {@link #getProperty()}.
   * @param index - see {@link #getIndex()}.
   */
  public BeanPrototypeProperty(GenericPropertyImpl<?> property, int index) {

    super();
    this.property = property;
    this.index = index;
  }

  /**
   * @return the property
   */
  public GenericPropertyImpl<?> getProperty() {

    return this.property;
  }

  /**
   * @return the index
   */
  public int getIndex() {

    return this.index;
  }

}
