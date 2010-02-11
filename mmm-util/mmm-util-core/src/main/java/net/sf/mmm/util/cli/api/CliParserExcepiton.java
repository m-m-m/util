/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliParserExcepiton extends CliException {

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param type is the class reflecting the
   *        {@link net.sf.mmm.util.cli.base.CliState#getState() state-object}.
   */
  public CliParserExcepiton(Exception nested, Class<?> type) {

    super(nested, NlsBundleUtilCore.ERR_CLI_PARSER, toMap(KEY_TYPE, type));
  }

}
