/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.regex.Pattern;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts a
 * {@link CharSequence} to a {@link Pattern}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.3.0
 */
@Singleton
@Named
public class ValueConverterToPattern extends AbstractSimpleValueConverter<CharSequence, Pattern> {

  /**
   * The constructor.
   */
  public ValueConverterToPattern() {

    super();
  }

  @Override
  public Class<CharSequence> getSourceType() {

    return CharSequence.class;
  }

  @Override
  public Class<Pattern> getTargetType() {

    return Pattern.class;
  }

  @SuppressWarnings("all")
  @Override
  public <T extends Pattern> T convert(CharSequence value, Object valueSource, Class<T> targetClass) {

    if (value == null) {
      return null;
    }
    return (T) Pattern.compile(value.toString());
  }

}
