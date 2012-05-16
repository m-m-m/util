/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsMessagesBundleUtilCore;

/**
 * A {@link CliArgumentMissingException} is thrown if a {@link CliArgument#required() required}
 * {@link CliArgument argument} is missing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliArgumentMissingException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = -3181963192632167209L;

  /**
   * The constructor.
   * 
   * @param argument is the {@link CliArgument#name() name} of the required {@link CliArgument argument}.
   * @param mode is {@link CliModeObject#getTitle() title} of the activated {@link CliMode mode}.
   */
  public CliArgumentMissingException(String argument, String mode) {

    super(createBundle(NlsMessagesBundleUtilCore.class).errorCliArgumentMissing(argument, mode));
  }

}
