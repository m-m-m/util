/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import junit.framework.TestCase;

/**
 * This is a {@link TestCase test-case} for {@link AbstractHttpMessage}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public abstract class AbstractHttpMessageTest {

  /**
   * The constructor.
   */
  public AbstractHttpMessageTest() {

    super();
  }

  public void checkMessage(AbstractHttpMessage message) {

    int maxage = 1800;
    String ccStart = "amax-age=12345; max-ageing=42";
    // message.setHeaderProperty(AbstractHttpMessage.HEADER_PROPERTY_CACHE_CONTROL, ccStart);
    // message.setCacheControlMaxAge(maxage);
    // String ccMain = ccStart + ",max-age=" + maxage;
    // assertEquals(ccMain, message.getHeaderProperty(AbstractHttpMessage.HEADER_PROPERTY_CACHE_CONTROL));
    // message.appendHeaderProperty(AbstractHttpMessage.HEADER_PROPERTY_CACHE_CONTROL, "foo-bar", "; ");
    // assertEquals(ccMain + "; foo-bar", message.getHeaderProperty(AbstractHttpMessage.HEADER_PROPERTY_CACHE_CONTROL));
    // assertEquals(maxage, message.getCacheControlMaxAge());
  }
}
