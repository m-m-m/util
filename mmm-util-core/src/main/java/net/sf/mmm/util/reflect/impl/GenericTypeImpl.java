/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;

import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.AbstractGenericType;

/**
 * This is the implementation of the {@link GenericType} interface.
 *
 * @param <T> is the templated type of the {@link #getRetrievalClass() upper bound}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class GenericTypeImpl<T> extends AbstractGenericType<T> {

  /** @see #getDefiningType() */
  private final GenericType<?> definingType;

  /** @see #getType() */
  private final Type type;

  /** @see #getAssignmentClass() */
  private final Class<? extends T> assignmentClass;

  /** @see #getRetrievalClass() */
  private final Class<T> retrievalClass;

  /** @see #getComponentType() */
  private final GenericType<?> componentType;

  /** @see #getKeyType() */
  private final GenericType<?> keyType;

  /** @see #getTypeArgument(int) */
  private final Type[] typeArgs;

  /** @see #getTypeArgument(int) */
  private final GenericType<?>[] typesArguments;

  /**
   * The constructor.
   *
   * @param type is the {@link #getType() type}.
   */
  public GenericTypeImpl(Type type) {

    this(type, null);
  }

  /**
   * The constructor.
   *
   * @param valueType is the {@link #getType() value-type}.
   * @param definingType is the {@link #getDefiningType() defining-type}.
   */
  @SuppressWarnings("unchecked")
  public GenericTypeImpl(Type valueType, GenericType<?> definingType) {

    super();
    this.type = valueType;
    this.definingType = definingType;
    ClassBounds bounds = getClassBounds(this.type);
    this.assignmentClass = (Class<? extends T>) bounds.assignmentClass;
    this.retrievalClass = (Class<T>) bounds.retrievalClass;
    Type genericComponentType = null;
    Type genericKeyType = null;
    if (valueType instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) valueType;
      this.typeArgs = parameterizedType.getActualTypeArguments();
      this.typesArguments = new GenericType[this.typeArgs.length];
    } else {
      this.typeArgs = ReflectionUtil.NO_TYPES;
      this.typesArguments = NO_TYPES;
      if (valueType instanceof GenericArrayType) {
        GenericArrayType arrayType = (GenericArrayType) valueType;
        genericComponentType = arrayType.getGenericComponentType();
      }
    }
    if (genericComponentType == null) {
      TypeVariable<?> keyTypeVariable = null;
      TypeVariable<?> componentTypeVariable = null;
      if (this.retrievalClass.isArray()) {
        genericComponentType = this.retrievalClass.getComponentType();
      } else if (Collection.class.isAssignableFrom(this.retrievalClass)) {
        componentTypeVariable = CommonTypeVariables.TYPE_VARIABLE_COLLECTION_ELEMENT;
      } else if (Map.class.isAssignableFrom(this.retrievalClass)) {
        componentTypeVariable = CommonTypeVariables.TYPE_VARIABLE_MAP_VALUE;
        keyTypeVariable = CommonTypeVariables.TYPE_VARIABLE_MAP_KEY;
      }
      if (componentTypeVariable != null) {
        genericComponentType = resolveTypeVariable(componentTypeVariable, this);
        if (genericComponentType == null) {
          genericComponentType = componentTypeVariable;
        }
      }
      if (keyTypeVariable != null) {
        genericKeyType = resolveTypeVariable(keyTypeVariable, this);
        if (genericKeyType == null) {
          genericKeyType = keyTypeVariable;
        }
      }
    }
    if (genericComponentType == null) {
      this.componentType = null;
    } else {
      this.componentType = create(genericComponentType, definingType);
    }
    if (genericKeyType == null) {
      this.keyType = null;
    } else {
      this.keyType = create(genericKeyType, definingType);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericType<?> create(Type genericType) {

    return new GenericTypeImpl<>(genericType);
  }

  /**
   * This method creates a new instance of this class. It may be overridden to create the appropriate sub-type.
   *
   * @param genericType is the {@link #getType() value-type}.
   * @param genericDefiningType is the {@link #getDefiningType() defining-type}.
   * @return a new {@link GenericType} instance.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected AbstractGenericType<T> create(Type genericType, GenericType<?> genericDefiningType) {

    return new GenericTypeImpl(genericType, genericDefiningType);
  }

  /**
   * This method gets the most specific {@link Class} available by the type-safe analyzation of the given generic
   * <code>type</code>. Unlike {@link #getClassBounds(Type)} this method resolves {@link TypeVariable} s with the proper
   * type they have been bound with. <br>
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
   * <td>E is a {@link TypeVariable} representing the generic return-type of the method {@link java.util.List#get(int)}</td>
   * </tr>
   * </table>
   *
   * @param currentType is the type to convert.
   * @return the closest class representing the given <code>type</code>.
   */
  protected ClassBounds getClassBounds(Type currentType) {

    if (currentType instanceof Class<?>) {
      return new ClassBounds((Class<?>) currentType);
    } else if (currentType instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) currentType;
      return getClassBounds(pt.getRawType());
    } else if (currentType instanceof WildcardType) {
      WildcardType wt = (WildcardType) currentType;
      Class<?> upperBoundClass;
      Type[] upper = wt.getUpperBounds();
      if (upper.length > 0) {
        ClassBounds bounds = getClassBounds(upper[0]);
        upperBoundClass = bounds.retrievalClass;
      } else {
        upperBoundClass = Object.class;
      }
      Class<?> lowerBoundClass;
      Type[] lower = wt.getLowerBounds();
      if (lower.length > 0) {
        ClassBounds bounds = getClassBounds(lower[0]);
        lowerBoundClass = bounds.assignmentClass;
      } else {
        lowerBoundClass = upperBoundClass;
      }
      return new ClassBounds(lowerBoundClass, upperBoundClass);
    } else if (currentType instanceof GenericArrayType) {
      GenericArrayType gat = (GenericArrayType) currentType;
      ClassBounds bounds = getClassBounds(gat.getGenericComponentType());
      Class<?> lower = getArrayClass(bounds.assignmentClass);
      Class<?> upper;
      if (bounds.assignmentClass == bounds.retrievalClass) {
        upper = lower;
      } else {
        upper = getArrayClass(bounds.retrievalClass);
      }
      return new ClassBounds(lower, upper);
    } else if (currentType instanceof TypeVariable<?>) {
      TypeVariable<?> variable = (TypeVariable<?>) currentType;
      if (this.definingType != null) {
        Type resolvedType = resolveTypeVariable(variable, this.definingType);
        if ((resolvedType != null) && (resolvedType != variable)) {
          return getClassBounds(resolvedType);
        }
      }
      Type[] bounds = variable.getBounds();
      if (bounds.length > 0) {
        return getClassBounds(bounds[0]);
      }
    }
    return new ClassBounds(Object.class);
  }

  /**
   * This method creates the {@link Class} reflecting an {@link Class#isArray() array} of the given
   * <code>{@link Class#getComponentType() componentType}</code>.
   *
   * @param componentClass is the {@link Class#getComponentType() component type}.
   * @return the according {@link Class#isArray() array}-class.
   */
  public Class<?> getArrayClass(Class<?> componentClass) {

    // this is sort of stupid but there seems no other way...
    return Array.newInstance(componentClass, 0).getClass();
  }

  /**
   * {@inheritDoc}
   */
  public GenericType<?> getComponentType() {

    return this.componentType;
  }

  /**
   * {@inheritDoc}
   */
  public GenericType<?> getKeyType() {

    return this.keyType;
  }

  /**
   * This method gets the defining type.
   *
   * @return the defining type or <code>null</code> if NOT available.
   */
  @Override
  public GenericType<?> getDefiningType() {

    return this.definingType;
  }

  /**
   * {@inheritDoc}
   */
  public Class<? extends T> getAssignmentClass() {

    return this.assignmentClass;
  }

  /**
   * {@inheritDoc}
   */
  public Class<T> getRetrievalClass() {

    return this.retrievalClass;
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
  public GenericType<?> getTypeArgument(int index) {

    GenericType<?> result = this.typesArguments[index];
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

    /** @see #getAssignmentClass() */
    private final Class<?> assignmentClass;

    /** @see #getRetrievalClass() */
    private final Class<?> retrievalClass;

    /**
     * The constructor.
     *
     * @param bound is the {@link Class} for {@link #getAssignmentClass() assignment} and {@link #getRetrievalClass()
     *        retrieval}.
     */
    public ClassBounds(Class<?> bound) {

      this(bound, bound);
    }

    /**
     * The constructor.
     *
     * @param assignmentClass is the {@link #getAssignmentClass() assignment class}.
     * @param retrievalClass is the {@link #getRetrievalClass() retrieval class} .
     */
    public ClassBounds(Class<?> assignmentClass, Class<?> retrievalClass) {

      super();
      this.assignmentClass = assignmentClass;
      this.retrievalClass = retrievalClass;
    }

    /**
     * @see GenericType#getAssignmentClass()
     *
     * @return the {@link Class} for assignment.
     */
    public Class<?> getAssignmentClass() {

      return this.assignmentClass;
    }

    /**
     * @see GenericType#getRetrievalClass()
     *
     * @return the {@link Class} for retrieval.
     */
    public Class<?> getRetrievalClass() {

      return this.retrievalClass;
    }

  }

}
