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

  /**
   * This constructor interconnects this {@link PojoPropertyDescriptor} with the given field
   * @param field sets the {@link Field} for and the property's {@link #getName() name}
   */
  public AbstractPojoPropertyDescriptor(Field field) {
	
	super();
	this.name = field.getName();
	this.field = field;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return this.name;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Field getField() {
	return field;
  }
  
  /**
   * Sets the {@link Field} represented by this {@link PojoPropertyDescriptor descriptor}. 
   * Necessary in case of {@link Field field} gets introspected after methods.
   * @param field {@link Field} represented by this {@link PojoPropertyDescriptor descriptor}
   */
  public void setField(Field field) {
	  this.field = field;
  }

  /**
   * This method puts the given <code>accessor</code> into this property-descriptor. <br>
   * <b>ATTENTION:</b><br>
   * This method may only be used during creation and initialization of this object. Be careful NOT to
   * accidently replace existing {@link PojoPropertyAccessor accessors}.
   * 
   * @see #getAccessor(net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode)
   * 
   * @param accessor is the accessor to add.
   * @return the {@link PojoPropertyAccessor} with the same {@link PojoPropertyAccessor#getMode() mode} as the
   *         given <code>accessor</code> that has been replaced by <code>accessor</code> or <code>null</code>
   *         if none has been replaced.
   */
  public abstract PojoPropertyAccessor putAccessor(PojoPropertyAccessor accessor);

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "Descriptor for property " + this.name;
  }

}
