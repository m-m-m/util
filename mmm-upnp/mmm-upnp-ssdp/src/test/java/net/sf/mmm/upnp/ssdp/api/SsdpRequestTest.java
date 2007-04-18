/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.upnp.ssdp.api;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import org.junit.Test;

import net.sf.mmm.util.http.HttpMessage;
import net.sf.mmm.util.http.HttpParser;
import net.sf.mmm.util.http.HttpRequest;

import junit.framework.TestCase;

/**
 * This is a {@link TestCase test-case} for {@link SsdpRequest}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class SsdpRequestTest extends TestCase {

  @Test
  public void testPingPong() throws Exception {

    SsdpRequest ping = new SsdpRequest();
    ping.initializeDefaults();
    ping.setNotificationType(SsdpRequest.UPNP_ROOT_DEVICE);
    String usn = SsdpRequest.PREFIX_UUID + "device-UUID::" + SsdpRequest.UPNP_ROOT_DEVICE;
    ping.setUniqueServiceName(usn);
    String productName = "m-m-m";
    String productVersion = "1.0";
    ping.setServerProduct(productName, productVersion);
    String msg = ping.toString();
    ByteArrayInputStream bais = new ByteArrayInputStream(msg.getBytes());
    SsdpRequest pong = new SsdpRequest();
    HttpParser.parseRequest(bais, pong);
    assertEquals(HttpRequest.METHOD_NOTIFY, pong.getMethod());
    assertEquals(SsdpRequest.SSDP_URI, pong.getUri());
    assertEquals(SsdpRequest.NTS_SSDP_ALIVE, pong.getNotificationSubType());
    assertEquals(SsdpRequest.MULTICAST_ADDRESS + ":" + SsdpRequest.MULTICAST_PORT, pong
        .getHeaderProperty(HttpMessage.HEADER_PROPERTY_HOST));
    assertEquals(SsdpRequest.UPNP_ROOT_DEVICE, pong.getNotificationType());
    assertEquals(usn, pong.getUniqueServiceName());
    assertEquals(productName, pong.getServerProductName());
    assertEquals(productVersion, pong.getServerProductVersion());
    assertEquals(SsdpRequest.MAX_AGE_DEFAULT, pong.getCacheControlMaxAge());
  }

}
