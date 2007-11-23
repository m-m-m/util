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

    assertTrue(StringUtil.INSTANCE.isEmpty(null));
    assertTrue(StringUtil.INSTANCE.isEmpty(""));
    assertTrue(StringUtil.INSTANCE.isEmpty("\t"));
    assertTrue(StringUtil.INSTANCE.isEmpty("\n"));
    assertTrue(StringUtil.INSTANCE.isEmpty("\r"));
    assertTrue(StringUtil.INSTANCE.isEmpty(" "));
    assertTrue(StringUtil.INSTANCE.isEmpty("\t\n\r \r\n"));
    assertFalse(StringUtil.INSTANCE.isEmpty("a"));
    assertFalse(StringUtil.INSTANCE.isEmpty(" a "));
    assertFalse(StringUtil.INSTANCE.isEmpty(" ", false));
  }

  @Test
  public void testCamlCase() {

    assertEquals("abc", StringUtil.INSTANCE.toCamlCase("abc"));
    assertEquals("aaBbCc", StringUtil.INSTANCE.toCamlCase("aa-bb-cc"));
    assertEquals("aaBbCc", StringUtil.INSTANCE.toCamlCase("aa bb_cc"));
    assertEquals("aaBb", StringUtil.INSTANCE.toCamlCase("aa -_bb_ "));
  }

  @Test
  public void testReplaceSuffixWithCase() {

    assertEquals("foofoo", StringUtil.INSTANCE.replaceSuffixWithCase("foobar", 3, "foo"));
    assertEquals("FOOFOO", StringUtil.INSTANCE.replaceSuffixWithCase("FOOBAR", 3, "foo"));
    assertEquals("FooFoo", StringUtil.INSTANCE.replaceSuffixWithCase("FooBar", 3, "foo"));
    assertEquals("FooBfoo", StringUtil.INSTANCE.replaceSuffixWithCase("FooBar", 2, "foo"));
    assertEquals("FUSS", StringUtil.INSTANCE.replaceSuffixWithCase("FOO", 2, "u\u00df"));
  }

}
