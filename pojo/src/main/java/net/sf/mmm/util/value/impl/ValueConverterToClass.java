/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ValueException;
import net.sf.mmm.util.value.base.AbstractValueConverter;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts a
 * {@link CharSequence} to a {@link Class}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
@SuppressWarnings("rawtypes")
public class ValueConverterToClass extends AbstractValueConverter<CharSequence, Class> {

  /**
   * The constructor.
   */
  public ValueConverterToClass() {

    super();
  }

  @Override
  public Class<CharSequence> getSourceType() {

    return CharSequence.class;
  }

  @Override
  public Class<Class> getTargetType() {

    return Class.class;
  }

  @SuppressWarnings("all")
  public <T extends Class> T convert(CharSequence value, Object valueSource, GenericType<T> targetType)
      throws ValueException {

    if (value == null) {
      return null;
    }
    String className = value.toString().trim();
    try {
      Class<?> result = Class.forName(className);
      if (targetType.getTypeArgumentCount() == 1) {
        Class<?> superClass = targetType.getTypeArgument(0).getRetrievalClass();
        if (!superClass.isAssignableFrom(result)) {
          throw new ClassCastException(superClass.getName());
        }
      }
      return (T) result;
    } catch (ClassNotFoundException e) {
      throw new NlsParseException(e, value, targetType, valueSource);
    }
  }

}
