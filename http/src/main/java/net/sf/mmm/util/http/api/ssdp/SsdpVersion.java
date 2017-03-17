/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.ssdp;

import net.sf.mmm.util.version.base.AbstractNameVersion;

/**
 * This class represents a {@link SsdpVersion} what is a particular version of the protocol
 * <a href="https://tools.ietf.org/html/draft-cai-ssdp-v1-03">SSDP (Simple Service Discovery Protocol)</a>.
 *
 * @see Ssdp
 * @see net.sf.mmm.util.http.api.upnp.UpnpVersion
 *
 * @author hohwille
 * @since 8.4.0
 */
public class SsdpVersion extends AbstractNameVersion {

  /** The SSDP version <a href="https://tools.ietf.org/html/draft-cai-ssdp-v1-03">1.0</a>. */
  public static final SsdpVersion SSDP_1_0 = new SsdpVersion("1.0");

  /**
   * The constructor.
   *
   * @param version the {@link #getVersion() version}.
   */
  private SsdpVersion(String version) {
    super(version);
  }

  @Override
  public String getName() {

    return "SSDP";
  }

}
