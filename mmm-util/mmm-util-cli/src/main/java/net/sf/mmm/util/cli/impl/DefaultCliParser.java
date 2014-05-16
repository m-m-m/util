/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.impl;

import net.sf.mmm.util.cli.base.AbstractCliParser;
import net.sf.mmm.util.cli.base.CliParserDependencies;
import net.sf.mmm.util.cli.base.CliState;

/**
 * This is the default implementation of the {@link net.sf.mmm.util.cli.api.CliParser} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DefaultCliParser extends AbstractCliParser {

  /**
   * The constructor.
   *
   * @param state is the {@link #getState() state}.
   * @param cliState is the {@link CliState}.
   * @param dependencies are the {@link CliParserDependencies}.
   */
  public DefaultCliParser(Object state, CliState cliState, CliParserDependencies dependencies) {

    super(state, cliState, dependencies);
  }

}
