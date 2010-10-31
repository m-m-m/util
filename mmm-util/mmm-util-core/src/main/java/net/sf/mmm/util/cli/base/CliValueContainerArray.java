/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.ArrayList;

import org.slf4j.Logger;

/**
 * This is an implementation of {@link CliValueContainer} for an array. It uses
 * a {@link ArrayList} that is dynamically converted to an array if
 * {@link #getValue()} gets called.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliValueContainerArray extends CliValueContainerCollection {

  /** The array or <code>null</code> if NOT yet set. */
  private Object array;

  /**
   * The constructor.
   * 
   * @param parameterContainer is the {@link #getParameterContainer()
   *        parameter-container}.
   * @param cliState is the {@link #getCliState() state}.
   * @param dependencies are the {@link #getDependencies() dependencies}.
   * @param logger is the {@link #getLogger() logger}.
   */
  public CliValueContainerArray(CliParameterContainer parameterContainer, CliState cliState,
      CliParserDependencies dependencies, Logger logger) {

    super(parameterContainer, cliState, dependencies, logger, new ArrayList<Object>());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setValueInternal(Object containerValue) {

    assert (this.array == null);
    this.array = containerValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getValue() {

    if (this.array == null) {
      Class<?> componentType = getParameterContainer().getSetter().getPropertyClass()
          .getComponentType();
      this.array = getDependencies().getCollectionReflectionUtil().toArray(getCollection(),
          componentType);
    }
    return this.array;
  }
}
