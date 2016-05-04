/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * This is an implementation of the {@link TypeVariable} interface.
 *
 * @param <DECLARATION> the type of generic declaration that declared the underlying type variable.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class TypeVariableImpl<DECLARATION extends GenericDeclaration> implements TypeVariable<DECLARATION> {

  private final String name;

  private final Type[] bounds;

  private final DECLARATION genericDeclaration;

  /**
   * The constructor.
   *
   * @param name is the {@link #getName() name}.
   * @param declaration is the {@link #getGenericDeclaration() declaring element} (e.g. Class or Method).
   * @param bounds are the {@link #getBounds()}.
   */
  public TypeVariableImpl(String name, DECLARATION declaration, Type[] bounds) {

    super();
    this.name = name;
    this.genericDeclaration = declaration;
    this.bounds = bounds;
  }

  @Override
  public Type[] getBounds() {

    return this.bounds;
  }

  @Override
  public DECLARATION getGenericDeclaration() {

    return this.genericDeclaration;
  }

  @Override
  public String getName() {

    return this.name;
  }

  // Java8 support
  @Override
  public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {

    return null;
  }

  @Override
  @SuppressWarnings({ "unchecked" })
  // Java8 support
  public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {

    return (T[]) Array.newInstance(annotationClass, 0);
  }

  // Java8 support
  @Override
  public Annotation[] getAnnotations() {

    return ReflectionUtil.NO_ANNOTATIONS;
  }

  // Java8 support
  @Override
  public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {

    return null;
  }

  @Override
  @SuppressWarnings({ "unchecked" })
  // Java8 support
  public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {

    return (T[]) Array.newInstance(annotationClass, 0);
  }

  // Java8 support
  @Override
  public Annotation[] getDeclaredAnnotations() {

    return getAnnotations();
  }

  // Java8 support
  @Override
  public java.lang.reflect.AnnotatedType[] getAnnotatedBounds() {

    // TODO hohwille actually incorrect...
    return new java.lang.reflect.AnnotatedType[0];
  }

  @Override
  public boolean equals(Object other) {

    if (other == this) {
      return true;
    }
    if (other instanceof TypeVariable<?>) {
      TypeVariable<?> otherVariable = (TypeVariable<?>) other;
      return (this.name.equals(otherVariable.getName())
          && this.genericDeclaration.equals(otherVariable.getGenericDeclaration()));
    }
    return false;
  }

  @Override
  public int hashCode() {

    return this.genericDeclaration.hashCode() ^ this.name.hashCode();
  }

  @Override
  public String toString() {

    return this.name;
  }

}
