/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

import net.sf.mmm.util.cli.NlsBundleUtilCliRoot;
import net.sf.mmm.util.reflect.base.ManifestLoader;

/**
 * This is the abstract base class for a {@link AbstractMain main-program} that has a version.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@CliMode(id = CliMode.ID_VERSION, title = NlsBundleUtilCliRoot.INF_MAIN_MODE_VERSION, //
usage = NlsBundleUtilCliRoot.MSG_MAIN_MODE_VERSION_USAGE)
public abstract class AbstractVersionedMain extends AbstractMain {

  /** The {@link #getVersion() version} if NOT available from {@link Manifest}. */
  private static final String SNAPSHOT = "SNAPSHOT";

  /** The option to print the version. */
  @CliOption(name = CliOption.NAME_VERSION, aliases = CliOption.ALIAS_VERSION, //
  usage = NlsBundleUtilCliRoot.MSG_MAIN_OPTION_VERSION_USAGE, mode = CliMode.ID_VERSION, required = true)
  private boolean version;

  /**
   * This method gets the Version of this program.
   *
   * @return the program-version.
   */
  protected String getVersion() {

    Manifest manifest = ManifestLoader.loadManifest(getClass());
    String versionNumber = null;
    if (manifest != null) {
      versionNumber = ManifestLoader.getValue(manifest, Attributes.Name.IMPLEMENTATION_VERSION);
      if (versionNumber == null) {
        versionNumber = ManifestLoader.getValue(manifest, Attributes.Name.SPECIFICATION_VERSION);
      }
    }
    if (versionNumber == null) {
      versionNumber = SNAPSHOT;
    }
    return versionNumber;
  }

  @Override
  protected int run(CliModeObject mode) throws Exception {

    if (this.version) {
      assert (CliMode.ID_VERSION.equals(mode.getId()));
      getStandardOutput().println(getVersion());
      return 0;
    } else if (CliMode.ID_DEFAULT.equals(mode.getId())) {
      return runDefaultMode();
    }
    return EXIT_CODE_ILLEGAL_SYNTAX;
  }

  /**
   * This method is called from {@link #run(CliModeObject)} if the program is executed in
   * {@link CliMode#ID_DEFAULT default} {@link CliModeObject mode}.
   *
   * @return the error-code or {@link #EXIT_CODE_OK} on success.
   * @throws Exception in case of an unexpected error.
   */
  protected abstract int runDefaultMode() throws Exception;

}
