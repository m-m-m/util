/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

import net.sf.mmm.util.cli.api.CliContainerStyle;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is the abstract base class for a container with the metadata of a CLI-parameter. A parameter is either
 * an {@link net.sf.mmm.util.cli.api.CliOption option} or an {@link net.sf.mmm.util.cli.api.CliArgument
 * argument}.
 *
 * @see CliArgumentContainer
 * @see CliOptionContainer
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class CliParameterContainer {

  private  final PojoPropertyAccessorOneArg setter;

  private  final PojoPropertyAccessorNonArg getter;

  private  final ValueValidator<?> validator;

  /**
   * The constructor.
   *
   * @param setter is the {@link #getSetter() setter}.
   * @param getter is the {@link #getGetter() getter}.
   * @param validator is the {@link #getValidator() validator}.
   */
  public CliParameterContainer(PojoPropertyAccessorOneArg setter, PojoPropertyAccessorNonArg getter,
      ValueValidator<?> validator) {

    super();
    assert (setter.getMode() == PojoPropertyAccessorOneArgMode.SET);
    this.setter = setter;
    assert ((getter == null) || (getter.getMode() == PojoPropertyAccessorNonArgMode.GET));
    this.getter = getter;
    this.validator = validator;
  }

  /**
   * This method gets the {@link PojoPropertyAccessorOneArg accessor} used to set the value of the according
   * {@link net.sf.mmm.util.cli.api.CliOption option} or {@link net.sf.mmm.util.cli.api.CliArgument argument}.
   *
   * @return the {@link PojoPropertyAccessorOneArg setter}.
   */
  public PojoPropertyAccessorOneArg getSetter() {

    return this.setter;
  }

  /**
   * This method gets the {@link PojoPropertyAccessorOneArg accessor} used to get the value of the according
   * {@link net.sf.mmm.util.cli.api.CliOption option} or {@link net.sf.mmm.util.cli.api.CliArgument argument}.
   *
   * @return the {@link PojoPropertyAccessorNonArg getter} or {@code null} if NOT available.
   */
  public PojoPropertyAccessorNonArg getGetter() {

    return this.getter;
  }

  /**
   * This method gets the name of the {@link net.sf.mmm.util.cli.api.CliOption option} or
   * {@link net.sf.mmm.util.cli.api.CliArgument argument}.
   *
   * @return the name of this parameter.
   */
  protected abstract String getName();

  /**
   * This method gets the annotation with the metadata of the {@link net.sf.mmm.util.cli.api.CliOption option}
   * or {@link net.sf.mmm.util.cli.api.CliArgument argument}.
   *
   * @return the {@link Annotation}.
   */
  protected abstract Annotation getParameterAnnotation();

  /**
   * This method gets the {@link ValueValidator}.
   *
   * @return the validator.
   */
  public ValueValidator<?> getValidator() {

    return this.validator;
  }

  /**
   * This method determines if the {@link PojoPropertyAccessorOneArg#getPropertyType() property-type} of the
   * {@link #getSetter() setter} is a container-type (an array, {@link Collection} or {@link Map}).
   *
   * @see CliValueContainer#isArrayMapOrCollection()
   *
   * @return {@code true} if the {@link PojoPropertyAccessorOneArg#getPropertyType() property-type} is a
   *         container-type.
   */
  public boolean isArrayMapOrCollection() {

    Class<?> propertyClass = this.setter.getPropertyClass();
    return isArrayMapOrCollection(propertyClass);
  }

  /**
   * This method determines if the given {@code type} is a container-type (an array, {@link Collection}
   * or {@link Map}).
   *
   * @see CliValueContainer#isArrayMapOrCollection()
   *
   * @param type is the {@link Class} to check.
   * @return {@code true} if the given {@code type} is a container-type.
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

  /**
   * This method gets the {@link CliContainerStyle style} of this parameter. If the style of the
   * parameter-annotation ( {@link net.sf.mmm.util.cli.api.CliArgument#containerStyle()} or
   * {@link net.sf.mmm.util.cli.api.CliOption#containerStyle()}) is {@link CliContainerStyle#DEFAULT} this
   * method will return {@link net.sf.mmm.util.cli.api.CliStyle#containerStyle()}.
   *
   * @param cliStyle TODO
   *
   * @return the {@link CliContainerStyle style} for this parameter.
   */
  public abstract CliContainerStyle getContainerStyle(CliStyle cliStyle);
}
