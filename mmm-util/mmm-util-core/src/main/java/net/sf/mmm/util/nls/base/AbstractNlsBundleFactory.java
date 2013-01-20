/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Named;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleFactory;
import net.sf.mmm.util.nls.api.NlsBundleKey;
import net.sf.mmm.util.nls.api.NlsBundleLocation;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsBundleOptions;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of {@link NlsBundleFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@NlsBundleOptions
public abstract class AbstractNlsBundleFactory extends AbstractComponent implements NlsBundleFactory {

  /** @see #createBundle(Class) */
  private final ClassLoader classLoader;

  /** @see #createBundle(Class) */
  private final Map<Class<? extends NlsBundle>, NlsBundle> bundleMap;

  /**
   * The constructor.
   */
  public AbstractNlsBundleFactory() {

    this(Thread.currentThread().getContextClassLoader());
  }

  /**
   * The constructor.
   * 
   * @param classLoader is the {@link ClassLoader} to use.
   */
  public AbstractNlsBundleFactory(ClassLoader classLoader) {

    super();
    this.classLoader = classLoader;
    this.bundleMap = new ConcurrentHashMap<Class<? extends NlsBundle>, NlsBundle>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <BUNDLE extends NlsBundle> BUNDLE createBundle(Class<BUNDLE> bundleInterface) {

    BUNDLE result = (BUNDLE) this.bundleMap.get(bundleInterface);
    if (result == null) {
      if (!bundleInterface.isInterface()) {
        throw new IllegalArgumentException(bundleInterface.getName());
      }
      InvocationHandler handler = createHandler(bundleInterface);
      result = (BUNDLE) Proxy.newProxyInstance(this.classLoader, new Class<?>[] { bundleInterface }, handler);
      this.bundleMap.put(bundleInterface, result);
    }
    return result;
  }

  /**
   * This method gets the {@link NlsBundleOptions} for the given <code>bundleInterface</code>. If NOT present
   * a default instance is returned.
   * 
   * @param bundleInterface is the {@link Class} reflecting the {@link NlsBundle} interface.
   * @return the annotated {@link NlsBundleOptions} or the default if <code>bundleInterface</code> is NOT
   *         annotated accordingly.
   */
  protected NlsBundleOptions getBundleOptions(Class<? extends NlsBundle> bundleInterface) {

    NlsBundleOptions options = bundleInterface.getAnnotation(NlsBundleOptions.class);
    if (options == null) {
      options = AbstractNlsBundleFactory.class.getAnnotation(NlsBundleOptions.class);
    }
    return options;
  }

  /**
   * This method creates a new {@link InvocationHandler} for the given <code>bundleInterface</code>.
   * 
   * @param bundleInterface is the {@link Class} reflecting the {@link NlsBundle} interface.
   * @return the {@link InvocationHandler} for the given <code>bundleInterface</code>.
   */
  protected InvocationHandler createHandler(Class<? extends NlsBundle> bundleInterface) {

    String bundleName = getBundleQualifiedName(bundleInterface);
    NlsBundleOptions options = getBundleOptions(bundleInterface);
    return new NlsBundleInvocationHandler(bundleName, options);
  }

  /**
   * @return the {@link NlsMessageFactory}.
   */
  protected NlsMessageFactory getMessageFactory() {

    return NlsAccess.getFactory();
  }

  /**
   * This Method gets the {@link java.util.ResourceBundle#getBundle(String) qualified name} of the
   * {@link java.util.ResourceBundle} associated by the given <code>bundleInterface</code>.
   * 
   * @see NlsBundleLocation
   * 
   * @param bundleInterface is the {@link NlsBundle} interface.
   * @return the qualified name.
   */
  protected String getBundleQualifiedName(Class<? extends NlsBundle> bundleInterface) {

    NlsBundleLocation bundleLocation = bundleInterface.getAnnotation(NlsBundleLocation.class);
    String bundlePackage = "";
    String bundleName = "";
    if (bundleLocation != null) {
      bundlePackage = bundleLocation.bundlePackage();
      bundleName = bundleLocation.bundleName();
    }
    if (bundlePackage.isEmpty()) {
      bundlePackage = bundleInterface.getPackage().getName();
    }
    if (bundleName.isEmpty()) {
      bundleName = bundleInterface.getSimpleName();
    }
    String bundleFqn;
    if (bundlePackage.isEmpty()) {
      bundleFqn = bundleName;
    } else {
      bundleFqn = bundlePackage + "." + bundleName;
    }
    return bundleFqn;
  }

  /**
   * This inner class is an {@link InvocationHandler} for the dynamic {@link NlsBundle} instance.
   */
  protected class NlsBundleInvocationHandler implements InvocationHandler {

    /** @see #invoke(Object, Method, Object[]) */
    private final String bundleName;

    /** The {@link NlsBundleOptions}. */
    private final NlsBundleOptions options;

    /** The cache for the {@link NlsBundleMethodInfo}s. */
    private final Map<Method, NlsBundleMethodInfo> method2BundleInfoMap;

    /**
     * The constructor.
     * 
     * @param bundleName is the qualified name of the {@link java.util.ResourceBundle}.
     * @param options are the {@link NlsBundleOptions}.
     */
    public NlsBundleInvocationHandler(String bundleName, NlsBundleOptions options) {

      super();
      this.bundleName = bundleName;
      this.options = options;
      this.method2BundleInfoMap = new ConcurrentHashMap<Method, NlsBundleMethodInfo>();
    }

    /**
     * This method converts the given <code>arguments</code> to a {@link Map} with the
     * {@link NlsMessage#getArgument(String) arguments}.
     * 
     * @param method is the {@link NlsBundle}-{@link Method} that has been invoked.
     * @param methodInfo is the {@link NlsBundleMethodInfo} for the given {@link Method}.
     * @param arguments are the arguments for the call of the {@link Method}.
     * @return the {@link Map} with the {@link NlsMessage#getArgument(String) arguments}.
     */
    protected Map<String, Object> createArgumentMap(Method method, NlsBundleMethodInfo methodInfo, Object[] arguments) {

      Map<String, Object> map = new HashMap<String, Object>();
      String[] argumentNames = methodInfo.argumentNames;
      for (int i = 0; i < arguments.length; i++) {
        Object old = map.put(argumentNames[i], arguments[i]);
        if (old != null) {
          throw new DuplicateObjectException(method, argumentNames[i]);
        }
      }
      return map;
    }

    /**
     * This method gets the names of the {@link NlsMessage#getArgument(String) arguments} for the given
     * {@link Method}.
     * 
     * @param method is the {@link NlsBundle}-{@link Method} that has been invoked.
     * @param arguments are the arguments for the call of the {@link Method}. Can actually be ignored but may
     *        be used instead of {@link Method#getParameterTypes()} to determine the length.
     * @return an array with the {@link NlsMessage#getArgument(String) argument-names}.
     */
    protected String[] getArgumentNames(Method method, Object[] arguments) {

      String[] names = new String[arguments.length];
      Annotation[][] parameterAnnotations = method.getParameterAnnotations();
      for (int i = 0; i < names.length; i++) {
        Named namedAnnotation = null;
        for (Annotation currentAnnotation : parameterAnnotations[i]) {
          if (currentAnnotation instanceof Named) {
            namedAnnotation = (Named) currentAnnotation;
          }
        }
        if (namedAnnotation != null) {
          names[i] = namedAnnotation.value();
        } else {
          names[i] = Integer.toString(i);
        }
      }
      return names;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

      Object result;
      if (NlsMessage.class.equals(method.getReturnType())) {
        NlsBundleMethodInfo methodInfo = this.method2BundleInfoMap.get(method);
        if (methodInfo == null) {
          NlsTemplate template = createTemplate(method);
          String[] argumentNames;
          if ((args == null) || (args.length == 0)) {
            argumentNames = StringUtil.EMPTY_STRING_ARRAY;
          } else {
            argumentNames = getArgumentNames(method, args);
          }
          methodInfo = new NlsBundleMethodInfo(template, argumentNames);
          this.method2BundleInfoMap.put(method, methodInfo);
        }
        if ((args == null) || (args.length == 0)) {
          result = getMessageFactory().create(methodInfo.template);
        } else {
          Map<String, Object> messageArguments = createArgumentMap(method, methodInfo, args);
          result = getMessageFactory().create(methodInfo.template, messageArguments);
        }
      } else {
        // equals, hashCode, etc.
        result = method.invoke(proxy, args);
      }
      return result;
    }

    /**
     * This method creates the {@link NlsTemplate} for the given {@link NlsBundle}-{@link Method}.
     * 
     * @param method is the {@link Method} of an {@link NlsBundle}.
     * @return the according {@link NlsTemplate}.
     */
    protected NlsTemplate createTemplate(Method method) {

      NlsBundleKey keyAnnotation = method.getAnnotation(NlsBundleKey.class);
      String key;
      if (keyAnnotation != null) {
        key = keyAnnotation.value();
      } else {
        key = method.getName();
      }
      NlsBundleMessage messageAnnotation = method.getAnnotation(NlsBundleMessage.class);
      NlsTemplate template;
      if (messageAnnotation != null) {
        String message = messageAnnotation.value();
        template = new NlsTemplateImplWithMessage(this.bundleName, key, message);
      } else {
        if (this.options.requireMessages()) {
          throw new ObjectNotFoundException("@" + NlsBundleMessage.class.getSimpleName(), method.getName());
        }
        template = new NlsTemplateImpl(this.bundleName, key);
      }
      return template;
    }
  }

  /**
   * This inner class holds all the information to be cached for a {@link NlsBundle}-method.
   */
  protected static class NlsBundleMethodInfo {

    /** @see #getTemplate() */
    private final NlsTemplate template;

    /** @see #getArgumentNames() */
    private final String[] argumentNames;

    /**
     * The constructor.
     * 
     * @param template - see {@link #getTemplate()}.
     * @param argumentNames - see {@link #getArgumentNames()}.
     */
    public NlsBundleMethodInfo(NlsTemplate template, String[] argumentNames) {

      super();
      this.template = template;
      this.argumentNames = argumentNames;
    }

    /**
     * @return the {@link NlsTemplate}
     */
    public NlsTemplate getTemplate() {

      return this.template;
    }

    /**
     * @return the names of the method parameters for the {@link NlsMessage#getArgument(String) arguments}.
     */
    public String[] getArgumentNames() {

      return this.argumentNames;
    }

  }

}
