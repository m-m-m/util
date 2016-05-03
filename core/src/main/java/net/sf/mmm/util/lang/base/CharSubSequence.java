/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

/**
 * This is an implementation of the {@link java.lang.CharSequence} Interface that can be used to implement the
 * method {@link java.lang.CharSequence#subSequence(int, int)}. <br>
 * ATTENTION: This implementation assumes that the original char sequence has an immutable
 * {@link CharSequence#length() length} (at least it should NOT shrink)!
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class CharSubSequence extends CoreCharSequence {

  /** the original sequence that is wrapped here */
  private final CharSequence original;

  /** the start position of the sub-sequence */
  private final int start;

  /** the length of the sub-sequence */
  private final int length;

  /**
   * The constructor.
   * 
   * @param originalSequence is the underlying sequence.
   * @param startPosition is the start-index in {@code originalSequence}.
   * @param endPosition is the end-index in {@code originalSequence}.
   */
  public CharSubSequence(CharSequence originalSequence, int startPosition, int endPosition) {

    super();
    this.original = originalSequence;
    this.start = startPosition;
    this.length = endPosition - startPosition;
  }

  @Override
  public CharSequence subSequence(int startPosition, int endPosition) {

    if (startPosition < 0) {
      throw new IndexOutOfBoundsException("Start (" + startPosition + ") must not be negative!");
    }
    if (endPosition < startPosition) {
      throw new IndexOutOfBoundsException("End (" + endPosition + ") must be greater or equal to start ("
          + startPosition + ")!");
    }
    // do not allow to resize the sequence to read beyond the shrinked
    // sub-sequence.
    if (endPosition > length()) {
      throw new IndexOutOfBoundsException("End (" + endPosition + ") greater than length of sequence (" + length()
          + ")");
    }
    return new CharSubSequence(this.original, this.start + startPosition, endPosition);
  }

  @Override
  public int length() {

    return this.length;
  }

  @Override
  public char charAt(int index) {

    return this.original.charAt(index + this.start);
  }

}
