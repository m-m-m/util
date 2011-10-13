/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.sequence;

/**
 * This is the interface for a manager of database sequences.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SequenceManager {

  /**
   * This method gets the next value of the given <code>sequence</code>. The
   * value of the {@link Sequence} is incremented on each call. This method will
   * also handle the wrapping of the {@link Sequence} according to
   * {@link Sequence#getMaximumValue()}.
   * 
   * @param sequence is the {@link Sequence} to get.
   * @return the next value of the sequence.
   */
  long getNextValue(Sequence sequence);

  /**
   * This method sets the specified <code>sequence</code> to the given
   * <code>value</code>. The next call of {@link #getNextValue(Sequence)} will
   * return the given <code>value</code>.
   * 
   * @param sequence is the {@link Sequence} to set.
   * @param value the value to set. Has to be in the range of
   *        {@link Sequence#getMinimumValue()} and
   *        {@link Sequence#getMaximumValue()}.
   */
  void setValue(Sequence sequence, long value);

  /**
   * This method resets the given <code>sequence</code> to
   * {@link Sequence#getMinimumValue()}.
   * 
   * @param sequence is the {@link Sequence} to reset.
   */
  void reset(Sequence sequence);

}
