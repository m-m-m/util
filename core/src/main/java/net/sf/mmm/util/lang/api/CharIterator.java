/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for an {@link java.util.Iterator} of primitive char values. <br>
 * The intention of {@link CharIterator} is to allow faster processing of large streams of characters by using the
 * primitive type {@code char} and by making calls of {@link #hasNext()} unnecessary.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface CharIterator {

  /**
   * This is a character that is illegal according to the unicode specification. It is used to indicate that this
   * iterator has reached its end.
   */
  char END_OF_ITERATOR = 0xfeff;

  /**
   * This method determines whether there is a {@link #next() next} char available or the end of this iterator has been
   * reached.
   *
   * @see #next()
   * @see java.util.Iterator#hasNext()
   *
   * @return {@code true} if there is at least one {@link #next() next} char available, or {@code false} if the end of
   *         this iterator has been reached and further calls of {@link #next()} will return {@link #END_OF_ITERATOR}.
   */
  boolean hasNext();

  /**
   * This method returns the next character to iterate or {@link #END_OF_ITERATOR} if the end of this iterator has been
   * reached. If {@link #END_OF_ITERATOR} is returned further calls will always return {@link #END_OF_ITERATOR}.
   *
   * @see java.util.Iterator#next()
   *
   * @return the next character or {@link #END_OF_ITERATOR} if the end of this iterator has been reached.
   */
  char next();

}
