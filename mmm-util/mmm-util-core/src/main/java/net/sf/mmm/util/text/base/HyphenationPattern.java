/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.text.api.StringHasher;

/**
 * A {@link HyphenationPattern} is a pattern that acts as rule for a hyphenation
 * algorithm.<br>
 * The concept is based on the thesis <em>Word Hy-phen-a-tion by Com-put-er</em>
 * by <em>Franklin Mark Liang</em>. Out of an entire dictionary of hyphenated
 * words for a specific language, a set of {@link HyphenationPattern patterns}
 * is extracted. To allow correct results with a reasonable small set of
 * patterns, these patterns form a chain of positive rules and exceptions.
 * Therefore a pattern can {@link HyphenationPatternPosition#ranking rank} a
 * potential hyphenation-position with a number from <code>1</code> to
 * <code>9</code>. If two patterns apply for a hyphenation-position the higher
 * number wins. Odd numbers indicate a hyphenation while even values indicate an
 * exception that should NOT be hyphenated. The character '.' is used at the
 * beginning and/or end of a pattern to indicate that it should only match at
 * the beginning/end of the word to hyphenate.<br>
 * Logically for each start-index of the (normalized) word to hyphenate
 * (enclosed with '.') all {@link HyphenationPattern patterns} are checked if
 * they match (please note that the order of the patterns is important!).
 * Matching means that the pattern stripped from digits is a substring of the
 * word at this start-index. If the pattern matches the
 * {@link HyphenationPatternPosition hyphenation-positions} are applied.<br>
 * <br>
 * Here is an example to illustrate the algorithm:<br>
 * The string <code>"Computer"</code> will be transformed to
 * <code>".computer."</code> that matches the following patterns:
 * <ul>
 * <li>4m1p</li>
 * <li>pu2t</li>
 * <li>5pute</li>
 * <li>put3er</li>
 * </ul>
 * This results to <code>co4m5pu2t3er</code> so the hyphenated input String is
 * finally <code>"Com-put-er"</code>. The challenge is to implement this
 * algorithm in an efficient way.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class HyphenationPattern {

  /** The {@link HyphenationPattern pattern} without digits. */
  private final String wordPart;

  /** @see #getWordPartHash() */
  private final int wordPartHash;

  /** @see #getHyphenationPositions() */
  private final HyphenationPatternPosition[] hyphenationPositions;

  /** The word-terminator representing start end end of a word. */
  public static final char TERMINATOR = '.';

  /**
   * The constructor.
   * 
   * @param pattern is the raw {@link HyphenationPattern pattern}.
   * @param hasher is the {@link StringHasher hash-algorithm} to use for the
   *        {@link #getWordPartHash() word-part-hash}.
   */
  public HyphenationPattern(String pattern, StringHasher hasher) {

    super();
    int length = pattern.length();
    if (length <= 2) {
      throw new NlsIllegalArgumentException(pattern, "pattern");
    }
    int hpLength = 0;
    for (int i = 0; i < length; i++) {
      char c = pattern.charAt(i);
      if ((c >= '0') && (c <= '9')) {
        hpLength++;
      }
    }
    this.hyphenationPositions = new HyphenationPatternPosition[hpLength];
    int hpIndex = 0;
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      char c = pattern.charAt(i);
      if ((c >= '0') && (c <= '9')) {
        this.hyphenationPositions[hpIndex++] = new HyphenationPatternPosition(sb.length(), c - '0');
      } else {
        sb.append(c);
      }
    }
    this.wordPart = sb.toString();
    this.wordPartHash = hasher.getHashCode(this.wordPart);
  }

  /**
   * This method gets the {@link HyphenationPatternPosition
   * hyphenation-positions} of the pattern.
   * 
   * @return the {@link HyphenationPatternPosition}s.
   */
  protected HyphenationPatternPosition[] getHyphenationPositions() {

    return this.hyphenationPositions;
  }

  /**
   * This method gets the <em>word-part</em>, that is the
   * {@link HyphenationPattern pattern} without digits. If the word-part is a
   * substring of the word to hyphenate (enclosed with '.'), the
   * {@link #getHyphenationPositions() hyphenation-points} are applied to the
   * {@link HyphenationState}.
   * 
   * @see HyphenationState#apply(HyphenationPattern)
   * 
   * @return the word-part.
   */
  public String getWordPart() {

    return this.wordPart;
  }

  /**
   * This method gets the pre-calculated hash of {@link #getWordPart()
   * word-part}.<br>
   * <b>ATTENTION:</b><br>
   * The result may be different to the {@link String#hashCode() hash-code} of
   * {@link #getWordPart() word-part}. A specific hash algorithm is used that
   * allows efficient calculation of shifting substrings.
   * 
   * @return the hash.
   */
  public int getWordPartHash() {

    return this.wordPartHash;
  }

  /**
   * This method gets the original pattern ({@link #getWordPart() word-part}
   * with {@link #getHyphenationPositions() hyphenation-points}).<br>
   * <b>ATTENTION:</b><br>
   * This method is intended for debugging purposes. It rebuilds the pattern
   * wasting some performance.
   * 
   * @return the pattern (e.g. ".af1t").
   */
  public String getPattern() {

    StringBuilder pattern = new StringBuilder(this.wordPart.length()
        + this.hyphenationPositions.length);
    int start = 0;
    for (HyphenationPatternPosition position : this.hyphenationPositions) {
      int end = position.index;
      pattern.append(this.wordPart, start, end);
      pattern.append(Integer.toString(position.ranking));
      start = end;
    }
    pattern.append(this.wordPart, start, this.wordPart.length());
    return pattern.toString();
  }

  /**
   * This method gets the original pattern.
   * 
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getPattern();
  }

}
