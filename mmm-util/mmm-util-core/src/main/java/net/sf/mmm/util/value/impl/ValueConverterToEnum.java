/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.Locale;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to an {@link Enum}. It supports objects given as
 * {@link CharSequence} (e.g. {@link String}) or {@link Number} as well as an
 * {@link Enum} having an value with the same {@link Enum#name() name}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("unchecked")
public class ValueConverterToEnum extends AbstractSimpleValueConverter<Object, Enum> {

  /** @see #getStringUtil() */
  private StringUtil stringUtil;

  /**
   * The constructor.
   */
  public ValueConverterToEnum() {

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
  public void setStringUtil(StringUtil stringUtil) {

    getInitializationState().requireNotInitilized();
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
  public Class<Enum> getTargetType() {

    return Enum.class;
  }

  /**
   * {@inheritDoc}
   */
  public Enum convert(Object value, Object valueSource, Class<? extends Enum> targetClass) {

    if (value == null) {
      return null;
    }
    if (value instanceof CharSequence) {
      String name = this.stringUtil.fromCamlCase(value.toString(), '_').toUpperCase(Locale.US);
      return Enum.valueOf(targetClass, name);
    } else if (value instanceof Enum<?>) {
      String name = ((Enum<?>) value).name();
      return Enum.valueOf(targetClass, name);
    }
    return null;
  }

}
