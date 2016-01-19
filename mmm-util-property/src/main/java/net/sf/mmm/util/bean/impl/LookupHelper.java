/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;
import net.sf.mmm.util.reflect.api.InvocationFailedException;

/**
 * Helper class for {@link Lookup} in order to invoke default methods and other magic stuff.
 *
 * @author hohwille
 * @since 8.0.0
 */
final class LookupHelper {

  public static final LookupHelper INSTANCE = new LookupHelper();

  private final Constructor<Lookup> lookupConstructor;

  /**
   * The constructor.
   */
  private LookupHelper() {
    super();
    try {
      this.lookupConstructor = Lookup.class.getDeclaredConstructor(Class.class, int.class);
      if (!this.lookupConstructor.isAccessible()) {
        this.lookupConstructor.setAccessible(true);
      }
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * @param type the {@link Class} to create the {@link Lookup} for.
   * @return a new instance of {@link Lookup} for the given <code>type</code>.
   */
  public Lookup newLookup(Class<?> type) {

    try {
      return this.lookupConstructor.newInstance(type, Integer.valueOf(Lookup.PRIVATE));
    } catch (InstantiationException e) {
      throw new InstantiationFailedException(e, Lookup.class);
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, Lookup.class);
    } catch (InvocationTargetException e) {
      throw new InvocationFailedException(e, this.lookupConstructor);
    }
  }

  /**
   * @param method the {@link Method}.
   * @return the according {@link MethodHandle}.
   */
  public MethodHandle newMethodHandle(Method method) {

    Class<?> declaringClass = method.getDeclaringClass();
    try {
      MethodHandle methodHandle = newLookup(declaringClass).unreflectSpecial(method, declaringClass);
      return methodHandle;
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, declaringClass);
    }
  }

  public Object invokeDefaultMethod(Object instance, Method method, Object... args) {

    MethodHandle methodHandle = newMethodHandle(method);
    try {
      return methodHandle.bindTo(instance).invokeWithArguments(args);
    } catch (Throwable e) {
      throw new InvocationFailedException(e);
    }
  }

}
