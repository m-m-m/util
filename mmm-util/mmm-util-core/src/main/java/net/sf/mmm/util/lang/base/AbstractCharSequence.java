/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

/**
 * This is an abstract implementation of the {@link java.lang.CharSequence} to make life easier.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public abstract class AbstractCharSequence extends CoreCharSequence {

  /**
   * The constructor.
   */
  public AbstractCharSequence() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public CharSequence subSequence(int startPosition, int endPosition) {

    if (startPosition < 0) {
      throw new IndexOutOfBoundsException("Start (" + startPosition + ") must not be negative!");
    }
    if (endPosition < startPosition) {
      throw new IndexOutOfBoundsException("End (" + endPosition + ") must be greater or equal to start ("
          + startPosition + ")!");
    }
    if (endPosition > length()) {
      throw new IndexOutOfBoundsException("End (" + endPosition + ") greater than length of sequence (" + length()
          + ")");
    }
    return new CharSubSequence(this, startPosition, endPosition);
  }

}
