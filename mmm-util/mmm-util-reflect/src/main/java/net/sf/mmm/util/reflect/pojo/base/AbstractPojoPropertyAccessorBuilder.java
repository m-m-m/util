/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.base;

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
   * @return the requested property-name or <code>null</code> if NOT available
   *         <br> (<code>methodName</code>.{@link String#length() length()}
   *         &lt;= <code>prefixLength</code>).
   */
  protected String getPropertyName(String methodName, int prefixLength) {

    String methodSuffix = methodName.substring(prefixLength);
    if (methodSuffix.length() > 0) {
      StringBuffer sb = new StringBuffer(methodSuffix);
      sb.setCharAt(0, Character.toLowerCase(methodSuffix.charAt(0)));
      return sb.toString();
    }
    return null;
  }

}
