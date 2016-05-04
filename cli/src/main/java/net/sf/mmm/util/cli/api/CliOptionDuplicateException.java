/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;


/**
 * A {@link CliOptionDuplicateException} is thrown if the same {@link CliOption option} occurred multiple
 * times as commandline-argument.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliOptionDuplicateException extends CliException {

  private static final long serialVersionUID = 5934504531477162386L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "DuplOpt";

  /**
   * The constructor.
   *
   * @param option is the according {@link CliOption option}.
   */
  public CliOptionDuplicateException(String option) {

    super(createBundle().errorCliOptionDuplicate(option));
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
