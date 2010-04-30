/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link CliOptionMissingException} is thrown if a
 * {@link CliOption#required() required} {@link CliOption option} is missing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliOptionMissingException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4100754383566756427L;

  /**
   * The constructor.
   * 
   * @param option is the required {@link CliOption option}.
   * @param mode is the activated {@link CliMode mode}.
   */
  public CliOptionMissingException(String option, String mode) {

    super(NlsBundleUtilCore.ERR_CLI_OPTION_MISSING, toMap(KEY_OPTION, option, KEY_MODE, mode));
  }

}
