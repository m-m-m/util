/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.filter.base.ConstantFilter;
import net.sf.mmm.util.filter.base.ListCharFilter;
import net.sf.mmm.util.lang.api.BasicHelper;
import net.sf.mmm.util.lang.api.Visitor;
import net.sf.mmm.util.reflect.api.ClassResolver;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.impl.GenericArrayTypeImpl;
import net.sf.mmm.util.reflect.impl.GenericTypeImpl;
import net.sf.mmm.util.reflect.impl.LowerBoundWildcardType;
import net.sf.mmm.util.reflect.impl.ParameterizedTypeImpl;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.reflect.impl.UnboundedWildcardType;
import net.sf.mmm.util.reflect.impl.UpperBoundWildcardType;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This class is a collection of utility functions for dealing with {@link java.lang.reflect reflection}.
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class ReflectionUtilImpl extends ReflectionUtilLimitedImpl implements ReflectionUtil {

  private static final Logger LOG = LoggerFactory.getLogger(ReflectionUtilImpl.class);

  private static final String FILE_EXTENSION_JAR = "jar";

  private static final String URL_SUFFIX_JAR = "!/";

  private static final String URL_PREFIX_JAR = "jar:";

  private static final String URL_PREFIX_FILE = "file:";

  /** System property for path separator. */
  private static final String PROPERTY_PATH_SEPARATOR = "path.separator";

  /** System property for classpath. */
  private static final String PROPERTY_JAVA_CLASS_PATH = "java.class.path";

  /** The prefix of resources in WAR-files. */
  private static final String WEB_INF_CLASSES = "WEB-INF/classes/";

  private static ReflectionUtil instance;

  /** @see #toType(CharSequenceScanner, ClassResolver, Type) */
  private static final CharFilter CHAR_FILTER = new ListCharFilter(false, '<', '[', ',', '?', '>');

  /**
   * The constructor.
   */
  public ReflectionUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link ReflectionUtil}. <br>
   * <b>ATTENTION:</b><br>
   * Please prefer dependency-injection instead of using this method.
   *
   * @return the singleton instance.
   */
  public static ReflectionUtil getInstance() {

    if (instance == null) {
      ReflectionUtilImpl impl = null;
      synchronized (ReflectionUtilImpl.class) {
        if (instance == null) {
          impl = new ReflectionUtilImpl();
          instance = impl;
        }
      }
      if (impl != null) {
        // static access is generally discouraged, cyclic dependency forces this ugly initialization to
        // prevent deadlock.
        impl.initialize();
      }
    }
    return instance;
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    if (instance == null) {
      instance = this;
    }
  }

  @Override
  public Class<?>[] getClasses(Object[] objects) {

    Class<?>[] result = new Class[objects.length];
    for (int i = 0; i < objects.length; i++) {
      if (objects[i] == null) {
        result[i] = null;
      } else {
        result[i] = objects[i].getClass();
      }
    }
    return result;

  }

  @Override
  public <T> GenericType<T> createGenericType(Class<T> type) {

    return new SimpleGenericTypeImpl<>(type);
  }

  @Override
  @SuppressWarnings("rawtypes")
  public GenericType<?> createGenericType(Type type) {

    if (type instanceof GenericType) {
      return (GenericType<?>) type;
    } else if (type instanceof Class) {
      return createGenericType((Class<?>) type);
    } else {
      return new GenericTypeImpl(type);
    }
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public GenericType<?> createGenericType(Type type, GenericType<?> definingType) {

    return new GenericTypeImpl(type, definingType);
  }

  @Override
  public GenericType<?> createGenericType(Type type, Class<?> definingType) {

    return createGenericType(type, createGenericType(definingType));
  }

  @Override
  public <E> GenericType<List<E>> createGenericTypeOfList(GenericType<E> elementType) {

    return new GenericTypeBuilder<List<E>>() { // anonymous
    }.with(new GenericTypeVariable<E>() { // anonymous
    }, elementType).build();
  }

  @Override
  public <K, V> GenericType<Map<K, V>> createGenericTypeOfMap(GenericType<K> keyType, GenericType<V> valueType) {

    return new GenericTypeBuilder<Map<K, V>>() { // anonymous
    }.with(new GenericTypeVariable<K>() { // anonymous
    }, keyType).with(new GenericTypeVariable<V>() { // anonymous
    }, valueType).build();
  }

  @Override
  public <E> GenericType<Set<E>> createGenericTypeOfSet(GenericType<E> elementType) {

    return new GenericTypeBuilder<Set<E>>() { // anonymous
    }.with(new GenericTypeVariable<E>() { // anonymous
    }, elementType).build();
  }

  /**
   * This method walks up the {@link Class}-hierarchy from {@code descendant} up to {@code ancestor} and returns the
   * sub-class or sub-interface of {@code ancestor} on that hierarchy-path. <br>
   * Please note that if {@code ancestor} is an {@link Class#isInterface() interface}, the hierarchy may NOT be unique.
   * In such case it will be unspecified which of the possible paths is used.
   *
   * @param ancestor is the super-class or super-interface of {@code descendant}.
   * @param descendant is the sub-class or sub-interface of {@code ancestor}.
   * @return the sub-class or sub-interface on the hierarchy-path from {@code descendant} up to {@code ancestor}.
   */
  protected Class<?> getSubClass(Class<?> ancestor, Class<?> descendant) {

    if (ancestor == descendant) {
      return null;
    }
    if (!ancestor.isAssignableFrom(descendant)) {
      return null;
    }
    Class<?> child = descendant;
    if (ancestor.isInterface()) {
      while (true) {
        for (Class<?> childInterface : child.getInterfaces()) {
          if (childInterface == ancestor) {
            return child;
          } else if (ancestor.isAssignableFrom(childInterface)) {
            child = childInterface;
            break;
          }
        }
      }
    } else {
      Class<?> parent = child.getSuperclass();
      while (parent != ancestor) {
        child = parent;
        parent = child.getSuperclass();
      }
      return child;
    }
  }

  /**
   * This method walks up the {@link Class}-hierarchy from {@code descendant} up to {@code ancestor} and returns the
   * sub-class or sub-interface of {@code ancestor} on that hierarchy-path. <br>
   * Please note that if {@code ancestor} is an {@link Class#isInterface() interface}, the hierarchy may NOT be unique.
   * In such case it will be unspecified which of the possible paths is used.
   *
   * @param ancestor is the super-class or super-interface of {@code descendant}.
   * @param descendant is the sub-class or sub-interface of {@code ancestor}.
   * @return the sub-class or sub-interface on the hierarchy-path from {@code descendant} up to {@code ancestor}.
   */
  protected Type getGenericDeclaration(Class<?> ancestor, Class<?> descendant) {

    if (ancestor == descendant) {
      return null;
    }
    if (!ancestor.isAssignableFrom(descendant)) {
      return null;
    }
    Class<?> child = descendant;
    if (ancestor.isInterface()) {
      while (true) {
        Class<?>[] interfaces = child.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
          Class<?> childInterface = interfaces[i];
          if (childInterface == ancestor) {
            return child.getGenericInterfaces()[i];
          } else if (ancestor.isAssignableFrom(childInterface)) {
            child = childInterface;
            break;
          }
        }
      }
    } else {
      Class<?> parent = child.getSuperclass();
      while (parent != ancestor) {
        child = parent;
        parent = child.getSuperclass();
      }
      return child.getGenericSuperclass();
    }
  }

  @Override
  public Class<?> getArrayClass(Class<?> componentType) {

    // this is sort of stupid but there seems no other way...
    return Array.newInstance(componentType, 0).getClass();
  }

  @Override
  public Type toType(String type) {

    return toType(type, ClassResolver.CLASS_FOR_NAME_RESOLVER);
  }

  @Override
  public Type toType(String type, ClassResolver resolver) {

    try {
      CharSequenceScanner parser = new CharSequenceScanner(type);
      Type result = toType(parser, resolver, null);
      parser.skipWhile(' ');
      if (parser.hasNext()) {
        throw new IllegalArgumentException(parser.read(Integer.MAX_VALUE));
      }
      return result;
    } catch (RuntimeException e) {
      throw new IllegalArgumentException(type, e);
    }
  }

  /**
   * This method parses the given {@code type} as generic {@link Type}. <br>
   * This would be easier when using {@code StringParser} but we want to avoid the dependency on {@code util-misc}.
   *
   * @param parser is the string-parser on the type string to parse.
   * @param resolver is used to resolve classes.
   * @param owner is the {@link java.lang.reflect.ParameterizedType#getOwnerType() owner-type} or {@code null}.
   * @return the parsed type.
   */
  private Type toType(CharSequenceScanner parser, ClassResolver resolver, Type owner) {

    parser.skipWhile(' ');
    Type result;
    // wildcard-type?
    char c = parser.forcePeek();
    if (c == '?') {
      parser.next();
      int spaces = parser.skipWhile(' ');
      if (spaces > 0) {
        String sequence = parser.readUntil(' ', false);
        boolean lowerBound;
        if ("super".equals(sequence)) {
          lowerBound = false;
        } else if ("extends".equals(sequence)) {
          lowerBound = true;
        } else {
          throw new IllegalWildcardSequenceException(sequence);
        }
        Type bound = toType(parser, resolver, null);
        if (lowerBound) {
          result = new UpperBoundWildcardType(bound);
        } else {
          result = new LowerBoundWildcardType(bound);
        }
      } else {
        result = UnboundedWildcardType.INSTANCE;
      }
      parser.skipWhile(' ');
      c = parser.forcePeek();
      if (c == '[') {
        parser.next();
        if (!parser.expect(']')) {
          // TODO: NLS
          throw new IllegalArgumentException("Illegal array!");
        }
        result = new GenericArrayTypeImpl(result);
      }
      return result;
    }
    String segment = parser.readWhile(CHAR_FILTER).trim();
    c = parser.forceNext();
    if (c == '[') {
      if (!parser.expect(']')) {
        // TODO: NLS
        throw new IllegalArgumentException("Illegal array!");
      }
      // array...
      StringBuilder sb = new StringBuilder(segment.length() + 3);
      sb.append("[L");
      sb.append(segment);
      sb.append(";");
      result = resolver.resolveClass(sb.toString());
    } else {
      Class<?> segmentClass = resolver.resolveClass(segment);
      result = segmentClass;
      if (c == '<') {
        List<Type> typeArgList = new ArrayList<>();
        while (true) {
          Type arg = toType(parser, resolver, null);
          typeArgList.add(arg);
          char d = parser.forceNext();
          if (d == '>') {
            // list completed...
            Type[] typeArguments = typeArgList.toArray(new Type[typeArgList.size()]);
            result = new ParameterizedTypeImpl(segmentClass, typeArguments, owner);
            parser.skipWhile(' ');
            d = parser.forcePeek();
            if (d == '[') {
              parser.next();
              if (!parser.expect(']')) {
                // TODO: NLS
                throw new IllegalArgumentException("Illegal array!");
              }
              result = new GenericArrayTypeImpl(result);
            } else if (d == '.') {
              parser.next();
              result = toType(parser, resolver, result);
            }
            break;
          } else if (d != ',') {
            // TODO
            throw new IllegalArgumentException("Failed to parse!");
          }
        }
      } else if (c != 0) {
        parser.stepBack();
      }
    }
    return result;
  }

  @Override
  public String toString(Type type) {

    if (type instanceof Class<?>) {
      return ((Class<?>) type).getName();
    } else {
      return type.toString();
    }
  }

  @Override
  public String toStringSimple(Type type) {

    final StringBuilder buffer = new StringBuilder();
    Visitor<Class<?>> formatter = new Visitor<Class<?>>() {

      @Override
      public void visit(Class<?> clazz) {

        buffer.append(clazz.getSimpleName());
      }
    };
    toString(type, buffer, formatter);
    return buffer.toString();
  }

  @Override
  public void toString(Type type, Appendable appendable, Visitor<Class<?>> classFormatter) {

    Objects.requireNonNull(type, "type");
    Objects.requireNonNull(appendable, "appendable");
    Objects.requireNonNull(classFormatter, "classFormatter");
    try {
      Type actualType = type;
      if (type instanceof GenericType<?>) {
        actualType = ((GenericType<?>) type).getType();
      }
      if (actualType instanceof Class<?>) {
        classFormatter.visit((Class<?>) actualType);
      } else if (actualType instanceof ParameterizedType) {
        ParameterizedType parameterizedType = (ParameterizedType) actualType;
        Type ownerType = parameterizedType.getOwnerType();
        if (ownerType != null) {
          toString(ownerType, appendable, classFormatter);
          appendable.append('.');
        }
        toString(parameterizedType.getRawType(), appendable, classFormatter);
        appendable.append('<');
        boolean separator = false;
        for (Type arg : parameterizedType.getActualTypeArguments()) {
          if (separator) {
            appendable.append(", ");
          }
          toString(arg, appendable, classFormatter);
          separator = true;
        }
        appendable.append('>');
      } else if (actualType instanceof TypeVariable<?>) {
        TypeVariable<?> typeVariable = (TypeVariable<?>) actualType;
        appendable.append(typeVariable.getName());
        Type[] bounds = typeVariable.getBounds();
        if (bounds.length > 0) {
          // is this supported after all?
          Type firstBound = bounds[0];
          if (!Object.class.equals(firstBound)) {
            appendable.append(" extends ");
            toString(firstBound, appendable, classFormatter);
          }
        }
      } else if (actualType instanceof WildcardType) {
        WildcardType wildcardType = (WildcardType) actualType;
        Type[] lowerBounds = wildcardType.getLowerBounds();
        if (lowerBounds.length > 0) {
          // "? super "
          appendable.append(LowerBoundWildcardType.PREFIX);
          toString(lowerBounds[0], appendable, classFormatter);
        } else {
          Type[] upperBounds = wildcardType.getUpperBounds();
          if (upperBounds.length > 0) {
            // "? extends "
            appendable.append(UpperBoundWildcardType.PREFIX);
            toString(upperBounds[0], appendable, classFormatter);
          } else {
            // ?
            appendable.append(UnboundedWildcardType.PREFIX);
          }
        }
      } else if (actualType instanceof GenericArrayType) {
        toString(((GenericArrayType) actualType).getGenericComponentType(), appendable, classFormatter);
        appendable.append("[]");
      } else {
        throw new IllegalStateException(type.getClass().getName());
      }
    } catch (IOException e) {
      throw new IllegalStateException("Error writing type to Appendable.", e);
    }
  }

  @Override
  public int compare(Class<?> class1, Class<?> class2) {

    if (class1.equals(class2)) {
      return 0;
    } else if (class1.isAssignableFrom(class2)) {
      return -1;
    } else if (class2.isAssignableFrom(class1)) {
      return 1;
    } else {
      return Integer.MIN_VALUE;
    }
  }

  @Override
  public boolean isMarkerInterface(Class<?> interfaceClass) {

    if (Cloneable.class == interfaceClass) {
      return true;
    }
    if (Serializable.class == interfaceClass) {
      return true;
    }
    if (EventListener.class == interfaceClass) {
      return true;
    }
    return false;
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public <T> T getStaticField(Class<?> type, String fieldName, Class<T> fieldType, boolean exactTypeMatch, boolean mustBeFinal, boolean inherit)
      throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {

    Field field = type.getField(fieldName);
    int modifiers = field.getModifiers();
    if (!Modifier.isStatic(modifiers)) {
      throw new IllegalArgumentException("Field '" + fieldName + "' (in type '" + type + "') is not static!");
    }
    if (mustBeFinal && !Modifier.isFinal(modifiers)) {
      throw new IllegalArgumentException("Field '" + fieldName + "' (in type '" + type + "') is not final!");
    }
    Class<?> actualType = field.getType();
    boolean typeMismatch = false;
    if (exactTypeMatch) {
      typeMismatch = !actualType.equals(fieldType);
    } else {
      Class actualObjectType = getNonPrimitiveType(actualType);
      Class expectedObjectType = getNonPrimitiveType(fieldType);
      if (!expectedObjectType.isAssignableFrom(actualObjectType)) {
        typeMismatch = true;
      }
    }
    if ((!inherit) && (field.getDeclaringClass() != type)) {
      // TODO: this sucks
      throw new NoSuchFieldException(fieldName);
    }
    if (typeMismatch) {
      throw new IllegalArgumentException(
          "Field '" + fieldName + "' (in type '" + type + "') has type '" + field.getType() + "' but requested type was '" + fieldType + "'!");
    }
    return (T) field.get(null);
  }

  @Override
  public <T> T getStaticFieldOrNull(Class<?> type, String fieldName, Class<T> fieldType, boolean exactTypeMatch, boolean mustBeFinal, boolean inherit)
      throws IllegalArgumentException {

    try {
      return getStaticField(type, fieldName, fieldType, exactTypeMatch, mustBeFinal, inherit);
    } catch (NoSuchFieldException e) {
      return null;
    } catch (IllegalAccessException e) {
      return null;
    }
  }

  @Override
  public Method getParentMethod(Method method) throws SecurityException {

    return getParentMethod(method.getDeclaringClass(), method.getName(), method.getParameterTypes());
  }

  @Override
  public Method getParentMethod(Class<?> inheritingClass, String methodName, Class<?>[] parameterTypes) throws SecurityException {

    Class<?> parentClass = inheritingClass.getSuperclass();
    if (parentClass != null) {
      try {
        return parentClass.getMethod(methodName, parameterTypes);
      } catch (NoSuchMethodException e) {
        // method does NOT exist...
      }
    }
    Class<?>[] interfaces = inheritingClass.getInterfaces();
    for (int i = 0; i < interfaces.length; i++) {
      try {
        return interfaces[i].getMethod(methodName, parameterTypes);
      } catch (NoSuchMethodException e) {
        // method does NOT exist...
      }
    }
    // search indirectly from interfaces...
    for (int i = 0; i < interfaces.length; i++) {
      Method result = getParentMethod(interfaces[i], methodName, parameterTypes);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  /**
   * This method scans the given {@code packageDirectory} recursively for resources.
   *
   * @param packageDirectory is the directory representing the {@link Package}.
   * @param qualifiedNameBuilder is a {@link StringBuilder} containing the qualified prefix (the {@link Package} with a
   *        trailing dot).
   * @param qualifiedNamePrefixLength the length of the prefix used to rest the string-builder after reuse.
   * @param visitor is the {@link ResourceVisitor}.
   */
  private static void visitResources(File packageDirectory, StringBuilder qualifiedNameBuilder, int qualifiedNamePrefixLength, ResourceVisitor visitor) {

    for (File childFile : packageDirectory.listFiles()) {
      String fileName = childFile.getName();
      qualifiedNameBuilder.setLength(qualifiedNamePrefixLength);
      if (childFile.isDirectory()) {
        StringBuilder subBuilder = new StringBuilder(qualifiedNameBuilder);
        subBuilder.append(fileName);
        subBuilder.append('/');
        if (visitor.visitPackage(subBuilder.toString())) {
          visitResources(childFile, subBuilder, subBuilder.length(), visitor);
        }
      } else {
        qualifiedNameBuilder.append(fileName);
        visitor.visitResource(qualifiedNameBuilder.toString());
      }
    }
  }

  @Override
  public Set<String> findClassNames(String packageName, boolean includeSubPackages) {

    Set<String> classSet = new HashSet<>();
    findClassNames(packageName, includeSubPackages, classSet);
    return classSet;
  }

  @Override
  public void findClassNames(String packageName, boolean includeSubPackages, Set<String> classSet) {

    Filter<String> filter = ConstantFilter.getInstance(true);
    findClassNames(packageName, includeSubPackages, classSet, filter, getDefaultClassLoader());
  }

  @Override
  public Set<String> findClassNames(String packageName, boolean includeSubPackages, Filter<? super String> filter) {

    Set<String> result = new HashSet<>();
    findClassNames(packageName, includeSubPackages, result, filter, getDefaultClassLoader(filter.getClass()));
    return result;
  }

  @Override
  public Set<String> findClassNames(String packageName, boolean includeSubPackages, Filter<? super String> filter, ClassLoader classLoader) {

    Set<String> result = new HashSet<>();
    findClassNames(packageName, includeSubPackages, result, filter, classLoader);
    return result;
  }

  /**
   * @see #findClassNames(String, boolean, Filter, ClassLoader)
   *
   * @param packageName is the name of the {@link Package} to scan.
   * @param includeSubPackages - if {@code true} all sub-packages of the specified {@link Package} will be included in
   *        the search.
   * @param classSet is where to add the classes.
   * @param filter is used to {@link Filter#accept(Object) filter} the {@link Class}-names to be added to the resulting
   *        {@link Set}. The {@link Filter} will receive {@link Class#getName() fully qualified class-names} as argument
   *        (e.g. "net.sf.mmm.reflect.api.ReflectionUtil").
   * @param classLoader is the explicit {@link ClassLoader} to use.
   * @if the operation failed with an I/O error.
   */
  protected void findClassNames(String packageName, boolean includeSubPackages, Set<String> classSet, Filter<? super String> filter, ClassLoader classLoader) {

    ResourceVisitor visitor = new ClassNameCollector(classSet, filter);
    visitResourceNames(packageName, includeSubPackages, classLoader, visitor);
  }

  @Override
  public Set<String> findResourceNames(String packageName, boolean includeSubPackages, Filter<? super String> filter) {

    return findResourceNames(packageName, includeSubPackages, filter, getDefaultClassLoader(filter.getClass()));
  }

  @Override
  public Set<String> findResourceNames(String packageName, boolean includeSubPackages, Filter<? super String> filter, ClassLoader classLoader) {

    Set<String> result = new HashSet<>();
    ResourceNameCollector visitor = new ResourceNameCollector(result, filter);
    visitResourceNames(packageName, includeSubPackages, classLoader, visitor);
    return result;
  }

  /**
   * This method does the actual magic to locate resources on the classpath.
   *
   * @param packageName is the name of the {@link Package} to scan. Both "." and "/" are accepted as separator (e.g.
   *        "net.sf.mmm.util.reflect).
   * @param includeSubPackages - if {@code true} all sub-packages of the specified {@link Package} will be included in
   *        the search.
   * @param classLoader is the explicit {@link ClassLoader} to use.
   * @param visitor is the {@link ResourceVisitor}.
   * @if the operation failed with an I/O error.
   */
  public void visitResourceNames(String packageName, boolean includeSubPackages, ClassLoader classLoader, ResourceVisitor visitor) {

    try {
      String path = packageName.replace('.', '/');
      if (path.isEmpty()) {
        LOG.debug("Scanning entire classpath...");
      } else {
        LOG.trace("Scanning for resources on classpath for {}", path);
      }
      StringBuilder qualifiedNameBuilder = new StringBuilder(path);
      if (qualifiedNameBuilder.length() > 0) {
        qualifiedNameBuilder.append('/');
      }
      String pathWithPrefix = qualifiedNameBuilder.toString();
      int qualifiedNamePrefixLength = qualifiedNameBuilder.length();
      Enumeration<URL> urls = classLoader.getResources(path);
      Set<String> urlSet = new HashSet<>();
      while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
        visitResourceUrl(includeSubPackages, visitor, pathWithPrefix, qualifiedNameBuilder, qualifiedNamePrefixLength, url, urlSet);
      }
      if (path.isEmpty()) {
        visitResourceClassloader(includeSubPackages, classLoader, visitor, pathWithPrefix, qualifiedNameBuilder, qualifiedNamePrefixLength, urlSet);
        visitResourceClasspath(includeSubPackages, visitor, qualifiedNameBuilder, pathWithPrefix, qualifiedNamePrefixLength, urlSet);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Error reading resources.", e);
    }
  }

  private void visitResourceClasspath(boolean includeSubPackages, ResourceVisitor visitor, StringBuilder qualifiedNameBuilder, String pathWithPrefix,
      int qualifiedNamePrefixLength, Set<String> urlSet) throws MalformedURLException, UnsupportedEncodingException, IOException {

    String classpath = System.getProperty(PROPERTY_JAVA_CLASS_PATH);
    String separator = System.getProperty(PROPERTY_PATH_SEPARATOR);
    String[] entries = classpath.split(separator);
    for (String entry : entries) {
      File file = new File(entry);
      URL url;
      if (BasicHelper.toLowerCase(file.getName()).endsWith(FILE_EXTENSION_JAR)) {
        url = createJarUrl(URL_PREFIX_FILE + file.getAbsolutePath());
      } else {
        url = file.toURI().toURL();
      }
      visitResourceUrl(includeSubPackages, visitor, pathWithPrefix, qualifiedNameBuilder, qualifiedNamePrefixLength, url, urlSet);
    }
  }

  private void visitResourceClassloader(boolean includeSubPackages, ClassLoader classLoader, ResourceVisitor visitor, String pathWithPrefix,
      StringBuilder qualifiedNameBuilder, int qualifiedNamePrefixLength, Set<String> urlSet) throws IOException {

    if (classLoader instanceof URLClassLoader) {
      URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
      for (URL url : urlClassLoader.getURLs()) {
        String file = BasicHelper.toLowerCase(url.getFile());
        if (file.endsWith(FILE_EXTENSION_JAR)) {
          URL jarUrl = createJarUrl(url.toString());
          visitResourceUrl(includeSubPackages, visitor, pathWithPrefix, qualifiedNameBuilder, qualifiedNamePrefixLength, jarUrl, urlSet);
        } else {
          visitResourceUrl(includeSubPackages, visitor, pathWithPrefix, qualifiedNameBuilder, qualifiedNamePrefixLength, url, urlSet);
        }
      }
    }
    ClassLoader parent = classLoader.getParent();
    if (parent != null) {
      visitResourceClassloader(includeSubPackages, parent, visitor, pathWithPrefix, qualifiedNameBuilder, qualifiedNamePrefixLength, urlSet);
    }
  }

  private URL createJarUrl(String path) throws MalformedURLException {

    return new URL(URL_PREFIX_JAR + path + URL_SUFFIX_JAR);
  }

  private void visitResourceUrl(boolean includeSubPackages, ResourceVisitor visitor, String pathWithPrefix, StringBuilder qualifiedNameBuilder,
      int qualifiedNamePrefixLength, URL url, Set<String> urlSet) throws UnsupportedEncodingException, IOException {

    LOG.trace("Scanning classpath entry for URL: {}", url);
    boolean added = urlSet.add(url.toString());
    if (!added) {
      LOG.trace("Ignoring duplicate URL that has already been scanned: {}", url);
      return; // ignore duplicates already visited
    }
    String protocol = BasicHelper.toLowerCase(url.getProtocol());
    if ("file".equals(protocol)) {
      String urlString = URLDecoder.decode(url.getFile(), "UTF-8");
      File packageDirectory = new File(urlString);
      if (packageDirectory.isDirectory()) {
        if (includeSubPackages) {
          visitResources(packageDirectory, qualifiedNameBuilder, qualifiedNamePrefixLength, visitor);
        } else {
          for (File child : packageDirectory.listFiles()) {
            if (child.isFile()) {
              qualifiedNameBuilder.setLength(qualifiedNamePrefixLength);
              qualifiedNameBuilder.append(child.getName());
              visitor.visitResource(qualifiedNameBuilder.toString());
            }
          }
        }
      }
    } else if (FILE_EXTENSION_JAR.equals(protocol)) {
      // somehow the connection has no close method and can NOT be disposed
      JarURLConnection connection = (JarURLConnection) url.openConnection();
      JarFile jarFile = connection.getJarFile();
      Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
      while (jarEntryEnumeration.hasMoreElements()) {
        JarEntry jarEntry = jarEntryEnumeration.nextElement();
        String classpath = jarEntry.getName();
        if (classpath.startsWith("/")) {
          classpath = classpath.substring(1);
        }
        if (classpath.startsWith(WEB_INF_CLASSES)) {
          // special treatment for WAR files...
          // "WEB-INF/lib/" entries should be opened directly in contained
          // jar
          classpath = classpath.substring(WEB_INF_CLASSES.length());
        }
        if (classpath.startsWith(pathWithPrefix)) {
          boolean accept = true;
          if (!includeSubPackages) {
            int index = classpath.indexOf('/', qualifiedNamePrefixLength + 1);
            if (index != -1) {
              accept = false;
            }
          }
          if (accept) {
            if (jarEntry.isDirectory()) {
              visitor.visitPackage(classpath);
            } else {
              visitor.visitResource(classpath);
            }
          }
        }
      }
    } else {
      LOG.warn("Unknown protocol '" + protocol + "' in classpath entry!");
    }
  }

  @Override
  public Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames) {

    return loadClasses(qualifiedClassNames, ClassResolver.CLASS_FOR_NAME_RESOLVER, ConstantFilter.getInstance(true));
  }

  @Override
  public Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames, Filter<? super Class<?>> filter) {

    return loadClasses(qualifiedClassNames, ClassResolver.CLASS_FOR_NAME_RESOLVER, filter);
  }

  @Override
  public Set<Class<?>> loadClasses(Collection<String> classNames, ClassResolver classResolver, Filter<? super Class<?>> filter) {

    Set<Class<?>> classesSet = new HashSet<>();
    for (String className : classNames) {
      try {
        Class<?> clazz = classResolver.resolveClass(className);
        if (filter.accept(clazz)) {
          classesSet.add(clazz);
        }
      } catch (Throwable e) {
        // we catch throwable to also get NoClassDefFoundError, etc.
        LOG.warn("Failed to resolve class {}: {}", className, e.toString());
      }
    }
    return classesSet;
  }

  /**
   * This method gets the default {@link ClassLoader} to use. This should be the {@link Thread#getContextClassLoader()
   * ContextClassLoader} but falls back to alternatives if no such {@link ClassLoader} is available.
   *
   * @return the default {@link ClassLoader} to use.
   */
  protected ClassLoader getDefaultClassLoader() {

    return getDefaultClassLoader(null);
  }

  /**
   * This method gets the default {@link ClassLoader} to use. This should be the {@link Thread#getContextClassLoader()
   * ContextClassLoader} but falls back to alternatives if no such {@link ClassLoader} is available.
   *
   * @param fallbackClass is used to {@link Class#getClassLoader() retrieve} a {@link ClassLoader} as fallback if the
   *        {@link Thread#getContextClassLoader() ContextClassLoader} is not available.
   * @return the default {@link ClassLoader} to use.
   */
  public ClassLoader getDefaultClassLoader(Class<?> fallbackClass) {

    ClassLoader result = Thread.currentThread().getContextClassLoader();
    if (result == null) {
      LOG.warn("strange container: no context-class-loader available!");
      Class<?> clazz = fallbackClass;
      if (clazz == null) {
        clazz = getClass();
      }
      result = clazz.getClassLoader();
      if (result == null) {
        LOG.warn("strange JVM: only system-class-loader available!");
        result = ClassLoader.getSystemClassLoader();
      }
    }
    return result;
  }

}
