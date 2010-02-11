/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link CliOptionIllegalNameOrAliasException} is thrown if the
 * {@link CliOption#name() name} or {@link CliOption#aliases() alias} of a
 * {@link CliOption} is illegal.<br>
 * Proper values should follow GNU conventions (e.g. "-h" for short option and
 * "--help" for long option).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliOptionIllegalNameOrAliasException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = -2084363234149185718L;

  /**
   * The constructor.
   * 
   * @param nameOrAlias is the illegal {@link CliOption#name() name} or
   *        {@link CliOption#aliases() alias}.
   * @param option is the according {@link CliOption}.
   */
  public CliOptionIllegalNameOrAliasException(String nameOrAlias, CliOption option) {

    super(NlsBundleUtilCore.ERR_CLI_OPTION_NAME_ILLEGAL, toMap(KEY_NAME, nameOrAlias, KEY_OPTION,
        option));
  }

}
