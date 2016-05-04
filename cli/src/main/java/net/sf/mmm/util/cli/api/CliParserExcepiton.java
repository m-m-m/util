/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;


/**
 * A {@link CliParserExcepiton} is thrown if a {@link net.sf.mmm.util.cli.api.CliClass state class} could NOT
 * be parsed.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliParserExcepiton extends CliException {

  private static final long serialVersionUID = 7576087965627428527L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "CliParser";

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param type is the {@link net.sf.mmm.util.cli.base.CliState#getStateClass() state-class}.
   */
  public CliParserExcepiton(Exception nested, Class<?> type) {

    super(nested, createBundle().errorCliParser(type));
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
