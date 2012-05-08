/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

/**
 * This is the interface for the result of a {@link Hyphenator#hyphenate(String, int, int) hyphenation}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface Hyphenation {

  /**
   * This method gets the number of hyphenation-points in the word.
   * 
   * @return the number of hyphenation-points (e.g. <code>0</code> for "I" or "egg" or <code>1</code> for
   *         "im-pact").
   */
  int getHyphenationCount();

  /**
   * This method gets the index of the given hyphenation-point.
   * 
   * @param hyphenationIndex is the index of the hyphenation-point in the range from <code>0</code> to
   *        <code>{@link #getHyphenationCount()} - 1</code>.
   * @return the index of the position in the {@link #getWord() word}.
   */
  int getHyphenation(int hyphenationIndex);

  /**
   * This method gets the greatest {@link #getHyphenation(int) hyphenation position} that is less to the given
   * <code>offset</code>.
   * 
   * @param offset is the offset in the {@link #getWord() word} where a hyphenation is desired.
   * @return the {@link #getHyphenation(int) hyphenation position} just before the given <code>offset</code>
   *         or <code>-1</code> if there is no such position.
   */
  int getHyphenationBefore(int offset);

  /**
   * This method gets the raw word to be hyphenated.
   * 
   * @return the word without added hyphens.
   */
  String getWord();

  /**
   * @see Hyphenator#getHyphen()
   * 
   * @return the hyphen character.
   */
  char getHyphen();

  /**
   * The hyphenation of the according word (e.g. un-avail-able).
   * 
   * {@inheritDoc}
   */
  @Override
  String toString();

}
