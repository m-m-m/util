/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;

/**
 * This class extends {@link BeanAccessPrototypeExternal} for a {@link #isVirtual() virtual}
 * {@link BeanFactory#createPrototype(Class) prototype} of a {@link Bean}.
 *
 * @param <BEAN> the generic type of the {@link Bean}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class BeanAccessPrototypeVirtual<BEAN extends Bean> extends BeanAccessPrototypePolymorphic<BEAN> {

  /**
   * The constructor.
   *
   * @param prototypeBuilder - see {@link #getPrototypeBuilder()}.
   * @param master the {@link BeanAccessPrototype} to copy.
   * @param dynamic - see {@link #isDynamic()}.
   * @param qualifiedName - see {@link #getQualifiedName()}.
   * @param interfaces - the interfaces to be implemented by the {@link #getBean() dynamic proxy}.
   */
  public BeanAccessPrototypeVirtual(BeanPrototypeBuilderImpl prototypeBuilder, BeanAccessPrototype<BEAN> master,
      boolean dynamic, String qualifiedName, Class<?>... interfaces) {
    super(prototypeBuilder, master, dynamic, qualifiedName, interfaces);
  }

  @Override
  public boolean isVirtual() {

    return true;
  }

}
