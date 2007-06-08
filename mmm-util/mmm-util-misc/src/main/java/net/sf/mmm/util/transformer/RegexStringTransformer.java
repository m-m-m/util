/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class converts a string by
 * {@link Pattern#matcher(CharSequence) matching} a given regular expression
 * {@link Pattern} and if it {@link Matcher#find() partially matches} replacing
 * the match(es) with a given replacement.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class RegexStringTransformer implements Transformer<String> {

  /** @see #getPattern() */
  private final Pattern pattern;

  /** @see #getReplacement() */
  private final String replacement;

  /** @see #isReplaceAll() */
  private final boolean replaceAll;

  /**
   * The constructor.
   * 
   * @param pattern
   *        is the pattern used for conversion.
   * @param replacement
   *        is the replacement to fill in the string to convert.
   * @param replaceAll -
   *        if <code>true</code> {@link Matcher#replaceAll(String)} will be
   *        used, else if <code>false</code>
   *        {@link Matcher#replaceFirst(String)}.
   */
  public RegexStringTransformer(Pattern pattern, String replacement, boolean replaceAll) {

    super();
    this.pattern = pattern;
    this.replacement = replacement;
    this.replaceAll = replaceAll;
  }

  /**
   * @return the pattern
   */
  public Pattern getPattern() {

    return this.pattern;
  }

  /**
   * @return the replaceAll
   */
  public boolean isReplaceAll() {

    return this.replaceAll;
  }

  /**
   * @return the replacement
   */
  public String getReplacement() {

    return this.replacement;
  }

  /**
   * {@inheritDoc}
   */
  public String transform(String original) {

    Matcher matcher = this.pattern.matcher(original);
    if (matcher.find()) {
      if (this.replaceAll) {
        return matcher.replaceAll(this.replacement);
      } else {
        return matcher.replaceFirst(this.replacement);
      }
    }
    return original;
  }

}
