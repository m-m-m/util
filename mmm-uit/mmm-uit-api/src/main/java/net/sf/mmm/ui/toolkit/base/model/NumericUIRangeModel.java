/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.model;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteIntegerRange;
import net.sf.mmm.util.event.api.ChangeType;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel} interface that
 * contains a range of numeric (integer) values.<br>
 * This model should NOT be used for a regular list or combo-box widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NumericUIRangeModel extends AbstractUIListModel<Integer> implements UiWriteIntegerRange {

  /** @see #getMinimumValue() */
  private int minimum;

  /** @see #getMaximumValue() */
  private int maximum;

  /**
   * The constructor.
   */
  public NumericUIRangeModel() {

    this(0, HIGHEST_MAXIMUM);
  }

  /**
   * The constructor.
   * 
   * @param min is the {@link #getMinimumValue() minimum value}.
   * @param max is the {@link #getMaximumValue() maximum value}.
   */
  public NumericUIRangeModel(int min, int max) {

    super();
    this.minimum = min;
    this.maximum = max;
  }

  /**
   * {@inheritDoc}
   */
  public int getElementCount() {

    return this.maximum - this.minimum + 1;
  }

  /**
   * {@inheritDoc}
   */
  public Integer getElement(int index) {

    if (index < 0) {
      throw new IndexOutOfBoundsException("Illegal index: " + index);
    }
    return Integer.valueOf(index + this.minimum);
  }

  /**
   * {@inheritDoc}
   */
  public void setMaximumValue(int newMaximum) {

    if (this.maximum != newMaximum) {
      if (newMaximum < this.minimum) {
        throw new IllegalArgumentException("TODO");
      }
      int max = newMaximum;
      if (max > HIGHEST_MAXIMUM) {
        max = HIGHEST_MAXIMUM;
      }
      int oldSize = this.maximum - this.minimum;
      int newSize = max - this.minimum;
      UIListModelEvent changeEvent;
      if (max > this.maximum) {
        changeEvent = new UIListModelEvent(ChangeType.ADD, oldSize, newSize - 1);
      } else {
        changeEvent = new UIListModelEvent(ChangeType.REMOVE, newSize, oldSize - 1);
      }
      this.maximum = max;
      fireChangeEvent(changeEvent);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setMinimumValue(int newMinimum) {

    if (this.minimum != newMinimum) {
      if (newMinimum > this.maximum) {
        throw new IllegalArgumentException("TODO");
      }
      int min = newMinimum;
      if (min < LOWEST_MINIMUM) {
        min = LOWEST_MINIMUM;
      }
      UIListModelEvent changeEvent;
      if (min > this.minimum) {
        changeEvent = new UIListModelEvent(ChangeType.ADD, 0, (min - this.minimum) - 1);
      } else {
        changeEvent = new UIListModelEvent(ChangeType.REMOVE, 0, (this.minimum - min) - 1);
      }
      this.minimum = min;
      fireChangeEvent(changeEvent);
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getMaximumValue() {

    return this.maximum;
  }

  /**
   * {@inheritDoc}
   */
  public int getMinimumValue() {

    return this.minimum;
  }

  /**
   * {@inheritDoc}
   */
  public int getIndexOf(Integer element) {

    if ((element.intValue() < this.minimum) || (element.intValue() > this.maximum)) {
      return -1;
    }
    int index = element.intValue() - this.minimum;
    return index;
  }

  /**
   * {@inheritDoc}
   */
  public int getIndexOfString(String element) {

    try {
      int i = Integer.parseInt(element);
      return getIndexOf(Integer.valueOf(i));
    } catch (NumberFormatException e) {
      return -1;
    }
  }

}
