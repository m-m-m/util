/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.upnp;

import net.sf.mmm.util.http.api.HttpVersion;
import net.sf.mmm.util.version.base.AbstractNameVersion;

/**
 * This class represents a {@link HttpVersion} what is a particular version of the protocol
 * <a href="http://upnp.org/specs/">UPnP (Universal Plug and Play)</a>.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class UpnpVersion extends AbstractNameVersion {

  /**
   * The UPnP version <a href="http://www.upnp.org/specs/arch/UPnP-arch-DeviceArchitecture-v1.0-20080424.pdf">1.0</a>.
   */
  public static final UpnpVersion UPNP_1_0 = new UpnpVersion("1.0");

  /** The UPnP version <a href="http://upnp.org/specs/arch/UPnP-arch-DeviceArchitecture-v1.1.pdf">1.1</a>. */
  public static final UpnpVersion UPNP_1_1 = new UpnpVersion("1.1");

  private static final String UPNP = "UPnP";

  /**
   * The constructor.
   *
   * @param version the {@link #getVersion() version}.
   */
  private UpnpVersion(String version) {
    super(version);
  }

  @Override
  public String getName() {

    return UPNP;
  }

}
