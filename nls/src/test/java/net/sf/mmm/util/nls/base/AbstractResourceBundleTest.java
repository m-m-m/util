/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

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

    String[] expectedKeys = new String[] { "ERR_TEST", "MSG_TEST", "XYZ_TEST" };
    MyBundle myRB = new MyBundle();
    Set<String> expectedKeySet = new HashSet<String>(expectedKeys.length);
    for (String key : expectedKeys) {
      expectedKeySet.add(key);
      assertEquals(key + ".value", myRB.getString(key));
    }
    Enumeration<String> keys = myRB.getKeys();
    int count = 0;
    while (keys.hasMoreElements()) {
      String key = keys.nextElement();
      assertTrue(expectedKeySet.contains(key));
      count++;
    }
    assertEquals(count, expectedKeySet.size());
  }

  public static class MyBundle extends AbstractResourceBundle {

    public static final String ERR_TEST = "ERR_TEST.value";

    public static final String MSG_TEST = "MSG_TEST.value";

    public static final String XYZ_TEST = "XYZ_TEST.value";

  }
}
