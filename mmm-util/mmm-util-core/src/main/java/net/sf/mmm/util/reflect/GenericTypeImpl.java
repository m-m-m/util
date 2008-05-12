/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;

/**
 * This is the implementation of the {@link GenericType} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class GenericTypeImpl extends AbstractGenericType {

  /** @see #getDefiningType() */
  private final GenericType definingType;

  /** @see #getType() */
  private final Type type;

  /** @see #getLowerBound() */
  private final Class<?> lowerBound;

  /** @see #getUpperBound() */
  private final Class<?> upperBound;

  /** @see #getComponentType() */
  private final GenericType componentType;

  /** @see #getTypeArgument(int) */
  private final Type[] typeArgs;

  /** @see #getTypeArgument(int) */
  private final GenericType[] typesArguments;

  /**
   * The constructor.
   * 
   * @param type is the {@link #getType() type}.
   */
  protected GenericTypeImpl(Type type) {

    this(type, null);
  }

  /**
   * The constructor.
   * 
   * @param valueType is the {@link #getType() value-type}.
   * @param definingType is the {@link #getDefiningType() defining-type}.
   */
  protected GenericTypeImpl(Type valueType, GenericType definingType) {

    super();
    this.type = valueType;
    this.definingType = definingType;
    ClassBounds bounds = getClassBounds(this.type);
    this.lowerBound = bounds.lowerBound;
    this.upperBound = bounds.upperBound;
    GenericType genericComponentType = null;
    if (valueType instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) valueType;
      this.typeArgs = parameterizedType.getActualTypeArguments();
      this.typesArguments = new GenericType[this.typeArgs.length];
      if (Collection.class.isAssignableFrom(this.upperBound)) {
        if (this.typeArgs.length == 1) {
          genericComponentType = getTypeArgument(0);
        } else {
          genericComponentType = SimpleGenericType.TYPE_OBJECT;
        }
      } else if (Map.class.isAssignableFrom(this.upperBound)) {
        if (this.typeArgs.length == 2) {
          genericComponentType = getTypeArgument(1);
        } else {
          genericComponentType = SimpleGenericType.TYPE_OBJECT;
        }
      }
    } else {
      this.typeArgs = ReflectionUtil.NO_TYPES;
      this.typesArguments = NO_TYPES;
      if (valueType instanceof GenericArrayType) {
        GenericArrayType arrayType = (GenericArrayType) valueType;
        genericComponentType = create(arrayType.getGenericComponentType(), definingType);
      }
    }
    if ((genericComponentType == null) && (this.upperBound.isArray())) {
      genericComponentType = create(this.upperBound.getComponentType(), definingType);
    }
    this.componentType = genericComponentType;
  }

  /**
   * This method creates a new instance of this class. It may be overridden to
   * create the appropriate sub-type.
   * 
   * @param genericType is the {@link #getType() value-type}.
   * @param genericDefiningType is the {@link #getDefiningType() defining-type}.
   * @return a new {@link GenericType} instance.
   */
  protected GenericTypeImpl create(Type genericType, GenericType genericDefiningType) {

    return new GenericTypeImpl(genericType, genericDefiningType);
  }

  /**
   * This method gets the most specific {@link Class} available by the type-safe
   * analyzation of the given generic <code>type</code>. Unlike
   * {@link #getClassBounds(Type)} this method resolves {@link TypeVariable}s
   * with the proper type they have been bound with.<br>
   * 
   * Examples: <br>
   * <table border="1">
   * <tr>
   * <th><code>type</code></th>
   * <th><code>owningType</code></th>
   * <th><code>{@link #getClassBounds(Type) getClass}(type, owningType)</code></th>
   * <th>comment</th>
   * </tr>
   * <tr>
   * <td>E</td>
   * <td>{@link java.util.List}&lt;Foo&gt;</td>
   * <td>Foo</td>
   * <td>E is a {@link TypeVariable} representing the generic return-type of
   * the method {@link java.util.List#get(int)}</td>
   * </tr>
   * </table>
   * 
   * @param currentType is the type to convert.
   * @return the closest class representing the given <code>type</code>.
   */
  protected ClassBounds getClassBounds(Type currentType) {

    if (currentType instanceof Class) {
      return new ClassBounds((Class<?>) currentType);
    } else if (currentType instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) currentType;
      return getClassBounds(pt.getRawType());
    } else if (currentType instanceof WildcardType) {
      WildcardType wt = (WildcardType) currentType;
      Class<?> lowerBoundClass;
      Type[] lower = wt.getLowerBounds();
      if (lower.length > 0) {
        ClassBounds bounds = getClassBounds(lower[0]);
        lowerBoundClass = bounds.lowerBound;
      } else {
        // TODO
        lowerBoundClass = Object.class;
      }
      Class<?> upperBoundClass;
      Type[] upper = wt.getUpperBounds();
      if (upper.length > 0) {
        ClassBounds bounds = getClassBounds(upper[0]);
        upperBoundClass = bounds.upperBound;
      } else {
        // TODO
        upperBoundClass = Object.class;
      }
      return new ClassBounds(lowerBoundClass, upperBoundClass);
    } else if (currentType instanceof GenericArrayType) {
      GenericArrayType gat = (GenericArrayType) currentType;
      ClassBounds bounds = getClassBounds(gat.getGenericComponentType());
      Class<?> lower = getArrayClass(bounds.lowerBound);
      Class<?> upper;
      if (bounds.lowerBound == bounds.upperBound) {
        upper = lower;
      } else {
        upper = getArrayClass(bounds.upperBound);
      }
      return new ClassBounds(lower, upper);
    } else if (currentType instanceof TypeVariable) {
      TypeVariable<?> variable = (TypeVariable<?>) currentType;
      if (this.definingType != null) {
        GenericDeclaration genericDeclaration = variable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
          Class<?> declaringClass = (Class<?>) genericDeclaration;
          TypeVariable<?>[] variables = genericDeclaration.getTypeParameters();
          for (int variableIndex = 0; variableIndex < variables.length; variableIndex++) {
            TypeVariable<?> currentVariable = variables[variableIndex];
            if (variable == currentVariable) {
              Class<?> owningClass = this.definingType.getUpperBound();
              Type owningType = this.definingType.getType();
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
                return getClassBounds(variableType);
              }
            }
          }
          // } else if (genericDeclaration instanceof Method) {
        }
      }
      Type[] bounds = variable.getBounds();
      if (bounds.length == 1) {
        return getClassBounds(bounds[0]);
      }
    }
    return new ClassBounds(Object.class);
  }

  /**
   * This method creates the {@link Class} reflecting an
   * {@link Class#isArray() array} of the given
   * <code>{@link Class#getComponentType() componentType}</code>.
   * 
   * @param componentClass is the
   *        {@link Class#getComponentType() component type}.
   * @return the according {@link Class#isArray() array}-class.
   */
  public Class<?> getArrayClass(Class<?> componentClass) {

    // this is sort of stupid but there seems no other way...
    return Array.newInstance(componentClass, 0).getClass();
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
   * {@inheritDoc}
   */
  public GenericType getComponentType() {

    return this.componentType;
  }

  /**
   * This method gets the defining type.
   * 
   * @return the defining type or <code>null</code> if NOT available.
   */
  @Override
  public GenericType getDefiningType() {

    return this.definingType;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getLowerBound() {

    return this.lowerBound;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getUpperBound() {

    return this.upperBound;
  }

  /**
   * {@inheritDoc}
   */
  public Type getType() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  public int getTypeArgumentCount() {

    return this.typesArguments.length;
  }

  /**
   * {@inheritDoc}
   */
  public GenericType getTypeArgument(int index) {

    GenericType result = this.typesArguments[index];
    if (result == null) {
      result = create(this.typeArgs[index], this.definingType);
      this.typesArguments[index] = result;
    }
    return result;
  }

  /**
   * This inner class represents the bounds of a {@link GenericType}.
   */
  protected static class ClassBounds {

    /** @see #getLowerBound() */
    private final Class<?> lowerBound;

    /** @see #getUpperBound() */
    private final Class<?> upperBound;

    /**
     * The constructor.
     * 
     * @param bound is the {@link #getLowerBound() lower} and
     *        {@link #getUpperBound() upper bound}.
     */
    public ClassBounds(Class<?> bound) {

      this(bound, bound);
    }

    /**
     * The constructor.
     * 
     * @param lowerBound is the {@link #getLowerBound() lower bound}.
     * @param upperBound is the {@link #getUpperBound() upper bound}.
     */
    public ClassBounds(Class<?> lowerBound, Class<?> upperBound) {

      super();
      this.lowerBound = lowerBound;
      this.upperBound = upperBound;
    }

    /**
     * @return the lowerBound
     */
    public Class<?> getLowerBound() {

      return this.lowerBound;
    }

    /**
     * @return the upperBound
     */
    public Class<?> getUpperBound() {

      return this.upperBound;
    }

  }

}
