/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;

/**
 * This is a container for a {@link CliArgument} together with additional
 * associated information.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliArgumentContainer extends CliParameterContainer {

  /** @see #getArgument() */
  private final CliArgument argument;

  /**
   * The constructor.
   * 
   * @param argument is the {@link #getArgument() argument}.
   * @param setter is the {@link #getSetter() setter}.
   */
  public CliArgumentContainer(CliArgument argument, PojoPropertyAccessorOneArg setter) {

    super(setter);
    this.argument = argument;
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
   * {@inheritDoc}
   */
  @Override
  protected Annotation getAnnotation() {

    return this.argument;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getName() {

    return this.argument.name();
  }

}
