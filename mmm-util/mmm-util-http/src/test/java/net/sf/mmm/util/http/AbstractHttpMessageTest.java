/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http;

import junit.framework.TestCase;

/**
 * This is a {@link TestCase test-case} for {@link HttpMessage}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public abstract class AbstractHttpMessageTest extends TestCase {

  /**
   * The constructor
   */
  public AbstractHttpMessageTest() {

    super();
  }

  public void checkMessage(HttpMessage message) {

    int maxage = 1800;
    String ccStart =  "amax-age=12345; max-ageing=42";
    message.setHeaderProperty(HttpMessage.HEADER_PROPERTY_CACHE_CONTROL,
        ccStart);
    message.setCacheControlMaxAge(maxage);
    String ccMain = ccStart + ",max-age=" + maxage;
    assertEquals(ccMain, message.getHeaderProperty(HttpMessage.HEADER_PROPERTY_CACHE_CONTROL));
    message.appendHeaderProperty(HttpMessage.HEADER_PROPERTY_CACHE_CONTROL, "foo-bar", "; ");
    assertEquals(ccMain + "; foo-bar", message.getHeaderProperty(HttpMessage.HEADER_PROPERTY_CACHE_CONTROL));
    assertEquals(maxage, message.getCacheControlMaxAge());
  }
}
