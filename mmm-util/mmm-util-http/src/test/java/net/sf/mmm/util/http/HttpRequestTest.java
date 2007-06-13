/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * This is a {@link TestCase test-case} for {@link HttpRequest}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class HttpRequestTest extends AbstractHttpMessageTest {

  /**
   * The constructor.
   */
  public HttpRequestTest() {

    super();
  }

  @Test
  public void testRequest() {

    checkMessage(new HttpRequest());
  }

}
