/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.ssdp;

import net.sf.mmm.util.http.api.AbstractHttpResponse;
import net.sf.mmm.util.http.api.HttpResponseStatus;
import net.sf.mmm.util.http.api.header.AbstractHttpHeader;
import net.sf.mmm.util.http.api.header.AbstractHttpHeaders;
import net.sf.mmm.util.http.api.ssdp.header.HttpHeaderServerHelper;
import net.sf.mmm.util.version.api.NameVersion;

/**
 * This is the implementation of {@link SsdpResponse}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class SsdpResponseImpl extends AbstractHttpResponse implements SsdpResponse {

  private final SsdpFactory factory;

  /**
   * The constructor.
   *
   * @param factory
   * @param status
   */
  public SsdpResponseImpl(SsdpFactory factory, HttpResponseStatus status) {
    super(factory.getHttpVersion(), status);
    this.factory = factory;
    product(null);
  }

  /**
   * The constructor.
   *
   * @param factory
   * @param status
   * @param headers
   */
  public SsdpResponseImpl(SsdpFactory factory, HttpResponseStatus status, AbstractHttpHeaders headers) {
    super(factory.getHttpVersion(), status, headers);
    this.factory = factory;
    product(null);
  }

  @Override
  public Ssdp getSsdp() {

    return this.factory;
  }

  @Override
  public SsdpResponse product(NameVersion productInfo) {

    requireMutable();
    AbstractHttpHeader server = HttpHeaderServerHelper.of(this.factory.getUpnpVersion(), productInfo);
    getHeaders().setHeader(server);
    return this;
  }

}
