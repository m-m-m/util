/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.lang.api.BasicUtil;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.text.api.Hyphenation;
import net.sf.mmm.util.text.api.StringHasher;

/**
 * This is the implementation of the {@link net.sf.mmm.util.text.api.Hyphenator} interface. It uses a list of rules to
 * determine hyphenation-points.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class HyphenatorImpl extends AbstractHyphenator {

  /** The {@link Map} with explicit exceptions. */
  private final Map<String, HyphenationImpl> exceptionMap;

  /** The {@link HyphenationPattern patterns}. */
  private final HyphenationPattern[] patterns;

  /** The maximum length of the {@link #patterns}. */
  private final int maxPatternLength;

  /** @see StringHasher */
  private final StringHasher hasher;

  /** @see StringUtil */
  private final StringUtil stringUtil;

  /**
   * The constructor.
   *
   * @param locale is the {@link #getLocale() locale}.
   * @param hyphen is the {@link #getHyphen() hyphen-character}.
   * @param patternList is the {@link List} of {@link HyphenationPattern patterns}.
   * @param exceptionList is the list of pre-hyphenated exceptions (e.g. "as-so-ciate").
   * @param hasher is the {@link StringHasher hash-algorithm}. It should be fast.
   * @param stringUtil is the {@link StringUtil} to use.
   */
  public HyphenatorImpl(Locale locale, char hyphen, List<String> patternList, List<String> exceptionList,
      StringHasher hasher, StringUtil stringUtil) {

    super(locale, hyphen);
    this.hasher = hasher;
    this.stringUtil = stringUtil;
    this.exceptionMap = new HashMap<>(exceptionList.size());
    for (String exception : exceptionList) {
      HyphenationImpl hypenation = new HyphenationImpl(exception, hyphen);
      this.exceptionMap.put(hypenation.getWord(), hypenation);
    }
    HyphenationPattern[] patternArray = new HyphenationPattern[patternList.size()];
    int maxLength = 2;
    for (int i = 0; i < patternArray.length; i++) {
      patternArray[i] = new HyphenationPattern(patternList.get(i), hasher);
      // assert (patternList.get(i).equals(this.patterns[i].getPattern()));
      int len = patternArray[i].getWordPart().length();
      if (len > maxLength) {
        maxLength = len;
      }
    }
    patternArray = sortPatterns(patternArray);
    this.patterns = patternArray;
    this.maxPatternLength = maxLength;
  }

  /**
   * This method sorts the given {@link HyphenationPattern patterns} according to the {@link String#length() length} of
   * their {@link HyphenationPattern#getWordPart() word-part}.
   *
   * @param patternArray are the unsorted {@link HyphenationPattern patterns}.
   * @return the sorted {@link HyphenationPattern patterns}.
   */
  private static HyphenationPattern[] sortPatterns(HyphenationPattern[] patternArray) {

    Comparator<HyphenationPattern> comparator = new Comparator<HyphenationPattern>() {

      public int compare(HyphenationPattern p1, HyphenationPattern p2) {

        return (p2.getWordPart().length() - p1.getWordPart().length());
      }
    };
    Arrays.sort(patternArray, comparator);
    return patternArray;
  }

  /**
   * This method normalizes the given <code>word</code>.
   *
   * @param word is the word to normalize.
   * @return the normalized form of <code>word</code>.
   */
  protected String normalize(String word) {

    return word.toLowerCase(getLocale());
  }

  /**
   * {@inheritDoc}
   */
  public Hyphenation hyphenate(String word) {

    if (word.length() <= 1) {
      return new HyphenationImpl(word, getHyphen(), BasicUtil.EMPTY_INT_ARRAY);
    }
    String normalizedWord = normalize(word);
    Hyphenation hyphenation;
    HyphenationImpl exception = this.exceptionMap.get(normalizedWord);
    if (exception != null) {
      hyphenation = new HyphenationImpl(word, getHyphen(), exception.getHyphenationPoints());
    } else {
      String newWord = HyphenationPattern.TERMINATOR + normalizedWord + HyphenationPattern.TERMINATOR;
      HyphenationState state = new HyphenationState(word, newWord, getHyphen(), this.maxPatternLength, 0,
          this.hasher, this.stringUtil);
      for (HyphenationPattern pattern : this.patterns) {
        state.apply(pattern);
      }
      if (getLogger().isTraceEnabled()) {
        getLogger().trace("Hyphenation is " + state.toString());
      }
      hyphenation = state.toHyphenation();
    }
    return hyphenation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "Hyphenator for " + getLocale();
  }

}
