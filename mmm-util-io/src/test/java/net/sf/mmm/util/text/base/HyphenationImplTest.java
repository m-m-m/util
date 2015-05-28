/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.text.api.Hyphenation;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * This is the test-case for {@link HyphenationImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class HyphenationImplTest extends Assertions {

  /**
   * This method performs general tests on {@link HyphenationImpl}, especially {@link HyphenationImpl#toString()}.
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
    Hyphenation hyphenation = new HyphenationImpl(text.substring(start, end), hyphen, new int[] {});
    assertThat(hyphenation.toString()).isEqualTo(word);
    assertThat(hyphenation.getWord()).isEqualTo(word);
    assertThat(hyphenation.getHyphen()).isEqualTo(hyphen);
    assertThat(hyphenation.getHyphenationCount()).isEqualTo(0);
    assertThat(hyphenation.getHyphenationBefore(0)).isEqualTo(-1);
    assertThat(hyphenation.getHyphenationBefore(word.length())).isEqualTo(-1);
    hyphenation = new HyphenationImpl(text.substring(start, end), hyphen, new int[] { 1, 5, 7 });
    assertThat(hyphenation.toString()).isEqualTo("e-very-bo-dy");
    assertThat(hyphenation.getWord()).isEqualTo(word);
    assertThat(hyphenation.getHyphenationCount()).isEqualTo(3);
    assertThat(hyphenation.getHyphenationBefore(0)).isEqualTo(-1);
    assertThat(hyphenation.getHyphenationBefore(word.length())).isEqualTo(7);
    assertThat(hyphenation.getHyphenationBefore(7)).isEqualTo(5);
    assertThat(hyphenation.getHyphenationBefore(5)).isEqualTo(1);
    assertThat(hyphenation.getHyphenation(0)).isEqualTo(1);
    assertThat(hyphenation.getHyphenation(1)).isEqualTo(5);
    assertThat(hyphenation.getHyphenation(2)).isEqualTo(7);
  }
}
