/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.model;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.state.UIWriteIntegerRange;
import net.sf.mmm.util.event.ChangeEvent.Type;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIListModel} interface that contains
 * a range of numeric (integer) values.<br>
 * This model should NOT be used for a regular list or combo-box widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NumericUIRangeModel extends AbstractUIListModel<Integer> implements
    UIWriteIntegerRange {

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
   * @param min
   * @param max
   */
  public NumericUIRangeModel(int min, int max) {

    super();
    this.minimum = min;
    this.maximum = max;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleListenerException(UIListModelListener listener, Throwable t) {

    t.printStackTrace();
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
      if (newMaximum > HIGHEST_MAXIMUM) {
        newMaximum = HIGHEST_MAXIMUM;
      }
      if (newMaximum < this.minimum) {
        throw new IllegalArgumentException("TODO");
      }
      int oldSize = this.maximum - this.minimum;
      int newSize = newMaximum - this.minimum;
      UIListModelEvent changeEvent;
      if (newMaximum > this.maximum) {
        changeEvent = new UIListModelEvent(Type.ADD, oldSize, newSize - 1);
      } else {
        changeEvent = new UIListModelEvent(Type.REMOVE, newSize, oldSize - 1);
      }
      this.maximum = newMaximum;
      fireChangeEvent(changeEvent);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setMinimumValue(int newMinimum) {

    if (this.minimum != newMinimum) {
      if (newMinimum < LOWEST_MINIMUM) {
        newMinimum = LOWEST_MINIMUM;
      }
      if (newMinimum > this.maximum) {
        throw new IllegalArgumentException("TODO");
      }
      UIListModelEvent changeEvent;
      if (newMinimum > this.minimum) {
        changeEvent = new UIListModelEvent(Type.ADD, 0, (newMinimum - this.minimum) - 1);
      } else {
        changeEvent = new UIListModelEvent(Type.REMOVE, 0, (this.minimum - newMinimum) - 1);
      }
      this.minimum = newMinimum;
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
  @Override
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
      Integer i = Integer.parseInt(element);
      return getIndexOf(i);
    } catch (NumberFormatException e) {
      return -1;
    }
  }

}
