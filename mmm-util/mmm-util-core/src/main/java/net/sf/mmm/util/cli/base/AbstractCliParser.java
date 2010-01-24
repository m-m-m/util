/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.cli.api.CliException;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliOutputSettings;
import net.sf.mmm.util.cli.api.CliParser;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public abstract class AbstractCliParser implements CliParser {

  /** @see #getCliState() */
  private final CliState cliState;

  /**
   * The constructor.
   */
  public AbstractCliParser(Object state, PojoDescriptorBuilder descriptorBuilder) {

    super();
    this.cliState = new CliState(state, descriptorBuilder);
  }

  /**
   * This method gets the {@link CliState}.
   * 
   * @return the {@link CliState}.
   */
  protected CliState getCliState() {

    return this.cliState;
  }

  /**
   * {@inheritDoc}
   */
  public CliModeObject parseArguments(String... arguments) throws CliException {

    CliModeObject mode = null;
    for (String arg : arguments) {
      if (arg == null) {
        throw new NlsNullPointerException("argument");
      }
      CliOptionContainer container = this.cliState.getOption(arg);
      if (container != null) {

      } else {

      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public void printHelp(Appendable target) {

  // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  public void printHelp(Appendable target, CliOutputSettings settings) {

  // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  public void printHelp(Appendable target, boolean omitMainUsage) {

  // TODO Auto-generated method stub

  }

}
