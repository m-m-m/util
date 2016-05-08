/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.impl;

import net.sf.mmm.util.cli.api.CliParser;
import net.sf.mmm.util.cli.base.AbstractCliParserBuilder;
import net.sf.mmm.util.cli.base.CliState;

/**
 * The default implementation of the {@link net.sf.mmm.util.cli.api.CliParserBuilder} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DefaultCliParserBuilder extends AbstractCliParserBuilder {

  /**
   * The constructor.
   */
  public DefaultCliParserBuilder() {

    super();
  }

  @Override
  protected CliParser buildInternal(Object state, CliState cliState) {

    return new DefaultCliParser(state, cliState, this);
  }

}
