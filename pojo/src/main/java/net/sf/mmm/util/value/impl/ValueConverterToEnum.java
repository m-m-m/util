/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;
import net.sf.mmm.util.value.base.EnumHelper;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts
 * an {@link Object} to an {@link Enum}. It supports objects given as {@link CharSequence} (e.g.
 * {@link String}) or {@link Number} as well as an {@link Enum} having an value with the same
 * {@link Enum#name() name}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ValueConverterToEnum extends AbstractSimpleValueConverter<Object, Enum> {

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

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.stringUtil == null) {
      this.stringUtil = StringUtilImpl.getInstance();
    }
  }

  @Override
  public Class<Object> getSourceType() {

    return Object.class;
  }

  @Override
  public Class<Enum> getTargetType() {

    return Enum.class;
  }

  @Override
  public <T extends Enum> T convert(Object value, Object valueSource, Class<T> targetClass) {

    if (value == null) {
      return null;
    }
    if (value instanceof CharSequence) {
      return EnumHelper.fromString(targetClass, value.toString(), false);
    } else if (value instanceof Enum<?>) {
      String name = ((Enum<?>) value).name();
      return (T) Enum.valueOf(targetClass, name);
    }
    return null;
  }

}
