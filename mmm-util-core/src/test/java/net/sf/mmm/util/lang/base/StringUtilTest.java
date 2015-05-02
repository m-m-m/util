/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.value.impl.ValueConverterToNumber;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link StringUtilImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringUtilTest extends Assert {

  public StringUtil getStringUtil() {

    return StringUtilImpl.getInstance();
  }

  protected ValueConverterToNumber getValueConverterToNumber() {

    ValueConverterToNumber converter = new ValueConverterToNumber();
    converter.initialize();
    return converter;
  }

  @Test
  public void testIsEmpty() {

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

  /**
   * Test of {@link StringUtil#isAllUpperCase(String)}
   */
  @Test
  public void testIsAllUpperCase() {

    assertTrue(getStringUtil().isAllUpperCase("UPPER_CASE"));
    assertFalse(getStringUtil().isAllUpperCase("lower_case"));
    assertFalse(getStringUtil().isAllUpperCase("CamlCase"));
  }

  /**
   * Test of {@link StringUtil#isAllLowerCase(String)}
   */
  @Test
  public void testIsAllLowerCase() {

    assertFalse(getStringUtil().isAllLowerCase("UPPER_CASE"));
    assertTrue(getStringUtil().isAllLowerCase("lower_case"));
    assertFalse(getStringUtil().isAllLowerCase("CamlCase"));
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

  @Test
  public void testToSeparatedString() {

    Collection<Object> collection = new ArrayList<Object>();
    collection.add("abc;");
    collection.add("a,b,c");
    collection.add(Integer.valueOf(123));
    collection.add(Character.valueOf('\''));

    StringSyntaxBean syntax = new StringSyntaxBean('\\');
    assertEquals("abc;,a\\,b\\,c,123,'", getStringUtil().toSeparatedString(collection, ",", syntax));

    syntax.setQuote('\'');
    assertEquals("'abc;','a,b,c','123','\\''", getStringUtil().toSeparatedString(collection, ",", syntax));

    syntax.setQuoteStart('[');
    syntax.setQuoteEnd(']');
    assertEquals("[abc;]; [a,b,c]; [123]; [']", getStringUtil().toSeparatedString(collection, "; ", syntax));
  }

  @Test
  public void testFromSeparatedString() {

    StringSyntaxBean syntax = new StringSyntaxBean('\\');
    List<String> list = getStringUtil().fromSeparatedString("a,bc,d,ef", ",", syntax);
    assertEquals(4, list.size());
    assertEquals("a", list.get(0));
    assertEquals("bc", list.get(1));
    assertEquals("d", list.get(2));
    assertEquals("ef", list.get(3));

    List<Integer> collection = new ArrayList<Integer>();
    ValueConverterToNumber converter = getValueConverterToNumber();
    getStringUtil().fromSeparatedString("1; 42; 3; 99", "; ", syntax, collection, converter, Integer.class);
    assertEquals(4, collection.size());
    assertEquals(Integer.valueOf(1), collection.get(0));
    assertEquals(Integer.valueOf(42), collection.get(1));
    assertEquals(Integer.valueOf(3), collection.get(2));
    assertEquals(Integer.valueOf(99), collection.get(3));
  }
}
