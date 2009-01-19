/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.filter.base.ListCharFilter;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.reflect.NlsBundleUtilReflect;
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
 * This class is a collection of utility functions for dealing with
 * {@link java.lang.reflect reflection}.
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ReflectionUtilImpl implements ReflectionUtil {

  /** @see #getInstance() */
  private static ReflectionUtil instance;

  /** @see #toType(CharSequenceScanner, ClassResolver, Type) */
  private static final CharFilter CHAR_FILTER = new ListCharFilter(false, '<', '[', ',', '?', '>');

  // private static final WeakHashMap<K, V>

  /**
   * The constructor.
   */
  public ReflectionUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link ReflectionUtil}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static ReflectionUtil getInstance() {

    if (instance == null) {
      synchronized (ReflectionUtilImpl.class) {
        if (instance == null) {
          instance = new ReflectionUtilImpl();
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  public <T> GenericType<T> createGenericType(Class<T> type) {

    return new SimpleGenericTypeImpl<T>(type);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public GenericType<?> createGenericType(Type type) {

    if (type instanceof Class) {
      return createGenericType((Class<?>) type);
    } else {
      return new GenericTypeImpl(type);
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public GenericType<?> createGenericType(Type type, GenericType<?> definingType) {

    return new GenericTypeImpl(type, definingType);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public GenericType<?> createGenericType(Type type, Class<?> definingType) {

    return new GenericTypeImpl(type, createGenericType(definingType));
  }

  /**
   * This method walks up the {@link Class}-hierarchy from
   * <code>descendant</code> up to <code>ancestor</code> and returns the
   * sub-class or sub-interface of <code>ancestor</code> on that hierarchy-path.<br>
   * Please note that if <code>ancestor</code> is an {@link Class#isInterface()
   * interface}, the hierarchy may NOT be unique. In such case it will be
   * unspecified which of the possible paths is used.
   * 
   * @param ancestor is the super-class or super-interface of
   *        <code>descendant</code>.
   * @param descendant is the sub-class or sub-interface of
   *        <code>ancestor</code>.
   * @return the sub-class or sub-interface on the hierarchy-path from
   *         <code>descendant</code> up to <code>ancestor</code>.
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
   * This method walks up the {@link Class}-hierarchy from
   * <code>descendant</code> up to <code>ancestor</code> and returns the
   * sub-class or sub-interface of <code>ancestor</code> on that hierarchy-path.<br>
   * Please note that if <code>ancestor</code> is an {@link Class#isInterface()
   * interface}, the hierarchy may NOT be unique. In such case it will be
   * unspecified which of the possible paths is used.
   * 
   * @param ancestor is the super-class or super-interface of
   *        <code>descendant</code>.
   * @param descendant is the sub-class or sub-interface of
   *        <code>ancestor</code>.
   * @return the sub-class or sub-interface on the hierarchy-path from
   *         <code>descendant</code> up to <code>ancestor</code>.
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

  /**
   * {@inheritDoc}
   */
  public Class<?> getArrayClass(Class<?> componentType) {

    // this is sort of stupid but there seems no other way...
    return Array.newInstance(componentType, 0).getClass();
  }

  /**
   * {@inheritDoc}
   */
  public Type toType(String type) throws ClassNotFoundException, IllegalArgumentException {

    return toType(type, ClassResolver.CLASS_FOR_NAME_RESOLVER);
  }

  /**
   * {@inheritDoc}
   */
  public Type toType(String type, ClassResolver resolver) throws ClassNotFoundException,
      IllegalArgumentException {

    // List<String>
    // Map<Integer, Date>
    // Set<? extends Serializable>
    CharSequenceScanner parser = new CharSequenceScanner(type);
    Type result = toType(parser, resolver, null);
    parser.skipWhile(' ');
    if (parser.hasNext()) {
      throw new IllegalArgumentException("Not terminated!");
    }
    return result;
    // return Class.forName(type);
  }

  /**
   * This method parses the given <code>type</code> as generic {@link Type}.<br>
   * This would be easier when using <code>StringParser</code> but we want to
   * avoid the dependency on <code>util-misc</code>.
   * 
   * @param parser is the string-parser on the type string to parse.
   * @param resolver is used to resolve classes.
   * @param owner is the
   *        {@link java.lang.reflect.ParameterizedType#getOwnerType()
   *        owner-type} or <code>null</code>.
   * @return the parsed type.
   * @throws ClassNotFoundException if a class could NOT be resolved.
   */
  private Type toType(CharSequenceScanner parser, ClassResolver resolver, Type owner)
      throws ClassNotFoundException {

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
          throw new NlsIllegalArgumentException(NlsBundleUtilReflect.ERR_TYPE_ILLEGAL_WILDCARD,
              sequence);
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
          throw new NlsIllegalArgumentException("Illegal array!");
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
        throw new NlsIllegalArgumentException("Illegal array!");
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
        List<Type> typeArgList = new ArrayList<Type>();
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

  /**
   * {@inheritDoc}
   */
  public String toString(Type type) {

    if (type instanceof Class) {
      return ((Class<?>) type).getName();
    } else {
      return type.toString();
    }
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  public Class<?> getNonPrimitiveType(Class<?> type) {

    Class<?> result = type;
    if (type.isPrimitive()) {
      if (int.class == type) {
        return Integer.class;
      } else if (long.class == type) {
        return Long.class;
      } else if (double.class == type) {
        return Double.class;
      } else if (boolean.class == type) {
        return Boolean.class;
      } else if (float.class == type) {
        return Float.class;
      } else if (char.class == type) {
        return Character.class;
      } else if (byte.class == type) {
        return Byte.class;
      } else if (short.class == type) {
        return Short.class;
      } else {
        throw new IllegalStateException("Class-loader isolation trap!");
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <T> T getStaticField(Class<?> type, String fieldName, Class<T> fieldType,
      boolean exactTypeMatch, boolean mustBeFinal, boolean inherit) throws NoSuchFieldException,
      IllegalAccessException, IllegalArgumentException {

    Field field = type.getField(fieldName);
    int modifiers = field.getModifiers();
    if (!Modifier.isStatic(modifiers)) {
      throw new IllegalArgumentException("Field '" + fieldName + "' (in type '" + type
          + "') is not static!");
    }
    if (mustBeFinal && !Modifier.isFinal(modifiers)) {
      throw new IllegalArgumentException("Field '" + fieldName + "' (in type '" + type
          + "') is not final!");
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
      throw new IllegalArgumentException("Field '" + fieldName + "' (in type '" + type
          + "') has type '" + field.getType() + "' but requested type was '" + fieldType + "'!");
    }
    return (T) field.get(null);
  }

  /**
   * {@inheritDoc}
   */
  public <T> T getStaticFieldOrNull(Class<?> type, String fieldName, Class<T> fieldType,
      boolean exactTypeMatch, boolean mustBeFinal, boolean inherit) throws IllegalArgumentException {

    try {
      return getStaticField(type, fieldName, fieldType, exactTypeMatch, mustBeFinal, inherit);
    } catch (NoSuchFieldException e) {
      return null;
    } catch (IllegalAccessException e) {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  public Method getParentMethod(Method method) throws SecurityException {

    return getParentMethod(method.getDeclaringClass(), method.getName(), method.getParameterTypes());
  }

  /**
   * {@inheritDoc}
   */
  public Method getParentMethod(Class<?> inheritingClass, String methodName,
      Class<?>[] parameterTypes) throws SecurityException {

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
   * This method checks and transforms the filename of a potential {@link Class}
   * given by <code>fileName</code>.
   * 
   * @param fileName is the filename.
   * @return the according Java {@link Class#getName() class-name} for the given
   *         <code>fileName</code> if it is a class-file that is no anonymous
   *         {@link Class}, else <code>null</code>.
   */
  private static String fixClassName(String fileName) {

    if (fileName.endsWith(".class")) {
      // remove extension (".class".length() == 6)
      String nameWithoutExtension = fileName.substring(0, fileName.length() - 6);
      // handle inner classes...
      /*
       * int lastDollar = nameWithoutExtension.lastIndexOf('$'); if (lastDollar
       * > 0) { char innerClassStart = nameWithoutExtension.charAt(lastDollar +
       * 1); if ((innerClassStart >= '0') && (innerClassStart <= '9')) { //
       * ignore anonymous class } else { return
       * nameWithoutExtension.replace('$', '.'); } } else { return
       * nameWithoutExtension; }
       */
      return nameWithoutExtension;
    }
    return null;
  }

  /**
   * This method finds the recursively scans the given
   * <code>packageDirectory</code> for {@link Class} files and adds their
   * according Java names to the given <code>classSet</code>.
   * 
   * @param packageDirectory is the directory representing the {@link Package}.
   * @param classSet is where to add the Java {@link Class}-names to.
   * @param qualifiedNameBuilder is a {@link StringBuilder} containing the
   *        qualified prefix (the {@link Package} with a trailing dot).
   * @param qualifiedNamePrefixLength the length of the prefix used to rest the
   *        string-builder after reuse.
   */
  private static void findClassNamesRecursive(File packageDirectory, Set<String> classSet,
      StringBuilder qualifiedNameBuilder, int qualifiedNamePrefixLength) {

    for (File childFile : packageDirectory.listFiles()) {
      String fileName = childFile.getName();
      if (childFile.isDirectory()) {
        qualifiedNameBuilder.setLength(qualifiedNamePrefixLength);
        StringBuilder subBuilder = new StringBuilder(qualifiedNameBuilder);
        subBuilder.append(fileName);
        subBuilder.append('.');
        findClassNamesRecursive(childFile, classSet, subBuilder, subBuilder.length());
      } else {
        String simpleClassName = fixClassName(fileName);
        if (simpleClassName != null) {
          qualifiedNameBuilder.setLength(qualifiedNamePrefixLength);
          qualifiedNameBuilder.append(simpleClassName);
          classSet.add(qualifiedNameBuilder.toString());
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public Set<String> findClassNames(String packageName, boolean includeSubPackages)
      throws IOException {

    Set<String> classSet = new HashSet<String>();
    findClassNames(packageName, includeSubPackages, classSet);
    return classSet;
  }

  /**
   * {@inheritDoc}
   */
  public void findClassNames(String packageName, boolean includeSubPackages, Set<String> classSet)
      throws IOException {

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    String path = packageName.replace('.', '/');
    String pathWithPrefix = path + '/';
    Enumeration<URL> urls = classLoader.getResources(path);
    StringBuilder qualifiedNameBuilder = new StringBuilder(packageName);
    qualifiedNameBuilder.append('.');
    int qualifiedNamePrefixLength = qualifiedNameBuilder.length();
    while (urls.hasMoreElements()) {
      URL packageUrl = urls.nextElement();
      String urlString = URLDecoder.decode(packageUrl.getFile(), "UTF-8");
      String protocol = packageUrl.getProtocol().toLowerCase();
      if ("file".equals(protocol)) {
        File packageDirectory = new File(urlString);
        if (packageDirectory.isDirectory()) {
          if (includeSubPackages) {
            findClassNamesRecursive(packageDirectory, classSet, qualifiedNameBuilder,
                qualifiedNamePrefixLength);
          } else {
            for (String fileName : packageDirectory.list()) {
              String simpleClassName = fixClassName(fileName);
              if (simpleClassName != null) {
                qualifiedNameBuilder.setLength(qualifiedNamePrefixLength);
                qualifiedNameBuilder.append(simpleClassName);
                classSet.add(qualifiedNameBuilder.toString());
              }
            }
          }
        }
      } else if ("jar".equals(protocol)) {
        // somehow the connection has no close method and can NOT be disposed
        JarURLConnection connection = (JarURLConnection) packageUrl.openConnection();
        JarFile jarFile = connection.getJarFile();
        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
        while (jarEntryEnumeration.hasMoreElements()) {
          JarEntry jarEntry = jarEntryEnumeration.nextElement();
          String absoluteFileName = jarEntry.getName();
          if (absoluteFileName.endsWith(".class")) {
            if (absoluteFileName.startsWith("/")) {
              absoluteFileName = absoluteFileName.substring(1);
            }
            // special treatment for WAR files...
            // "WEB-INF/lib/" entries should be opened directly in contained jar
            if (absoluteFileName.startsWith("WEB-INF/classes/")) {
              // "WEB-INF/classes/".length() == 16
              absoluteFileName = absoluteFileName.substring(16);
            }
            boolean accept = true;
            if (absoluteFileName.startsWith(pathWithPrefix)) {
              String qualifiedName = absoluteFileName.replace('/', '.');
              if (!includeSubPackages) {
                int index = absoluteFileName.indexOf('/', qualifiedNamePrefixLength + 1);
                if (index != -1) {
                  accept = false;
                }
              }
              if (accept) {
                String className = fixClassName(qualifiedName);
                if (className != null) {
                  classSet.add(className);
                }
              }
            }
          }
        }
      } else {
        // TODO: unknown protocol - log this?
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames)
      throws ClassNotFoundException {

    return loadClasses(qualifiedClassNames, ClassResolver.CLASS_FOR_NAME_RESOLVER,
        Filter.ACCEPT_ALL);
  }

  /**
   * {@inheritDoc}
   */
  public Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames,
      Filter<? super Class<?>> filter) throws ClassNotFoundException {

    return loadClasses(qualifiedClassNames, ClassResolver.CLASS_FOR_NAME_RESOLVER, filter);
  }

  /**
   * {@inheritDoc}
   */
  public Set<Class<?>> loadClasses(Collection<String> classNames, ClassResolver classResolver,
      Filter<? super Class<?>> filter) throws ClassNotFoundException {

    Set<Class<?>> classesSet = new HashSet<Class<?>>();
    for (String className : classNames) {
      Class<?> clazz = classResolver.resolveClass(className);
      if (filter.accept(clazz)) {
        classesSet.add(clazz);
      }
    }
    return classesSet;
  }

}
