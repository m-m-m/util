/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.text.api.Hyphenation;
import net.sf.mmm.util.text.api.Hyphenator;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

/**
 * This is the abstract base-class for test-cases of {@link Hyphenator}.
 *
 * @see #checkHyphenations(Hyphenator, String...)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractHyphenatorTest extends Assertions {

  /**
   * The constructor.
   */
  public AbstractHyphenatorTest() {

    super();
  }

  /**
   * This method checks if a given {@code hyphenatedWord} is properly hyphenated using the given
   * {@code hyphenator}.
   *
   * @param hyphenator is the {@link Hyphenator} to test.
   * @param hyphenatedWord is the hyphenated word.
   * @param assertions the {@link SoftAssertions}.
   */
  protected void checkHyphenation(Hyphenator hyphenator, String hyphenatedWord, SoftAssertions assertions) {

    Hyphenation hyphenation = new HyphenationImpl(hyphenatedWord);
    String word = hyphenation.getWord();
    Hyphenation result = hyphenator.hyphenate(word);
    assertThat(result).isNotNull();
    assertThat(result.getWord()).isEqualTo(word);
    assertions.assertThat(result.toString()).isEqualTo(hyphenatedWord);
    // Assert.assertEquals(hyphenatedWord, result.toString());
    // if (!hyphenatedWord.equals(result.toString())) {
    // if (failuresExpected.length() > 0) {
    // failuresExpected.append('\n');
    // failuresActual.append('\n');
    // }
    // failuresExpected.append(hyphenatedWord);
    // failuresActual.append(result.toString());
    // }
  }

  /**
   * This method {@link #checkHyphenation(Hyphenator, String, SoftAssertions) checks the hyphenation} of for all given
   * words. It performs the actual assertion at the end so the test does NOT stop on the first failure.
   *
   * @param hyphenator is the {@link Hyphenator} to test.
   * @param hyphenatedWords are the pre-hyphenated words.
   */
  protected void checkHyphenations(Hyphenator hyphenator, String... hyphenatedWords) {

    SoftAssertions assertions = new SoftAssertions();
    for (String word : hyphenatedWords) {
      checkHyphenation(hyphenator, word, assertions);
    }
    assertions.assertAll();
  }
}
