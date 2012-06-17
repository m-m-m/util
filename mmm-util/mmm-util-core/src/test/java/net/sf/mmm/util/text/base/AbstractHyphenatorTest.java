/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.text.api.Hyphenation;
import net.sf.mmm.util.text.api.Hyphenator;

import org.junit.Assert;

/**
 * This is the abstract base-class for test-cases of {@link Hyphenator}.
 * 
 * @see #checkHyphenation(Hyphenator, String, StringBuilder, StringBuilder)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractHyphenatorTest {

  /**
   * The constructor.
   */
  public AbstractHyphenatorTest() {

    super();
  }

  /**
   * This method checks if a given <code>hyphenatedWord</code> is properly hyphenated using the given
   * <code>hyphenator</code>.
   * 
   * @param hyphenator is the {@link Hyphenator} to test.
   * @param hyphenatedWord is the hyphenated word.
   * @param failuresExpected is where expected value is appended in case of failure.
   * @param failuresActual is where actual value is appended in case of failure.
   */
  protected void checkHyphenation(Hyphenator hyphenator, String hyphenatedWord, StringBuilder failuresExpected,
      StringBuilder failuresActual) {

    Hyphenation hyphenation = new HyphenationImpl(hyphenatedWord);
    String word = hyphenation.getWord();
    Hyphenation result = hyphenator.hyphenate(word);
    Assert.assertNotNull(result);
    Assert.assertEquals(word, result.getWord());
    // Assert.assertEquals(hyphenatedWord, result.toString());
    if (!hyphenatedWord.equals(result.toString())) {
      if (failuresExpected.length() > 0) {
        failuresExpected.append('\n');
        failuresActual.append('\n');
      }
      failuresExpected.append(hyphenatedWord);
      failuresActual.append(result.toString());
    }
  }

  /**
   * This method {@link #checkHyphenation(Hyphenator, String, StringBuilder, StringBuilder) checks the
   * hyphenation} of for all given words. It performs the actual assertion at the end so the test does NOT
   * stop on the first failure.
   * 
   * @param hyphenator is the {@link Hyphenator} to test.
   * @param hyphenatedWords are the pre-hyphenated words.
   */
  protected void checkHyphenations(Hyphenator hyphenator, String... hyphenatedWords) {

    StringBuilder failuresActual = new StringBuilder();
    StringBuilder failuresExpected = new StringBuilder();
    for (String word : hyphenatedWords) {
      checkHyphenation(hyphenator, word, failuresExpected, failuresActual);
    }
    Assert.assertEquals("incorrect hyphenation(s)!", failuresExpected.toString(), failuresActual.toString());
  }

}
