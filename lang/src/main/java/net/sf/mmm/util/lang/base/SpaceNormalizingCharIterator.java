/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.lang.api.CharIterator;

/**
 * This is an implementation of the {@link CharIterator} interface that adapts a {@link CharIterator} such that spaces
 * are normalized.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class SpaceNormalizingCharIterator extends AbstractCharIterator {

  /** The underlying {@link CharIterator} to delegate to. */
  private final CharIterator delegate;

  /** {@link #SpaceNormalizingCharIterator(CharIterator, CharFilter, boolean)} */
  private final CharFilter spaceFilter;

  /** {@link #SpaceNormalizingCharIterator(CharIterator, CharFilter, boolean)} */
  private final boolean trim;

  /** The next char from lookahead. */
  private char next;

  /** {@code true} if the first non-whitespace was found (for trim). */
  private boolean foundNonWhitespace;

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
   * @param spaceFilter is the {@link CharFilter} that defines what is {@link CharFilter#accept(char) accepted} as
   *        space. It needs to {@link CharFilter#accept(char) accept} at least ' '.
   * @param trim - if {@code true} leading and trailing spaces are entirely consumed.
   */
  public SpaceNormalizingCharIterator(CharIterator delegate, CharFilter spaceFilter, boolean trim) {

    super();
    this.delegate = delegate;
    this.spaceFilter = spaceFilter;
    this.trim = trim;
    this.next = END_OF_ITERATOR;
    findFirst();
  }

  @Override
  protected char findNext() {

    char result;
    if (this.next != END_OF_ITERATOR) {
      result = this.next;
      this.next = END_OF_ITERATOR;
    } else {
      result = this.delegate.next();
      if ((result != END_OF_ITERATOR) && this.spaceFilter.accept(result)) {
        do {
          this.next = this.delegate.next();
        } while ((this.next != END_OF_ITERATOR) && this.spaceFilter.accept(this.next));
        if (this.trim & (!this.foundNonWhitespace || (this.next != END_OF_ITERATOR))) {
          result = this.next;
          this.next = END_OF_ITERATOR;
        }
      } else {
        this.foundNonWhitespace = true;
      }

    }
    return result;
  }

  @Override
  public String toString() {

    return this.delegate.toString();
  }
}
