/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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
public class StringUtilTest {

  public StringUtil getStringUtil() {

    return StringUtilImpl.getInstance();
  }

  protected ValueConverterToNumber getValueConverterToNumber() {

    ValueConverterToNumber converter = new ValueConverterToNumber();
    converter.initialize();
    return converter;
  }

  @Test
  public void testEmpty() {

    Assert.assertTrue(getStringUtil().isEmpty(null));
    Assert.assertTrue(getStringUtil().isEmpty(""));
    Assert.assertTrue(getStringUtil().isEmpty("\t"));
    Assert.assertTrue(getStringUtil().isEmpty("\n"));
    Assert.assertTrue(getStringUtil().isEmpty("\r"));
    Assert.assertTrue(getStringUtil().isEmpty(" "));
    Assert.assertTrue(getStringUtil().isEmpty("\t\n\r \r\n"));
    Assert.assertFalse(getStringUtil().isEmpty("a"));
    Assert.assertFalse(getStringUtil().isEmpty(" a "));
    Assert.assertFalse(getStringUtil().isEmpty(" ", false));
  }

  @Test
  public void testToCamlCase() {

    Assert.assertEquals("abc", getStringUtil().toCamlCase("abc"));
    Assert.assertEquals("aaBbCc", getStringUtil().toCamlCase("aa-bb-cc"));
    Assert.assertEquals("aaBbCc", getStringUtil().toCamlCase("aa bb_cc"));
    Assert.assertEquals("aaBb", getStringUtil().toCamlCase("aa -_bb_ "));
  }

  @Test
  public void testFromCamlCase() {

    Assert.assertEquals("abc", getStringUtil().fromCamlCase("abc", ' '));
    Assert.assertEquals("foo-bar", getStringUtil().fromCamlCase("FooBar", '-'));
    Assert.assertEquals("foo_bar", getStringUtil().fromCamlCase("FOO_BAR", '*'));
    Assert.assertEquals("some.word.mix", getStringUtil().fromCamlCase("someWordMix", '.'));
    Assert.assertEquals("abbreviations_like_xmlshould_not_be_capitalized", getStringUtil()
        .fromCamlCase("AbbreviationsLikeXMLshouldNotBeCapitalized", '_'));
  }

  @Test
  public void testPadNumber() {

    Assert.assertEquals("005", getStringUtil().padNumber(5, 3));
    Assert.assertEquals("025", getStringUtil().padNumber(25, 3));
    Assert.assertEquals("100", getStringUtil().padNumber(100, 3));
    Assert.assertEquals("1234", getStringUtil().padNumber(1234, 3));
  }

  @Test
  public void testReplaceSuffixWithCase() {

    Assert.assertEquals("foofoo", getStringUtil().replaceSuffixWithCase("foobar", 3, "foo"));
    Assert.assertEquals("FOOFOO", getStringUtil().replaceSuffixWithCase("FOOBAR", 3, "foo"));
    Assert.assertEquals("FooFoo", getStringUtil().replaceSuffixWithCase("FooBar", 3, "foo"));
    Assert.assertEquals("FooBfoo", getStringUtil().replaceSuffixWithCase("FooBar", 2, "foo"));
    Assert.assertEquals("FUSS", getStringUtil().replaceSuffixWithCase("FOO", 2, "u\u00df"));
  }

  @Test
  public void testToSeparatedString() {

    Collection<Object> collection = new ArrayList<Object>();
    collection.add("abc;");
    collection.add("a,b,c");
    collection.add(Integer.valueOf(123));
    collection.add(Character.valueOf('\''));

    StringSyntaxBean syntax = new StringSyntaxBean('\\');
    Assert.assertEquals("abc;,a\\,b\\,c,123,'",
        getStringUtil().toSeparatedString(collection, ",", syntax));

    syntax.setQuote('\'');
    Assert.assertEquals("'abc;','a,b,c','123','\\''",
        getStringUtil().toSeparatedString(collection, ",", syntax));

    syntax.setQuoteStart('[');
    syntax.setQuoteEnd(']');
    Assert.assertEquals("[abc;]; [a,b,c]; [123]; [']",
        getStringUtil().toSeparatedString(collection, "; ", syntax));
  }

  @Test
  public void testFromSeparatedString() {

    StringSyntaxBean syntax = new StringSyntaxBean('\\');
    List<String> list = getStringUtil().fromSeparatedString("a,bc,d,ef", ",", syntax);
    Assert.assertEquals(4, list.size());
    Assert.assertEquals("a", list.get(0));
    Assert.assertEquals("bc", list.get(1));
    Assert.assertEquals("d", list.get(2));
    Assert.assertEquals("ef", list.get(3));

    List<Integer> collection = new ArrayList<Integer>();
    ValueConverterToNumber converter = getValueConverterToNumber();
    getStringUtil().fromSeparatedString("1; 42; 3; 99", "; ", syntax, collection, converter,
        Integer.class);
    Assert.assertEquals(4, collection.size());
    Assert.assertEquals(Integer.valueOf(1), collection.get(0));
    Assert.assertEquals(Integer.valueOf(42), collection.get(1));
    Assert.assertEquals(Integer.valueOf(3), collection.get(2));
    Assert.assertEquals(Integer.valueOf(99), collection.get(3));
  }
}
