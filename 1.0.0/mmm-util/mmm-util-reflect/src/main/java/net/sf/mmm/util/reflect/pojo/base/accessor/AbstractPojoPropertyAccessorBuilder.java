/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.base.accessor;

import java.lang.reflect.Method;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorBuilder;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgBuilder;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArgBuilder}
 * interface for {@link PojoPropertyAccessorNonArgMode#GET getter-access}.
 * 
 * @param <ACCESSOR> is the type of the accessor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
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
   * This method determines if the given <code>type</code> is a boolean type (<code>boolean.class</code>
   * or <code>{@link Boolean}.class</code>
   * 
   * @param type is the class to check.
   * @return <code>true</code> if type represents a boolean,
   *         <code>false</code> otherwise.
   */
  protected static boolean isBooleanType(Class<?> type) {

    if (type == boolean.class) {
      return true;
    } else if (type == Boolean.class) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * This method determines if the given <code>type</code> is an integer type (<code>int.class</code>
   * or <code>{@link Integer}.class</code>
   * 
   * @param type is the class to check.
   * @return <code>true</code> if type represents an integer,
   *         <code>false</code> otherwise.
   */
  protected static boolean isIntegerType(Class<?> type) {

    if (type == int.class) {
      return true;
    } else if (type == Integer.class) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * This method gets the according
   * {@link net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor#getName() property-name}
   * for the given <code>methodName</code>.<br>
   * This is the un-capitalized substring of the <code>methodName</code> after
   * between the given <code>prefix</code> and <code>suffix</code>.
   * 
   * @param methodName is the {@link Method#getName() name} of the
   *        {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor#getAccessibleObject() accessor-method}.
   * @param prefixes is an array with all possible prefixes (e.g.
   *        <code>"get", "is", "has"</code>). May also be empty array instead
   *        of <code>{""}</code>.
   * @param suffixes is an array with all possible suffixes (e.g.
   *        <code>"Size", "Length", "Count"</code>). May also be empty array
   *        instead of <code>{""}</code>.
   * @return the requested property-name or <code>null</code> if NOT
   *         available. (<code>methodName</code> does NOT
   *         {@link String#startsWith(String) start with} one of the
   *         <code>prefixes</code> or does NOT
   *         {@link String#endsWith(String) end with} one of the
   *         <code>suffixes</code>).
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
   * This method gets the according
   * {@link net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor#getName() property-name}
   * for the given <code>methodName</code>.<br>
   * This is the un-capitalized substring of the <code>methodName</code> after
   * between the given <code>prefix</code> and <code>suffix</code>.
   * 
   * @param methodName is the {@link Method#getName() name} of the
   *        {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor#getAccessibleObject() accessor-method}.
   * @param prefix is the prefix (e.g. "get", "set" or "is").
   * @param suffix is the suffix (e.g. "" or "Size").
   * @return the requested property-name or <code>null</code> if NOT
   *         available. (<code>methodName</code> does NOT
   *         {@link String#startsWith(String) start with} <code>prefix</code>
   *         or does NOT {@link String#endsWith(String) end with}
   *         <code>suffix</code>).
   */
  protected String getPropertyName(String methodName, String prefix, String suffix) {

    if (methodName.startsWith(prefix) && methodName.endsWith(suffix)) {
      return getPropertyName(methodName, prefix.length(), suffix.length());
    }
    return null;
  }

  /**
   * This method gets the according
   * {@link net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor#getName() property-name}
   * for the given <code>methodName</code>.<br>
   * This is the un-capitalized substring of the <code>methodName</code> after
   * the prefix (given via <code>prefixLength</code>).
   * 
   * @param methodName is the {@link Method#getName() name} of the
   *        {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor#getAccessibleObject() accessor-method}.
   * @param prefixLength is the length of the method prefix (e.g. 3 for
   *        "get"/"set" or 2 for "is").
   * @param suffixLength is the length of the method suffix (e.g. 4 for "Size").
   * @return the requested property-name or <code>null</code> if NOT available
   *         <br> (<code>methodName</code>.{@link String#length() length()}
   *         &lt;= <code>prefixLength</code>).
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
