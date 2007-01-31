/* $Id$
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.upnp.ssdp.impl;

import org.junit.Test;

import net.sf.mmm.upnp.ssdp.api.SsdpListener;
import net.sf.mmm.upnp.ssdp.api.SsdpRequest;
import net.sf.mmm.upnp.ssdp.api.SsdpSender;

import junit.framework.TestCase;

/**
 * This is a {@link TestCase test-case} for {@link SsdpSenderImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class SsdpSenderImplTest extends TestCase {

  private static final String USN = SsdpRequest.PREFIX_UUID + "device-UUID::"
      + SsdpRequest.UPNP_ROOT_DEVICE;

  /**
   * The constructor
   */
  public SsdpSenderImplTest() {

    super();
  }

  public static SsdpRequest createRequest() {

    SsdpRequest request = new SsdpRequest();
    request.initializeDefaults();
    // do not confuse the LAN by this test
    request.setNotificationSubType(SsdpRequest.NTS_SSDP_BYEBYE);
    request.setNotificationType(SsdpRequest.UPNP_ROOT_DEVICE);
    request.setUniqueServiceName(USN);
    String productName = "m-m-m";
    String productVersion = "1.0";
    request.setServerProduct(productName, productVersion);
    return request;
  }

  public static void checkRequest(SsdpRequest receivedMessage) {

    assertNotNull(receivedMessage);
    assertEquals(SsdpRequest.NTS_SSDP_BYEBYE, receivedMessage.getNotificationSubType());
    assertEquals(USN, receivedMessage.getUniqueServiceName());

  }

  @Test
  public void testSend() throws Exception {

    SsdpSenderImpl senderImpl = new SsdpSenderImpl();
    senderImpl.initialize();
    SsdpSender sender = senderImpl;
    SsdpRequest request = createRequest();
    // send the message
    sender.send(request);
    senderImpl.dispose();
  }

}
