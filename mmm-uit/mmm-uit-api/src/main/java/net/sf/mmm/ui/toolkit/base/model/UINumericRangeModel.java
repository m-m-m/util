/* $Id: UINumericRangeModel.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.base.model;

import net.sf.mmm.ui.toolkit.api.event.EventType;
import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteIntegerRangeIF;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIListModelIF} interface that contains
 * a range of numeric (integer) values.<br>
 * This model should NOT be used for a regular list or combo-box widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UINumericRangeModel extends UIAbstractListModel<Integer> implements
        UIWriteIntegerRangeIF {

    /** @see #getMinimumValue() */
    private int minimum;

    /** @see #getMaximumValue() */
    private int maximum;

    /**
     * The constructor.
     */
    public UINumericRangeModel() {

        this(0, HIGHEST_MAXIMUM);
    }

    /**
     * The constructor.
     * 
     * @param min
     * @param max
     */
    public UINumericRangeModel(int min, int max) {

        super();
        this.minimum = min;
        this.maximum = max;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.model.UIAbstractListModel#handleListenerException(net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF,
     *      java.lang.Throwable)
     * {@inheritDoc}
     */
    @Override
    protected void handleListenerException(UIListModelListenerIF listener, Throwable t) {

        t.printStackTrace();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getElementCount()
     * {@inheritDoc}
     */
    public int getElementCount() {

        return this.maximum - this.minimum + 1;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getElement(int)
     * {@inheritDoc}
     */
    public Integer getElement(int index) {

        if (index < 0) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }        
        return Integer.valueOf(index + this.minimum);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getElementAsString(int)
     * {@inheritDoc}
     */
    @Override
    public String getElementAsString(int index) {
    
        if (index < 0) {
            throw new IndexOutOfBoundsException("Illegal index: " + index);
        }        
        return Integer.toString(index + this.minimum);
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteIntegerRangeIF#setMaximumValue(int)
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
                changeEvent = new UIListModelEvent(EventType.ADD, oldSize, newSize - 1);   
            } else {
                changeEvent = new UIListModelEvent(EventType.REMOVE, newSize, oldSize - 1);
            }
            this.maximum = newMaximum;
            fireChangeEvent(changeEvent);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteIntegerRangeIF#setMinimumValue(int)
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
                changeEvent = new UIListModelEvent(EventType.ADD, 0, (newMinimum - this.minimum) - 1);        
            } else {
                changeEvent = new UIListModelEvent(EventType.REMOVE, 0, (this.minimum - newMinimum) - 1);        
            }
            this.minimum = newMinimum;
            fireChangeEvent(changeEvent);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadIntegerRangeIF#getMaximumValue()
     * {@inheritDoc}
     */
    public int getMaximumValue() {

        return this.maximum;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadIntegerRangeIF#getMinimumValue()
     * {@inheritDoc}
     */
    public int getMinimumValue() {

        return this.minimum;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getIndexOf(Object)
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
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getIndexOfString(java.lang.String)
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
