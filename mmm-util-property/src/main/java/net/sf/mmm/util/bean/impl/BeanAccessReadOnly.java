/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.impl.ReadOnlyPropertyImpl;

class BeanAccessReadOnly extends BeanAccessInstance {

  private final BeanAccessBase delegate;

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param delegate the original intercepter instance to adapt.
   */
  public BeanAccessReadOnly(BeanAccessPrototype<?> prototype, BeanAccessBase delegate) {
    super(prototype);
    this.delegate = delegate;
  }

  @Override
  protected GenericProperty<?> createProperty(BeanPrototypeProperty prototypeProperty) {

    GenericProperty<?> property = this.delegate.getProperty(prototypeProperty, true);
    return new ReadOnlyPropertyImpl<>(property);
  }

  @Override
  public boolean isReadOnly() {

    return true;
  }

}