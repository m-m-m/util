/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is a container for a {@link Method} of a {@link Bean} with additional introspection information.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BeanMethod {

  private static final String PREFIX_SET = "set";

  private static final String PREFIX_GET = "get";

  private static final String SUFFIX_PROPERTY = "Property";

  private static final String PREFIX_IS = "is";

  private static final String PREFIX_HAS = "has";

  private final Method method;

  private final BeanMethodType methodType;

  private final String propertyName;

  private final Type propertyType;

  /**
   * The constructor.
   *
   * @param method - see {@link #getMethod()}.
   */
  public BeanMethod(Method method) {
    super();
    this.method = method;
    String name = this.method.getName();
    char first = name.charAt(0);
    Type[] parameterTypes = this.method.getGenericParameterTypes();
    BeanMethodType mType = null;
    String pName = null;
    Type pType = null;
    if (parameterTypes.length == 0) {
      if (name.equals("access")) {
        mType = BeanMethodType.ACCESS;
      } else if (name.equals("getClass")) {
        // ignore getClass()
      } else if (Character.isUpperCase(first)) {
        mType = BeanMethodType.PROPERTY;
        pName = name;
      } else if (name.equals("hashCode")) {
        mType = BeanMethodType.HASH_CODE;
      } else if (name.equals("toString")) {
        mType = BeanMethodType.TO_STRING;
      } else if (name.endsWith(SUFFIX_PROPERTY)) {
        mType = BeanMethodType.PROPERTY;
        pName = Character.toUpperCase(first) + name.substring(1, name.length() - SUFFIX_PROPERTY.length());
      } else {
        pName = getCapitalSuffixAfterPrefixes(name, PREFIX_GET, PREFIX_HAS, PREFIX_IS);
        if (pName != null) {
          mType = BeanMethodType.GET;
        }
      }
      if (pName != null) {
        pType = method.getGenericReturnType();
      }
    } else if (parameterTypes.length == 1) {
      pName = getCapitalSuffixAfterPrefix(name, PREFIX_SET);
      if (pName != null) {
        mType = BeanMethodType.SET;
        pType = parameterTypes[0];
      } else if ((name.equals("equals")) && (parameterTypes[0] == Object.class)) {
        mType = BeanMethodType.EQUALS;
      }
    }
    if ((mType == null) && method.isDefault()) {
      mType = BeanMethodType.DEFAULT_METHOD;
    }
    this.methodType = mType;
    this.propertyName = pName;
    this.propertyType = pType;

  }

  private String getCapitalSuffixAfterPrefixes(String string, String... prefixes) {

    for (String prefix : prefixes) {
      String suffix = getCapitalSuffixAfterPrefix(string, prefix);
      if (suffix != null) {
        return suffix;
      }
    }
    return null;
  }

  private String getCapitalSuffixAfterPrefix(String string, String prefix) {

    if (string.startsWith(prefix)) {

      String suffix = string.substring(prefix.length());
      if ((suffix.length() > 0) && (Character.isUpperCase(suffix.charAt(0)))) {
        return suffix;
      }
    }
    return null;
  }

  /**
   * @return the {@link Method}.
   */
  public Method getMethod() {

    return this.method;
  }

  /**
   * @return the {@link BeanMethodType} or <code>null</code> for undefined {@link Method}.
   */
  public BeanMethodType getMethodType() {

    return this.methodType;
  }

  /**
   * @return the capitalized name of the property or <code>null</code> if no property.
   */
  public String getPropertyName() {

    return this.propertyName;
  }

  /**
   * @return the generic {@link Type} of the property or <code>null</code> if no property. Will be the {@link Type} of
   *         the {@link WritableProperty} for {@link BeanMethodType#PROPERTY}.
   */
  public Type getPropertyType() {

    return this.propertyType;
  }

}
