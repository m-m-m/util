/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;

/**
 * This is the abstract base implementation of the {@link PojoPropertyDescriptor} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractPojoPropertyDescriptor implements PojoPropertyDescriptor {

  private  final String name;

  /**
   * The constructor.
   *
   * @param propertyName is the {@link #getName() name} of the property.
   */
  public AbstractPojoPropertyDescriptor(String propertyName) {

    super();
    this.name = propertyName;
  }

  @Override
  public String getName() {

    return this.name;
  }

  /**
   * This method puts the given {@code accessor} into this property-descriptor. <br>
   * <b>ATTENTION:</b><br>
   * This method may only be used during creation and initialization of this object. Be careful NOT to accidently
   * replace existing {@link PojoPropertyAccessor accessors}.
   *
   * @see #getAccessor(net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode)
   *
   * @param accessor is the accessor to add.
   * @return the {@link PojoPropertyAccessor} with the same {@link PojoPropertyAccessor#getMode() mode} as the given
   *         {@code accessor} that has been replaced by {@code accessor} or {@code null} if none has been
   *         replaced.
   */
  public abstract PojoPropertyAccessor putAccessor(PojoPropertyAccessor accessor);

  @Override
  public String toString() {

    return "Descriptor for property " + this.name;
  }

}
