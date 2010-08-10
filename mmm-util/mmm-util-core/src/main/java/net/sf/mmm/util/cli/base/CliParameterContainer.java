/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;

/**
 * This is the abstract base class for a container with the metadata of an
 * {@link net.sf.mmm.util.cli.api.CliOption option} or
 * {@link net.sf.mmm.util.cli.api.CliArgument argument}.
 * 
 * @see CliArgumentContainer
 * @see CliOptionContainer
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class CliParameterContainer {

  /** @see #getSetter() */
  private final PojoPropertyAccessorOneArg setter;

  /** @see #getGetter() */
  private final PojoPropertyAccessorNonArg getter;

  /** @see #getConstraint() */
  private final Annotation constraint;

  /**
   * The constructor.
   * 
   * @param setter is the {@link #getSetter() setter}.
   * @param getter is the {@link #getGetter() getter}.
   * @param constraint is the {@link #getConstraint() constraint}.
   */
  public CliParameterContainer(PojoPropertyAccessorOneArg setter,
      PojoPropertyAccessorNonArg getter, Annotation constraint) {

    super();
    assert (setter.getMode() == PojoPropertyAccessorOneArgMode.SET);
    this.setter = setter;
    assert ((getter == null) || (getter.getMode() == PojoPropertyAccessorNonArgMode.GET));
    this.getter = getter;
    this.constraint = constraint;
  }

  /**
   * This method gets the {@link PojoPropertyAccessorOneArg accessor} used to
   * set the value of the according {@link net.sf.mmm.util.cli.api.CliOption
   * option} or {@link net.sf.mmm.util.cli.api.CliArgument argument}.
   * 
   * @return the {@link PojoPropertyAccessorOneArg setter}.
   */
  public PojoPropertyAccessorOneArg getSetter() {

    return this.setter;
  }

  /**
   * This method gets the {@link PojoPropertyAccessorOneArg accessor} used to
   * get the value of the according {@link net.sf.mmm.util.cli.api.CliOption
   * option} or {@link net.sf.mmm.util.cli.api.CliArgument argument}.
   * 
   * @return the {@link PojoPropertyAccessorNonArg getter} or <code>null</code>
   *         if NOT available.
   */
  public PojoPropertyAccessorNonArg getGetter() {

    return this.getter;
  }

  /**
   * This method gets the optional constraint-annotation.
   * 
   * @see net.sf.mmm.util.cli.api.CliConstraintCollection
   * @see net.sf.mmm.util.cli.api.CliConstraintNumber
   * @see net.sf.mmm.util.cli.api.CliConstraintFile
   * 
   * @return the constraint or <code>null</code> for no constraint.
   */
  public Annotation getConstraint() {

    return this.constraint;
  }

  /**
   * This method gets the {@link #getConstraint() constraint} in a type-safe
   * way.
   * 
   * @param <A> is the generic type of the requested annotation.
   * @param annotationClass is the expected type of the annotation.
   * @return the constraint or <code>null</code> for no constraint.
   */
  public <A extends Annotation> A getConstraint(Class<A> annotationClass) {

    NlsNullPointerException.checkNotNull(Class.class, annotationClass);
    if (this.constraint == null) {
      return null;
    }
    try {
      return annotationClass.cast(this.constraint);
    } catch (ClassCastException e) {
      throw new NlsClassCastException(e, this.constraint, annotationClass);
    }
  }

  /**
   * This method gets the name of the {@link net.sf.mmm.util.cli.api.CliOption
   * option} or {@link net.sf.mmm.util.cli.api.CliArgument argument}.
   * 
   * @return the name of this parameter.
   */
  protected abstract String getName();

  /**
   * This method gets the annotation with the metadata of the
   * {@link net.sf.mmm.util.cli.api.CliOption option} or
   * {@link net.sf.mmm.util.cli.api.CliArgument argument}.
   * 
   * @return the {@link Annotation}.
   */
  protected abstract Annotation getAnnotation();

  /**
   * This method determines if the
   * {@link PojoPropertyAccessorOneArg#getPropertyType() property-type} of the
   * {@link #getSetter() setter} is a container-type (an array,
   * {@link Collection} or {@link Map}).
   * 
   * @see CliValueContainer#isArrayMapOrCollection()
   * 
   * @return <code>true</code> if the
   *         {@link PojoPropertyAccessorOneArg#getPropertyType() property-type}
   *         is a container-type.
   */
  public boolean isArrayMapOrCollection() {

    Class<?> propertyClass = this.setter.getPropertyClass();
    return isArrayMapOrCollection(propertyClass);
  }

  /**
   * This method determines if the given <code>type</code> is a container-type
   * (an array, {@link Collection} or {@link Map}).
   * 
   * @see CliValueContainer#isArrayMapOrCollection()
   * 
   * @param type is the {@link Class} to check.
   * @return <code>true</code> if the given <code>type</code> is a
   *         container-type.
   */
  protected static boolean isArrayMapOrCollection(Class<?> type) {

    if (type.isArray()) {
      return true;
    } else if (Collection.class.isAssignableFrom(type)) {
      return true;
    } else if (Map.class.isAssignableFrom(type)) {
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(this.setter.getDeclaringClass().getSimpleName());
    sb.append('.');
    sb.append(this.setter.getName());
    sb.append('@');
    sb.append(CliOption.class.getSimpleName());
    sb.append("(name=\"");
    sb.append(getName());
    sb.append("\")");
    return sb.toString();
  }

}
