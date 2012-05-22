/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.lang.reflect.Method;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleKey;
import net.sf.mmm.util.nls.api.NlsBundleLocation;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.reflect.api.ClassName;

/**
 * Helper class to deal with {@link NlsBundle} interfaces.
 * 
 * @author hohwille
 * @since 2.0.2
 */
public class NlsBundleHelper {

  /**
   * This method gets the {@link NlsBundleLocation location} of the {@link java.util.ResourceBundle} for the
   * given {@link NlsBundle}-interface.
   * 
   * @param nlsBundleInterface is the {@link NlsBundle}-interface.
   * @return the fully qualified {@link java.util.ResourceBundle}-location for the given {@link NlsBundle}
   *         interface.
   */
  public ClassName getQualifiedLocation(Class<? extends NlsBundle> nlsBundleInterface) {

    NlsBundleLocation location = nlsBundleInterface.getAnnotation(NlsBundleLocation.class);
    if (location != null) {
      String pkg = location.bundlePackage();
      if (pkg == null) {
        pkg = nlsBundleInterface.getPackage().getName();
      }
      String name = location.bundleName();
      if (name == null) {
        name = nlsBundleInterface.getSimpleName();
      }
      return new ClassName(pkg, name);
    }
    return new ClassName(nlsBundleInterface);
  }

  /**
   * This method gets the {@link NlsBundleKey key} of an {@link NlsBundle}-method.
   * 
   * @param method is the Method to check.
   * @return the requested key.
   */
  public String getKey(Method method) {

    String key;
    NlsBundleKey keyAnnotation = method.getAnnotation(NlsBundleKey.class);
    if (keyAnnotation == null) {
      key = method.getName();
    } else {
      key = keyAnnotation.value();
    }
    return key;
  }

  /**
   * This method determines if the given {@link Method} is a regular {@link NlsBundle}-method.
   * 
   * @param method the {@link Method} to check.
   * @param ignoreIllegalMethods - <code>true</code> if illegal methods (non NlsBundleMethods other than those
   *        defined by {@link Object}) should be ignored, <code>false</code> if they should cause an
   *        exception.
   * @return <code>true</code> if the given {@link Method} is a legal {@link NlsBundle} method,
   *         <code>false</code> otherwise (e.g. for <code>toString()</code>).
   */
  public boolean isNlsBundleMethod(Method method, boolean ignoreIllegalMethods) {

    Class<?> declaringClass = method.getDeclaringClass();
    assert (declaringClass.isInterface());
    if (NlsMessage.class.equals(method.getReturnType())) {
      assert (NlsBundle.class.isAssignableFrom(declaringClass));
      return true;
    } else if (!declaringClass.isAssignableFrom(NlsBundle.class)) {
      if (!ignoreIllegalMethods) {
        throw new NlsIllegalArgumentException(declaringClass.getName() + "." + method.getName());
      }
    }
    return false;
  }

}
