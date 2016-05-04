/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to a {@link File}. It supports objects given as {@link CharSequence} (e.g. {@link String} ).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
public class ValueConverterToFile extends AbstractSimpleValueConverter<CharSequence, File> {

  /**
   * The constructor.
   */
  public ValueConverterToFile() {

    super();
  }

  @Override
  public Class<CharSequence> getSourceType() {

    return CharSequence.class;
  }

  @Override
  public Class<File> getTargetType() {

    return File.class;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends File> T convert(CharSequence value, Object valueSource, Class<T> targetClass) {

    if (value == null) {
      return null;
    }
    return (T) new File(value.toString());
  }

}
