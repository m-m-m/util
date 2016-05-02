/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.lang.api.CharIterator;

/**
 * This is an implementation of the {@link CharIterator} interface that simply iterates a
 * {@link #SequenceCharIterator(CharSequence) given} {@link CharSequence}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class SequenceCharIterator implements CharIterator {

  /** @see #SequenceCharIterator(CharSequence) */
  private final CharSequence sequence;

  /** The {@link CharSequence#length() length} of the {@link #sequence}. */
  private final int length;

  /** The current {@link CharSequence#charAt(int) index} in {@link #sequence}. */
  private int index;

  /**
   * The constructor.
   * 
   * @param sequence the {@link CharSequence} to {@link #next() iterate} char by char.
   */
  public SequenceCharIterator(CharSequence sequence) {

    super();
    this.sequence = sequence;
    this.length = this.sequence.length();
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    return (this.index < this.length);
  }

  /**
   * {@inheritDoc}
   */
  public char next() {

    if (this.index < this.length) {
      return this.sequence.charAt(this.index++);
    }
    return END_OF_ITERATOR;
  }

}
