/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.lang.api.BasicHelper;

/**
 * This is a simple implementation of the {@link TransformerRule}. If the given string ends with a specific
 * suffix, that suffix is replaced by a suffix for the result form.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SuffixTransformerRule extends TransformerRule {

  /** @see SuffixTransformerRule#SuffixTransformerRule(String, String) */
  private final String sourceSuffix;

  /** @see SuffixTransformerRule#SuffixTransformerRule(String, String) */
  private final String destinationSuffix;

  /**
   * The constructor.
   *
   * @param sourceSuffix is the suffix of the plural form. If a plural term ends with that suffix, this rule
   *        will apply and replace it with the given {@code singularSuffix}.
   * @param destinationSuffix is the suffix of the singular form. It is the replacement for
   *        {@code pluralSuffix}. Use the empty string if you just want to remove the {@code pluralSuffix}.
   */
  public SuffixTransformerRule(String sourceSuffix, String destinationSuffix) {

    super();
    this.sourceSuffix = sourceSuffix;
    this.destinationSuffix = destinationSuffix;
  }

  @Override
  public String transform(String string, String stringLowerCase) {

    if (stringLowerCase.endsWith(this.sourceSuffix)) {
      return replaceSuffixWithCase(string, this.sourceSuffix.length(), this.destinationSuffix);
    }
    return null;
  }

  private static String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase) {

    // copied from StringUtilImpl to avoid dependency
    int stringLength = string.length();
    int newSuffixLength = newSuffixLowerCase.length();
    int replaceIndex = stringLength - suffixLength;
    if (replaceIndex < 0) {
      replaceIndex = 0;
    }
    StringBuilder result = new StringBuilder(replaceIndex + newSuffixLength);
    if (replaceIndex > 0) {
      result.append(string.substring(0, replaceIndex));
    }
    if (suffixLength > 0) {
      char c = string.charAt(replaceIndex);
      // is lower case?
      if (c == Character.toLowerCase(c)) {
        result.append(newSuffixLowerCase);
      } else {
        // first replaced char is upper case!
        if (suffixLength > 1) {
          c = string.charAt(replaceIndex + 1);
          // is lower case?
          if (c == Character.toLowerCase(c)) {
            // capitalize
            String first = newSuffixLowerCase.substring(0, 1);
            String capital = BasicHelper.toUpperCase(first);
            if (capital.length() == 1) {
              result.append(newSuffixLowerCase);
              result.setCharAt(replaceIndex, capital.charAt(0));
            } else {
              result.append(capital);
              result.append(newSuffixLowerCase.substring(1));
            }
          } else {
            // all upper case
            result.append(BasicHelper.toUpperCase(newSuffixLowerCase));
          }
        }
      }
    }
    return result.toString();
  }

}
