/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.text.api.Hyphenation;
import net.sf.mmm.util.text.api.StringHasher;

/**
 * This is the implementation of the {@link net.sf.mmm.util.text.api.Hyphenator}
 * interface. It uses a list of rules to determine hyphenation-points.
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
   * @param patternList is the {@link List} of {@link HyphenationPattern
   *        patterns}.
   * @param exceptionList is the list of pre-hyphenated exceptions (e.g.
   *        "as-so-ciate").
   * @param hasher is the {@link StringHasher hash-algorithm}. It should be
   *        fast.
   * @param stringUtil is the {@link StringUtil} to use.
   */
  public HyphenatorImpl(Locale locale, char hyphen, List<String> patternList,
      List<String> exceptionList, StringHasher hasher, StringUtil stringUtil) {

    super(locale, hyphen);
    this.hasher = hasher;
    this.stringUtil = stringUtil;
    this.exceptionMap = new HashMap<String, HyphenationImpl>(exceptionList.size());
    for (String exception : exceptionList) {
      HyphenationImpl hypenation = new HyphenationImpl(exception, hyphen);
      this.exceptionMap.put(hypenation.getWord(), hypenation);
    }
    this.patterns = new HyphenationPattern[patternList.size()];
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
    int patternIndex = 0;
    for (int i = 2; i <= maxLength; i++) {
      for (int j = 0; j < patternArray.length; j++) {
        int len = patternArray[j].getWordPart().length();
        if (len == i) {
          this.patterns[patternIndex++] = patternArray[j];
        }
      }
    }
    assert (patternIndex == patternList.size());
    // boolean todo = true;
    // while (todo) {
    // todo = false;
    // for (String pattern : patternList) {
    // this.patterns[patternIndex++] = new
    // HyphenationPattern(patternList.get(i),
    // // hasher);
    // // // assert (patternList.get(i).equals(this.patterns[i].getPattern()));
    // // int len = this.patterns[i].getWordPart().length();
    // // if (len > maxLength) {
    // // maxLength = len;
    // }
    // }
    this.maxPatternLength = maxLength;
  }

  /**
   * {@inheritDoc}
   */
  public Hyphenation hyphenate(String word) {

    String normalizedWord = word.toLowerCase(getLocale());
    Hyphenation hyphenation;
    HyphenationImpl exception = this.exceptionMap.get(normalizedWord);
    if (exception != null) {
      hyphenation = new HyphenationImpl(word, getHyphen(), exception.getHyphenationPoints());
    } else {
      String newWord = HyphenationPattern.TERMINATOR + normalizedWord
          + HyphenationPattern.TERMINATOR;
      HyphenationState state = new HyphenationState(word, newWord, getHyphen(),
          this.maxPatternLength, 0, this.hasher, this.stringUtil);
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
