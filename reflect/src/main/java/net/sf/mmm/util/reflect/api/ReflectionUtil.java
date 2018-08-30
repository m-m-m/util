/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.lang.api.Visitor;

/**
 * This is the interface for a collection of utility functions to deal with {@link java.lang.reflect
 * reflection}. <br/>
 * <b>Note:</b><br/>
 * If you are looking for a method called {@code visitObjectRecursive} see {@code PojoUtil} instead.
 *
 * @see AnnotationUtil
 * @see net.sf.mmm.util.reflect.base.ReflectionUtilImpl
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface ReflectionUtil extends ReflectionUtilLimited {

  /** an empty {@link Type}-array */
  Type[] NO_TYPES = new Type[0];

  /**
   * An empty {@link Annotation}-array.
   *
   * @since 4.0.0
   */
  Annotation[] NO_ANNOTATIONS = new Annotation[0];

  /**
   * This method gets the {@link Object#getClass() classes} of the given objects.
   *
   * @param objects is an array containing the objects for that the classes are requested.
   * @return an array of the same length as the given array. At each position the returned array contains the
   *         {@link Object#getClass() class} of the object from the given array at the same position or
   *         {@code null}, if that object is {@code null}.
   */
  Class<?>[] getClasses(Object[] objects);

  /**
   * This method creates the {@link Class} reflecting an {@link Class#isArray() array} of the given
   * {@link Class#getComponentType() componentType}.
   *
   * @param componentType is the {@link Class#getComponentType() component type} .
   * @return the according {@link Class#isArray() array}-class.
   */
  Class<?> getArrayClass(Class<?> componentType);

  /**
   * This method is the analogy to {@link Class#forName(String)} for creating a {@link Type} instance from
   * {@link String}.
   *
   * @see #toType(String, ClassResolver)
   *
   * @param type is the string representation of the requested type.
   * @return the requested type.
   * @throws TypeNotFoundException if a class could NOT be found (e.g. in
   *         {@code java.util.Map<java.long.String>} - what should be {@code lang} instead of {@code long}).
   * @throws IllegalArgumentException if the given {@code type} could NOT be parsed (e.g.
   *         {@code java.util.Map<<String>}).
   */
  Type toType(String type) throws TypeNotFoundException, IllegalArgumentException;

  /**
   * This method is the analogy to {@link Class#forName(String)} for creating a {@link Type} instance from
   * {@link String}.
   *
   * @param type is the string representation of the requested type.
   * @param resolver is used to resolve classes.
   * @return the requested type.
   * @throws TypeNotFoundException if a class could NOT be found (e.g. in
   *         {@code java.util.Map<java.long.String>} - what should be {@code lang} instead of {@code long}).
   * @throws IllegalArgumentException if the given {@code type} could NOT be parsed (e.g.
   *         {@code java.util.Map<<String>}).
   */
  Type toType(String type, ClassResolver resolver) throws TypeNotFoundException, IllegalArgumentException;

  /**
   * This method gets the string representation of a {@link Type}. Instead of
   * <code>{@link Type}.toString()</code> it returns {@link Class#getName()} if the type is a {@link Class}.
   *
   * @param type is the type to get as string.
   * @return the string representation of the given {@code type}.
   */
  String toString(Type type);

  /**
   * This method gets a compact string representation of a {@link Type}. Instead of {@link #toString(Type)}
   * this method will write {@link Class#getSimpleName() simple names} of {@link Class}es.
   *
   * @param type is the type to get as string.
   * @return the compact string representation of the given {@code type}.
   */
  String toStringSimple(Type type);

  /**
   * This method gets the string representation of a {@link Type}. Instead of
   * <code>{@link Type}.toString()</code> it returns {@link Class#getName()} if the type is a {@link Class}.
   *
   * @param type is the type to get as string.
   * @param appendable is where to {@link Appendable#append(CharSequence) append} the string representation
   *        to.
   * @param classFormatter is a {@link Visitor} that gets called for each {@link Class} and has to
   *        {@link Appendable#append(CharSequence) append} as string-representation of the visited
   *        {@link Class} to the {@code appendable}.
   * @throws IllegalStateException if the {@link Appendable} caused an {@link java.io.IOException}
   * @since 2.0.0
   */
  void toString(Type type, Appendable appendable, Visitor<Class<?>> classFormatter);

  /**
   * This method compares the given classes.
   *
   * @param class1 is the first class.
   * @param class2 is the second class.
   * @return
   *         <ul>
   *         <li>{@code 0} if both classes are equal to each other.</li>
   *         <li>{@code 1} if {@code class1} inherits from {@code class2}.</li>
   *         <li>{@code -1} if {@code class2} inherits from {@code class1}.</li>
   *         <li>{@link Integer#MIN_VALUE} otherwise.</li>
   *         </ul>
   */
  int compare(Class<?> class1, Class<?> class2);

  /**
   * This method determines if the given {@code interfaceClass} is a marker-interface (e.g.
   * {@link java.io.Serializable} or {@link Cloneable}). A marker-interface is also called a
   * tagging-interface.
   *
   * @param interfaceClass is the {@link Class} reflecting the interface to check.
   * @return {@code true} if the given {@code interfaceClass} is a marker-interface, {@code false} otherwise
   *         (if regular interface or no interface at all).
   */
  boolean isMarkerInterface(Class<?> interfaceClass);

  /**
   * This method gets the {@link java.lang.reflect.Field#get(java.lang.Object) value} of a
   * {@link java.lang.reflect.Modifier#isStatic(int) static} {@link java.lang.reflect.Field field} .
   *
   * @param <T> the templated type the requested field is assigned to.
   * @param type is the class or interface containing the requested field.
   * @param fieldName is the {@link java.lang.reflect.Field#getName() name} of the requested field.
   * @param fieldType is the type the requested field is assigned to. Therefore the field declaration (!) must
   *        be assignable to this type.
   * @param exactTypeMatch - if {@code true}, the {@code fieldType} must match exactly the type of the static
   *        field, else if {@code false} the type of the field may be a sub-type of {@code fieldType} or one
   *        of the types may be {@link Class#isPrimitive() primitive} while the other is the
   *        {@link #getNonPrimitiveType(Class) according} object-type.
   * @param mustBeFinal - if {@code true}, an {@link IllegalArgumentException} is thrown if the specified
   *        static field exists but is NOT {@link java.lang.reflect.Modifier#isFinal(int) final},
   *        {@code false} otherwise.
   * @param inherit if {@code true} the field may be inherited from a {@link Class#getSuperclass()
   *        super-class} or {@link Class#getInterfaces() super-interface} of {@code type}, else if
   *        {@code false} the field is only accepted if it is declared in {@code type}.
   * @return the value of the field with the given type.
   * @throws NoSuchFieldException if the given {@code type} has no field with the given {@code fieldName}.
   * @throws IllegalAccessException if you do not have permission to read the field (e.g. field is private).
   * @throws IllegalArgumentException if the field is NOT static (or final) or has the wrong type.
   */
  <T> T getStaticField(Class<?> type, String fieldName, Class<T> fieldType, boolean exactTypeMatch, boolean mustBeFinal, boolean inherit)
      throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException;

  /**
   *
   * @param <T> the templated type the requested field is assigned to.
   * @param type is the class or interface containing the requested field.
   * @param fieldName is the {@link java.lang.reflect.Field#getName() name} of the requested field.
   * @param fieldType is the type the requested field is assigned to. Therefore the field declaration (!) must
   *        be assignable to this type.
   * @param exactTypeMatch - if {@code true}, the {@code fieldType} must match exactly the type of the static
   *        field, else if {@code false} the type of the field may be a sub-type of {@code fieldType} or one
   *        of the types may be {@link Class#isPrimitive() primitive} while the other is the
   *        {@link #getNonPrimitiveType(Class) according} object-type.
   * @param mustBeFinal - if {@code true}, an {@link IllegalArgumentException} is thrown if the specified
   *        static field exists but is NOT {@link java.lang.reflect.Modifier#isFinal(int) final},
   *        {@code false} otherwise.
   * @param inherit if {@code true} the field may be inherited from a {@link Class#getSuperclass()
   *        super-class} or {@link Class#getInterfaces() super-interface} of {@code type}, else if
   *        {@code false} the field is only accepted if it is declared in {@code type}.
   * @return the value of the field with the given type or {@code null} if the field does NOT exist or is NOT
   *         accessible.
   * @throws IllegalArgumentException if the field is NOT static (or final) or has the wrong type.
   */
  <T> T getStaticFieldOrNull(Class<?> type, String fieldName, Class<T> fieldType, boolean exactTypeMatch, boolean mustBeFinal, boolean inherit)
      throws IllegalArgumentException;

  /**
   * This method gets the parent method of the given {@code method}. The parent method is the method
   * overridden (is the sense of {@link Override}) by the given {@code method} or directly inherited from an
   * {@link Class#getInterfaces() interface}.
   *
   * @param method is the method.
   * @return the parent method or {@code null} if no such method exists.
   * @throws SecurityException if access has been denied by the {@link SecurityManager}.
   */
  Method getParentMethod(Method method) throws SecurityException;

  /**
   * This method gets the method {@link Class#getMethod(String, Class[]) identified} by {@code methodName} and
   * {@code parameterTypes} that is NOT {@link Method#getDeclaringClass() declared} but inherited by the given
   * {@code declaringClass}.
   *
   * @see #getParentMethod(Class, String, Class[])
   *
   * @param inheritingClass is the class inheriting the requested method.
   * @param methodName is the {@link Method#getName() name} of the requested method.
   * @param parameterTypes is the {@link Method#getParameterTypes() signature} of the requested method.
   * @return the inherited method or {@code null} if no such method exists.
   * @throws SecurityException if access has been denied by the {@link SecurityManager}.
   */
  Method getParentMethod(Class<?> inheritingClass, String methodName, Class<?>[] parameterTypes) throws SecurityException;

  /**
   * This method finds all classes that are located in the package identified by the given
   * {@code packageName}. <br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath multiple directories, JAR-, and
   * WAR-files may need to be scanned.
   *
   * @param packageName is the name of the {@link Package} to scan.
   * @param includeSubPackages - if {@code true} all sub-packages of the specified {@link Package} will be
   *        included in the search.
   * @return a {@link Set} with the fully qualified names of all requested classes.
   * @throws IllegalStateException if the operation failed with an I/O error.
   */
  Set<String> findClassNames(String packageName, boolean includeSubPackages);

  /**
   * This method finds all classes that are located in the package identified by the given
   * {@code packageName}. <br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath multiple directories, JAR-, and
   * WAR-files may need to be scanned.
   *
   * @param packageName is the name of the {@link Package} to scan.
   * @param includeSubPackages - if {@code true} all sub-packages of the specified {@link Package} will be
   *        included in the search.
   * @param filter is used to {@link Filter#accept(Object) filter} the {@link Class}-names to be added to the
   *        resulting {@link Set}. The {@link Filter} will receive {@link Class#getName() fully qualified
   *        class-names} as argument (e.g. "net.sf.mmm.reflect.api.ReflectionUtil").
   * @return a {@link Set} with the fully qualified names of all requested classes.
   * @throws IllegalStateException if the operation failed with an I/O error.
   * @since 1.1.0
   */
  Set<String> findClassNames(String packageName, boolean includeSubPackages, Filter<? super String> filter);

  /**
   * This method finds all classes that are located in the package identified by the given
   * {@code packageName}. <br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath multiple directories, JAR-, and
   * WAR-files may need to be scanned.
   *
   * @param packageName is the name of the {@link Package} to scan.
   * @param includeSubPackages - if {@code true} all sub-packages of the specified {@link Package} will be
   *        included in the search.
   * @param filter is used to {@link Filter#accept(Object) filter} the {@link Class}-names to be added to the
   *        resulting {@link Set}. The {@link Filter} will receive {@link Class#getName() fully qualified
   *        class-names} as argument (e.g. "net.sf.mmm.reflect.api.ReflectionUtil").
   * @param classLoader is the explicit {@link ClassLoader} to use.
   * @return a {@link Set} with the fully qualified names of all requested classes.
   * @throws IllegalStateException if the operation failed with an I/O error.
   * @since 1.1.0
   */
  Set<String> findClassNames(String packageName, boolean includeSubPackages, Filter<? super String> filter, ClassLoader classLoader);

  /**
   * This method finds all classes that are located in the package identified by the given
   * {@code packageName}. <br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath multiple directories, JAR-, and
   * WAR-files may need to be scanned.
   *
   * @param packageName is the name of the {@link Package} to scan.
   * @param includeSubPackages - if {@code true} all sub-packages of the specified {@link Package} will be
   *        included in the search.
   * @param classSet is where to add the classes.
   * @throws IllegalStateException if the operation failed with an I/O error.
   */
  void findClassNames(String packageName, boolean includeSubPackages, Set<String> classSet);

  /**
   * This method loads the classes given as {@link Collection} of {@link Class#getName() fully qualified
   * names} by {@code qualifiedClassNames} and returns them as {@link Set}.
   *
   * @param qualifiedClassNames is a collection containing the {@link Class#getName() qualified names} of the
   *        classes to load.
   * @return a {@link Set} with all loaded classes.
   * @throws TypeNotFoundException if one of the classes could NOT be loaded.
   */
  Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames) throws TypeNotFoundException;

  /**
   * This method loads the classes given as {@link Collection} of {@link Class#getName() fully qualified
   * names} by {@code qualifiedClassNames}. It returns a {@link Set} containing only those loaded classes that
   * are {@link Filter#accept(Object) accepted} by the given {@code filter}.
   *
   * @param qualifiedClassNames is a collection containing the {@link Class#getName() qualified names} of the
   *        classes to load.
   * @param filter is used to filter the loaded classes.
   * @return a {@link Set} with all loaded classes that are {@link Filter#accept(Object) accepted} by the
   *         given {@code filter}.
   * @throws TypeNotFoundException if one of the classes could NOT be loaded.
   */
  Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames, Filter<? super Class<?>> filter) throws TypeNotFoundException;

  /**
   * This method loads the classes given as {@link Collection} of names by {@code classNames} using the given
   * {@code classResolver}. It returns a {@link Set} containing only those loaded classes that are
   * {@link Filter#accept(Object) accepted} by the given {@code filter}.
   *
   * @param classNames is a collection containing the names of the classes to load. The class names should
   *        typically be the {@link Class#getName() qualified names} of the classes to load. But this may
   *        differ depending on the {@code classResolver}.
   * @param classResolver is used to load/resolve the classes by their names.
   * @param filter is used to filter the loaded classes.
   * @return a {@link Set} with all loaded classes that are {@link Filter#accept(Object) accepted} by the
   *         given {@code filter}.
   * @throws TypeNotFoundException if one of the classes could NOT be loaded.
   */
  Set<Class<?>> loadClasses(Collection<String> classNames, ClassResolver classResolver, Filter<? super Class<?>> filter) throws TypeNotFoundException;

  /**
   * This method finds all resources that are located in the package identified by the given
   * {@code packageName}. <br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath multiple directories, JAR-, and
   * WAR-files may need to be scanned.
   *
   * @param packageName is the name of the {@link Package} to scan. Both "." and "/" are accepted as separator
   *        (e.g. "net.sf.mmm.util.reflect).
   * @param includeSubPackages - if {@code true} all sub-packages of the specified {@link Package} will be
   *        included in the search.
   * @param filter is used to {@link Filter#accept(Object) filter} the resources. The {@link Filter} will
   *        receive fully qualified classpath entries as argument (e.g.
   *        "net/sf/mmm/util/reflect/beans-util-reflect.xml"). Typically you will exclude resources that end
   *        with ".class" or only accept resources that end with ".xml".
   * @return a {@link Set} with the fully qualified names of all requested resources (e.g.
   *         "net/sf/mmm/util/reflect/beans-util-reflect.xml").
   * @throws IllegalStateException if the operation failed with an I/O error.
   * @since 1.1.0
   */
  Set<String> findResourceNames(String packageName, boolean includeSubPackages, Filter<? super String> filter);

  /**
   * This method finds all resources that are located in the package identified by the given
   * {@code packageName}. <br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath multiple directories, JAR-, and
   * WAR-files may need to be scanned.
   *
   * @param packageName is the name of the {@link Package} to scan. Both "." and "/" are accepted as separator
   *        (e.g. "net.sf.mmm.util.reflect).
   * @param includeSubPackages - if {@code true} all sub-packages of the specified {@link Package} will be
   *        included in the search.
   * @param filter is used to {@link Filter#accept(Object) filter} the resources. The {@link Filter} will
   *        receive fully qualified classpath entries as argument (e.g.
   *        "net/sf/mmm/util/reflect/beans-util-reflect.xml"). Typically you will exclude resources that end
   *        with ".class" or only accept resources that end with ".xml".
   * @param classLoader is the explicit {@link ClassLoader} to use.
   * @return a {@link Set} with the fully qualified names of all requested resources (e.g.
   *         "net/sf/mmm/util/reflect/beans-util-reflect.xml").
   * @throws IllegalStateException if the operation failed with an I/O error.
   * @since 1.1.0
   */
  Set<String> findResourceNames(String packageName, boolean includeSubPackages, Filter<? super String> filter, ClassLoader classLoader);

}
