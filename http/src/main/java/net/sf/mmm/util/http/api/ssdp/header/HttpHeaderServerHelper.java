/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.ssdp.header;

import net.sf.mmm.util.http.api.header.AbstractHttpHeader;
import net.sf.mmm.util.http.api.header.HttpResponseHeader;
import net.sf.mmm.util.http.api.upnp.UpnpVersion;
import net.sf.mmm.util.lang.api.SystemInformation;
import net.sf.mmm.util.lang.base.SystemUtilImpl;
import net.sf.mmm.util.version.api.NameVersion;
import net.sf.mmm.util.version.base.GenericNameVersion;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 8.4.0
 */
public final class HttpHeaderServerHelper {

  private static final SystemInformation SYSTEM_INFO = SystemUtilImpl.getInstance().getSystemInformation();

  private static final GenericNameVersion OS_INFO = new GenericNameVersion(SYSTEM_INFO.getSystemName(), SYSTEM_INFO.getSystemVersion());

  private static final NameVersion DEFAULT_PRODUCT = new GenericNameVersion("mmm", "1.0");

  private HttpHeaderServerHelper() {}

  public static AbstractHttpHeader of() {

    return of(UpnpVersion.UPNP_1_1);
  }

  public static AbstractHttpHeader of(UpnpVersion upnpVersion) {

    return of(upnpVersion, null);
  }

  public static AbstractHttpHeader of(UpnpVersion upnpVersion, NameVersion productInfo) {

    return of(OS_INFO, upnpVersion, productInfo);
  }

  public static AbstractHttpHeader of(NameVersion osInfo, UpnpVersion upnpVersion, NameVersion productInfo) {

    NameVersion product = productInfo;
    if (product == null) {
      product = DEFAULT_PRODUCT;
    }
    String server = GenericNameVersion.toString(osInfo, upnpVersion, product);
    return AbstractHttpHeader.of(HttpResponseHeader.HEADER_SERVER, server);
  }

}
