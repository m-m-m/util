/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * A {@link CliOptionIncompatibleModesException} is thrown if two {@link CliOption options} are used together
 * that have incompatible {@link CliOption#mode() modes}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliOptionIncompatibleModesException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1233209808038797353L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "IncompModes";

  /**
   * The constructor.
   * 
   * @param option1 is the first option.
   * @param option2 is the second option.
   */
  public CliOptionIncompatibleModesException(String option1, String option2) {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorCliOptionIncompatibleModes(option1, option2));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
