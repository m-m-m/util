/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is the test-case for {@link StringUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringUtilTest {

  @Test
  public void testEmpty() {

    assertTrue(StringUtil.isEmpty(null));
    assertTrue(StringUtil.isEmpty(""));
    assertTrue(StringUtil.isEmpty("\t"));
    assertTrue(StringUtil.isEmpty("\n"));
    assertTrue(StringUtil.isEmpty("\r"));
    assertTrue(StringUtil.isEmpty(" "));
    assertTrue(StringUtil.isEmpty("\t\n\r \r\n"));
    assertFalse(StringUtil.isEmpty("a"));
    assertFalse(StringUtil.isEmpty(" a "));
    assertFalse(StringUtil.isEmpty(" ", false));
  }

  @Test
  public void testCamlCase() {

    assertEquals("abc", StringUtil.toCamlCase("abc"));
    assertEquals("aaBbCc", StringUtil.toCamlCase("aa-bb-cc"));
    assertEquals("aaBbCc", StringUtil.toCamlCase("aa bb_cc"));
    assertEquals("aaBb", StringUtil.toCamlCase("aa -_bb_ "));
  }

}
