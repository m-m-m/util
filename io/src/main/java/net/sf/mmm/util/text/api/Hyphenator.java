/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

import java.util.Locale;

/**
 * A {@link Hyphenator} is used to hyphenate words. If a word is to long to fit at the end of a line of text,
 * it may be hyphenated according to the {@link #getLocale() locale}. <br>
 * An implementation has to be thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface Hyphenator {

  /**
   * @see #hyphenate(String, int, int)
   * 
   * @param word is the word to hyphenate.
   * @return the {@link Hyphenation} for the given word.
   */
  Hyphenation hyphenate(String word);

  /**
   * @see #hyphenate(String, int, int)
   * 
   * @param text is the text ending with the word to hyphenate.
   * @param start is the {@link String#indexOf(int) index} of the words first character.
   * @return the {@link Hyphenation} for the word given by
   *         <code>text.{@link String#substring(int, int) substring}(start)</code>
   */
  Hyphenation hyphenate(String text, int start);

  /**
   * This method hyphenates the word from the given {@code text} from {@code start} to
   * {@code end}. <br>
   * <b>ATTENTION:</b><br>
   * To ensure correct results you need to invoke this method for a single word of text. Please also note that
   * word detection is far from trivial for specific languages such as Thai. You should use
   * {@link java.text.BreakIterator#getWordInstance(Locale)} for word-separation.
   * 
   * @param text is the text containing the word to hyphenate.
   * @param start is the {@link String#indexOf(int) index} of the words first character.
   * @param end is the exclusive end-index. The {@link String#indexOf(int) index} of the words last character
   *        is {@code end - 1}.
   * @return the {@link Hyphenation} for the word given by
   *         <code>text.{@link String#substring(int, int) substring}(start, end)</code>
   */
  Hyphenation hyphenate(String text, int start, int end);

  /**
   * This method gets the {@link Locale} this {@link Hyphenator} works for. It may differ from (be more
   * general than) the {@link Locale} this {@link Hyphenator} was
   * {@link HyphenatorBuilder#getHyphenator(Locale) requested for}.
   * 
   * @see HyphenatorBuilder#getHyphenator(Locale)
   * 
   * @return the {@link Locale}.
   */
  Locale getLocale();

  /**
   * This method gets the character used to separate the hyphenated parts of a word. This is typically the
   * character '-'. However it may differ for specific {@link Locale}s.
   * 
   * @return the hyphen character.
   */
  char getHyphen();

}
