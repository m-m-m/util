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
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleFactory;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsBundleOptions;
import net.sf.mmm.util.nls.api.NlsBundleWithLookup;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsTemplate;

/**
 * This is the abstract base implementation of {@link NlsBundleFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@NlsBundleOptions
public abstract class AbstractNlsBundleFactory extends AbstractComponent implements NlsBundleFactory {

  /** The name of the method {@link net.sf.mmm.util.nls.api.NlsBundleWithLookup#getMessage(String, Map)}. */
  public static final String METHOD_NAME_LOOKUP = "getMessage";

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
    this.bundleMap = new ConcurrentHashMap<>();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
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
   * This method gets the {@link NlsBundleOptions} for the given {@code bundleInterface}. If NOT present a default
   * instance is returned.
   *
   * @param bundleInterface is the {@link Class} reflecting the {@link NlsBundle} interface.
   * @return the annotated {@link NlsBundleOptions} or the default if {@code bundleInterface} is NOT annotated
   *         accordingly.
   */
  protected NlsBundleOptions getBundleOptions(Class<? extends NlsBundle> bundleInterface) {

    NlsBundleOptions options = bundleInterface.getAnnotation(NlsBundleOptions.class);
    if (options == null) {
      options = AbstractNlsBundleFactory.class.getAnnotation(NlsBundleOptions.class);
    }
    return options;
  }

  /**
   * This method creates a new {@link InvocationHandler} for the given {@code bundleInterface}.
   *
   * @param bundleInterface is the {@link Class} reflecting the {@link NlsBundle} interface.
   * @return the {@link InvocationHandler} for the given {@code bundleInterface}.
   */
  protected InvocationHandler createHandler(Class<? extends NlsBundle> bundleInterface) {

    String bundleName = NlsBundleHelper.getInstance().getQualifiedLocation(bundleInterface).getName();
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
   * This inner class is an {@link InvocationHandler} for the dynamic {@link NlsBundle} instance.
   */
  protected class NlsBundleInvocationHandler implements InvocationHandler {

    /** @see #invoke(Object, Method, Object[]) */
    private final String bundleName;

    /** The {@link NlsBundleOptions}. */
    private final NlsBundleOptions options;

    /** The cache for the {@link NlsBundleMethodInfo}s. */
    private final Map<String, NlsBundleMethodInfo> method2BundleInfoMap;

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
      this.method2BundleInfoMap = new ConcurrentHashMap<>();
    }

    /**
     * This method converts the given {@code arguments} to a {@link Map} with the
     * {@link NlsMessage#getArgument(String) arguments}.
     *
     * @param method is the {@link NlsBundle}-{@link Method} that has been invoked.
     * @param methodInfo is the {@link NlsBundleMethodInfo} for the given {@link Method}.
     * @param arguments are the arguments for the call of the {@link Method}.
     * @return the {@link Map} with the {@link NlsMessage#getArgument(String) arguments}.
     */
    protected Map<String, Object> createArgumentMap(Method method, NlsBundleMethodInfo methodInfo,
        Object[] arguments) {

      Map<String, Object> map = new HashMap<>();
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
     * This method gets the names of the {@link NlsMessage#getArgument(String) arguments} for the given {@link Method}.
     *
     * @param method is the {@link NlsBundle}-{@link Method} that has been invoked.
     * @return an array with the {@link NlsMessage#getArgument(String) argument-names}.
     */
    protected String[] getArgumentNames(Method method) {

      Annotation[][] parameterAnnotations = method.getParameterAnnotations();
      String[] names = new String[parameterAnnotations.length];
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
    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

      Object result;
      if (NlsMessage.class.equals(method.getReturnType())) {
        String methodName = method.getName();
        NlsBundleMethodInfo methodInfo;
        if (methodName.equals(METHOD_NAME_LOOKUP)) {
          assert (method.getDeclaringClass() == NlsBundleWithLookup.class);
          String actualMethodName;
          Map<String, Object> parameters;
          try {
            actualMethodName = (String) args[0];
            parameters = (Map<String, Object>) args[1];
          } catch (RuntimeException e) {
            throw new IllegalArgumentException(method.toGenericString(), e);
          }
          methodInfo = getOrCreateMethodInfo(null, null, actualMethodName, proxy);
          if (methodInfo == null) {
            // undefined method name
            result = null;
          } else {
            if ((parameters == null) || (parameters.isEmpty())) {
              result = getMessageFactory().create(methodInfo.template);
            } else {
              result = getMessageFactory().create(methodInfo.template, parameters);
            }
          }
        } else {
          methodInfo = getOrCreateMethodInfo(method, args, methodName, null);
          if ((args == null) || (args.length == 0)) {
            result = getMessageFactory().create(methodInfo.template);
          } else {
            Map<String, Object> messageArguments = createArgumentMap(method, methodInfo, args);
            result = getMessageFactory().create(methodInfo.template, messageArguments);
          }
        }
      } else {
        // equals, hashCode, etc.
        result = method.invoke(proxy, args);
      }
      return result;
    }

    /**
     * Gets {@link NlsBundleMethodInfo} for {@code methodName} from cache or creates it and puts it into the cache.
     *
     * @param method is the {@link Method} or {@code null} for generic invocation (lookup).
     * @param args are the method arguments or {@code null} for generic invocation (lookup).
     * @param methodName is the {@link Method#getName() name} of the {@link Method}.
     * @param proxy is the proxy object used for generic invocation to find the {@link Method} by
     *        {@code methodName} if not given.
     * @return the {@link NlsBundleMethodInfo}. May be {@code null} for generic invocation if method for
     *         {@code methodName} was not found (does not exist).
     */
    private NlsBundleMethodInfo getOrCreateMethodInfo(Method method, Object[] args, String methodName, Object proxy) {

      NlsBundleMethodInfo methodInfo;
      methodInfo = this.method2BundleInfoMap.get(methodName);
      if (methodInfo == null) {
        Method identifiedMethod = method;
        if (identifiedMethod == null) {
          Class<?>[] interfaces = proxy.getClass().getInterfaces();
          if (interfaces.length != 1) {
            throw new IllegalArgumentException(proxy.getClass().toString());
          }
          Method[] methods = interfaces[0].getMethods();
          for (Method currentMethod : methods) {
            if (currentMethod.getName().equals(methodName)) {
              identifiedMethod = currentMethod;
              break;
            }
          }
          if (identifiedMethod == null) {
            // Method not found / does not exist...
            return null;
          }
        }
        NlsTemplate template = createTemplate(identifiedMethod);
        String[] argumentNames;
        if ((args != null) && (args.length == 0)) {
          argumentNames = StringUtil.EMPTY_STRING_ARRAY;
        } else {
          argumentNames = getArgumentNames(identifiedMethod);
        }
        methodInfo = new NlsBundleMethodInfo(template, argumentNames);
        this.method2BundleInfoMap.put(methodName, methodInfo);
      }
      return methodInfo;
    }

    /**
     * This method creates the {@link NlsTemplate} for the given {@link NlsBundle}-{@link Method}.
     *
     * @param method is the {@link Method} of an {@link NlsBundle}.
     * @return the according {@link NlsTemplate}.
     */
    protected NlsTemplate createTemplate(Method method) {

      NlsBundleHelper bundleHelper = NlsBundleHelper.getInstance();
      String key = bundleHelper.getKey(method);
      String message = bundleHelper.getMessage(method);
      NlsTemplate template;
      if (message == null) {
        if (this.options.requireMessages()) {
          throw new ObjectNotFoundException("@" + NlsBundleMessage.class.getSimpleName(), method.getName());
        }
        template = new NlsTemplateImpl(this.bundleName, key);
      } else {
        template = new NlsTemplateImplWithMessage(this.bundleName, key, message);
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
