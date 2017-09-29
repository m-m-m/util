/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ValueConverter;

/**
 * This is the test-case for {@link StringUtilImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringUtilTest extends Assertions {

  public StringUtil getStringUtil() {

    return StringUtilImpl.getInstance();
  }

  @Test
  public void testIsEmpty() {

    assertThat(getStringUtil().isEmpty(null)).isTrue();
    assertThat(getStringUtil().isEmpty("")).isTrue();
    assertThat(getStringUtil().isEmpty("\t")).isTrue();
    assertThat(getStringUtil().isEmpty("\n")).isTrue();
    assertThat(getStringUtil().isEmpty("\r")).isTrue();
    assertThat(getStringUtil().isEmpty(" ")).isTrue();
    assertThat(getStringUtil().isEmpty("\t\n\r \r\n")).isTrue();
    assertThat(getStringUtil().isEmpty("a")).isFalse();
    assertThat(getStringUtil().isEmpty(" a ")).isFalse();
    assertThat(getStringUtil().isEmpty(" ", false)).isFalse();
  }

  /**
   * Test of {@link StringUtil#isAllUpperCase(String)}
   */
  @Test
  public void testIsAllUpperCase() {

    assertThat(getStringUtil().isAllUpperCase("UPPER_CASE")).isTrue();
    assertThat(getStringUtil().isAllUpperCase("lower_case")).isFalse();
    assertThat(getStringUtil().isAllUpperCase("CamlCase")).isFalse();
  }

  /**
   * Test of {@link StringUtil#isAllLowerCase(String)}
   */
  @Test
  public void testIsAllLowerCase() {

    assertThat(getStringUtil().isAllLowerCase("UPPER_CASE")).isFalse();
    assertThat(getStringUtil().isAllLowerCase("lower_case")).isTrue();
    assertThat(getStringUtil().isAllLowerCase("CamlCase")).isFalse();
  }

  @Test
  public void testToCamlCase() {

    assertThat(getStringUtil().toCamlCase("abc")).isEqualTo("abc");
    assertThat(getStringUtil().toCamlCase("aa-bb-cc")).isEqualTo("aaBbCc");
    assertThat(getStringUtil().toCamlCase("aa bb_cc")).isEqualTo("aaBbCc");
    assertThat(getStringUtil().toCamlCase("aa -_bb_ ")).isEqualTo("aaBb");
  }

  @Test
  public void testFromCamlCase() {

    assertThat(getStringUtil().fromCamlCase("abc", ' ')).isEqualTo("abc");
    assertThat(getStringUtil().fromCamlCase("FooBar", '-')).isEqualTo("foo-bar");
    assertThat(getStringUtil().fromCamlCase("FOO_BAR", '*')).isEqualTo("foo_bar");
    assertThat(getStringUtil().fromCamlCase("someWordMix", '.')).isEqualTo("some.word.mix");
    assertThat(getStringUtil().fromCamlCase("AbbreviationsLikeXMLshouldNotBeCapitalized", '_')).isEqualTo("abbreviations_like_xmlshould_not_be_capitalized");
  }

  @Test
  public void testPadNumber() {

    assertThat(getStringUtil().padNumber(5, 3)).isEqualTo("005");
    assertThat(getStringUtil().padNumber(25, 3)).isEqualTo("025");
    assertThat(getStringUtil().padNumber(100, 3)).isEqualTo("100");
    assertThat(getStringUtil().padNumber(1234, 3)).isEqualTo("1234");
  }

  @Test
  public void testReplaceSuffixWithCase() {

    assertThat(getStringUtil().replaceSuffixWithCase("foobar", 3, "foo")).isEqualTo("foofoo");
    assertThat(getStringUtil().replaceSuffixWithCase("FOOBAR", 3, "foo")).isEqualTo("FOOFOO");
    assertThat(getStringUtil().replaceSuffixWithCase("FooBar", 3, "foo")).isEqualTo("FooFoo");
    assertThat(getStringUtil().replaceSuffixWithCase("FooBar", 2, "foo")).isEqualTo("FooBfoo");
    assertThat(getStringUtil().replaceSuffixWithCase("FOO", 2, "u\u00df")).isEqualTo("FUSS");
  }

  @Test
  public void testToSeparatedString() {

    StringUtil util = getStringUtil();

    Collection<Object> collection = new ArrayList<Object>();
    collection.add("abc;");
    collection.add("a,b,c");
    collection.add(Integer.valueOf(123));
    collection.add(Character.valueOf('\''));

    StringSyntaxBean syntax = new StringSyntaxBean('\\');
    assertThat(util.toSeparatedString(collection, ",", syntax)).isEqualTo("abc;,a\\,b\\,c,123,'");

    syntax.setQuote('\'');
    assertThat(util.toSeparatedString(collection, ",", syntax)).isEqualTo("'abc;','a,b,c','123','\\''");

    syntax.setQuoteStart('[');
    syntax.setQuoteEnd(']');
    assertThat(util.toSeparatedString(collection, "; ", syntax)).isEqualTo("[abc;]; [a,b,c]; [123]; [']");
  }

  @Test
  public void testFromSeparatedString() {

    StringSyntaxBean syntax = new StringSyntaxBean('\\');
    List<String> list = getStringUtil().fromSeparatedString("a,bc,d,ef", ",", syntax);
    assertThat(list).hasSize(4).containsExactly("a", "bc", "d", "ef");

    List<Integer> collection = new ArrayList<Integer>();
    ValueConverter<String, Integer> converter = new ValueConverter<String, Integer>() {

      @Override
      public Class<String> getSourceType() {

        return String.class;
      }

      @Override
      public Class<Integer> getTargetType() {

        return Integer.class;
      }

      @Override
      public <T extends Integer> T convert(String value, Object valueSource, Class<T> targetClass) {

        return (T) Integer.valueOf(value);
      }

      @Override
      public <T extends Integer> T convert(String value, Object valueSource, GenericType<T> targetType) {

        return convert(value, valueSource, targetType.getAssignmentClass());
      }
    };
    getStringUtil().fromSeparatedString("1; 42; 3; 99", "; ", syntax, collection, converter, Integer.class);
    assertThat(collection).hasSize(4).containsExactly(Integer.valueOf(1), Integer.valueOf(42), Integer.valueOf(3), Integer.valueOf(99));
  }

  @Test
  public void testToHex() {

    StringUtil util = getStringUtil();
    assertThat(util.toHex(new byte[] { 0x00f, 0x01e, 0x02d, 0x03c, 0x04b, 0x05a, 0x069, 0x078 })).isEqualTo("0f1e2d3c4b5a6978");
    assertThat(util.toHex(new byte[] { (byte) 0x0fe, (byte) 0x0dc, (byte) 0x0ba, (byte) 0x098, 0x076, 0x054, 0x032, 0x010 })).isEqualTo("fedcba9876543210");
    assertThat(util.toHex(new byte[] { 0x07f })).isEqualTo("7f");
    assertThat(util.toHex(new byte[0])).isEqualTo("");
  }

  @Test
  public void testResolveEscapeChar() {

    // given
    StringUtil util = getStringUtil();
    // when + then
    assertThat(util.resolveEscape('B')).isNull();
    assertThat(util.resolveEscape('b')).isEqualTo('\b');
    assertThat(util.resolveEscape('\'')).isEqualTo('\'');
    assertThat(util.resolveEscape('"')).isEqualTo('\"');
    assertThat(util.resolveEscape('t')).isEqualTo('\t');
    assertThat(util.resolveEscape('n')).isEqualTo('\n');
    assertThat(util.resolveEscape('r')).isEqualTo('\r');
    assertThat(util.resolveEscape('0')).isEqualTo('\0');
    assertThat(util.resolveEscape('1')).isEqualTo('\1');
    assertThat(util.resolveEscape('2')).isEqualTo('\2');
    assertThat(util.resolveEscape('3')).isEqualTo('\3');
    assertThat(util.resolveEscape('4')).isEqualTo('\4');
    assertThat(util.resolveEscape('5')).isEqualTo('\5');
    assertThat(util.resolveEscape('6')).isEqualTo('\6');
    assertThat(util.resolveEscape('7')).isEqualTo('\7');
    assertThat(util.resolveEscape('8')).isNull();
    assertThat(util.resolveEscape('9')).isNull();
    assertThat(util.resolveEscape('a')).isNull();
  }

  @Test
  public void testResolveEscapeString() {

    // given
    StringUtil util = getStringUtil();
    // when + then
    assertThat(util.resolveEscape("B")).isNull();
    assertThat(util.resolveEscape("b")).isEqualTo('\b');
    assertThat(util.resolveEscape("\'")).isEqualTo('\'');
    assertThat(util.resolveEscape("\"")).isEqualTo('\"');
    assertThat(util.resolveEscape("t")).isEqualTo('\t');
    assertThat(util.resolveEscape("n")).isEqualTo('\n');
    assertThat(util.resolveEscape("r")).isEqualTo('\r');
    assertThat(util.resolveEscape("0")).isEqualTo('\0');
    assertThat(util.resolveEscape("1")).isEqualTo('\1');
    assertThat(util.resolveEscape("2")).isEqualTo('\2');
    assertThat(util.resolveEscape("3")).isEqualTo('\3');
    assertThat(util.resolveEscape("4")).isEqualTo('\4');
    assertThat(util.resolveEscape("5")).isEqualTo('\5');
    assertThat(util.resolveEscape("6")).isEqualTo('\6');
    assertThat(util.resolveEscape("7")).isEqualTo('\7');
    assertThat(util.resolveEscape("8")).isNull();
    assertThat(util.resolveEscape("9")).isNull();
    assertThat(util.resolveEscape("a")).isNull();
    assertThat(util.resolveEscape("17")).isEqualTo('\17');
    assertThat(util.resolveEscape("37")).isEqualTo('\37');
    assertThat(util.resolveEscape("77")).isEqualTo('\77');
    assertThat(util.resolveEscape("377")).isEqualTo('\377');
    assertThat(util.resolveEscape("378")).isNull();
    assertThat(util.resolveEscape("400")).isNull();
    assertThat(util.resolveEscape("u1234")).isEqualTo('\u1234');
    assertThat(util.resolveEscape("uu1234")).isEqualTo('\uu1234');
    // Java Language Spec is somewhat crazy
    assertThat(util.resolveEscape("uuuuuuuuuuuuuuuuuuuuuuuuu1234")).isEqualTo('\uuuuuuuuuuuuuuuuuuuuuuuuu1234');
  }

}
