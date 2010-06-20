/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;

/**
 * This is a container for a {@link CliOption} together with additional
 * associated information.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliOptionContainer extends CliParameterContainer {

  /** @see #getOption() */
  private final CliOption option;

  /**
   * The constructor.
   * 
   * @param setter is the {@link #getSetter() setter}.
   * @param option is the {@link #getOption() option}.
   */
  public CliOptionContainer(CliOption option, PojoPropertyAccessorOneArg setter) {

    super(setter);
    this.option = option;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getName() {

    return this.option.name();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Annotation getAnnotation() {

    return this.option;
  }

  /**
   * This method gets the actual {@link CliOption}.
   * 
   * @return the {@link CliOption}.
   */
  public CliOption getOption() {

    return this.option;
  }

}
