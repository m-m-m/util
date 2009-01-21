/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of the {@link GenericType} interface.
 * 
 * @param <T> is the templated type of the {@link #getRetrievalClass() upper
 *        bound}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractGenericType<T> implements GenericType<T> {

  /**
   * The constructor.
   */
  protected AbstractGenericType() {

    super();
  }

  /**
   * This method creates the {@link GenericType} that encapsulates the given
   * <code>type</code>.
   * 
   * @see net.sf.mmm.util.reflect.api.ReflectionUtil#createGenericType(Type)
   * 
   * @param type is the {@link Type} to get as {@link GenericType}.
   * @return the according {@link GenericType}.
   */
  protected abstract GenericType<?> create(Type type);

  /**
   * This method gets the defining type. This will typically be the
   * {@link Class} where the {@link #getType() type} of this {@link GenericType}
   * was retrieved from as parameter, return-type or field-type.
   * 
   * @return the defining type or <code>null</code> if NOT available.
   */
  public abstract GenericType<?> getDefiningType();

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean equals(Object other) {

    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }
    if (other instanceof AbstractGenericType) {
      AbstractGenericType<?> otherType = (AbstractGenericType<?>) other;
      if (getType().equals(otherType.getType())) {
        GenericType<?> definingType = getDefiningType();
        if (definingType == null) {
          return (otherType.getDefiningType() == null);
        } else {
          return (definingType.equals(otherType.getDefiningType()));
        }
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {

    int hash = getType().hashCode();
    GenericType<?> definingType = getDefiningType();
    if (definingType != null) {
      hash = hash * 31 + definingType.hashCode();
    }
    return hash;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAssignableFrom(GenericType<?> subType) {

    Class<?> upperBound = getRetrievalClass();
    Class<?> subTypeUpperBound = subType.getRetrievalClass();
    // <? extends Number>.isAssignableFrom(<? extends Integer>) = true
    // <? extends Integer>.isAssignableFrom(<? extends Number>) = false
    if (!upperBound.isAssignableFrom(subTypeUpperBound)) {
      return false;
    }

    Class<?> lowerBound = getAssignmentClass();
    Class<?> subTypeLowerBound = subType.getAssignmentClass();
    if ((lowerBound != upperBound) || (subTypeLowerBound != subTypeUpperBound)) {
      // List<? super Number> numberList = null;
      // List<? super Integer> integerList = numberList;
      // numberList = integerList; --> Compile error

      // e.g. <? super Integer>.isAssignableFrom(<? super Number>)
      // but Number.class.isAssignableFrom(Integer.class)
      if (!subTypeLowerBound.isAssignableFrom(lowerBound)) {
        return false;
      }
    }

    int argCount = getTypeArgumentCount();
    if (argCount > 0) {
      if (upperBound == subTypeUpperBound) {
        int subTypeArgCount = subType.getTypeArgumentCount();
        if (subTypeArgCount != argCount) {
          // this is actually an internal error...
          return false;
        }
        for (int argIndex = 0; argIndex < argCount; argIndex++) {
          GenericType<?> typeArgument = getTypeArgument(argIndex);
          GenericType<?> subTypeArgument = subType.getTypeArgument(argIndex);
          // infinity loop possible in case of recursive generic declarations?
          // Enum<E extends Enum<E>>
          if (!typeArgument.isAssignableFrom(subTypeArgument)) {
            return false;
          }
        }
      } else {
        // here comes the killer:
        // * Map<Number,CharSequence>.isAssignableFrom(HashMap<Integer,String>)
        // * Map<Number,CharSequence>.isAssignableFrom(MyMap<String>) for
        // class MyMap<V> extends HashMap<Integer,V>

        // strategy:
        // either both types are classes with a common super-class
        // or this is an interface extended/implemented by subType
        // this way we can find the common type and resolve type variables...

        // strategy: find common super-type and resolve type variables
        // is it possible that the interface does NOT have type-parameters???
        for (TypeVariable<?> typeVariable : upperBound.getTypeParameters()) {
          Type resolvedType = resolveTypeVariable(typeVariable, this);
          Type resolvedSubType = resolveTypeVariable(typeVariable, subType);
          GenericType<?> resolvedGenericType = create(resolvedType);
          GenericType<?> resolvedGenericSubType = create(resolvedSubType);
          if (!resolvedGenericType.isAssignableFrom(resolvedGenericSubType)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * This method walks up the {@link Class}-hierarchy from
   * <code>descendant</code> up to <code>ancestor</code> and collects the
   * generic {@link Class#getGenericSuperclass() super-classes} or
   * {@link Class#getGenericInterfaces() super-interfaces} of
   * <code>ancestor</code> on that hierarchy-path.<br>
   * Please note that if <code>ancestor</code> is an {@link Class#isInterface()
   * interface}, the hierarchy may NOT be unique. In such case it will be
   * unspecified which of the possible paths is used.
   * 
   * @param ancestor is the super-class or super-interface of
   *        <code>descendant</code>.
   * @param descendant is the sub-class or sub-interface of
   *        <code>ancestor</code>.
   * @return the {@link List} of the generic super-{@link Type}s from
   *         <code>descendant</code> up to <code>ancestor</code>, where the
   *         first element represents the super-{@link Type} of
   *         <code>descendant</code> and the last element represents the generic
   *         declaration of <code>ancestor</code> itself.
   */
  protected List<Type> getGenericDeclarations(Class<?> ancestor, Class<?> descendant) {

    if (!ancestor.isAssignableFrom(descendant)) {
      return null;
    }
    List<Type> declarations = new ArrayList<Type>();
    if (ancestor != descendant) {
      Class<?> child = descendant;
      if (ancestor.isInterface()) {
        while (child != ancestor) {
          Class<?>[] interfaces = child.getInterfaces();
          Class<?> superInterface = null;
          for (int i = 0; i < interfaces.length; i++) {
            Class<?> currentInterface = interfaces[i];
            if (ancestor.isAssignableFrom(currentInterface)) {
              superInterface = currentInterface;
              Type genericDeclaration = child.getGenericInterfaces()[i];
              declarations.add(genericDeclaration);
              break;
            }
          }
          if (superInterface == null) {
            declarations.add(child.getGenericSuperclass());
            child = child.getSuperclass();
          } else {
            child = superInterface;
          }
        }
      } else {
        while (child != ancestor) {
          Type genericDeclaration = child.getGenericSuperclass();
          declarations.add(genericDeclaration);
          child = child.getSuperclass();
        }
      }
    }
    return declarations;
  }

  /**
   * This method gets the declaration-index of the given
   * <code>typeVariable</code>.
   * 
   * @param typeVariable is the {@link TypeVariable}.
   * @return the index of the given <code>typeVariable</code> in its
   *         {@link TypeVariable#getGenericDeclaration() declaration}.
   */
  protected int getDeclarationIndex(TypeVariable<?> typeVariable) {

    GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
    TypeVariable<?>[] variables = genericDeclaration.getTypeParameters();
    for (int variableIndex = 0; variableIndex < variables.length; variableIndex++) {
      if (variables[variableIndex] == typeVariable) {
        return variableIndex;
      }
    }
    return -1;
  }

  /**
   * This method resolves the given <code>typeVariable</code> in the context of
   * the given <code>declaringType</code>.
   * 
   * @param typeVariable is the {@link TypeVariable} to resolve.
   * @param declaringType is the {@link GenericType} where the given
   *        <code>typeVariable</code> occurs or is replaced.
   * @return the resolved {@link Type} or <code>null</code> if the given
   *         <code>typeVariable</code> could NOT be resolved (e.g. it was
   *         {@link TypeVariable#getGenericDeclaration() declared} in a
   *         {@link Class} that is NOT {@link Class#isAssignableFrom(Class)
   *         assignable from} the given <code>declaringType</code>) .
   */
  protected Type resolveTypeVariable(TypeVariable<?> typeVariable, GenericType<?> declaringType) {

    GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
    if (genericDeclaration instanceof Class) {
      Class<?> declaringClass = (Class<?>) genericDeclaration;
      List<Type> hierarchy = getGenericDeclarations(declaringClass, declaringType
          .getRetrievalClass());
      if (hierarchy != null) {
        TypeVariable<?> currentVariable = typeVariable;
        for (int i = hierarchy.size() - 1; i >= -1; i--) {
          Type hierarchyType;
          if (i >= 0) {
            hierarchyType = hierarchy.get(i);
          } else {
            hierarchyType = declaringType.getType();
          }
          if (hierarchyType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) hierarchyType;
            Type[] typeArguments = pt.getActualTypeArguments();
            int variableIndex = getDeclarationIndex(currentVariable);
            if (variableIndex >= 0) {
              Type typeArgument = typeArguments[variableIndex];
              if (typeArgument instanceof TypeVariable) {
                currentVariable = (TypeVariable<?>) typeArgument;
              } else {
                return typeArgument;
              }
            }
          }
        }
        if (currentVariable != typeVariable) {
          // NOT really resolved, but maybe bounds are more specific...
          return currentVariable;
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String toString() {

    Type type = getType();
    Class<?> upperBound = getRetrievalClass();
    if (upperBound == type) {
      return upperBound.getName();
    } else {
      return type.toString();
    }
  }

}
