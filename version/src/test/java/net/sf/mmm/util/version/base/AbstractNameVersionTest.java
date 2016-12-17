/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.base;

import org.assertj.core.api.Assertions;

/**
 * The test of {@link GenericNameVersionComment}.
 *
 * @author hohwille
 */
public class AbstractNameVersionTest extends Assertions {

  // given

  protected static final String MOZILLA_NAME = "Mozilla";

  protected static final String MOZILLA_VERSION = "5.0";

  protected static final String MOZILLA_COMMENT = "X11; U; Linux i686; it-it";

  protected static final String MOZILLA_NV = MOZILLA_NAME + "/" + MOZILLA_VERSION;

  protected static final String MOZILLA_NVC = MOZILLA_NV + " (" + MOZILLA_COMMENT + ")";

  protected static final String WEBKIT_NAME = "AppleWebKit";

  protected static final String WEBKIT_VERSION = "531.2+";

  protected static final String WEBKIT_COMMENT = "KHTML, like Gecko";

  protected static final String WEBKIT_NV = WEBKIT_NAME + "/" + WEBKIT_VERSION;

  protected static final String WEBKIT_NVC = WEBKIT_NV + " (" + WEBKIT_COMMENT + ")";

  protected static final String VERSION_NAME = "Version";

  protected static final String VERSION_VERSION = "5.0";

  protected static final String VERSION_COMMENT = null;

  protected static final String VERSION_NV = VERSION_NAME + "/" + VERSION_VERSION;

  protected static final String SAFARI_NAME = "Safari";

  protected static final String SAFARI_VERSION = "531.2+";

  protected static final String SAFARI_COMMENT = null;

  protected static final String SAFARI_NV = SAFARI_NAME + "/" + SAFARI_VERSION;

  protected static final String DEBIAN_NAME = "Debian";

  protected static final String DEBIAN_VERSION = "squeeze";

  protected static final String DEBIAN_COMMENT = "2.30.6-1";

  protected static final String DEBIAN_NV = DEBIAN_NAME + "/" + DEBIAN_VERSION;

  protected static final String DEBIAN_NVC = DEBIAN_NV + " (" + DEBIAN_COMMENT + ")";

  protected static final String EPIPHANY_NAME = "Epiphany";

  protected static final String EPIPHANY_VERSION = "2.30.6";

  protected static final String EPIPHANY_COMMENT = null;

  protected static final String EPIPHANY_NV = EPIPHANY_NAME + "/" + EPIPHANY_VERSION;

  /** A real User-Agent string from the wild wild web. */
  protected static final String ALL = MOZILLA_NVC + " " + WEBKIT_NVC + " " + VERSION_NV + " " + SAFARI_NV + " " + DEBIAN_NVC + " " + EPIPHANY_NV;

}
