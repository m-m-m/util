/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;


/**
 * A {@link CliOptionMisplacedException} is thrown if an option is misplaced, meaning that it occurred after
 * the first {@link CliArgument argument} has been detected.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliOptionMisplacedException extends CliException {

  private static final long serialVersionUID = -1935672231264852131L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "MisplOpt";

  /**
   * The constructor.
   *
   * @param option is the according {@link CliOption option}.
   */
  public CliOptionMisplacedException(String option) {

    super(createBundle().errorCliOptionMisplaced(option));
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
