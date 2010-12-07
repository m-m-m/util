/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.text.api.Hyphenation;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link HyphenationImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class HyphenationImplTest {

  /**
   * This method performs general tests on {@link HyphenationImpl}, especially
   * {@link HyphenationImpl#toString()}.
   */
  @Test
  public void testToStringAndGetWord() {

    String word = "everybody";
    String prefix = "Hello ";
    String suffix = "!";
    String text = prefix + word + suffix;
    int start = prefix.length();
    int end = text.length() - suffix.length();
    char hyphen = '-';
    Hyphenation hyphenation = new HyphenationImpl(text.substring(start, end), hyphen, new int[] { });
    Assert.assertEquals(word, hyphenation.toString());
    Assert.assertEquals(word, hyphenation.getWord());
    Assert.assertEquals(hyphen, hyphenation.getHyphen());
    Assert.assertEquals(0, hyphenation.getHyphenationCount());
    Assert.assertEquals(-1, hyphenation.getHyphenationBefore(0));
    Assert.assertEquals(-1, hyphenation.getHyphenationBefore(word.length()));
    hyphenation = new HyphenationImpl(text.substring(start, end), hyphen, new int[] { 1, 5, 7 });
    Assert.assertEquals("e-very-bo-dy", hyphenation.toString());
    Assert.assertEquals(word, hyphenation.getWord());
    Assert.assertEquals(3, hyphenation.getHyphenationCount());
    Assert.assertEquals(-1, hyphenation.getHyphenationBefore(0));
    Assert.assertEquals(7, hyphenation.getHyphenationBefore(word.length()));
    Assert.assertEquals(5, hyphenation.getHyphenationBefore(7));
    Assert.assertEquals(1, hyphenation.getHyphenationBefore(5));
    Assert.assertEquals(1, hyphenation.getHyphenation(0));
    Assert.assertEquals(5, hyphenation.getHyphenation(1));
    Assert.assertEquals(7, hyphenation.getHyphenation(2));
  }
}
