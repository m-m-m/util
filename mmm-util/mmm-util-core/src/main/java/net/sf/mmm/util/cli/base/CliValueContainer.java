/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.cli.api.CliStyle;

import org.slf4j.Logger;

/**
 * A {@link CliValueContainer} is a simple container for the {@link #getValue()
 * value} of a {@link net.sf.mmm.util.cli.api.CliOption option} or a
 * {@link net.sf.mmm.util.cli.api.CliArgument argument}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface CliValueContainer {

  /**
   * This method gets the value of a {@link net.sf.mmm.util.cli.api.CliOption
   * option} or a {@link net.sf.mmm.util.cli.api.CliArgument argument}.
   * 
   * @return the value.
   */
  Object getValue();

  /**
   * This method sets the {@link #getValue() value} given as string. For the
   * parameter-types array, collection or map this method may be called multiple
   * times, one for each item.
   * 
   * @param argument is the argument from the commandline containing the value.
   * @param parameterContainer is the {@link CliParameterContainer}.
   * @param cliStyle is the {@link CliState}.
   * @param configuration is the {@link CliParserConfiguration}.
   * @param logger is the {@link Logger} used to log {@link CliStyle} anomalies.
   */
  void setValue(String argument, CliParameterContainer parameterContainer, CliStyle cliStyle,
      CliParserConfiguration configuration, Logger logger);

  /**
   * @return <code>true</code> if this is a container for a value of the type
   *         array, {@link java.util.Collection} or {@link java.util.Map} -
   *         <code>false</code> otherwise.
   */
  boolean isCollection();

}
