/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * This is the abstract base class for a {@link AbstractMain main-program} that
 * has a version.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
@CliMode(id = CliMode.MODE_VERSION, title = NlsBundleUtilCore.INF_MAIN_VERSION)
public abstract class AbstractVersionedMain extends AbstractMain {

  /** */
  @CliOption(name = "--version", aliases = "-v", usage = NlsBundleUtilCore.INF_MAIN_VERSION_USAGE)
  private boolean version;

  /**
   * This method gets the Version of this program.
   * 
   * @return the program-version.
   */
  protected abstract String getVersion();

  /**
   * {@inheritDoc}
   */
  @Override
  protected int run(CliModeObject mode) throws Exception {

    if (this.version) {
      assert (CliMode.MODE_VERSION.equals(mode.getMode().id()));
      getOutputStream().println(getVersion());
      return 0;
    }
    return EXIT_CODE_ILLEGAL_SYNTAX;
  }

}
