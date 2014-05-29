/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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
   * This method gets the next value of the given <code>sequence</code>. The value of the {@link Sequence} is
   * incremented on each call. Each {@link Sequence} has a minimum and a maximum value. If the maximum value
   * is reached, it will be reset to the minimum value.
   *
   * @param sequence is the {@link Sequence} to get.
   * @return the next value of the sequence.
   */
  long getNextValue(Sequence sequence);

}
