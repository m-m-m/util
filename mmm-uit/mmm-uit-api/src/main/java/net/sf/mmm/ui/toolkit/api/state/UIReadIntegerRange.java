/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to a {@link #getMinimumValue() minimum} and
 * {@link #getMaximumValue() maximum} that define a integer value range.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadIntegerRange {

  /** this is the lowes {@link #getMinimumValue() minimum} allowed */
  int LOWEST_MINIMUM = Integer.MIN_VALUE / 2;

  /** this is the highest {@link #getMaximumValue() maximum} allowed */
  int HIGHEST_MAXIMUM = Integer.MAX_VALUE / 2;

  /**
   * This method gets the minimum value allowed.
   * 
   * @return the minimum, {@link #LOWEST_MINIMUM} for no minimum.
   */
  int getMinimumValue();

  /**
   * This method gets the maximum value allowed.
   * 
   * @return the maximum, {@link #HIGHEST_MAXIMUM} for no maximum.
   */
  int getMaximumValue();

}
