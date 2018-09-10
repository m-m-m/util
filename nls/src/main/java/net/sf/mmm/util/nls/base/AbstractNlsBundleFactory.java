/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleFactory;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsBundleOptions;
import net.sf.mmm.util.nls.api.NlsBundleWithLookup;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.resource.api.ClasspathScanner;
import net.sf.mmm.util.resource.impl.AbstractClasspathScanner;

/**
 * This is the abstract base implementation of {@link NlsBundleFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@NlsBundleOptions // annotation here is used to get a default as fallback
public abstract class AbstractNlsBundleFactory extends AbstractComponent implements NlsBundleFactory {

  /** The name of the method {@link net.sf.mmm.util.nls.api.NlsBundleWithLookup#getMessage(String, Map)}. */
  public static final String METHOD_NAME_LOOKUP = "getMessage";

  /** An empty {@link String} array. */
  static final String[] EMPTY_STRING_ARRAY = new String[0];

  /** An internal trick used for optimization to avoid reflective parameter lookup. */
  private static final Object[] FAKE_ARGS = new Object[1];

  private static final Pattern NLS_BUNDLE_CLASS_NAME_PATTERN = Pattern.compile("(.*\\.)?NlsBundle.*Root");

  private final ClassLoader classLoader;

  private final Map<Class<? extends NlsBundle>, NlsBundle> bundleMap;

  private NlsMessageFactory messageFactory;

  private ClasspathScanner classpathScanner;

  private List<NlsBundleInvocationHandler> bundleDescriptors;

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

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.messageFactory == null) {
      this.messageFactory = NlsAccess.getFactory();
    }
    if (this.classpathScanner == null) {
      this.classpathScanner = AbstractClasspathScanner.getInstance();
    }
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    NlsAccess.setBundleFactory(this);
  }

  /**
   * @return the {@link NlsMessageFactory}.
   */
  protected NlsMessageFactory getMessageFactory() {

    return this.messageFactory;
  }

  /**
   * @param messageFactory the {@link NlsMessageFactory} to {@link Inject}.
   */
  @Inject
  public void setMessageFactory(NlsMessageFactory messageFactory) {

    getInitializationState().requireNotInitilized();
    this.messageFactory = messageFactory;
  }

  /**
   * @return the classpathScanner
   */
  public ClasspathScanner getClasspathScanner() {

    return this.classpathScanner;
  }

  /**
   * @param classpathScanner the {@link ClasspathScanner} to {@link Inject}.
   */
  @Inject
  public void setClasspathScanner(ClasspathScanner classpathScanner) {

    getInitializationState().requireNotInitilized();
    this.classpathScanner = classpathScanner;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <BUNDLE extends NlsBundle> BUNDLE createBundle(Class<BUNDLE> bundleInterface) {

    // #151: when switching to Java8: change get to computeIfAbsence
    BUNDLE result = (BUNDLE) this.bundleMap.get(bundleInterface);
    if (result == null) {
      result = createBundleInternal(bundleInterface);
      this.bundleMap.put(bundleInterface, result);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private <BUNDLE extends NlsBundle> BUNDLE createBundleInternal(Class<BUNDLE> bundleInterface) {

    if (!bundleInterface.isInterface()) {
      throw new IllegalArgumentException(bundleInterface.getName());
    }
    InvocationHandler handler = createHandler(bundleInterface);
    BUNDLE result = (BUNDLE) Proxy.newProxyInstance(this.classLoader, new Class<?>[] { bundleInterface }, handler);
    return result;
  }

  /**
   * This method gets the {@link NlsBundleOptions} for the given {@code bundleInterface}. If NOT present a
   * default instance is returned.
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
    return new NlsBundleInvocationHandler(bundleInterface, bundleName, options);
  }

  /**
   * @return a {@link Collection} of {@link NlsBundleDescriptor}s for all {@link NlsBundle}-interfaces on the
   *         classpath following the suggested naming convention {@code NlsBundle*Root}.
   */
  public Collection<? extends NlsBundleDescriptor> getNlsBundleDescriptors() {

    if (this.bundleDescriptors == null) {
      synchronized (this) {
        if (this.bundleDescriptors == null) {
          List<NlsBundleInvocationHandler> descriptors = populateNlsBundleDescriptors();
          this.bundleDescriptors = Collections.unmodifiableList(descriptors);
        }
      }
    }
    return this.bundleDescriptors;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private List<NlsBundleInvocationHandler> populateNlsBundleDescriptors() {

    List<NlsBundleInvocationHandler> descriptors = new ArrayList<>();

    Filter<String> classnameFilter = new Filter<String>() {

      @Override
      public boolean accept(String classname) {

        return NLS_BUNDLE_CLASS_NAME_PATTERN.matcher(classname).matches();
      }
    };
    Filter<Class<?>> classFilter = new Filter<Class<?>>() {

      @Override
      public boolean accept(Class<?> javaClass) {

        return NlsBundle.class.isAssignableFrom(javaClass);
      }
    };
    @SuppressWarnings({ "unchecked", "rawtypes" })
    Iterable<Class<? extends NlsBundle>> classes = (Iterable) this.classpathScanner.getClasspathResourceClasses(classnameFilter, classFilter);
    for (Class<? extends NlsBundle> bundleInterface : classes) {
      NlsBundle bundle = createBundle(bundleInterface);
      NlsBundleInvocationHandler invocationHandler = (NlsBundleInvocationHandler) Proxy.getInvocationHandler(bundle);
      invocationHandler.populate();
      descriptors.add(invocationHandler);
    }
    return descriptors;
  }

  /**
   * Interface describing an {@link NlsBundle} interface.
   *
   * @since 7.3.0
   */
  public interface NlsBundleDescriptor {

    /**
     * @return an {@link Iterable} with the containers {@link Provider#get() providing} the contained
     *         {@link NlsTemplate}s.
     */
    Iterable<? extends Provider<NlsTemplate>> getTemplateContainers();

  }

  /**
   * This inner class is an {@link InvocationHandler} for the dynamic {@link NlsBundle} instance.
   */
  protected class NlsBundleInvocationHandler implements InvocationHandler, NlsBundleDescriptor {

    private final Class<? extends NlsBundle> bundleInterface;

    /** @see #invoke(Object, Method, Object[]) */
    private final String bundleName;

    /** The {@link NlsBundleOptions}. */
    private final NlsBundleOptions options;

    /** The cache for the {@link NlsBundleMethodInfo}s. */
    private final Map<String, NlsBundleMethodInfo> method2BundleInfoMap;

    /**
     * The constructor.
     *
     * @param bundleInterface the {@link NlsBundle} interface.
     * @param bundleName is the qualified name of the {@link java.util.ResourceBundle}.
     * @param options are the {@link NlsBundleOptions}.
     */
    public NlsBundleInvocationHandler(Class<? extends NlsBundle> bundleInterface, String bundleName, NlsBundleOptions options) {

      super();
      this.bundleInterface = bundleInterface;
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
    protected Map<String, Object> createArgumentMap(Method method, NlsBundleMethodInfo methodInfo, Object[] arguments) {

      Map<String, Object> map = new HashMap<>();
      String[] argumentNames = methodInfo.argumentNames;
      for (int i = 0; i < arguments.length; i++) {
        Object old = map.put(argumentNames[i], arguments[i]);
        if (old != null) {
          throw new IllegalStateException("Duplicate argument name '" + argumentNames[i] + "' in '" + method.getName() + "'.");
        }
      }
      return map;
    }

    /**
     * This method gets the names of the {@link NlsMessage#getArgument(String) arguments} for the given
     * {@link Method}.
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
        result = handleObjectMethod(proxy, method, args);
      }
      return result;
    }

    @SuppressWarnings("null") // Eclipse is a little stupid...
    private Object handleObjectMethod(Object proxy, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {

      int len;
      if (args == null) {
        len = 0;
      } else {
        len = args.length;
      }
      if ("equals".equals(method.getName()) && (len == 1)) {
        return Boolean.valueOf(args[0] == proxy);
      } else if ("hashCode".equals(method.getName()) && (len == 0)) {
        return Integer.valueOf(this.bundleName.hashCode());
      } else if ("toString".equals(method.getName()) && (len == 0)) {
        return this.bundleName;
      }
      throw new UnsupportedOperationException(method.toString());
    }

    /**
     * Gets {@link NlsBundleMethodInfo} for {@code methodName} from cache or creates it and puts it into the
     * cache.
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
      // #151: when switching to Java8: change get to computeIfAbsence
      methodInfo = this.method2BundleInfoMap.get(methodName);
      if (methodInfo == null) {
        methodInfo = createMethodInfo(method, args, methodName, proxy);
        if (methodInfo != null) {
          this.method2BundleInfoMap.put(methodName, methodInfo);
        }
      }
      return methodInfo;
    }

    private NlsBundleMethodInfo createMethodInfo(Method method, Object[] args, String methodName, Object proxy) {

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
        argumentNames = EMPTY_STRING_ARRAY;
      } else {
        argumentNames = getArgumentNames(identifiedMethod);
      }
      return new NlsBundleMethodInfo(template, argumentNames);
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
          throw new IllegalStateException("Missing @" + NlsBundleMessage.class.getSimpleName() + " for " + method.getName());
        }
        template = new NlsTemplateImpl(this.bundleName, key);
      } else {
        template = new NlsTemplateImplWithMessage(this.bundleName, key, message);
      }
      return template;
    }

    @Override
    public Iterable<? extends Provider<NlsTemplate>> getTemplateContainers() {

      return this.method2BundleInfoMap.values();
    }

    private void populate() {

      NlsBundleHelper bundleHelper = NlsBundleHelper.getInstance();
      for (Method method : this.bundleInterface.getMethods()) {
        if (bundleHelper.isNlsBundleMethod(method, true)) {
          getOrCreateMethodInfo(method, FAKE_ARGS, method.getName(), null);
        }
      }
    }

  }

  /**
   * This inner class holds all the information to be cached for a {@link NlsBundle}-method.
   */
  protected static class NlsBundleMethodInfo implements Provider<NlsTemplate> {

    private final NlsTemplate template;

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
     * @see #getTemplate()
     */
    @Override
    public NlsTemplate get() {

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
