/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api.accessor;

/**
 * An instance of this class represents a specific {@link PojoPropertyAccessorMode accessor-mode} for a
 * {@link PojoPropertyAccessorNonArg non-arg accessor} of a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}. This abstract base class acts like an
 * {@link Enum} but allows you to define your own custom mode by extending this class.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorNonArgMode extends PojoPropertyAccessorMode<PojoPropertyAccessorNonArg> {

  /**
   * The mode for a regular {@link PojoPropertyAccessorNonArg reader} of a property.
   */
  public static final PojoPropertyAccessorNonArgMode GET = new PojoPropertyAccessorNonArgMode("get");

  /**
   * The mode for a {@link PojoPropertyAccessorNonArg reader} of the size of a property that holds a container type
   * (array or {@link java.util.Collection} ). <br>
   * <b>ATTENTION:</b><br>
   * The accessor may NOT only {@link PojoPropertyAccessorNonArg#invoke(Object) return} an {@link Integer}. It is also
   * possible that it returns a {@link Byte} or {@link Short}. Use {@link Number} to avoid problems.
   */
  public static final PojoPropertyAccessorNonArgMode GET_SIZE = new PojoPropertyAccessorNonArgMode("get-size");

  /**
   * The constructor.
   *
   * @param name is the {@link #getName() name} of this mode.
   */
  protected PojoPropertyAccessorNonArgMode(String name) {

    super(name, true);
  }

}
