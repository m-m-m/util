/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link CliModeUndefinedException} is thrown if a {@link CliOption} or
 * {@link CliArgument} defines a {@link CliOption#mode() mode} that is NOT
 * {@link CliMode defined}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliModeUndefinedException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = 6430569870631737026L;

  /**
   * The constructor.
   * 
   * @param mode is the undefined mode.
   * @param annotation is the object representing the {@link CliOption} or
   *        {@link CliArgument} that used the undefined mode.
   */
  public CliModeUndefinedException(String mode, Object annotation) {

    super(NlsBundleUtilCore.ERR_CLI_MODE_UNDEFINED, toMap(KEY_MODE, mode, KEY_VALUE, annotation));
  }

}
