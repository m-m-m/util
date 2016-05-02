/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.util.Arrays;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.text.api.Hyphenation;

/**
 * This is the implementation of the {@link Hyphenation} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class HyphenationImpl implements Hyphenation {

  /** @see #getWord() */
  private final String word;

  /** @see #getHyphenation(int) */
  private final int[] hyphenationPoints;

  /** @see #getHyphen() */
  private final char hyphen;

  /**
   * The constructor.
   * 
   * @param word is the {@link #getWord() word}.
   * @param hyphen is the {@link #getHyphen() hyphen-character}.
   * @param hyphenationPoints are the actual {@link #getHyphenation(int) hyphenation-points}.
   */
  public HyphenationImpl(String word, char hyphen, int[] hyphenationPoints) {

    super();
    this.word = word;
    this.hyphen = hyphen;
    this.hyphenationPoints = hyphenationPoints;
  }

  /**
   * The constructor.
   * 
   * @param hyphenatedWord is the hyphenated word.
   */
  public HyphenationImpl(String hyphenatedWord) {

    this(hyphenatedWord, '-');
  }

  /**
   * The constructor.
   * 
   * @param hyphenatedWord is the hyphenated word.
   * @param hyphen is the {@link #getHyphen() hyphen-character}.
   */
  public HyphenationImpl(String hyphenatedWord, char hyphen) {

    super();
    this.hyphen = hyphen;
    int length = hyphenatedWord.length();
    StringBuilder wordBuilder = new StringBuilder(length);
    int maxPointsLength = length - 2;
    if (maxPointsLength < 0) {
      maxPointsLength = 0;
    }
    int[] points = new int[maxPointsLength];
    int hyphenationPointCount = 0;
    for (int i = 0; i < length; i++) {
      char c = hyphenatedWord.charAt(i);
      if (c == hyphen) {
        if (hyphenationPointCount >= points.length) {
          // e.g. if hypernatedWord is "-" or "a--".
          throw new NlsIllegalArgumentException(hyphenatedWord, "hyphenatedWord");
        }
        points[hyphenationPointCount] = wordBuilder.length();
        hyphenationPointCount++;
      } else {
        wordBuilder.append(c);
      }
    }
    this.hyphenationPoints = Arrays.copyOf(points, hyphenationPointCount);
    this.word = wordBuilder.toString();
  }

  /**
   * {@inheritDoc}
   */
  public int getHyphenationCount() {

    return this.hyphenationPoints.length;
  }

  /**
   * {@inheritDoc}
   */
  public int getHyphenation(int hyphenationIndex) {

    return this.hyphenationPoints[hyphenationIndex];
  }

  /**
   * {@inheritDoc}
   */
  public int getHyphenationBefore(int offset) {

    int result = -1;
    for (int i = this.hyphenationPoints.length - 1; i >= 0; i--) {
      if (this.hyphenationPoints[i] < offset) {
        result = this.hyphenationPoints[i];
        break;
      }
    }
    return result;
  }

  /**
   * @return the hyphenationPoints
   */
  public int[] getHyphenationPoints() {

    return this.hyphenationPoints;
  }

  /**
   * {@inheritDoc}
   */
  public String getWord() {

    return this.word;
  }

  /**
   * {@inheritDoc}
   */
  public char getHyphen() {

    return this.hyphen;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder result = new StringBuilder(this.word.length() + 4);
    int start = 0;
    for (int offset : this.hyphenationPoints) {
      int end = offset;
      result.append(this.word, start, end);
      result.append(this.hyphen);
      start = end;
    }
    result.append(this.word, start, this.word.length());
    return result.toString();
  }
}
