/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
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

import net.sf.mmm.util.filter.CharFilter;
import net.sf.mmm.util.filter.Filter;
import net.sf.mmm.util.filter.ListCharFilter;
import net.sf.mmm.util.nls.base.NlsIllegalArgumentException;
import net.sf.mmm.util.reflect.type.GenericArrayTypeImpl;
import net.sf.mmm.util.reflect.type.LowerBoundWildcardType;
import net.sf.mmm.util.reflect.type.ParameterizedTypeImpl;
import net.sf.mmm.util.reflect.type.UnboundedWildcardType;
import net.sf.mmm.util.reflect.type.UpperBoundWildcardType;
import net.sf.mmm.util.scanner.CharSequenceScanner;

/**
 * This class is a collection of utility functions for dealing with
 * {@link java.lang.reflect reflection}.
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ReflectionUtil {

  /** @see #getInstance() */
  private static ReflectionUtil instance;

  /** an empty class array */
  public static final Class<?>[] NO_PARAMETERS = new Class[0];

  /** an empty {@link Object}-array */
  public static final Object[] NO_ARGUMENTS = new Object[0];

  /** an empty {@link Type}-array */
  public static final Type[] NO_TYPES = new Type[0];

  /** @see #toType(CharSequenceScanner, ClassResolver, Type) */
  private static final CharFilter CHAR_FILTER = new ListCharFilter(false, '<', '[', ',', '?', '>');

  /**
   * The constructor.
   */
  public ReflectionUtil() {

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
      synchronized (ReflectionUtil.class) {
        if (instance == null) {
          instance = new ReflectionUtil();
        }
      }
    }
    return instance;
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
   * This method gets the component-type of the given <code>type</code>.<br>
   * Here are some examples:<br>
   * <table border="1">
   * <tr>
   * <th>type</th>
   * <th>getComponentType(type)</th>
   * </tr>
   * <tr>
   * <td><code>List&lt;Map&lt;String, Long&gt;&gt;</code></td>
   * <td><code>Map&lt;String, Long&gt;</code></td>
   * </tr>
   * <tr>
   * <td><code>List</code></td>
   * <td><code>Object</code></td>
   * </tr>
   * <tr>
   * <td><code>Foo&lt;Bar&gt;[]</code></td>
   * <td><code>Foo&lt;Bar&gt;</code></td>
   * </tr>
   * <tr>
   * <td><code>Map&lt;String, Long&gt;</code></td>
   * <td><code>null</code></td>
   * </tr>
   * </table>
   * 
   * @param type is the type where to get the component type from.
   * @param requireCollectionOrArray - if <code>true</code> then
   * @return the component type of the given <code>type</code> or
   *         <code>null</code> if the given <code>type</code> does NOT have
   *         a (single) component type (e.g.
   *         <code>Map&lt;String, Integer&gt;</code> or <code>MyClass</code>).
   */
  public Type getComponentType(Type type, boolean requireCollectionOrArray) {

    if (type instanceof Class) {
      Class<?> clazz = (Class<?>) type;
      if (clazz.isArray()) {
        return clazz.getComponentType();
      } else if (Collection.class.isAssignableFrom(clazz)) {
        return Object.class;
      }
    } else if (type instanceof GenericArrayType) {
      GenericArrayType gat = (GenericArrayType) type;
      return gat.getGenericComponentType();
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      if (requireCollectionOrArray) {
        Class<?> rawClass = getClass(pt.getRawType(), false);
        if (!Collection.class.isAssignableFrom(rawClass)) {
          return null;
        }
      }
      Type[] generics = pt.getActualTypeArguments();
      if (generics.length == 1) {
        return generics[0];
      }
    }
    return null;
  }

  /**
   * This method gets the most specific {@link Class} available by the type-safe
   * analyzation of the given generic <code>type</code>. Unlike
   * {@link #getClass(Type, boolean)} this method resolves {@link TypeVariable}s
   * with the proper type they have been bound with.<br>
   * 
   * Examples: <br>
   * <table border="1">
   * <tr>
   * <th><code>type</code></th>
   * <th><code>owningType</code></th>
   * <th><code>{@link #getClass(Type, boolean, Type) getClass}(type, owningType)</code></th>
   * <th>comment</th>
   * </tr>
   * <tr>
   * <td>E</td>
   * <td>{@link List}&lt;Foo&gt;</td>
   * <td>Foo</td>
   * <td>E is a {@link TypeVariable} representing the generic return-type of
   * the method {@link List#get(int)}</td>
   * </tr>
   * </table>
   * 
   * @param type is the type to convert.
   * @param forAssignment - <code>true</code> if the requested {@link Class}
   *        should be suitable as parameter or for assignment,
   *        <code>false</code> if the requested {@link Class} should be
   *        suitable as return-type of for retrieval.
   * @param owningType is the type where the given <code>type</code> was
   *        retrieved from.
   * @return the closest class representing the given <code>type</code>.
   */
  public Class<?> getClass(Type type, boolean forAssignment, Type owningType) {

    if (type instanceof Class) {
      return (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      return getClass(pt.getRawType(), forAssignment, owningType);
    } else if (type instanceof WildcardType) {
      WildcardType wt = (WildcardType) type;
      if (forAssignment) {
        Type[] lower = wt.getLowerBounds();
        if (lower.length == 1) {
          return getClass(lower[0], forAssignment, owningType);
        }
      }
      Type[] upper = wt.getUpperBounds();
      if (upper.length == 1) {
        return getClass(upper[0], forAssignment, owningType);
      }
    } else if (type instanceof GenericArrayType) {
      GenericArrayType gat = (GenericArrayType) type;
      Class<?> componentType = getClass(gat.getGenericComponentType(), forAssignment, owningType);
      // this is sort of stupid but there seems no other way...
      return Array.newInstance(componentType, 0).getClass();
    } else if (type instanceof TypeVariable) {
      TypeVariable<?> variable = (TypeVariable<?>) type;
      if (owningType != null) {
        GenericDeclaration genericDeclaration = variable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
          Class<?> declaringClass = (Class<?>) genericDeclaration;
          TypeVariable<?>[] variables = genericDeclaration.getTypeParameters();
          for (int variableIndex = 0; variableIndex < variables.length; variableIndex++) {
            TypeVariable<?> currentVariable = variables[variableIndex];
            if (variable == currentVariable) {
              Class<?> owningClass = getClass(owningType, false);
              Type declaringType = null;
              if (owningClass == declaringClass) {
                declaringType = owningType;
              } else {
                declaringType = getGenericDeclaration(declaringClass, owningClass);
                assert (declaringType != null);
              }
              if ((declaringType != null) && (declaringType instanceof ParameterizedType)) {
                ParameterizedType parameterizedSuperType = (ParameterizedType) declaringType;
                Type[] typeArguments = parameterizedSuperType.getActualTypeArguments();
                Type variableType = typeArguments[variableIndex];
                return getClass(variableType, forAssignment, owningType);
              }
            }
          }
          // } else if (genericDeclaration instanceof Method) {
        }
      }
      Type[] bounds = variable.getBounds();
      if (bounds.length == 1) {
        return getClass(bounds[0], forAssignment, owningType);
      }
    }
    return Object.class;
  }

  /**
   * This method creates the {@link GenericType} representing the given
   * <code>type</code> in the context of the given <code>definingType</code>.<br>
   * <b>ATTENTION:</b><br>
   * If you know the {@link Type} where the given <code>type</code> was
   * defined you should use {@link #createGenericType(Type, GenericType)}
   * instead to get a more precise result.
   * 
   * @param type is the {@link Type} to represent.
   * @return the according {@link GenericType}.
   */
  public GenericType createGenericType(Type type) {

    if (type instanceof Class) {
      return new SimpleGenericType((Class<?>) type);
    } else {
      return new GenericTypeImpl(type);
    }
  }

  /**
   * This method creates the {@link GenericType} representing the given
   * <code>type</code> in the context of the given <code>definingType</code>.<br>
   * Here is some typical example of how to use this:
   * 
   * <pre>
   * {@link ReflectionUtil} util = {@link ReflectionUtil#getInstance()};
   * Class&lt;?&gt; myClass = getSomeClass();
   * GenericType definingType = util.{@link #createGenericType(Type) createGenericType}(myClass);
   * {@link Method} myMethod = findSomeMethod(myClass);
   * Type returnType = myMethod.{@link Method#getGenericReturnType() getGenericReturnType()};
   * GenericType type = util.{@link #createGenericType(Type, GenericType) createGenericType}(returnType, definingType);
   * Class&lt;?&gt; returnClass = type.{@link GenericType#getUpperBound()};
   * </pre>
   * 
   * Now if you ask your self my all this instead of just using
   * <code>myMethod.{@link Method#getReturnType() getReturnType()}</code>?
   * Read the javadoc of {@link GenericType} to get the answer.<br>
   * <b>NOTE:</b><br>
   * Please look at <code>mmm-util-pojo</code> which allows to use this
   * features at a higher level and therefore much easier.
   * 
   * @see #createGenericType(Type, Class)
   * 
   * @param type is the {@link Type} to represent.
   * @param definingType is the {@link GenericType} where the given
   *        <code>type</code> is defined in. It is needed to resolve
   *        {@link java.lang.reflect.TypeVariable}s.
   * @return the according {@link GenericType}.
   */
  public GenericType createGenericType(Type type, GenericType definingType) {

    return new GenericTypeImpl(type, definingType);
  }

  /**
   * This method creates the {@link GenericType} representing the given
   * <code>type</code> in the context of the given <code>definingType</code>.<br>
   * It is a convenience method for
   * <code>{@link #createGenericType(Type, GenericType) createGenericType}(type, 
   * {@link #createGenericType(Type) createGenericType}(definingType))</code>
   * 
   * @param type is the {@link Type} to represent.
   * @param definingType is the {@link Class} where the given <code>type</code>
   *        is defined in. It is needed to resolve
   *        {@link java.lang.reflect.TypeVariable}s.
   * @return the according {@link GenericType}.
   */
  public GenericType createGenericType(Type type, Class<?> definingType) {

    return new GenericTypeImpl(type, new SimpleGenericType(definingType));
  }

  /**
   * This method gets the raw-type of the given generic <code>type</code>.
   * 
   * @see #getClass(Type, boolean)
   * 
   * @param type is the type to convert.
   * @return the closest class representing the given <code>type</code>.
   */
  public Class<?> getClass(Type type) {

    return getClass(type, false);
  }

  /**
   * This method gets the raw-type of the given generic <code>type</code>.<br>
   * Examples: <br>
   * <table border="1">
   * <tr>
   * <th><code>type</code></th>
   * <th><code>{@link #getClass(Type, boolean) getRawClass}(type, false)</code></th>
   * <th><code>{@link #getClass(Type, boolean) getRawClass}(type, true)</code></th>
   * </tr>
   * <tr>
   * <td><code>String</code></td>
   * <td><code>String</code></td>
   * <td><code>String</code></td>
   * </tr>
   * <tr>
   * <td><code>List&lt;String&gt;</code></td>
   * <td><code>List</code></td>
   * <td><code>List</code></td>
   * </tr>
   * <tr>
   * <td><code>&lt;T extends MyClass&gt; T[]</code></td>
   * <td><code>MyClass[]</code></td>
   * <td><code>MyClass[]</code></td>
   * </tr>
   * <tr>
   * <td><code>? super MyClass</code></td>
   * <td><code>Object</code></td>
   * <td><code>MyClass</code></td>
   * </tr>
   * </table>
   * 
   * @param type is the type to convert.
   * @param forAssignment - <code>true</code> if the requested {@link Class}
   *        should be suitable as parameter or for assignment,
   *        <code>false</code> if the requested {@link Class} should be
   *        suitable as return-type of for retrieval.
   * @return the closest class representing the given <code>type</code>.
   */
  public Class<?> getClass(Type type, boolean forAssignment) {

    return getClass(type, forAssignment, null);
  }

  /**
   * This method walks up the {@link Class}-hierarchy from
   * <code>descendant</code> up to <code>ancestor</code> and returns the
   * sub-class or sub-interface of <code>ancestor</code> on that
   * hierarchy-path.<br>
   * Please note that if <code>ancestor</code> is an
   * {@link Class#isInterface() interface}, the hierarchy may NOT be unique. In
   * such case it will be unspecified which of the possible paths is used.
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
   * sub-class or sub-interface of <code>ancestor</code> on that
   * hierarchy-path.<br>
   * Please note that if <code>ancestor</code> is an
   * {@link Class#isInterface() interface}, the hierarchy may NOT be unique. In
   * such case it will be unspecified which of the possible paths is used.
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
   * This method collects the {@link Class} objects in the hierarchy from
   * <code>descendant</code> up to <code>ancestor</code> excluding these two
   * classes themselves.<br>
   * Please note that if <code>ancestor</code> is an
   * {@link Class#isInterface() interface}, the hierarchy may NOT be unique. In
   * such case it will be unspecified which of the possible paths is used.
   * 
   * @param ancestor is the super-class or super-interface of
   *        <code>descendant</code>.
   * @param descendant is the sub-class or sub-interface of
   *        <code>ancestor</code>.
   * @param hierarchyList is the {@link List} where to add the {@link Class}es.
   * @return <code>true</code> if <code>ancestor</code> is
   *         {@link Class#isAssignableFrom(Class) assignable} from
   *         <code>descendant</code>, <code>false</code> otherwise.
   */
  protected boolean collectClassHierarchy(Class<?> ancestor, Class<?> descendant,
      List<Class<?>> hierarchyList) {

    if (ancestor == descendant) {
      return true;
    }
    if (!ancestor.isAssignableFrom(descendant)) {
      return false;
    }
    if (ancestor.isInterface()) {
      Class<?> child = descendant;
      while (true) {
        for (Class<?> childInterface : child.getInterfaces()) {
          if (childInterface == ancestor) {
            return true;
          } else if (ancestor.isAssignableFrom(childInterface)) {
            hierarchyList.add(childInterface);
            child = childInterface;
            break;
          }
        }
      }
    } else {
      Class<?> child = descendant.getSuperclass();
      while (child != ancestor) {
        hierarchyList.add(child);
        child = child.getSuperclass();
      }
      return true;
    }
  }

  /**
   * This method creates the {@link Class} reflecting an
   * {@link Class#isArray() array} of the given
   * <code>{@link Class#getComponentType() componentType}</code>.
   * 
   * @param componentType is the {@link Class#getComponentType() component type}.
   * @return the according {@link Class#isArray() array}-class.
   */
  public Class<?> getArrayClass(Class<?> componentType) {

    // this is sort of stupid but there seems no other way...
    return Array.newInstance(componentType, 0).getClass();
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
  public Type toType(String type) throws ClassNotFoundException, IllegalArgumentException {

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
   *        {@link java.lang.reflect.ParameterizedType#getOwnerType() owner-type}
   *        or <code>null</code>.
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
   * This method gets the string representation of a {@link Type}. Instead of
   * {@link Type#toString()} it returns {@link Class#getName()} if the type is a
   * {@link Class}.
   * 
   * @param type is the type to get as string.
   * @return the string representation of the given <code>type</code>.
   */
  public String toString(Type type) {

    if (type instanceof Class) {
      return ((Class<?>) type).getName();
    } else {
      return type.toString();
    }
  }

  /**
   * This method compares the given classes.
   * 
   * @param class1 is the first class.
   * @param class2 is the second class.
   * @return
   *        <ul>
   *        <li><code>0</code> if both classes are equal to each other. </li>
   *        <li><code>1</code> if <code>class1</code> inherits from
   *        <code>class2</code>.</li>
   *        <li><code>-1</code> if <code>class2</code> inherits from
   *        <code>class1</code>.</li>
   *        <li>{@link Integer#MIN_VALUE} otherwise.</li>
   *        </ul>
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
  public Method getParentMethod(Method method) throws SecurityException {

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
  public Set<String> findClassNames(String packageName, boolean includeSubPackages)
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
  public Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames)
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
  public Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames,
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
