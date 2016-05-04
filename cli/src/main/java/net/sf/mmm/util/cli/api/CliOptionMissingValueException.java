/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

/**
 * A {@link CliOptionMissingValueException} is thrown if an option requires a value that is missing (no proper value is
 * specified as commandline argument).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliOptionMissingValueException extends CliException {

  private static final long serialVersionUID = 5158277628043461982L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "MissOptValue";

  /**
   * The constructor.
   *
   * @param option is the according {@link CliOption option}.
   */
  public CliOptionMissingValueException(String option) {

    super(createBundle().errorCliOptionMissingValue(option));
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
