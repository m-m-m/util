/* $Id$ */
package net.sf.mmm.ui.toolkit.base.model;

import net.sf.mmm.ui.toolkit.api.event.EventType;
import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.state.UIWriteIntegerRange;

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
   * @see net.sf.mmm.ui.toolkit.base.model.AbstractUIListModel#handleListenerException(net.sf.mmm.ui.toolkit.api.event.UIListModelListener,
   *      java.lang.Throwable)
   */
  @Override
  protected void handleListenerException(UIListModelListener listener, Throwable t) {

    t.printStackTrace();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getElementCount()
   */
  public int getElementCount() {

    return this.maximum - this.minimum + 1;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getElement(int)
   */
  public Integer getElement(int index) {

    if (index < 0) {
      throw new IndexOutOfBoundsException("Illegal index: " + index);
    }
    return Integer.valueOf(index + this.minimum);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteIntegerRange#setMaximumValue(int)
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
        changeEvent = new UIListModelEvent(EventType.ADD, oldSize, newSize - 1);
      } else {
        changeEvent = new UIListModelEvent(EventType.REMOVE, newSize, oldSize - 1);
      }
      this.maximum = newMaximum;
      fireChangeEvent(changeEvent);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteIntegerRange#setMinimumValue(int)
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
        changeEvent = new UIListModelEvent(EventType.ADD, 0, (newMinimum - this.minimum) - 1);
      } else {
        changeEvent = new UIListModelEvent(EventType.REMOVE, 0, (this.minimum - newMinimum) - 1);
      }
      this.minimum = newMinimum;
      fireChangeEvent(changeEvent);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadIntegerRange#getMaximumValue()
   */
  public int getMaximumValue() {

    return this.maximum;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadIntegerRange#getMinimumValue()
   */
  public int getMinimumValue() {

    return this.minimum;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getIndexOf(Object)
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
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getIndexOfString(java.lang.String)
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
