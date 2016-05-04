/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

/**
 * A {@link CliModeUndefinedException} is thrown if a {@link CliOption} or {@link CliArgument} defines a
 * {@link CliOption#mode() mode} that is NOT {@link CliMode defined}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliModeUndefinedException extends CliException {

  private static final long serialVersionUID = 6430569870631737026L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ModeUndef";

  /**
   * The constructor.
   *
   * @param mode is the undefined mode.
   * @param annotation is the object representing the {@link CliOption} or {@link CliArgument} that used the undefined
   *        mode.
   */
  public CliModeUndefinedException(String mode, Object annotation) {

    super(createBundle().errorCliModeUndefined(mode, annotation));
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

  @Override
  public boolean isTechnical() {

    return true;
  }

}
