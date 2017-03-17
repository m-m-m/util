/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.ssdp;

import java.net.URI;

import net.sf.mmm.util.http.api.AbstractHttpRequest;
import net.sf.mmm.util.http.api.header.AbstractHttpHeader;
import net.sf.mmm.util.http.api.header.AbstractHttpHeaders;
import net.sf.mmm.util.http.api.ssdp.header.HttpHeaderServerHelper;
import net.sf.mmm.util.version.api.NameVersion;

/**
 * This is the implementation of {@link SsdpRequest}.
 *
 * @author hohwille
 * @since 8.4.0
 */
class SsdpRequestImpl extends AbstractHttpRequest implements SsdpRequest {

  private final SsdpFactory factory;

  /**
   * The constructor.
   *
   * @param version
   * @param method
   * @param uri
   */
  public SsdpRequestImpl(SsdpFactory factory, String method, URI uri) {
    super(factory.getHttpVersion(), method, uri);
    this.factory = factory;
    product(null);
  }

  /**
   * The constructor.
   *
   * @param version
   * @param method
   * @param uri
   * @param headers
   */
  public SsdpRequestImpl(SsdpFactory factory, String method, URI uri, AbstractHttpHeaders headers) {
    super(factory.getHttpVersion(), method, uri, headers);
    this.factory = factory;
    product(null);
  }

  @Override
  public Ssdp getSsdp() {

    return this.factory;
  }

  @Override
  public SsdpRequest product(NameVersion productInfo) {

    requireMutable();
    AbstractHttpHeader server = HttpHeaderServerHelper.of(this.factory.getUpnpVersion(), productInfo);
    getHeaders().setHeader(server);
    return this;
  }

}
