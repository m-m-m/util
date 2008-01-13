/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Test;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.impl.NlsMessageImpl;
import net.sf.mmm.util.nls.impl.SingleNlsTemplateResolver;

/**
 * This is the test-case for {@link AbstractResourceBundle}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class AbstractResourceBundleTest {

  public AbstractResourceBundleTest() {

    super();
  }

  @Test
  public void testKeys() {

    final MyResourceBundle myRB = new MyResourceBundle();
    Set<String> expectedKeys = new HashSet<String>();
    expectedKeys.add("ERR_NULL");
    expectedKeys.add("MSG_WELCOME");
    expectedKeys.add("MSG_BYE");
    Enumeration<String> keys = myRB.getKeys();
    int count = 0;
    while (keys.hasMoreElements()) {
      String key = keys.nextElement();
      assertTrue(expectedKeys.contains(key));
      count++;
    }
    assertEquals(count, expectedKeys.size());
    NlsTemplateResolver st = new SingleNlsTemplateResolver(myRB);
    NlsMessage msg = new NlsMessageImpl(MyResourceBundle.MSG_WELCOME, "Paul") {};
    assertEquals("Welcome \"Paul\"!", msg.getMessage());
    assertEquals("Willkommen \"Paul\"!", msg.getLocalizedMessage(Locale.GERMAN, st));
  }
}
