/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link CliOptionMissingValueException} is thrown if an option requires a
 * value that is missing (no proper value is specified as commandline argument).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliOptionMissingValueException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5158277628043461982L;

  /**
   * The constructor.
   * 
   * @param option is the according {@link CliOption option}.
   */
  public CliOptionMissingValueException(String option) {

    super(NlsBundleUtilCore.ERR_CLI_OPTION_MISSING_VALUE, toMap(KEY_OPTION, option));
  }

}
