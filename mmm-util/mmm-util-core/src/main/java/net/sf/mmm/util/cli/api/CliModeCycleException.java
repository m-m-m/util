/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link CliModeCycleException} is thrown if a
 * {@link net.sf.mmm.util.cli.api.CliMode} has a cyclic
 * {@link net.sf.mmm.util.cli.api.CliMode#parentIds() dependency}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliModeCycleException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = 8888925901313417066L;

  /**
   * Key for the {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String)
   * argument} {@value}.
   */
  public static final String KEY_CYCLE = "cycle";

  /**
   * The constructor.
   * 
   * @param cycle is the description of the cycle.
   */
  public CliModeCycleException(Object cycle) {

    super(NlsBundleUtilCore.ERR_CLI_MODE_CYCLE, toMap(KEY_CYCLE, cycle));
  }

}
