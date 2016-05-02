/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.lang.api.CharIterator;

/**
 * This is an implementation of the {@link CharIterator} interface that adapts a {@link CharIterator} such
 * that spaces are normalized.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class SpaceNormalizingCharIterator implements CharIterator {

  /** The underlying {@link CharIterator} to delegate to. */
  private final CharIterator delegate;

  /** {@link #SpaceNormalizingCharIterator(CharIterator, CharFilter, boolean)} */
  private final CharFilter spaceFilter;

  /** {@link #SpaceNormalizingCharIterator(CharIterator, CharFilter, boolean)} */
  private final boolean trim;

  /**
   * {@code true} if {@link #next()} has already been called, initially {@code false}.
   */
  private boolean nextCalled;

  /**
   * A buffer for the next non-space char if lookahead went to far, or ' ' if no char is buffered.
   */
  private char nextNonSpace;

  /**
   * The constructor.
   * 
   * @param delegate is the {@link CharIterator} to adapt.
   */
  public SpaceNormalizingCharIterator(CharIterator delegate) {

    this(delegate, CharFilter.WHITESPACE_FILTER, true);
  }

  /**
   * The constructor.
   * 
   * @param delegate is the {@link CharIterator} to adapt.
   * @param spaceFilter is the {@link CharFilter} that defines what is {@link CharFilter#accept(char)
   *        accepted} as space. It needs to {@link CharFilter#accept(char) accept} at least ' '.
   * @param trim - if {@code true} leading and trailing spaces are entirely consumed.
   */
  public SpaceNormalizingCharIterator(CharIterator delegate, CharFilter spaceFilter, boolean trim) {

    super();
    this.delegate = delegate;
    this.spaceFilter = spaceFilter;
    this.trim = trim;
    this.nextNonSpace = ' ';
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public char next() {

    char result;
    if (this.nextNonSpace != ' ') {
      result = this.nextNonSpace;
      if (this.nextNonSpace != END_OF_ITERATOR) {
        this.nextNonSpace = ' ';
      }
    } else {
      char next = this.delegate.next();
      char space = 0;
      while ((next != END_OF_ITERATOR) && this.spaceFilter.accept(next)) {
        if (next == '\n') {
          space = next;
        } else if (space == 0) {
          space = ' ';
        }
        next = this.delegate.next();
      }
      // space(s) found?
      if (space == 0) {
        result = next;
      } else {
        this.nextNonSpace = next;
        if (this.trim && ((this.nextNonSpace == END_OF_ITERATOR) || (!this.nextCalled))) {
          result = next;
        } else {
          result = space;
        }
      }
      this.nextCalled = true;
    }
    return result;
  }
}
