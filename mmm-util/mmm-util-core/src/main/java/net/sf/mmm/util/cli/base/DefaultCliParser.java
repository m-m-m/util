/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;


/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.cli.api.CliParser} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class DefaultCliParser extends AbstractCliParser {

  /**
   * The constructor.
   * 
   * @param state is the {@link #getState() state}.
   * @param cliState is the {@link CliState}.
   * @param configuration is the {@link CliParserConfiguration}.
   */
  public DefaultCliParser(Object state, CliState cliState, CliParserConfiguration configuration) {

    super(state, cliState, configuration);
  }

}
