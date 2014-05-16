/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;


/**
 * A {@link CliOptionMissingException} is thrown if a {@link CliOption#required() required} {@link CliOption
 * option} is missing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliOptionMissingException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4100754383566756427L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "MissingOpt";

  /**
   * The constructor.
   *
   * @param option is the required {@link CliOption option}.
   * @param mode is {@link CliModeObject#getTitle() title} of the activated {@link CliMode mode}.
   */
  public CliOptionMissingException(String option, String mode) {

    super(createBundle().errorCliOptionMissing(option, mode));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
