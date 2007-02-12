/* $Id$
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

/**
 * This is an implementation of the {@link CharFilter} interface that
 * {@link #accept(char) filters} characters according to a
 * {@link ListCharFilter#ListCharFilter(boolean, char...) given} blacklist or
 * whitelist.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ListCharFilter implements CharFilter {

  /** @see #accept(char) */
  private boolean blacklist;

  /** @see #accept(char) */
  private final char[] chars;

  /**
   * The constructor
   * 
   * @param accept -
   *        if <code>true</code> exactly the chars given by
   *        <code>charArray</code> are accepted (whitelist), if
   *        <code>false</code> exactly these chars are NOT accepted
   *        (blacklist).
   * @param charArray
   *        are the chars to accept or to reject.
   */
  public ListCharFilter(boolean accept, char... charArray) {

    super();
    this.chars = charArray;
    this.blacklist = !accept;
  }

  /**
   * @see net.sf.mmm.util.CharFilter#accept(char)
   */
  public boolean accept(char c) {

    for (char currentChar : this.chars) {
      if (c == currentChar) {
        if (this.blacklist) {
          // we found a blacklisted char
          return false;
        } else {
          return true;
        }
      }
    }
    // the char was NOT found!
    // if blacklist -> return true because c is NOT blacklisted
    // if whitelist -> return false because c is NOT in the whiltelist
    return this.blacklist;
  }

}
