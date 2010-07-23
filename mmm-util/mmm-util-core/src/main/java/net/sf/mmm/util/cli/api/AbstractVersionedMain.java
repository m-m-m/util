/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.reflect.api.Manifest;

/**
 * This is the abstract base class for a {@link AbstractMain main-program} that
 * has a version.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@CliMode(id = CliMode.ID_VERSION, title = NlsBundleUtilCore.INF_MAIN_MODE_VERSION, // 
usage = NlsBundleUtilCore.MSG_MAIN_MODE_VERSION_USAGE)
public abstract class AbstractVersionedMain extends AbstractMain {

  /** The {@link #getVersion() version} if NOT available from {@link Manifest}. */
  private static final String SNAPSHOT = "SNAPSHOT";

  /** The option to print the version. */
  @CliOption(name = CliOption.NAME_VERSION, aliases = CliOption.ALIAS_VERSION, //
  usage = NlsBundleUtilCore.MSG_MAIN_OPTION_VERSION_USAGE, mode = CliMode.ID_VERSION, required = true)
  private boolean version;

  /**
   * This method gets the Version of this program.
   * 
   * @return the program-version.
   */
  protected String getVersion() {

    Manifest manifest = Manifest.load(getClass());
    String versionNumber = null;
    if (manifest != null) {
      versionNumber = manifest.getImplementationVersion();
      if (versionNumber == null) {
        versionNumber = manifest.getSpecificationVersion();
      }
    }
    if (versionNumber == null) {
      versionNumber = SNAPSHOT;
    }
    return versionNumber;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int run(CliModeObject mode) throws Exception {

    if (this.version) {
      assert (CliMode.ID_VERSION.equals(mode.getId()));
      getStandardOutput().println(getVersion());
      return 0;
    }
    return EXIT_CODE_ILLEGAL_SYNTAX;
  }

}
