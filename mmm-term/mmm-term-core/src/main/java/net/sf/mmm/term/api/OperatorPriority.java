/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

/**
 * This enum contains the available
 * {@link Function#getOperatorPriority() "operator priorities"}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum OperatorPriority {

  /** the lowest available priority */
  MINIMUM(0),

  /** a low priority */
  LOW(2),

  /** a medium priority */
  MEDIUM(4),

  /** a high priority */
  HIGH(6),

  /** the highest available priority */
  MAXIMUM(8);

  /** UID for serialization */
  private static final long serialVersionUID = -5989649807734768L;

  /** The actual priority value. It can be used to compare priorities */
  public final int priority;

  /**
   * The constructor.
   * 
   * @param prio
   *        is the actual priority value.
   */
  private OperatorPriority(int prio) {

    this.priority = prio;
  }

  /**
   * This method gets the {@link OperatorPriority} with the given
   * {@link #priority}.
   * 
   * @param prio
   *        is the {@link #priority} of the requested {@link OperatorPriority}.
   * @return the requested {@link OperatorPriority}.
   * @throws IllegalArgumentException
   *         if no {@link OperatorPriority} exists with a {@link #priority} of
   *         <code>prio</code>.
   */
  public static OperatorPriority valueOf(int prio) {

    switch (prio) {
      // case MINIMUM.priority: {
      case 0: {
        return MINIMUM;
      }
        // case LOW.priority: {
      case 2: {
        return LOW;
      }
        // case MEDIUM.priority: {
      case 4: {
        return MEDIUM;
      }
        // case HIGH.priority: {
      case 6: {
        return HIGH;
      }
        // case MAXIMUM.priority: {
      case 8: {
        return MAXIMUM;
      }
      default : {
        throw new IllegalArgumentException(Integer.toString(prio));
      }
    }
  }

}
