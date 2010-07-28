/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to a {@link Boolean}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
public class ValueConverterToBoolean extends AbstractSimpleValueConverter<Object, Boolean> {

  /** @see #getStringUtil() */
  private StringUtil stringUtil;

  /**
   * The constructor.
   */
  public ValueConverterToBoolean() {

    super();
  }

  /**
   * This method gets the {@link StringUtil} to use.
   * 
   * @return the {@link StringUtil} instance.
   */
  protected StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * This method sets the {@link StringUtil} to use.
   * 
   * @param stringUtil is the {@link StringUtil} instance.
   */
  @Resource
  public void setStringUtil(StringUtil stringUtil) {

    this.stringUtil = stringUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.stringUtil == null) {
      this.stringUtil = StringUtilImpl.getInstance();
    }
  }

  /**
   * {@inheritDoc}
   */
  public Class<Object> getSourceType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Boolean> getTargetType() {

    return Boolean.class;
  }

  /**
   * {@inheritDoc}
   */
  public Boolean convert(Object value, Object valueSource, Class<? extends Boolean> targetClass) {

    if (value == null) {
      return null;
    }
    String valueAsString = value.toString();
    return this.stringUtil.parseBoolean(valueAsString);
  }

}
