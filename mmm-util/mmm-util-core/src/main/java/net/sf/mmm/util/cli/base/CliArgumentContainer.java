/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;

/**
 * This is a container for a {@link CliArgument} together with additional
 * associated information.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliArgumentContainer {

  /** @see #getArgument() */
  private final CliArgument argument;

  /** @see #getSetter() */
  private final PojoPropertyAccessorOneArg setter;

  /**
   * The constructor.
   * 
   * @param argument is the {@link #getArgument() argument}.
   * @param setter is the {@link #getSetter() setter}.
   */
  public CliArgumentContainer(CliArgument argument, PojoPropertyAccessorOneArg setter) {

    super();
    assert (setter.getMode() == PojoPropertyAccessorOneArgMode.SET);
    this.argument = argument;
    this.setter = setter;
  }

  /**
   * This method gets the actual {@link CliArgument}.
   * 
   * @return the {@link CliArgument}.
   */
  public CliArgument getArgument() {

    return this.argument;
  }

  /**
   * This method gets the {@link PojoPropertyAccessorOneArg accessor} used to
   * set the {@link CliArgument}.
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
    sb.append(CliArgument.class.getSimpleName());
    sb.append("(name=\"");
    sb.append(this.argument.name());
    sb.append("\",index=");
    sb.append(this.argument.index());
    sb.append(')');
    return sb.toString();
  }

}
