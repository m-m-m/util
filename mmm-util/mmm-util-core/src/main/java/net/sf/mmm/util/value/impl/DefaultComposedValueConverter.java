/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.annotation.Resource;

import net.sf.mmm.util.date.Iso8601Util;
import net.sf.mmm.util.math.MathUtil;

/**
 * This is a default {@link net.sf.mmm.util.value.api.ComposedValueConverter}.
 * It extends {@link ComposedValueConverterImpl} by
 * {@link #addConverter(net.sf.mmm.util.value.api.ValueConverter) adding}
 * typical {@link net.sf.mmm.util.value.api.ValueConverter}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DefaultComposedValueConverter extends ComposedValueConverterImpl {

  /** @see #getIso8601Util() */
  private Iso8601Util iso8601Util;

  /** @see #getMathUtil() */
  private MathUtil mathUtil;

  /**
   * The constructor.
   */
  public DefaultComposedValueConverter() {

    super();
  }

  /**
   * This method gets the {@link Iso8601Util} to use.
   * 
   * @return the {@link Iso8601Util} instance.
   */
  protected Iso8601Util getIso8601Util() {

    return this.iso8601Util;
  }

  /**
   * This method sets (injects) the {@link Iso8601Util} to use.
   * 
   * @param iso8601Util is the {@link Iso8601Util} to use.
   */
  @Resource
  public void setIso8601Util(Iso8601Util iso8601Util) {

    getInitializationState().requireNotInitilized();
    this.iso8601Util = iso8601Util;
  }

  /**
   * This method gets the {@link MathUtil} to use.
   * 
   * @return the {@link MathUtil}.
   */
  protected MathUtil getMathUtil() {

    return this.mathUtil;
  }

  /**
   * This method sets (injects) the {@link MathUtil} to use.
   * 
   * @param mathUtil is the {@link MathUtil} to use.
   */
  @Resource
  public void setMathUtil(MathUtil mathUtil) {

    getInitializationState().requireNotInitilized();
    this.mathUtil = mathUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.iso8601Util == null) {
      this.iso8601Util = Iso8601Util.getInstance();
    }
    if (this.mathUtil == null) {
      this.mathUtil = MathUtil.getInstance();
    }
    addConverter(new ValueConverterToDate(this.iso8601Util));
    addConverter(new ValueConverterToCalendar(this.iso8601Util));
    addConverter(new ValueConverterToNumber(this.mathUtil));
    addConverter(new ValueConverterToString(this.iso8601Util));
  }

}
