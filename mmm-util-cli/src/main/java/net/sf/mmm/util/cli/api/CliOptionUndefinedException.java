/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;


/**
 * A {@link CliOptionUndefinedException} is thrown if an {@link CliOption option} is given as
 * commandline-argument if NOT defined.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliOptionUndefinedException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = 7069859410875864942L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "UndefOpt";

  /**
   * The constructor.
   *
   * @param option is the according {@link CliOption option}.
   */
  public CliOptionUndefinedException(String option) {

    super(createBundle().errorCliOptionUndefined(option));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
