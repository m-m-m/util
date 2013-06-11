/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoPropertyDescriptor;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is the implementation of the {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyDescriptorImpl extends AbstractPojoPropertyDescriptor {

  /** @see #getAccessor(PojoPropertyAccessorMode) */
  private final Map<PojoPropertyAccessorMode<?>, PojoPropertyAccessor> accessorMap;

  /** @see #getAccessors() */
  private final Collection<? extends PojoPropertyAccessor> accessors;

  /** @see #getValidator() */
  private ValueValidator<?> validator;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   */
  public PojoPropertyDescriptorImpl(String propertyName) {

    super(propertyName);
    this.accessorMap = new HashMap<PojoPropertyAccessorMode<?>, PojoPropertyAccessor>();
    this.accessors = Collections.unmodifiableCollection(this.accessorMap.values());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(PojoPropertyAccessorMode<ACCESSOR> mode) {

    return (ACCESSOR) this.accessorMap.get(mode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<? extends PojoPropertyAccessor> getAccessors() {

    return this.accessors;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessor putAccessor(PojoPropertyAccessor accessor) {

    PojoPropertyAccessorMode<?> mode = accessor.getMode();
    return this.accessorMap.put(mode, accessor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ValueValidator<?> getValidator() {

    return this.validator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValidator(ValueValidator<?> validator) {

    if ((this.validator != null) && (this.validator != validator)) {
      throw new DuplicateObjectException(validator, "validator", this.validator);
    }
    this.validator = validator;
  }

}
