/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;


/**
 * A {@link CliOptionAndArgumentAnnotationException} is thrown if a property is annotated with both
 * {@link CliOption} and {@link CliArgument}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliOptionAndArgumentAnnotationException extends CliException {

  private static final long serialVersionUID = -3589470378509687716L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "Opt+Arg";

  /**
   * The constructor.
   *
   * @param property is the according property.
   */
  public CliOptionAndArgumentAnnotationException(String property) {

    super(createBundle().errorCliOptionAndArgumentAnnotation(property));
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
