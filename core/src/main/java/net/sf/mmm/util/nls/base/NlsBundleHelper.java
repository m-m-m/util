/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.inject.Named;

import net.sf.mmm.util.component.base.AbstractLoggableObject;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleKey;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.reflect.api.ClassName;

/**
 * Helper class to deal with {@link NlsBundle} interfaces.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class NlsBundleHelper extends AbstractLoggableObject {

  /** @see #getInstance() */
  public static final NlsBundleHelper INSTANCE = new NlsBundleHelper();

  /**
   * The constructor.
   */
  public NlsBundleHelper() {

    super();
  }

  /**
   * @return the singleton instance of this class.
   * @since 4.0.0
   */
  public static NlsBundleHelper getInstance() {

    return INSTANCE;
  }

  /**
   * This method gets the location of the {@link java.util.ResourceBundle} base name for the given {@link NlsBundle}
   * interface.
   *
   * @param nlsBundleInterface is the {@link NlsBundle} interface.
   * @return the fully qualified {@link java.util.ResourceBundle} base name for the given {@link NlsBundle} interface.
   */
  public ClassName getQualifiedLocation(Class<? extends NlsBundle> nlsBundleInterface) {

    String simpleName = nlsBundleInterface.getSimpleName();
    if (simpleName.endsWith(NlsBundle.INTERFACE_NAME_SUFFIX)) {
      simpleName = simpleName.substring(0, simpleName.length() - NlsBundle.INTERFACE_NAME_SUFFIX.length());
    } else {
      getLogger().error("Illegal NlsBundle interface '" + nlsBundleInterface.getName()
          + "': has to end with the suffix '" + NlsBundle.INTERFACE_NAME_SUFFIX + "'.");
    }
    return new ClassName(nlsBundleInterface.getPackage().getName(), simpleName);
  }

  /**
   * @param method is the {@link Method} of a {@link NlsBundle}.
   * @return the {@link NlsBundleKey#value() key} of the {@link Method} (defaults to {@link Method#getName()} if not
   *         annotated with {@link NlsBundleKey}).
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
   * @param qualifiedBundleName the {@link Class#getName() qualified name} of the {@link ResourceBundle} to load.
   * @param locale the {@link Locale} to translate to.
   * @return the requested {@link ResourceBundle}.
   */
  public ResourceBundle getResourceBundle(String qualifiedBundleName, Locale locale) {

    return ResourceBundle.getBundle(qualifiedBundleName, locale,
        ResourceBundleControlUtf8WithNlsBundleSupport.INSTANCE);
  }

  /**
   * @param method the {@link NlsBundle} {@link Method}.
   * @return the {@link NlsBundleMessage#value() bundle message} or {@code null} if not present.
   * @since 4.0.0
   */
  public String getMessage(Method method) {

    NlsBundleMessage messageAnnotation = method.getAnnotation(NlsBundleMessage.class);
    if (messageAnnotation != null) {
      return messageAnnotation.value();
    }
    return null;
  }

  /**
   * @param method the {@link NlsBundle} {@link Method}.
   * @return the {@link NlsMessageDescriptor} for the given {@link Method}.
   */
  public NlsMessageDescriptor getDescriptor(Method method) {

    return new NlsMessageDescriptor(method);
  }

  /**
   *
   * @param method is the {@link Method} of a {@link NlsBundle}.
   * @return an array of {@link NlsArgumentDescriptor}s describing the method parameters.
   * @since 7.3.0
   */
  public NlsArgumentDescriptor[] getArguments(Method method) {

    Class<?>[] parameterTypes = method.getParameterTypes();
    if (parameterTypes.length == 0) {
      return NlsArgumentDescriptor.EMPTY_ARRAY;
    }
    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
    NlsArgumentDescriptor[] arguments = new NlsArgumentDescriptor[parameterTypes.length];
    for (int i = 0; i < parameterTypes.length; i++) {
      String name = getName(parameterAnnotations[i], i);
      NlsArgumentDescriptor arg = new NlsArgumentDescriptor(name, parameterTypes[i], i);
      arguments[i] = arg;
    }
    return arguments;
  }

  private String getName(Annotation[] annotations, int i) {

    for (Annotation currentAnnotation : annotations) {
      if (currentAnnotation instanceof Named) {
        Named namedAnnotation = (Named) currentAnnotation;
        return namedAnnotation.value();
      }
    }
    return Integer.toString(i);
  }

  /**
   * Converts the given {@link NlsBundle} class to a {@link ResourceBundle}.
   *
   * @param bundleClass is the class reflecting a {@link NlsBundle}.
   * @return a {@link ResourceBundle} with the key/value pairs of the given {@code bundleClass} for
   *         {@link java.util.Locale#ROOT}.
   * @since 4.0.0
   */
  public ResourceBundle toResourceBundle(Class<? extends NlsBundle> bundleClass) {

    Hashtable<String, String> bundleProperties = new Hashtable<>();
    for (Method method : bundleClass.getMethods()) {
      if (isNlsBundleMethod(method, false)) {
        String key = getKey(method);
        String message = getMessage(method);
        bundleProperties.put(key, message);
      }
    }
    GenericResourceBundle bundle = new GenericResourceBundle(bundleProperties);
    return bundle;
  }

  /**
   * This method determines if the given {@link Method} is a regular {@link NlsBundle}-method.
   *
   * @param method the {@link Method} to check.
   * @param ignoreIllegalMethods - {@code true} if illegal methods (non NlsBundleMethods other than those defined by
   *        {@link Object}) should be ignored, {@code false} if they should cause an exception.
   * @return {@code true} if the given {@link Method} is a legal {@link NlsBundle} method, {@code false} otherwise (e.g.
   *         for {@code toString()}).
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
