/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api.accessor;

/**
 * An instance of this class represents a specific {@link PojoPropertyAccessorMode accessor-mode} for a
 * {@link PojoPropertyAccessorIndexedOneArg one-arg accessor} of a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}. This abstract base class acts
 * like an {@link Enum} but allows you to define your own custom mode by extending this class.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorIndexedNonArgMode extends PojoPropertyAccessorMode<PojoPropertyAccessorIndexedNonArg> {

  /**
   * The mode for a {@link PojoPropertyAccessorIndexedOneArg setter of an indexed property}.
   */
  public static final PojoPropertyAccessorIndexedNonArgMode GET_INDEXED = new PojoPropertyAccessorIndexedNonArgMode(
      "get-indexed");

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of this mode.
   */
  protected PojoPropertyAccessorIndexedNonArgMode(String name) {

    super(name, true);
  }

}
