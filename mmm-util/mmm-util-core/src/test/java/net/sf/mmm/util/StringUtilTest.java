/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * This is the test-case for {@link StringUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringUtilTest {

  @Test
  public void testEmpty() {

    assertTrue(StringUtil.getInstance().isEmpty(null));
    assertTrue(StringUtil.getInstance().isEmpty(""));
    assertTrue(StringUtil.getInstance().isEmpty("\t"));
    assertTrue(StringUtil.getInstance().isEmpty("\n"));
    assertTrue(StringUtil.getInstance().isEmpty("\r"));
    assertTrue(StringUtil.getInstance().isEmpty(" "));
    assertTrue(StringUtil.getInstance().isEmpty("\t\n\r \r\n"));
    assertFalse(StringUtil.getInstance().isEmpty("a"));
    assertFalse(StringUtil.getInstance().isEmpty(" a "));
    assertFalse(StringUtil.getInstance().isEmpty(" ", false));
  }

  @Test
  public void testCamlCase() {

    assertEquals("abc", StringUtil.getInstance().toCamlCase("abc"));
    assertEquals("aaBbCc", StringUtil.getInstance().toCamlCase("aa-bb-cc"));
    assertEquals("aaBbCc", StringUtil.getInstance().toCamlCase("aa bb_cc"));
    assertEquals("aaBb", StringUtil.getInstance().toCamlCase("aa -_bb_ "));
  }

  @Test
  public void testReplaceSuffixWithCase() {

    assertEquals("foofoo", StringUtil.getInstance().replaceSuffixWithCase("foobar", 3, "foo"));
    assertEquals("FOOFOO", StringUtil.getInstance().replaceSuffixWithCase("FOOBAR", 3, "foo"));
    assertEquals("FooFoo", StringUtil.getInstance().replaceSuffixWithCase("FooBar", 3, "foo"));
    assertEquals("FooBfoo", StringUtil.getInstance().replaceSuffixWithCase("FooBar", 2, "foo"));
    assertEquals("FUSS", StringUtil.getInstance().replaceSuffixWithCase("FOO", 2, "u\u00df"));
  }

}
