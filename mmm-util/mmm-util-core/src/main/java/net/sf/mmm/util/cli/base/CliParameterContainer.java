/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.cli.api.CliOption;
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

  /**
   * The constructor.
   * 
   * @param setter is the {@link #getSetter() setter}.
   */
  public CliParameterContainer(PojoPropertyAccessorOneArg setter) {

    super();
    assert (setter.getMode() == PojoPropertyAccessorOneArgMode.SET);
    this.setter = setter;
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

  //
  // /**
  // * This method determines if the
  // * {@link PojoPropertyAccessorOneArg#getPropertyType() property-type} of the
  // * {@link #getSetter() setter} is an array, {@link Collection} or {@link
  // Map}.
  // *
  // * @return
  // */
  // public boolean isContainerType() {
  //
  // }

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
