/* $Id: SpinnerModelAdapter.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.model;

import javax.swing.AbstractSpinnerModel;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF;
import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndexIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValueIF;

/**
 * This class adapts a {@link net.sf.mmm.ui.toolkit.api.model.UIListModelIF} to
 * a swing {@link javax.swing.SpinnerModel}.
 * 
 * @param <E>
 *        is the templated type of the list-elements.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SpinnerModelAdapter<E> extends AbstractSpinnerModel implements
        UIWriteSelectionIndexIF, UIWriteSelectionValueIF<E>, UIListModelListenerIF {

    /** the model to adapt */
    private UIListModelIF<E> model;

    /** the current selection index */
    private int index;

    /**
     * The constructor.
     * 
     * @param listModel
     *        is the model to adapt.
     */
    public SpinnerModelAdapter(UIListModelIF<E> listModel) {

        super();
        this.model = listModel;
        this.model.addListener(this);
    }

    /**
     * This method gets the adapted model.
     * 
     * @return the model.
     */
    public UIListModelIF<E> getModel() {

        return this.model;
    }

    /**
     * This method sets a new model.
     * 
     * @param newModel
     *        is the new model to set.
     */
    public void setModel(UIListModelIF<E> newModel) {

        this.index = 0;
        if (this.model != null) {
            this.model.removeListener(this);
        }
        this.model = newModel;
        this.model.addListener(this);
        fireStateChanged();
    }

    /**
     * @see javax.swing.SpinnerModel#getValue()
     * {@inheritDoc}
     */
    public Object getValue() {

        return getSelectedValue();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndexIF#getSelectedIndex()
     * {@inheritDoc}
     */
    public int getSelectedIndex() {

        return this.index;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndexIF#setSelectedIndex(int)
     * {@inheritDoc}
     */
    public void setSelectedIndex(int newIndex) {

        if (this.index != newIndex) {
            // TODO: validate!
            this.index = newIndex;
            fireStateChanged();
        }
    }

    /**
     * @see javax.swing.SpinnerModel#setValue(java.lang.Object)
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void setValue(Object value) {

        setSelectedValue((E) value);
    }

    /**
     * @see javax.swing.SpinnerModel#getNextValue()
     * {@inheritDoc}
     */
    public Object getNextValue() {

        int max = this.model.getElementCount() - 1;
        int nextIndex;
        if (this.index >= max) {
            nextIndex = max;
        } else {
            nextIndex = this.index + 1;
        }
        return this.model.getElement(nextIndex);
    }

    /**
     * @see javax.swing.SpinnerModel#getPreviousValue()
     * {@inheritDoc}
     */
    public Object getPreviousValue() {

        int prevIndex = this.index;
        if (this.index > 0) {
            prevIndex--;
        }
        return this.model.getElement(prevIndex);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF#listModelChanged(net.sf.mmm.ui.toolkit.api.event.UIListModelEvent)
     * {@inheritDoc}
     */
    public void listModelChanged(UIListModelEvent event) {

        int count = this.model.getElementCount();
        if (this.index >= count) {
            this.index = count - 1;
        }
        fireStateChanged();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValueIF#getSelectedValue()
     * {@inheritDoc}
     */
    public E getSelectedValue() {
        
        return this.model.getElement(this.index);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValueIF#setSelectedValue(Object)
     * {@inheritDoc}
     */
    public void setSelectedValue(E newValue) {

        int i = this.model.getIndexOf(newValue);
        if (i < 0) {
            throw new IllegalArgumentException("Not in model: " + newValue);
        }
        setSelectedIndex(i);
    }

}
