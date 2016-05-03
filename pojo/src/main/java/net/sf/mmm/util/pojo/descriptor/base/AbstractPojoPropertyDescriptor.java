/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import java.lang.reflect.Field;

import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;

/**
 * This is the abstract base implementation of the {@link PojoPropertyDescriptor} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractPojoPropertyDescriptor implements PojoPropertyDescriptor {

  /** @see #getName() */
  private final String name;

  /** @see #getField() */
  private Field field;

  /**
   * The constructor.
   *
   * @param propertyName is the {@link #getName() name} of the property.
   */
  public AbstractPojoPropertyDescriptor(String propertyName) {

    super();
    this.name = propertyName;
    this.field = null;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public Field getField() {

    return this.field;
  }

  /**
   * Sets the {@link Field} represented by this {@link PojoPropertyDescriptor descriptor}. Necessary in case of
   * {@link Field field} gets introspected after methods.
   *
   * @param field {@link Field} represented by this {@link PojoPropertyDescriptor descriptor}
   */
  public void setField(Field field) {

    this.field = field;
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
