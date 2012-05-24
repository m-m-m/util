/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to a {@link #getMinimumValue() minimum} and
 * {@link #getMaximumValue() maximum} that define a integer value range.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteIntegerRange extends UiReadIntegerRange {

  /**
   * This method sets the minimum value.
   * 
   * @param newMinimum is the new minimum value. It must be less than (or equal to) the
   *        {@link #getMaximumValue() maximum}. Use {@link #LOWEST_MINIMUM} for no minimum. Also
   *        {@link #LOWEST_MINIMUM} will be set if the value is less than that.
   * @throws IllegalArgumentException if the given value is greater than the {@link #getMaximumValue()
   *         maximum}.
   */
  void setMinimumValue(int newMinimum) throws IllegalArgumentException;

  /**
   * This method sets the maximum value.
   * 
   * @param newMaximum is the new maximum value. It must be greater than (or equal to) the
   *        {@link #getMinimumValue() minimum}. Use {@link #HIGHEST_MAXIMUM} for no maximum. Also
   *        {@link #HIGHEST_MAXIMUM} will be set if the value is greater than that.
   * @throws IllegalArgumentException if the given value is less than the {@link #getMinimumValue() minimum}.
   */
  void setMaximumValue(int newMaximum) throws IllegalArgumentException;

}
