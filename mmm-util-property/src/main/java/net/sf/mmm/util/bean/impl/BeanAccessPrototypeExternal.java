/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;

/**
 * This is the implementation of {@link net.sf.mmm.util.bean.api.BeanAccess} for an external
 * {@link BeanFactory#createPrototype(Class) prototype} of a {@link Bean}.
 *
 * @param <BEAN> the generic type of the {@link Bean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BeanAccessPrototypeExternal<BEAN extends Bean> extends BeanAccessPrototype<BEAN> {

  private final boolean dynamic;

  /**
   * The constructor.
   *
   * @param master the {@link BeanAccessPrototype} to copy.
   * @param dynamic - see {@link #isDynamic()}.
   * @param qualifiedName - see {@link #getQualifiedName()}.
   */
  public BeanAccessPrototypeExternal(BeanAccessPrototype<BEAN> master, boolean dynamic, String qualifiedName) {
    this(master, dynamic, qualifiedName, master.getBeanClass());
  }

  /**
   * The constructor.
   *
   * @param master the {@link BeanAccessPrototype} to copy.
   * @param dynamic - see {@link #isDynamic()}.
   * @param qualifiedName - see {@link #getQualifiedName()}.
   * @param interfaces - the interfaces to be implemented by the {@link #getBean() dynamic proxy}.
   */
  protected BeanAccessPrototypeExternal(BeanAccessPrototype<BEAN> master, boolean dynamic, String qualifiedName,
      Class<?>... interfaces) {
    super(master, qualifiedName, dynamic, interfaces);
    this.dynamic = dynamic;
  }

  @Override
  public boolean isDynamic() {

    return this.dynamic;
  }

}
