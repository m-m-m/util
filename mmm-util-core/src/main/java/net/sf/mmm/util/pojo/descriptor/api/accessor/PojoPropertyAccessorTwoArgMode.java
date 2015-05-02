/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api.accessor;

/**
 * An instance of this class represents a specific {@link PojoPropertyAccessorMode accessor-mode} for a
 * {@link PojoPropertyAccessorTwoArg two-arg accessor} of a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}. This abstract base class acts
 * like an {@link Enum} but allows you to define your own custom mode by extending this class.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorTwoArgMode extends PojoPropertyAccessorMode<PojoPropertyAccessorTwoArg> {

  /**
   * The mode for the {@link PojoPropertyAccessorOneArg setter} of a mapped property. The suggested argument
   * order is <code>key, value</code>.
   */
  public static final PojoPropertyAccessorTwoArgMode SET_MAPPED = new PojoPropertyAccessorTwoArgMode("set-mapped",
      false);

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of this mode.
   * @param reading is a flag that determines if this mode is for {@link #isReading() reading}.
   */
  protected PojoPropertyAccessorTwoArgMode(String name, boolean reading) {

    super(name, reading);
  }

}
