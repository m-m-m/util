/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.lang.reflect.Type;

/**
 * This is the interface for generic conversion of values from a specific source-type ( {@code <SOURCE>}) to a given
 * target-type ({@code <TARGET>}).<br>
 * <b>ATTENTION:</b><br>
 * An implementation of this interface should be stateless and thread-safe.
 *
 * @see ComposedValueConverter
 * @see StringValueConverter
 *
 * @param <SOURCE> is the generic type of the values to convert.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public interface GenericValueConverter<SOURCE> {

  /**
   * This method converts the given {@code value} to the given {@code type}.
   *
   * @param <TARGET> is the type to convert to.
   * @param value is the value to convert. It may be {@code null}.
   * @param valueSource describes the source of the value. This may be the filename where the value was read from, an
   *        XPath where the value was located in an XML document, etc. It is used in exceptions thrown if something goes
   *        wrong. This will help to find the problem easier.
   * @param targetClass is the type the {@code value} should be converted to.
   * @return the {@code value} converted to {@code type}.
   * @throws ValueNotSetException if the given {@code value} is {@code null}.
   * @throws WrongValueTypeException if the given {@code value} is NOT {@code null} but can NOT be converted to the
   *         given {@code type} (e.g. if {@code value} is "12x" and {@code type} is {@code Integer.class}).
   */
  <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass)
      throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method converts the given {@code value} to the given {@code type}.
   *
   * @param <TARGET> is the type to convert to.
   * @param value is the value to convert. It may be {@code null}.
   * @param valueSource describes the source of the value. This may be the filename where the value was read from, an
   *        XPath where the value was located in an XML document, etc. It is used in exceptions thrown if something goes
   *        wrong. This will help to find the problem easier.
   * @param targetClass is the type the {@code value} should be converted to. It is the raw-type of the given
   *        {@code targetType}.
   * @param targetType is the type to convert the {@code value} to. It is potentially generic and therefore contains
   *        more detailed information than {@code targetClass}. E.g. the {@code targetClass} may be
   *        {@code java.util.List} while this {@code targetType} could be {@code java.util.List<Long>}. This could help
   *        e.g. if the {@code value} is a string like {@code "2, 47, 4252525"}. The caller may supply the
   *        {@code targetClass} again here.
   * @return the {@code value} converted to {@code type}.
   * @throws ValueNotSetException if the given {@code value} is {@code null}.
   * @throws WrongValueTypeException if the given {@code value} is NOT {@code null} but can NOT be converted to the
   *         given {@code type} (e.g. if {@code value} is "12x" and {@code type} is {@code Integer.class}).
   */
  <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass, Type targetType)
      throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method converts the given {@code value} to the given {@code type}.
   *
   * @param <TARGET> is the type to convert to.
   * @param value is the value to convert. It may be {@code null}.
   * @param valueSource describes the source of the value. This may be the filename where the value was read from, an
   *        XPath where the value was located in an XML document, etc. It is used in exceptions thrown if something goes
   *        wrong. This will help to find the problem easier.
   * @param targetClass is the type the {@code value} should be converted to.
   * @param defaultValue is returned if the given {@code value} is {@code null}. It may also be {@code null}.
   * @return the {@code value} converted to {@code type} or the {@code defaultValue} if {@code value} was {@code null}.
   *         It will only return {@code null} if both {@code value} and {@code defaultValue} are {@code null}.
   * @throws WrongValueTypeException if the given {@code value} is NOT {@code null} but can NOT be converted to the
   *         given {@code type} (e.g. if {@code value} is "12x" and {@code type} is {@code Integer.class}).
   */
  <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass, TARGET defaultValue)
      throws WrongValueTypeException;

  /**
   * This method converts the given {@code value} to the given {@code type}.
   *
   * @param <TARGET> is the type to convert to.
   * @param value is the value to convert. It may be {@code null}.
   * @param valueSource describes the source of the value. This may be the filename where the value was read from, an
   *        XPath where the value was located in an XML document, etc. It is used in exceptions thrown if something goes
   *        wrong. This will help to find the problem easier.
   * @param targetClass is the type the {@code value} should be converted to. It is the raw-type of the given
   *        {@code targetType}.
   * @param targetType is the type to convert the {@code value} to. It is potentially generic and therefore contains
   *        more detailed information than {@code targetClass}. E.g. the {@code targetClass} may be
   *        {@code java.util.List} while this {@code targetType} could be {@code java.util.List<Long>}. This could help
   *        e.g. if the {@code value} is a string like {@code "2, 47, 4252525"}. The caller may supply the
   *        {@code targetClass} again here.
   * @param defaultValue is returned if the given {@code value} is {@code null}. It may also be {@code null}.
   * @return the {@code value} converted to {@code type} or the {@code defaultValue} if {@code value} was {@code null}.
   *         It will only return {@code null} if both {@code value} and {@code defaultValue} are {@code null}.
   * @throws WrongValueTypeException if the given {@code value} is NOT {@code null} but can NOT be converted to the
   *         given {@code type} (e.g. if {@code value} is "12x" and {@code type} is {@code Integer.class}).
   */
  <TARGET> TARGET convertValue(SOURCE value, Object valueSource, Class<TARGET> targetClass, Type targetType,
      TARGET defaultValue) throws WrongValueTypeException;

  /**
   * This method converts the given {@code value} to a numeric type and also validates that it is in the given range
   * from {@code minimum} to {@code maximum}.
   *
   * @param <TARGET> is the numeric-type to convert to.
   * @param value is the value to convert. It may be {@code null}.
   * @param valueSource describes the source of the value. This may be the filename where the value was read from, an
   *        XPath where the value was located in an XML document, etc. It is used in exceptions thrown if something goes
   *        wrong. This will help to find the problem easier.
   * @param minimum is the minimum number allowed. Use MIN_VALUE (e.g. {@link Double#MIN_VALUE}) if unbound.
   * @param maximum is the maximum number allowed. Use MAX_VALUE (e.g. {@link Long#MAX_VALUE}) if unbound.
   * @return the requested value in the given range from {@code minimum} and {@code maximum}.
   * @throws ValueNotSetException if the given {@code value} is {@code null}.
   * @throws WrongValueTypeException if the value is NO number.
   * @throws ValueOutOfRangeException if the value is NOT in the given range from {@code minimum} to {@code maximum}.
   */
  <TARGET extends Number> TARGET convertValue(SOURCE value, Object valueSource, TARGET minimum, TARGET maximum)
      throws ValueNotSetException, WrongValueTypeException, ValueOutOfRangeException;

  /**
   * This method gets a numeric value and also validates that it is in the given range from {@code minimum} to
   * {@code maximum}.
   *
   * @param <TARGET> is the numeric-type to convert to.
   * @param value is the value to convert. It may be {@code null}.
   * @param valueSource describes the source of the value. This may be the filename where the value was read from, an
   *        XPath where the value was located in an XML document, etc. It is used in exceptions thrown if something goes
   *        wrong. This will help to find the problem easier.
   * @param minimum is the minimum number allowed. Use MIN_VALUE (e.g. {@link Double#MIN_VALUE}) if unbound.
   * @param maximum is the maximum number allowed. Use MAX_VALUE (e.g. {@link Long#MAX_VALUE}) if unbound.
   * @param defaultValue is the default returned if {@code value} is {@code null}. It may be {@code null}. Else it must
   *        be in the given range from {@code minimum} to {@code maximum}.
   * @return the given {@code value} converted to {@code <TARGET>} in the range from {@code minimum} to {@code maximum}
   *         or the {@code defaultValue} if {@code value} is {@code null}. Will only be {@code null} if both
   *         {@code value} and {@code defaultValue} are {@code null}.
   * @throws WrongValueTypeException if the value is NO number.
   * @throws ValueOutOfRangeException if the value is NOT in the given range from {@code minimum} to {@code maximum}.
   */
  <TARGET extends Number> TARGET convertValue(SOURCE value, Object valueSource, TARGET minimum, TARGET maximum,
      TARGET defaultValue) throws WrongValueTypeException, ValueOutOfRangeException;

}
