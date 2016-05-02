/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;

/**
 * This is an abstract base implementation of the
 * {@link net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorBuilder} interface. <br>
 * It provides some helpful methods that make it easier to write implementations.
 *
 * @param <ACCESSOR> is the type of the accessor
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractPojoPropertyAccessorBuilder<ACCESSOR extends PojoPropertyAccessor>
    implements PojoPropertyAccessorBuilder<ACCESSOR> {

  /**
   * The constructor.
   */
  public AbstractPojoPropertyAccessorBuilder() {

    super();
  }

  /**
   * This method determines if the given {@code type} is a boolean type ({@code boolean.class} or {@code Boolean.class}.
   *
   * @param type is the class to check.
   * @return {@code true} if type represents a boolean, {@code false} otherwise.
   */
  protected static boolean isBooleanType(Class<?> type) {

    if ((type == boolean.class) || (type == Boolean.class)) {
      return true;
    }
    return false;
  }

  /**
   * This method determines if the given {@code type} is an integer type ({@code int.class} or {@code Integer.class}).
   *
   * @param type is the class to check.
   * @return {@code true} if type represents an integer, {@code false} otherwise.
   */
  protected static boolean isIntegerType(Class<?> type) {

    if ((type == int.class) || (type == Integer.class)) {
      return true;
    }
    return false;
  }

  /**
   * This method gets the according {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor#getName()
   * property-name} for the given {@code methodName}. <br>
   * This is the un-capitalized substring of the {@code methodName} after between the given {@code prefix} and
   * {@code suffix}.
   *
   * @param methodName is the {@link java.lang.reflect.Method#getName() name} of the
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor#getAccessibleObject()
   *        accessor-method}.
   * @param prefixes is an array with all possible prefixes (e.g. {@code "get", "is", "has"}). May also be empty array
   *        instead of <code>{""}</code>.
   * @param suffixes is an array with all possible suffixes (e.g. {@code "Size", "Length", "Count"}). May also be empty
   *        array instead of <code>{""}</code>.
   * @return the requested property-name or {@code null} if NOT available. ({@code methodName} does NOT
   *         {@link String#startsWith(String) start with} one of the {@code prefixes} or does NOT
   *         {@link String#endsWith(String) end with} one of the {@code suffixes}).
   */
  protected String getPropertyName(String methodName, String[] prefixes, String[] suffixes) {

    for (String prefix : prefixes) {
      if (methodName.startsWith(prefix)) {
        for (String suffix : suffixes) {
          if (methodName.endsWith(suffix)) {
            return getPropertyName(methodName, prefix.length(), suffix.length());
          }
        }
        if (suffixes.length == 0) {
          return getPropertyName(methodName, prefix.length(), 0);
        }
      }
    }
    if (prefixes.length == 0) {
      for (String suffix : suffixes) {
        if (methodName.endsWith(suffix)) {
          return getPropertyName(methodName, 0, suffix.length());
        }
      }
    }
    return null;
  }

  /**
   * This method gets the according {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor#getName()
   * property-name} for the given {@code methodName}. <br>
   * This is the un-capitalized substring of the {@code methodName} after between the given {@code prefix} and
   * {@code suffix}.
   *
   * @param methodName is the {@link java.lang.reflect.Method#getName() name} of the
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor#getAccessibleObject()
   *        accessor-method}.
   * @param prefix is the prefix (e.g. "get", "set" or "is").
   * @param suffix is the suffix (e.g. "" or "Size").
   * @return the requested property-name or {@code null} if NOT available. ({@code methodName} does NOT
   *         {@link String#startsWith(String) start with} {@code prefix} or does NOT {@link String#endsWith(String) end
   *         with} {@code suffix}).
   */
  protected String getPropertyName(String methodName, String prefix, String suffix) {

    if (methodName.startsWith(prefix) && methodName.endsWith(suffix)) {
      return getPropertyName(methodName, prefix.length(), suffix.length());
    }
    return null;
  }

  /**
   * This method gets the according {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor#getName()
   * property-name} for the given {@code methodName}. <br>
   * This is the un-capitalized substring of the {@code methodName} after the prefix (given via {@code prefixLength}).
   *
   * @param methodName is the {@link java.lang.reflect.Method#getName() name} of the
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor#getAccessibleObject()
   *        accessor-method}.
   * @param prefixLength is the length of the method prefix (e.g. 3 for "get"/"set" or 2 for "is").
   * @param suffixLength is the length of the method suffix (e.g. 4 for "Size").
   * @return the requested property-name or {@code null} if NOT available <br>
   *         ({@code methodName}.{@link String#length() length()} &lt;= {@code prefixLength}).
   */
  protected String getPropertyName(String methodName, int prefixLength, int suffixLength) {

    int len = methodName.length();
    int end = len - suffixLength;
    if (prefixLength < end) {
      String methodSuffix = methodName.substring(prefixLength, end);
      StringBuffer sb = new StringBuffer(methodSuffix);
      sb.setCharAt(0, Character.toLowerCase(methodSuffix.charAt(0)));
      return sb.toString();
    }
    return null;
  }

}
