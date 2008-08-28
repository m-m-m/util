/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * This is the test-case for {@link StringTokenizer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringTokenizerTest {

  public void checkTokenizer(char[] delimiters, String... strings) {

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
      assertTrue(tokenizer.hasMoreTokens());
      assertEquals(s, tokenizer.nextToken());
    }
    // advanced API
    tokenizer = new StringTokenizer(buffer.toString(), delimiters);
    int index = 0;
    for (String s : tokenizer) {
      assertEquals(strings[index++], s);
    }
    assertFalse(tokenizer.hasMoreTokens());
  }

  @Test
  public void testTokenizer() {

    StringTokenizer stringTokenizer;
    stringTokenizer = new StringTokenizer("", '.');
    assertFalse(stringTokenizer.hasNext());
    try {
      stringTokenizer.next();
      fail("Exception expected");
    } catch (NoSuchElementException e) {
    }
    checkTokenizer(new char[] { ',' }, "foo");
    checkTokenizer(new char[] { ',', '.' }, "", "foo", "", "bar", "");
    checkTokenizer(new char[] { ',', '.' }, "foo", "bar", "", "", "some");
    checkTokenizer(new char[] { '-', '+', '_' }, "foo", "bar", "some", "", "");
  }
}
