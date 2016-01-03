/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.impl.GenericPropertyImpl;

class BeanAccessMutable extends BeanAccessInstance {

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   */
  public BeanAccessMutable(BeanAccessPrototype<?> prototype) {
    super(prototype);
  }

  @Override
  protected GenericProperty<?> createProperty(BeanPrototypeProperty prototypeProperty) {

    GenericPropertyImpl<?> property = prototypeProperty.getProperty();
    return property.createFor(getBean());
  }

}