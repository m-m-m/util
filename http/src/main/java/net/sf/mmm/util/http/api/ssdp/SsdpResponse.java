/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.ssdp;

import net.sf.mmm.util.http.api.HttpResponse;
import net.sf.mmm.util.version.api.NameVersion;

/**
 * This is the interface for an {@link SsdpResponse}. It is a {@link HttpResponse} received by a client as answer to a
 * {@link SsdpRequest}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface SsdpResponse extends HttpResponse, SsdpMessage {

  @Override
  SsdpResponse product(NameVersion product);

}
