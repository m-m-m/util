/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.annotation.Resource;

import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.date.Iso8601Util;
import net.sf.mmm.util.math.MathUtil;
import net.sf.mmm.util.reflect.CollectionReflectionUtil;

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

  /** @see #getStringUtil() */
  private StringUtil stringUtil;

  /** @see #getCollectionReflectionUtil() */
  private CollectionReflectionUtil collectionReflectionUtil;

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
   * This method gets the {@link StringUtil} to use.
   * 
   * @return the {@link StringUtil} instance.
   */
  protected StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * @param stringUtil is the stringUtil to set
   */
  @Resource
  public void setStringUtil(StringUtil stringUtil) {

    getInitializationState().requireNotInitilized();
    this.stringUtil = stringUtil;
  }

  /**
   * This method gets the {@link CollectionReflectionUtil} instance to use.
   * 
   * @return the {@link CollectionReflectionUtil} to use.
   */
  protected CollectionReflectionUtil getCollectionReflectionUtil() {

    return this.collectionReflectionUtil;
  }

  /**
   * @param collectionReflectionUtil is the collectionReflectionUtil to set
   */
  @Resource
  public void setCollectionReflectionUtil(CollectionReflectionUtil collectionReflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.collectionReflectionUtil = collectionReflectionUtil;
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
    if (this.stringUtil == null) {
      this.stringUtil = StringUtil.getInstance();
    }
    addConverter(new ValueConverterToDate(getIso8601Util()));
    addConverter(new ValueConverterToCalendar(getIso8601Util()));
    addConverter(new ValueConverterToNumber(getMathUtil()));
    addConverter(new ValueConverterToString(getIso8601Util(), getStringUtil()));
    addConverter(new ValueConverterToEnum(getStringUtil()));
    ValueConverterToCollection converter2collection = new ValueConverterToCollection();
    converter2collection.setReflectionUtil(getReflectionUtil());
    converter2collection.setCollectionReflectionUtil(getCollectionReflectionUtil());
    converter2collection.setComposedValueConverter(this);
    converter2collection.initialize();
    addConverter(converter2collection);
  }
}
