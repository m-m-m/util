/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.Iterator;

import net.sf.mmm.util.collection.base.AbstractIterator;

/**
 * This is a rewrite of the awkward {@link java.util.StringTokenizer} provided
 * by the JDK. This implementation {@link #next() returns} an empty
 * {@link String} if a duplicate delimiter is detected. Further it implements
 * {@link Iterable} and can be used in enhanced for-loops.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StringTokenizer extends AbstractIterator<String> implements Iterable<String> {

  /** The string to be tokenized */
  private final char[] string;

  /** The characters that will be detected as delimiters. */
  private final char[] delimiters;

  /** The current index in {@link #string}. */
  private int index;

  /**
   * The constructor.
   * 
   * @param string is the string to be tokenized.
   * @param delimiters are the characters that will be detected as delimiters.
   */
  public StringTokenizer(String string, char... delimiters) {

    this(string.toCharArray(), delimiters);
  }

  /**
   * The constructor.
   * 
   * @param string is the string to be tokenized.
   * @param delimiters is a {@link String} with all the characters that will be
   *        detected as delimiters.
   */
  public StringTokenizer(String string, String delimiters) {

    this(string.toCharArray(), delimiters.toCharArray());
  }

  /**
   * The constructor.
   * 
   * @param string is the string to be tokenized.
   * @param delimiters are the characters that will be detected as delimiters.
   */
  public StringTokenizer(char[] string, char... delimiters) {

    super();
    this.string = string;
    this.delimiters = delimiters;
    this.index = -1;
    findFirst();
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<String> iterator() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String findNext() {

    if ((this.index >= this.string.length) || (this.string.length == 0)) {
      return null;
    }
    this.index++;
    int start = this.index;
    while (this.index < this.string.length) {
      char c = this.string[this.index];
      for (char delimiter : this.delimiters) {
        if (c == delimiter) {
          String result = new String(this.string, start, this.index - start);
          return result;
        }
      }
      this.index++;
    }
    return new String(this.string, start, this.index - start);
  }

  /**
   * @see java.util.StringTokenizer#hasMoreTokens()
   * 
   * @return <code>true</code> if {@link #next()} is available,
   *         <code>false</code> otherwise.
   */
  public boolean hasMoreTokens() {

    return hasNext();
  }

  /**
   * @see java.util.StringTokenizer#nextToken()
   * 
   * @return the {@link #next() next} token.
   */
  public String nextToken() {

    return next();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    if (this.index < this.string.length) {
      return new String(this.string, this.index, this.string.length - this.index);
    } else {
      return "";
    }
  }

}
