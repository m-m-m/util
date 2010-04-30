/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;

/**
 * This is a container for a {@link CliOption} together with additional
 * associated information.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliOptionContainer {

  /** @see #getOption() */
  private final CliOption option;

  /** @see #getSetter() */
  private final PojoPropertyAccessorOneArg setter;

  /**
   * The constructor.
   * 
   * @param option is the {@link #getOption() option}.
   * @param setter is the {@link #getSetter() setter}.
   */
  public CliOptionContainer(CliOption option, PojoPropertyAccessorOneArg setter) {

    super();
    assert (setter.getMode() == PojoPropertyAccessorOneArgMode.SET);
    this.option = option;
    this.setter = setter;
  }

  /**
   * This method gets the actual {@link CliOption}.
   * 
   * @return the {@link CliOption}.
   */
  public CliOption getOption() {

    return this.option;
  }

  /**
   * This method gets the {@link PojoPropertyAccessorOneArg accessor} used to
   * set the {@link CliOption}.
   * 
   * @return the {@link PojoPropertyAccessorOneArg setter}.
   */
  public PojoPropertyAccessorOneArg getSetter() {

    return this.setter;
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
    sb.append(this.option.name());
    sb.append("\")");
    return sb.toString();
  }

}
