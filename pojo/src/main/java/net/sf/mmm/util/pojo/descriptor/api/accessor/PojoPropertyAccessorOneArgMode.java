/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api.accessor;

/**
 * An instance of this class represents a specific {@link PojoPropertyAccessorMode accessor-mode} for a
 * {@link PojoPropertyAccessorOneArg one-arg accessor} of a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}. This abstract base class acts like an
 * {@link Enum} but allows you to define your own custom mode by extending this class.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorOneArgMode extends PojoPropertyAccessorMode<PojoPropertyAccessorOneArg> {

  /**
   * The mode for a regular {@link PojoPropertyAccessorOneArg setter} of a property.
   */
  public static final PojoPropertyAccessorOneArgMode SET = new PojoPropertyAccessorOneArgMode("set", false);

  /**
   * The mode for an {@link PojoPropertyAccessorOneArg accessor} used to add an item to a property with a container
   * (array or {@link java.util.Collection}) {@link PojoPropertyAccessor#getPropertyType() type}. The accessor will add
   * the item given as argument to the end of the container (if ordered). If the container is an array a copy with a
   * size increased by {@code 1} has to be created. Therefore a virtual add-accessor can only be generated for
   * array-type getters if also an according setter is present.
   *
   * @see java.util.Collection#add(Object)
   */
  public static final PojoPropertyAccessorOneArgMode ADD = new PojoPropertyAccessorOneArgMode("add", false);

  /**
   * The mode for an {@link PojoPropertyAccessorOneArg accessor} used to remove an item from a property with an
   * container (array or {@link java.util.Collection}) {@link PojoPropertyAccessor#getPropertyType() type}. The accessor
   * will remove the first occurrence of the item given as argument from the container. It will return {@code true} on
   * success and {@code false} if the item was NOT found in the container. If the container is an array a copy with a
   * size decreased by {@code 1} has to be created. Therefore a virtual remove-accessor can only be generated for
   * array-type getters if also an according setter is present.
   *
   * @see java.util.Collection#remove(Object)
   */
  public static final PojoPropertyAccessorOneArgMode REMOVE = new PojoPropertyAccessorOneArgMode("remove", false);

  /**
   * The mode for a {@link PojoPropertyAccessorOneArg getter} of a mapped property. <br>
   * For example an {@link PojoPropertyAccessorOneArg#invoke(Object, Object) invoke} with argument {@code key} for a
   * property {@link #getName() named} "colors" might cause a {@code pojo.getColors().get(key)} or
   * {@code pojo.getColors(key)} or even {@code pojo.getColor(key)}.
   */
  public static final PojoPropertyAccessorOneArgMode GET_MAPPED = new PojoPropertyAccessorOneArgMode("get-mapped",
      true);

  /**
   * The constructor.
   *
   * @param name is the {@link #getName() name} of this mode.
   * @param reading is a flag that determines if this mode is for {@link #isReading() reading}.
   */
  protected PojoPropertyAccessorOneArgMode(String name, boolean reading) {

    super(name, reading);
  }

}
