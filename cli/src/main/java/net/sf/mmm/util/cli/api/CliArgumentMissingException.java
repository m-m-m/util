/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;


/**
 * A {@link CliArgumentMissingException} is thrown if a {@link CliArgument#required() required}
 * {@link CliArgument argument} is missing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliArgumentMissingException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = -3181963192632167209L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "MissingArg";

  /**
   * The constructor.
   *
   * @param argument is the {@link CliArgument#name() name} of the required {@link CliArgument argument}.
   * @param mode is {@link CliModeObject#getTitle() title} of the activated {@link CliMode mode}.
   */
  public CliArgumentMissingException(String argument, String mode) {

    super(createBundle().errorCliArgumentMissing(argument, mode));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
