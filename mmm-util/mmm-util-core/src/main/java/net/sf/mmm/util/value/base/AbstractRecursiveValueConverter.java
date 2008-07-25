/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import javax.annotation.Resource;

import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.value.api.ComposedValueConverter;

/**
 * This is an abstract base-implementation for a
 * {@link net.sf.mmm.util.value.api.ValueConverter} that performs recursive
 * conversions on the {@link #getComposedValueConverter() owning-converter} for
 * converting child values.
 * 
 * @param <SOURCE> is the generic {@link #getSourceType() source-type}.
 * @param <TARGET> is the generic {@link #getTargetType() target-type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractRecursiveValueConverter<SOURCE, TARGET> extends
    AbstractValueConverter<SOURCE, TARGET> {

  /** @see #getComposedValueConverter() */
  private ComposedValueConverter composedValueConverter;

  /**
   * The constructor.
   */
  public AbstractRecursiveValueConverter() {

    super();
  }

  /**
   * This method gets the {@link ComposedValueConverter} owing this converter.
   * It is required for recursive invocations during conversion.
   * 
   * @return the {@link ComposedValueConverter}.
   */
  protected ComposedValueConverter getComposedValueConverter() {

    return this.composedValueConverter;
  }

  /**
   * This method sets (injects) the
   * {@link #getComposedValueConverter() composedValueConverter}. It has to be
   * called before {@link #initialize()} is invoked.
   * 
   * @param composedValueConverter is the {@link ComposedValueConverter} to set.
   */
  @Resource
  public void setComposedValueConverter(ComposedValueConverter composedValueConverter) {

    getInitializationState().requireNotInitilized();
    this.composedValueConverter = composedValueConverter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.composedValueConverter == null) {
      throw new ResourceMissingException("composedValueConverter");
    }
  }

}
