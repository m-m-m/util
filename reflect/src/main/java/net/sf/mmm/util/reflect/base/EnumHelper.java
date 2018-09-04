/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import net.sf.mmm.util.lang.api.CaseSyntax;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * A simple helper to convert {@link Enum} from and to {@link String}.
 *
 * @author hohwille
 * @since 7.6.0
 */
public class EnumHelper {

  /**
   * @param <E> generic type of the {@link Enum}.
   * @param value the {@link Enum#toString() string representation}.
   * @param enumType the {@link Class} reflecting the requested {@link Enum}.
   * @return the {@link Enum} instance for the given {@link String} {@code value}.
   */
  public static <E extends Enum<E>> E fromString(String value, Class<E> enumType) {

    if (value == null) {
      return null;
    }
    E[] constants = enumType.getEnumConstants();
    for (E e : constants) {
      if (e.name().equals(value)) {
        return e;
      }
    }
    if (AttributeReadValue.class.isAssignableFrom(enumType)) {
      for (E e : constants) {
        Object v = ((AttributeReadValue<?>) e).getValue();
        if ((v != null) && (value.equalsIgnoreCase(v.toString()))) {
          return e;
        }
      }
    }
    for (E e : constants) {
      String name = e.name();
      if (value.equalsIgnoreCase(name)) {
        return e;
      }
      name = name.replace("_", "");
      if (value.equalsIgnoreCase(name)) {
        return e;
      }
    }
    throw new IllegalStateException(value + "@" + enumType.getName());
  }

  /**
   * @param value the {@link Enum} value.
   * @param camlCase - {@code true} for converting the {@link Enum#toString() string representation} to
   *        {@code camlCase}, {@code false} otherwise.
   * @param deCapitalze - {@code true} to ensure the first character of the {@link Enum#toString() string
   *        representation} is lower case, {@code false} otherwise.
   * @return the {@link Enum#toString() string representation} as specified via the given options.
   */
  public static String toString(Enum<?> value, boolean camlCase, boolean deCapitalze) {

    if (value == null) {
      return null;
    }
    String string = value.toString();
    if (camlCase) {
      if (deCapitalze) {
        string = CaseSyntax.CAML_CASE.convert(string);
      } else {
        string = CaseSyntax.PASCAL_CASE.convert(string);
      }
    } else if (deCapitalze) {
      if (!string.isEmpty()) {
        char c = string.charAt(0);
        char low = Character.toLowerCase(c);
        if (low != c) {
          string = low + string.substring(1);
        }
      }
    }
    return string;
  }

}
