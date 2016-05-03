/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.base;

import net.sf.mmm.util.filter.api.CharFilter;

/**
 * This is an implementation of the {@link CharFilter} interface that {@link #accept(char) filters} characters
 * according to a {@link ListCharFilter#ListCharFilter(boolean, char...) given} blacklist or whitelist.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ListCharFilter implements CharFilter {

  /** @see #accept(char) */
  private boolean blacklist;

  /** @see #accept(char) */
  private final char[] chars;

  /**
   * The constructor.
   * 
   * @param accept - if {@code true} exactly the chars given by {@code charArray} are accepted
   *        (whitelist), if {@code false} exactly these chars are NOT accepted (blacklist).
   * @param charArray are the chars to accept or to reject.
   */
  public ListCharFilter(boolean accept, char... charArray) {

    super();
    this.chars = charArray;
    this.blacklist = !accept;
  }

  @Override
  public boolean accept(char c) {

    for (char currentChar : this.chars) {
      if (c == currentChar) {
        // if blacklist we return false because we found a disallowed char,
        // else we found an accepted char from the whitelist...
        return !this.blacklist;
      }
    }
    // the char was NOT found!
    // if blacklist -> return true because c is NOT blacklisted
    // if whitelist -> return false because c is NOT in the whitelist
    return this.blacklist;
  }

}
