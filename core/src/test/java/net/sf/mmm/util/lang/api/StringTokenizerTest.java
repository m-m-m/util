/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test of {@link StringTokenizer}.
 */
@SuppressWarnings("all")
public class StringTokenizerTest extends Assertions {

  public void checkTokenizer(String escapeStart, String escapeEnd, char[] delimiters, String... strings) {

    StringBuilder buffer = new StringBuilder();
    int delimiterIndex = 0;
    for (String s : strings) {
      buffer.append(s);
      buffer.append(delimiters[delimiterIndex++]);
      if (delimiterIndex >= delimiters.length) {
        delimiterIndex = 0;
      }
    }
    buffer.setLength(buffer.length() - 1);
    StringTokenizer tokenizer;
    // JDK StringTokenizer compliance
    tokenizer = new StringTokenizer(buffer.toString(), new String(delimiters));
    for (String s : strings) {
      assertThat(tokenizer.hasMoreTokens()).isTrue();
      assertThat(tokenizer.nextToken()).isEqualTo(s);
    }
    // advanced API
    tokenizer = new StringTokenizer(buffer.toString(), delimiters);
    int index = 0;
    for (String s : tokenizer) {
      assertThat(s).isEqualTo(strings[index++]);
    }
    assertThat(tokenizer.hasMoreTokens()).isFalse();
  }

  @Test
  public void testTokenizer() {

    StringTokenizer stringTokenizer;
    stringTokenizer = new StringTokenizer("", '.');
    assertThat(stringTokenizer.hasNext()).isFalse();
    try {
      stringTokenizer.next();
      fail("Exception expected");
    } catch (NoSuchElementException e) {
    }
    checkTokenizer(null, null, new char[] { ',' }, "foo");
    checkTokenizer(null, null, new char[] { ',', '.' }, "", "foo", "", "bar", "");
    checkTokenizer(null, null, new char[] { ',', '.' }, "foo", "bar", "", "", "some");
    checkTokenizer(null, null, new char[] { '-', '+', '_' }, "foo", "bar", "some", "", "");
  }

  @Test
  public void testTokenizerWithEscaping() {

    StringTokenizer tokenizer = new StringTokenizer("{[foo,{[bar,thing]}]},some", "{[", "]}", ',');
    assertThat(tokenizer.next()).isEqualTo("foo,{[bar,thing]}");
    assertThat(tokenizer.hasNext()).isTrue();
    assertThat(tokenizer.next()).isEqualTo("some");
    assertThat(tokenizer.hasNext()).as("no next token expected").isFalse();
  }

}
