/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.base.AbstractPojoPropertyAccessor;

/**
 * This class iterates all
 * {@link net.sf.mmm.util.pojo.api.PojoPropertyAccessor property-accessors} for
 * the methods of a given class.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MethodAccessorIterator implements Iterator<AbstractPojoPropertyAccessor> {

  /** method name prefix for classic getter */
  private static final String METHOD_PREFIX_GET = "get";

  /** method name prefix for classic setter */
  private static final String METHOD_PREFIX_SET = "set";

  /** alternative method name prefix for boolean getter */
  private static final String METHOD_PREFIX_IS = "is";

  /** alternative method name prefix for boolean getter */
  private static final String METHOD_PREFIX_HAS = "has";

  /** alternative method name prefix for boolean add-method */
  private static final String METHOD_PREFIX_ADD = "add";

  /** the current class */
  private Class currentClass;

  /** the methods */
  private Method[] methods;

  /** the current index of {@link #methods} */
  private int index;

  /** the next accessor */
  private AbstractPojoPropertyAccessor next;

  /**
   * if <code>true</code> only {@link Modifier#isPublic(int) public} methods
   * will be considered.
   */
  private final boolean publicOnly;

  /**
   * The constructor
   * 
   * @param pojoClass
   *        is the class for which the property-accessors should be iterated.
   * @param onlyPublic
   *        if <code>true</code> only {@link Modifier#isPublic(int) public}
   *        methods will be considered for when searching for
   *        property-accessors, else if <code>false</code> all implemented
   *        methods are introspected.
   */
  public MethodAccessorIterator(Class<?> pojoClass, boolean onlyPublic) {

    super();
    this.publicOnly = onlyPublic;
    if (this.publicOnly) {
      this.currentClass = null;
      this.methods = pojoClass.getMethods();
    } else {
      // TODO: what if pojoClass is abstract (or interface) and does NOT
      // implement all property accessor methods inherited from interfaces.
      this.currentClass = pojoClass;
      this.methods = pojoClass.getDeclaredMethods();
    }
    this.index = 0;
    stepNext();
  }

  /**
   * This method steps to the next accessor if available.
   */
  public void stepNext() {

    this.next = null;
    while (this.index < this.methods.length) {
      Method method = this.methods[this.index++];
      this.next = getAccessor(method);
      if (this.next != null) {
        return;
      }
    }
    if (this.currentClass != null) {
      this.currentClass = this.currentClass.getSuperclass();
      if (this.currentClass != null) {
        this.methods = this.currentClass.getDeclaredMethods();
        this.index = 0;
        stepNext();
      }
    }
  }

  /**
   * @see java.util.Iterator#hasNext()
   */
  public boolean hasNext() {

    return (this.next != null);
  }

  /**
   * @see java.util.Iterator#next()
   */
  public AbstractPojoPropertyAccessor next() {

    if (this.next == null) {
      throw new NoSuchElementException();
    }
    AbstractPojoPropertyAccessor accessor = this.next;
    stepNext();
    return accessor;
  }

  /**
   * @see java.util.Iterator#remove()
   */
  public void remove() {

    throw new UnsupportedOperationException();
  }

  /**
   * This method creates an accessor for the given method.
   * 
   * @param method
   *        is the method that potentially gives access to a property.
   * @return the accessor or <code>null</code> if the method is no accessor
   *         method.
   */
  public MethodPojoPropertyAccessor getAccessor(Method method) {

    int modifiers = method.getModifiers();
    if (Modifier.isStatic(modifiers)) {
      return null;
    }
    if (this.publicOnly && !Modifier.isPublic(modifiers)) {
      return null;
    }
    MethodPojoPropertyAccessor accessor = getAccessorForAcceptedMethod(method);
    if (!this.publicOnly && (accessor != null)) {
      method.setAccessible(true);
    }
    return accessor;
  }

  /**
   * This method creates an accessor for the given method.
   * 
   * @param method
   *        is the method that potentially gives access to a property.
   * @return the accessor or <code>null</code> if the method is no accessor
   *         method.
   */
  public static MethodPojoPropertyAccessor getAccessorForAcceptedMethod(Method method) {

    String methodName = method.getName();
    Class<?>[] argumentTypes = method.getParameterTypes();
    if (argumentTypes.length == 1) {
      // is property write method (setter)?
      Type[] genericArgTypes = method.getGenericParameterTypes();
      if (methodName.startsWith(METHOD_PREFIX_SET)) {
        // found compliant setter
        String propertyName = getPropertyName(methodName, METHOD_PREFIX_SET.length());
        if (propertyName != null) {
          return new MethodPojoPropertyAccessor(propertyName, PojoPropertyAccessMode.WRITE,
              genericArgTypes[0], argumentTypes[0], method);
        }
      } else if (methodName.startsWith(METHOD_PREFIX_ADD)) {
        // found compliant add-method
        String propertyName = getPropertyName(methodName, METHOD_PREFIX_ADD.length());
        if (propertyName != null) {
          return new MethodPojoPropertyAccessor(propertyName, PojoPropertyAccessMode.ADD,
              genericArgTypes[0], argumentTypes[0], method);
        }
      }
    } else if (argumentTypes.length == 0) {
      Class<?> propertyClass = method.getReturnType();
      String propertyName = null;
      if (propertyClass != Void.class) {
        // is property read method (getter)?
        if (methodName.startsWith(METHOD_PREFIX_GET)) {
          propertyName = getPropertyName(methodName, METHOD_PREFIX_GET.length());
        } else if ((propertyClass == boolean.class) || (propertyClass == Boolean.class)) {
          // boolean getters may be is* or has* ...
          if (methodName.startsWith(METHOD_PREFIX_IS)) {
            propertyName = getPropertyName(methodName, METHOD_PREFIX_IS.length());
          } else if (methodName.startsWith(METHOD_PREFIX_HAS)) {
            propertyName = getPropertyName(methodName, METHOD_PREFIX_HAS.length());
          }
        }
        if (propertyName != null) {
          return new MethodPojoPropertyAccessor(propertyName, PojoPropertyAccessMode.READ, method
              .getGenericReturnType(), propertyClass, method);
        }
      }
    }
    return null;
  }

  /**
   * This method gets the according
   * {@link PojoPropertyDescriptor#getName() property-name} for the given
   * <code>methodName</code>.<br>
   * This is the un-capitalized substring of the <code>methodName</code> after
   * the prefix (given via <code>prefixLength</code>).
   * 
   * @param methodName
   *        is the {@link Method#getName() name} of the
   *        {@link net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getAccessibleObject() accessor-method}.
   * @param prefixLength
   *        is the length of the method prefix (e.g. 3 for "get"/"set" or 2 for
   *        "is").
   * @return the requested property-name or <code>null</code> if NOT available
   *         <br> (<code>methodName</code>.{@link String#length() length()}
   *         &lt;= <code>prefixLength</code>).
   */
  private static String getPropertyName(String methodName, int prefixLength) {

    String methodSuffix = methodName.substring(prefixLength);
    if (methodSuffix.length() > 0) {
      StringBuffer sb = new StringBuffer(methodSuffix);
      sb.setCharAt(0, Character.toLowerCase(methodSuffix.charAt(0)));
      return sb.toString();
    }
    return null;
  }

}
