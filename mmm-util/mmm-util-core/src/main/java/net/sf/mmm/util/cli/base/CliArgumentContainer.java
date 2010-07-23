/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.component.api.InitializationState;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
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

  /** @see #getState() */
  private InitializationState state;

  /**
   * The constructor.
   * 
   * @param argument is the {@link #getArgument() argument}.
   * @param setter is the {@link #getSetter() setter}.
   * @param getter is the {@link #getGetter() getter}
   */
  public CliArgumentContainer(CliArgument argument, PojoPropertyAccessorOneArg setter,
      PojoPropertyAccessorNonArg getter) {

    super(setter, getter);
    this.argument = argument;
    this.state = InitializationState.UNINITIALIZED;
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

  /**
   * This method gets the {@link CliArgument#id() ID} of the {@link CliArgument
   * argument}.
   * 
   * @return the {@link CliArgument#id() ID} or the {@link CliArgument#name()
   *         name} if ID is not set.
   */
  public String getId() {

    String id = this.argument.id();
    if ((id == null) || (id.length() == 0)) {
      id = this.argument.name();
    }
    return id;
  }

  /**
   * @return the state
   */
  public InitializationState getState() {

    return this.state;
  }

  /**
   * @param state is the state to set
   */
  public void setState(InitializationState state) {

    this.state = state;
  }

}
