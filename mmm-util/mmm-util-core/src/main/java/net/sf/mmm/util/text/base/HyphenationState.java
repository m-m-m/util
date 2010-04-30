/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.text.api.Hyphenation;
import net.sf.mmm.util.text.api.StringHasher;

/**
 * This class represents the current state of the hyphenation of a specific
 * word.
 * 
 * @see net.sf.mmm.util.text.api.Hyphenator#hyphenate(String, int, int)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class HyphenationState {

  /** The minimum length of a pattern. */
  private static final int MIN_PATTERN_LENGTH = 2;

  /** The {@link StringHasher} to use. */
  private final StringHasher hasher;

  /** The {@link StringUtil} to use. */
  private final StringUtil stringUtil;

  /** @see #getWord() */
  private final String word;

  /** The normalized word. */
  private final char[] normalizedWord;

  /**
   * The hashes of all substrings of {@link #normalizedWord}. The first index
   * indicates the substring-length - 2, the second is the offset in
   * {@link #normalizedWord}.
   */
  private int[][] hashes;

  /** The {@link HyphenationPatternPosition#ranking}s */
  private final int[] rankings;

  /** The offset where to start hyphenation in {@link #word}. */
  private final int offset;

  /** @see net.sf.mmm.util.text.api.Hyphenator#getHyphen() */
  private final char hyphen;

  /**
   * The constructor.
   * 
   * @param word is the {@link #getWord() word to hyphenate}.
   * @param normalizedWord is the {@link #getNormalizedWord() normalized word}.
   * @param hyphen is the
   *        {@link net.sf.mmm.util.text.api.Hyphenator#getHyphen() hyphen}.
   * @param maxPatternLength is the maximum {@link String#length() length} of
   *        all {@link HyphenationPattern patterns}.
   * @param offset is the start-position in word. Typically <code>0</code>.
   * @param hasher is the {@link StringHasher hash-algorithm}. It should be
   *        fast. The same {@link StringHasher} needs to be used for
   *        {@link HyphenationPattern#getWordPartHash()}.
   * @param stringUtil is the {@link StringUtil} to use.
   */
  public HyphenationState(String word, String normalizedWord, char hyphen, int maxPatternLength,
      int offset, StringHasher hasher, StringUtil stringUtil) {

    super();
    this.stringUtil = stringUtil;
    this.hasher = hasher;
    this.word = word;
    this.normalizedWord = normalizedWord.toCharArray();
    this.hyphen = hyphen;
    this.offset = offset;
    int len = word.length() - offset - 2;
    if (len < 0) {
      String value = word;
      if (offset > 0) {
        value = value + "[+" + offset + "]";
      }
      throw new NlsIllegalArgumentException(value, "word");
    }
    // a hyphenation can never occur at the beginning or the end of a word.
    this.rankings = new int[len];
    this.hashes = new int[maxPatternLength - MIN_PATTERN_LENGTH + 1][];
  }

  private int[] getHashes(int patternLength) {

    int i = patternLength - MIN_PATTERN_LENGTH;
    int[] subHashes = this.hashes[i];
    if (subHashes == null) {
      subHashes = this.hasher.getHashCodes(this.normalizedWord, patternLength);
      this.hashes[i] = subHashes;
    }
    return subHashes;
  }

  /**
   * This is the heart of the hyphenation algorithm. It checks if the
   * {@link HyphenationPattern pattern} is a substring of the
   * 
   * 
   * @param pattern is the pattern to check and potentially apply.
   */
  public void apply(HyphenationPattern pattern) {

    String wordPart = pattern.getWordPart();
    int wordPartLength = wordPart.length();
    int wordPartHash = pattern.getWordPartHash();
    int[] subHashes = getHashes(wordPartLength);
    if (subHashes.length == 0) {
      return;
    }
    int start = 0;
    int end = subHashes.length - 1;
    if (wordPart.charAt(0) == HyphenationPattern.TERMINATOR) {
      end = 0;
    } else if (wordPart.charAt(wordPartLength - 1) == HyphenationPattern.TERMINATOR) {
      start = end;
    }
    for (int i = start; i <= end; i++) {
      if (subHashes[i] == wordPartHash) {
        if (this.stringUtil.isSubstring(this.normalizedWord, wordPart, i)) {
          apply(pattern, i);
        }
      }
    }
  }

  /**
   * This method applies the {@link HyphenationPattern pattern} matching at the
   * given <code>offset</code>.
   * 
   * @param pattern is the matching {@link HyphenationPattern pattern}.
   * @param pos is the offset in the word to hyphenate.
   */
  private void apply(HyphenationPattern pattern, int pos) {

    int internalOffset = pos - 2;
    HyphenationPatternPosition[] positions = pattern.getHyphenationPositions();
    for (HyphenationPatternPosition hyphenationPosition : positions) {
      int i = hyphenationPosition.index + internalOffset;
      if ((i >= 0) && (i < this.rankings.length)
          && (hyphenationPosition.ranking > this.rankings[i])) {
        this.rankings[i] = hyphenationPosition.ranking;
      }
    }
  }

  /**
   * This method creates the
   * {@link HyphenationImpl#HyphenationImpl(String, char, int[])
   * hyphenationPoints} of a hyphenated word.
   * 
   * @see #toHyphenation()
   * 
   * @return the hyphenation points.
   */
  protected int[] toHyphenationPoints() {

    int internalOffset = this.offset + 1;
    int hyphenationCount = 0;
    for (int i = 0; i < this.rankings.length; i++) {
      if ((this.rankings[i] & 1) == 1) {
        // odd ranking --> hyphenation...
        hyphenationCount++;
      }
    }
    int[] hyphenationPoints = new int[hyphenationCount];
    hyphenationCount = 0;
    for (int i = 0; i < this.rankings.length; i++) {
      if ((this.rankings[i] & 1) == 1) {
        // odd ranking --> hyphenation...
        hyphenationPoints[hyphenationCount] = internalOffset + i;
        hyphenationCount++;
      }
    }
    return hyphenationPoints;
  }

  /**
   * This method gets the {@link Hyphenation} from this state.
   * 
   * @return the {@link Hyphenation}.
   */
  public Hyphenation toHyphenation() {

    return new HyphenationImpl(this.word, this.hyphen, toHyphenationPoints());
  }

  /**
   * This method gets the word to hyphenate.
   * 
   * @return the word to hyphenate.
   */
  public String getWord() {

    return this.word;
  }

  /**
   * This method gets the normalized word. This is the {@link #getWord() word}
   * in normalized form (lower case) and surrounded by
   * {@link HyphenationPattern#TERMINATOR}.
   * 
   * @return the normalizedWord.
   */
  protected char[] getNormalizedWord() {

    return this.normalizedWord;
  }

  /**
   * This method generates a string with the given <code>word</code> including
   * the {@link HyphenationPatternPosition#ranking ranked} hyphenation points.
   * It is intended for debugging purposes.
   * 
   * @return the word with hyphenation ranks.
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(this.word.substring(0, this.offset));
    for (int i = 0; i < this.rankings.length; i++) {
      sb.append(this.word.charAt(i + this.offset));
      if (this.rankings[i] > 0) {
        sb.append(this.rankings[i]);
      }
    }
    sb.append(this.word.substring(this.rankings.length + this.offset));
    return sb.toString();
  }

}
