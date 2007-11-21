/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api.accessor;

import net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor;

/**
 * An instance of this class represents a specific
 * {@link PojoPropertyAccessorMode accessor-mode} for a
 * {@link PojoPropertyAccessorOneArg one-arg accessor} of a
 * {@link PojoPropertyDescriptor property}. This abstract base class acts like
 * an {@link Enum} but allows you to define your own custom mode by extending
 * this class.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class PojoPropertyAccessorOneArgMode extends
    PojoPropertyAccessorMode<PojoPropertyAccessorOneArg> {

  /**
   * The mode for a regular {@link PojoPropertyAccessorOneArg writer} of a
   * property.
   */
  public static final PojoPropertyAccessorOneArgMode SET = new PojoPropertyAccessorOneArgMode("set") {};

  /**
   * The mode for an {@link PojoPropertyAccessorOneArg accessor} used to add an
   * item to a list-like property.
   */
  public static final PojoPropertyAccessorOneArgMode ADD = new PojoPropertyAccessorOneArgMode("add") {};

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of this mode.
   */
  public PojoPropertyAccessorOneArgMode(String name) {

    super(name);
  }

}
