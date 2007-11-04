/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.io.File;
import java.io.IOException;
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

import net.sf.mmm.util.StringParser;
import net.sf.mmm.util.filter.CharFilter;
import net.sf.mmm.util.filter.Filter;
import net.sf.mmm.util.filter.ListCharFilter;
import net.sf.mmm.util.reflect.type.GenericArrayTypeImpl;
import net.sf.mmm.util.reflect.type.LowerBoundWildcardType;
import net.sf.mmm.util.reflect.type.ParameterizedTypeImpl;
import net.sf.mmm.util.reflect.type.UnboundedWildcardType;
import net.sf.mmm.util.reflect.type.UpperBoundWildcardType;

/**
 * This class is a collection of utility functions for dealing with
 * {@link java.lang.reflect reflection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class ReflectionUtil {

  /** an empty class array */
  public static final Class<?>[] NO_PARAMETERS = new Class[0];

  /** an empty object array */
  public static final Object[] NO_ARGUMENTS = new Object[0];

  /** an empty object array */
  public static final Type[] NO_TYPES = new Type[0];

  /** @see #toType(StringParser, ClassResolver, Type) */
  private static final CharFilter CHAR_FILTER = new ListCharFilter(false, '<', '[', ',', '?', '>');

  /**
   * Forbidden constructor.
   */
  private ReflectionUtil() {

    super();
  }

  /**
   * This method gets the {@link Object#getClass() classes} of the given
   * objects.
   * 
   * @param objects is an array containing the objects for that the classes are
   *        requested.
   * @return an array of the same length as the given array. At each position
   *         the returned array contains the {@link Object#getClass() class} of
   *         the object from the given array at the same position or
   *         <code>null</code>, if that object is <code>null</code>.
   */
  public static Class<?>[] getClasses(Object[] objects) {

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
   * This method gets the component type of the given <code>type</code>.<br>
   * For example the following types all have the component-type MyClass:
   * <ul>
   * <li>MyClass[]</li>
   * <li>List&lt;MyClass&gt;</li>
   * <li>Foo&lt;? extends MyClass&gt;</li>
   * <li>Bar&lt;? super MyClass&gt;</li>
   * <li>&lt;T extends MyClass&gt; T[]</li>
   * </ul>
   * 
   * @param type is the type where to get the component type from.
   * @return the component type of the given <code>type</code> or
   *         <code>null</code> if the given <code>type</code> does NOT have
   *         a single (component) type.
   */
  public static Class<?> getComponentType(Type type) {

    if (type instanceof Class) {
      Class<?> clazz = (Class<?>) type;
      if (clazz.isArray()) {
        return clazz.getComponentType();
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      Type[] generics = pt.getActualTypeArguments();
      if (generics.length == 1) {
        return toClass(generics[0]);
      }
    } else if (type instanceof GenericArrayType) {
      GenericArrayType gat = (GenericArrayType) type;
      return toClass(gat.getGenericComponentType());
    }
    return null;
  }

  /**
   * This method gets the raw-type of the given generic <code>type</code>.<br>
   * Examples: <br>
   * <table border="1">
   * <tr>
   * <th><code>type</code></th>
   * <th><code>{@link #toClass(Type) getSimpleType}(type)</code></th>
   * </tr>
   * <tr>
   * <td><code>String</code></td>
   * <td><code>String</code></td>
   * </td>
   * <tr>
   * <td><code>List&lt;String&gt;</code></td>
   * <td><code>List</code></td>
   * </td>
   * <tr>
   * <td><code>&lt;T extends MyClass&gt; T[]</code></td>
   * <td><code>MyClass[]</code></td>
   * </td>
   * </table>
   * 
   * @param type is the type to convert.
   * @return the closest class representing the given <code>type</code>.
   */
  public static Class<?> toClass(Type type) {

    if (type instanceof Class) {
      return (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      return toClass(pt.getRawType());
    } else if (type instanceof WildcardType) {
      WildcardType wt = (WildcardType) type;
      Type[] lower = wt.getLowerBounds();
      if (lower.length == 1) {
        return toClass(lower[0]);
      }
      Type[] upper = wt.getUpperBounds();
      if (upper.length == 1) {
        return toClass(upper[0]);
      }
    } else if (type instanceof GenericArrayType) {
      GenericArrayType gat = (GenericArrayType) type;
      Class<?> componentType = toClass(gat.getGenericComponentType());
      // this is sort of stupid but there seems no other way...
      return Array.newInstance(componentType, 0).getClass();
    } else if (type instanceof TypeVariable) {
      TypeVariable<?> tv = (TypeVariable<?>) type;
      Type[] bounds = tv.getBounds();
      if (bounds.length == 1) {
        return toClass(bounds[0]);
      }
    }
    return null;
  }

  /**
   * This method is the analogy to {@link Class#forName(String)} for creating a
   * {@link Type} instance from {@link String}.
   * 
   * @see #toType(String, ClassResolver)
   * 
   * @param type is the string representation of the requested type.
   * @return the requested type.
   * @throws ClassNotFoundException if a class could NOT be found (e.g. in
   *         <code>java.util.Map&lt;java.long.String&gt;</code> - what should
   *         be <code>lang</code> instead of <code>long</code>).
   * @throws IllegalArgumentException if the given <code>type</code> could NOT
   *         be parsed (e.g. <code>java.util.Map&lt;&lt;String&gt;</code>).
   */
  public static Type toType(String type) throws ClassNotFoundException, IllegalArgumentException {

    return toType(type, ClassResolver.CLASS_FOR_NAME_RESOLVER);
  }

  /**
   * This method is the analogy to {@link Class#forName(String)} for creating a
   * {@link Type} instance from {@link String}.
   * 
   * @param type is the string representation of the requested type.
   * @param resolver is used to resolve classes.
   * @return the requested type.
   * @throws ClassNotFoundException if a class could NOT be found (e.g. in
   *         <code>java.util.Map&lt;java.long.String&gt;</code> - what should
   *         be <code>lang</code> instead of <code>long</code>).
   * @throws IllegalArgumentException if the given <code>type</code> could NOT
   *         be parsed (e.g. <code>java.util.Map&lt;&lt;String&gt;</code>).
   */
  public static Type toType(String type, ClassResolver resolver) throws ClassNotFoundException,
      IllegalArgumentException {

    // List<String>
    // Map<Integer, Date>
    // Set<? extends Serializable>
    StringParser parser = new StringParser(type);
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
   * @param resolver
   * @param owner
   * @return the parsed type.
   * @throws ClassNotFoundException
   */
  private static Type toType(StringParser parser, ClassResolver resolver, Type owner)
      throws ClassNotFoundException {

    parser.skipWhile(' ');
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
          // TODO: NLS
          throw new IllegalArgumentException("Illegal sequence in wildcard type '" + sequence
              + "'!");
        }
        Type bound = toType(parser, resolver, null);
        if (lowerBound) {
          return new LowerBoundWildcardType(bound);
        } else {
          return new UpperBoundWildcardType(bound);
        }
      } else {
        return UnboundedWildcardType.INSTANCE;
      }
    }
    String segment = parser.readWhile(CHAR_FILTER).trim();
    c = parser.forceNext();
    Type result;
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
   * This method gets the string representation of a {@link Type}. Instead of
   * {@link Type#toString()} it returns {@link Class#getName()} if the type is a
   * {@link Class}.
   * 
   * @param type is the type to get as string.
   * @return the string representation of the given <code>type</code>.
   */
  public static String toString(Type type) {

    if (type instanceof Class) {
      return ((Class<?>) type).getName();
    } else {
      return type.toString();
    }
  }

  /**
   * This method gets the according non-{@link Class#isPrimitive() primitive}
   * type for the class given by <code>type</code>.<br>
   * E.g.
   * <code>{@link #getNonPrimitiveType(Class) getNonPrimitiveType}(int.class)</code>
   * will return <code>Integer.class</code>.
   * 
   * @see Class#isPrimitive()
   * 
   * @param type is the (potentially) {@link Class#isPrimitive() primitive}
   *        type.
   * @return the according object-type for the given <code>type</code>. This
   *         will be the given <code>type</code> itself if it is NOT
   *         {@link Class#isPrimitive() primitive}.
   */
  public static Class<?> getNonPrimitiveType(Class<?> type) {

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
   * This method gets the {@link Field#get(java.lang.Object) value} of a
   * {@link Modifier#isStatic(int) static} {@link Field field}.
   * 
   * @param <T> the templated type the requested field is assigned to.
   * @param type is the class or interface containing the requested field.
   * @param fieldName is the {@link Field#getName() name} of the requested
   *        field.
   * @param fieldType is the type the requested field is assigned to. Therefore
   *        the field declaration (!) must be assignable to this type.
   * @param exactTypeMatch - if <code>true</code>, the <code>fieldType</code>
   *        must match exactly the type of the static field, else if
   *        <code>false</code> the type of the field may be a sub-type of
   *        <code>fieldType</code> or one of the types may be
   *        {@link Class#isPrimitive() primitive} while the other is the
   *        {@link #getNonPrimitiveType(Class) according} object-type.
   * @param mustBeFinal - if <code>true</code>, an
   *        {@link IllegalArgumentException} is thrown if the specified static
   *        field exists but is NOT {@link Modifier#isFinal(int) final},
   *        <code>false</code> otherwise.
   * @param inherit if <code>true</code> the field may be inherited from a
   *        {@link Class#getSuperclass() super-class} or
   *        {@link Class#getInterfaces() super-interface} of <code>type</code>,
   *        else if <code>false</code> the field is only accepted if it is
   *        declared in <code>type</code>.
   * @return the value of the field with the given type.
   * @throws NoSuchFieldException if the given <code>type</code> has no field
   *         with the given <code>fieldName</code>.
   * @throws IllegalAccessException if you do not have permission to read the
   *         field (e.g. field is private).
   * @throws IllegalArgumentException if the field is NOT static (or final) or
   *         has the wrong type.
   */
  @SuppressWarnings("unchecked")
  public static <T> T getStaticField(Class<?> type, String fieldName, Class<T> fieldType,
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
   * 
   * @param <T> the templated type the requested field is assigned to.
   * @param type is the class or interface containing the requested field.
   * @param fieldName is the {@link Field#getName() name} of the requested
   *        field.
   * @param fieldType is the type the requested field is assigned to. Therefore
   *        the field declaration (!) must be assignable to this type.
   * @param exactTypeMatch - if <code>true</code>, the <code>fieldType</code>
   *        must match exactly the type of the static field, else if
   *        <code>false</code> the type of the field may be a sub-type of
   *        <code>fieldType</code> or one of the types may be
   *        {@link Class#isPrimitive() primitive} while the other is the
   *        {@link #getNonPrimitiveType(Class) according} object-type.
   * @param mustBeFinal - if <code>true</code>, an
   *        {@link IllegalArgumentException} is thrown if the specified static
   *        field exists but is NOT {@link Modifier#isFinal(int) final},
   *        <code>false</code> otherwise.
   * @param inherit if <code>true</code> the field may be inherited from a
   *        {@link Class#getSuperclass() super-class} or
   *        {@link Class#getInterfaces() super-interface} of <code>type</code>,
   *        else if <code>false</code> the field is only accepted if it is
   *        declared in <code>type</code>.
   * @return the value of the field with the given type or <code>null</code>
   *         if the field does NOT exist or is NOT accessible.
   * @throws IllegalArgumentException if the field is NOT static (or final) or
   *         has the wrong type.
   */
  public static <T> T getStaticFieldOrNull(Class<?> type, String fieldName, Class<T> fieldType,
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
   * This method gets the parent method of the given <code>method</code>. The
   * parent method is the method overridden (is the sense of {@link Override})
   * by the given <code>method</code> or directly inherited from an
   * {@link Class#getInterfaces() interface}.
   * 
   * @param method is the method.
   * @return the parent method or <code>null</code> if no such method exists.
   * @throws SecurityException if access has been denied by the
   *         {@link SecurityManager}.
   */
  public static Method getParentMethod(Method method) throws SecurityException {

    return getParentMethod(method.getDeclaringClass(), method.getName(), method.getParameterTypes());
  }

  /**
   * This method gets the method
   * {@link Class#getMethod(String, Class[]) identified} by
   * <code>methodName</code> and <code>parameterTypes</code> that is NOT
   * {@link Method#getDeclaringClass() declared} but inherited by the given
   * <code>declaringClass</code>.
   * 
   * @see #getParentMethod(Class, String, Class[])
   * 
   * @param inheritingClass is the class inheriting the requested method.
   * @param methodName is the {@link Method#getName() name} of the requested
   *        method.
   * @param parameterTypes is the {@link Method#getParameterTypes() signature}
   *        of the requested method.
   * @return the inherited method or <code>null</code> if no such method
   *         exists.
   * @throws SecurityException if access has been denied by the
   *         {@link SecurityManager}.
   */
  public static Method getParentMethod(Class<?> inheritingClass, String methodName,
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
       * int lastDollar = nameWithoutExtension.lastIndexOf('$'); if (lastDollar >
       * 0) { char innerClassStart = nameWithoutExtension.charAt(lastDollar +
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
   * This method finds all classes that are located in the package identified by
   * the given <code>packageName</code>.<br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath
   * multiple directories,JAR-, and WAR-files may need to be scanned.
   * 
   * @param packageName is the name of the {@link Package} to scan.
   * @param includeSubPackages - if <code>true</code> all sub-packages of the
   *        specified {@link Package} will be included in the search.
   * @return a {@link Set} will the fully qualified names of all requested
   *         classes.
   * @throws IOException if the operation failed with an I/O error.
   */
  public static Set<String> findClassNames(String packageName, boolean includeSubPackages)
      throws IOException {

    Set<String> classSet = new HashSet<String>();
    findClassNames(packageName, includeSubPackages, classSet);
    return classSet;
  }

  /**
   * This method finds all classes that are located in the package identified by
   * the given <code>packageName</code>.<br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath
   * multiple directories,JAR-, and WAR-files may need to be scanned.
   * 
   * @param packageName is the name of the {@link Package} to scan.
   * @param includeSubPackages - if <code>true</code> all sub-packages of the
   *        specified {@link Package} will be included in the search.
   * @param classSet is where to add the classes.
   * @throws IOException if the operation failed with an I/O error.
   */
  public static void findClassNames(String packageName, boolean includeSubPackages,
      Set<String> classSet) throws IOException {

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
              absoluteFileName.substring(1);
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
   * This method loads the classes given as {@link Collection} of
   * {@link Class#getName() fully qualified names} by
   * <code>qualifiedClassNames</code> and returns them as {@link Set}.
   * 
   * @param qualifiedClassNames is a collection containing the
   *        {@link Class#getName() qualified names} of the classes to load.
   * @return a {@link Set} with all loaded classes.
   * @throws ClassNotFoundException if one of the classes could NOT be loaded.
   */
  @SuppressWarnings("unchecked")
  public static Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames)
      throws ClassNotFoundException {

    return loadClasses(qualifiedClassNames, ClassResolver.CLASS_FOR_NAME_RESOLVER,
        Filter.ACCEPT_ALL);
  }

  /**
   * This method loads the classes given as {@link Collection} of
   * {@link Class#getName() fully qualified names} by
   * <code>qualifiedClassNames</code>. It returns a {@link Set} containing
   * only those loaded classes that are {@link Filter#accept(Object) accepted}
   * by the given <code>filter</code>.
   * 
   * @param qualifiedClassNames is a collection containing the
   *        {@link Class#getName() qualified names} of the classes to load.
   * @param filter is used to filter the loaded classes.
   * @return a {@link Set} with all loaded classes that are
   *         {@link Filter#accept(Object) accepted} by the given
   *         <code>filter</code>.
   * @throws ClassNotFoundException if one of the classes could NOT be loaded.
   */
  public static Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames,
      Filter<? super Class<?>> filter) throws ClassNotFoundException {

    return loadClasses(qualifiedClassNames, ClassResolver.CLASS_FOR_NAME_RESOLVER, filter);
  }

  /**
   * This method loads the classes given as {@link Collection} of names by
   * <code>classNames</code> using the given <code>classResolver</code>. It
   * returns a {@link Set} containing only those loaded classes that are
   * {@link Filter#accept(Object) accepted} by the given <code>filter</code>.
   * 
   * @param classNames is a collection containing the names of the classes to
   *        load. The class names should typically be the
   *        {@link Class#getName() qualified names} of the classes to load. But
   *        this may differ depending on the <code>classResolver</code>.
   * @param classResolver is used to load/resolve the classes by their names.
   * @param filter is used to filter the loaded classes.
   * @return a {@link Set} with all loaded classes that are
   *         {@link Filter#accept(Object) accepted} by the given
   *         <code>filter</code>.
   * @throws ClassNotFoundException if one of the classes could NOT be loaded.
   */
  public static Set<Class<?>> loadClasses(Collection<String> classNames,
      ClassResolver classResolver, Filter<? super Class<?>> filter) throws ClassNotFoundException {

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
