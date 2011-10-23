/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.mmm.util.lang.api.StringUtil;

import org.junit.Test;

/**
 * This is the test-case for {@link StringUtilImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringUtilTest {

  public StringUtil getStringUtil() {

    return StringUtilImpl.getInstance();
  }

  @Test
  public void testEmpty() {

    assertTrue(getStringUtil().isEmpty(null));
    assertTrue(getStringUtil().isEmpty(""));
    assertTrue(getStringUtil().isEmpty("\t"));
    assertTrue(getStringUtil().isEmpty("\n"));
    assertTrue(getStringUtil().isEmpty("\r"));
    assertTrue(getStringUtil().isEmpty(" "));
    assertTrue(getStringUtil().isEmpty("\t\n\r \r\n"));
    assertFalse(getStringUtil().isEmpty("a"));
    assertFalse(getStringUtil().isEmpty(" a "));
    assertFalse(getStringUtil().isEmpty(" ", false));
  }

  @Test
  public void testToCamlCase() {

    assertEquals("abc", getStringUtil().toCamlCase("abc"));
    assertEquals("aaBbCc", getStringUtil().toCamlCase("aa-bb-cc"));
    assertEquals("aaBbCc", getStringUtil().toCamlCase("aa bb_cc"));
    assertEquals("aaBb", getStringUtil().toCamlCase("aa -_bb_ "));
  }

  @Test
  public void testFromCamlCase() {

    assertEquals("abc", getStringUtil().fromCamlCase("abc", ' '));
    assertEquals("foo-bar", getStringUtil().fromCamlCase("FooBar", '-'));
    assertEquals("foo_bar", getStringUtil().fromCamlCase("FOO_BAR", '*'));
    assertEquals("some.word.mix", getStringUtil().fromCamlCase("someWordMix", '.'));
    assertEquals("abbreviations_like_xmlshould_not_be_capitalized",
        getStringUtil().fromCamlCase("AbbreviationsLikeXMLshouldNotBeCapitalized", '_'));
  }

  @Test
  public void testPadNumber() {

    assertEquals("005", getStringUtil().padNumber(5, 3));
    assertEquals("025", getStringUtil().padNumber(25, 3));
    assertEquals("100", getStringUtil().padNumber(100, 3));
    assertEquals("1234", getStringUtil().padNumber(1234, 3));
  }

  @Test
  public void testReplaceSuffixWithCase() {

    assertEquals("foofoo", getStringUtil().replaceSuffixWithCase("foobar", 3, "foo"));
    assertEquals("FOOFOO", getStringUtil().replaceSuffixWithCase("FOOBAR", 3, "foo"));
    assertEquals("FooFoo", getStringUtil().replaceSuffixWithCase("FooBar", 3, "foo"));
    assertEquals("FooBfoo", getStringUtil().replaceSuffixWithCase("FooBar", 2, "foo"));
    assertEquals("FUSS", getStringUtil().replaceSuffixWithCase("FOO", 2, "u\u00df"));
  }

}
