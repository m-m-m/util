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

  /**
   * The constructor.
   * 
   * @param parameterContainer is the {@link #getParameterContainer()
   *        parameter-container}.
   * @param cliState is the {@link #getCliState() state}.
   * @param configuraiton is the {@link #getConfiguration() configuration}.
   * @param logger is the {@link #getLogger() logger}.
   */
  public CliValueContainerArray(CliParameterContainer parameterContainer, CliState cliState,
      CliParserConfiguration configuraiton, Logger logger) {

    super(parameterContainer, cliState, configuraiton, logger, new ArrayList<Object>());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getValue() {

    Class<?> componentType = getParameterContainer().getSetter().getPropertyClass()
        .getComponentType();
    return getConfiguration().getCollectionReflectionUtil().toArray(getCollection(), componentType);
  }
}
