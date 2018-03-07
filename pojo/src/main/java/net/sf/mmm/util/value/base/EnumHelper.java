/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import java.util.Locale;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;
import net.sf.mmm.util.lang.base.StringUtilImpl;

/**
 * Helper class to deserialize {@link Enum} constants from {@link String}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.6.0
 */
public class EnumHelper {

  /**
   * @param <E> generic type of the {@link Enum}.
   * @param enumType the {@link Class} reflecting the {@link Enum}.
   * @param enumValue the {@link String} representing the requested {@link Enum} value.
   * @param required - {@code true} if the result is required and may not be {@code null}, {@code false}
   *        otherwise (to return {@code null} for an invalid {@code enumValue}).
   * @return the value of the {@link Enum} specified by the given {@link Class} with the given
   *         {@code enumValue}.
   */
  public static <E extends Enum<?>> E fromString(Class<E> enumType, String enumValue, boolean required) {

    if (enumValue == null) {
      return null;
    }
    String normalizedValue = StringUtilImpl.getInstance().fromCamlCase(enumValue.replace('-', '_'), '_').toUpperCase(Locale.US);
    E resultName = null;
    E resultToString = null;
    boolean hasGetValue = AttributeReadValue.class.isAssignableFrom(enumType);
    for (E enumConstant : enumType.getEnumConstants()) {
      if (enumConstant.name().equals(enumValue)) {
        return enumConstant;
      }
      if (enumConstant.name().equals(normalizedValue)) {
        resultName = enumConstant;
      }
      if (enumConstant.toString().equals(enumValue)) {
        resultToString = enumConstant;
      }
      if ((resultToString == null) && hasGetValue) {
        Object value = ((AttributeReadValue<?>) enumConstant).getValue();
        if (enumValue.equals(value)) {
          resultToString = enumConstant;
        }
      }
    }
    if (resultName != null) {
      return resultName;
    }
    return resultToString;
  }

}
