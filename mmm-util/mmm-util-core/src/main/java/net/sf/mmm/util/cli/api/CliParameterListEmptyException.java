/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsMessagesBundleUtilCore;

/**
 * A {@link CliParameterListEmptyException} is thrown if {@link CliParser#parseParameters(String...)} is
 * called without a single parameter.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliParameterListEmptyException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5752476229604563280L;

  /**
   * The constructor.
   */
  public CliParameterListEmptyException() {

    super(createBundle(NlsMessagesBundleUtilCore.class).errorCliParameterListEmpty());
  }

}
