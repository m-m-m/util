/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

/**
 * A {@link CliClassNoPropertyException} is thrown if a {@link CliClass CLI-class} is illegal because it has no property
 * annotated with {@link CliOption} or {@link CliArgument}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliClassNoPropertyException extends CliException {

  private static final long serialVersionUID = 6430569870631737026L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "CliClsNoProp";

  /**
   * The constructor.
   *
   * @param cliStateClass is the illegal {@link CliClass CLI-class}.
   */
  public CliClassNoPropertyException(Class<?> cliStateClass) {

    super(createBundle().errorCliClassNoProperty(cliStateClass));
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
