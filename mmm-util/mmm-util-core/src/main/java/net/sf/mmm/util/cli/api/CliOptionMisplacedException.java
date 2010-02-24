/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link CliOptionMisplacedException} is thrown if an option is misplaced,
 * meaning that it occurred after the first {@link CliArgument argument} has
 * been detected.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliOptionMisplacedException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = -1935672231264852131L;

  /**
   * The constructor.
   * 
   * @param option is the according {@link CliOption option}.
   */
  public CliOptionMisplacedException(String option) {

    super(NlsBundleUtilCore.ERR_CLI_OPTION_MISPLACED, toMap(KEY_OPTION, option));
  }

}
