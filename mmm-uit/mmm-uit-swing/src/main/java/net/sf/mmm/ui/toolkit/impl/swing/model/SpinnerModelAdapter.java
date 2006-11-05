/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.model;

import javax.swing.AbstractSpinnerModel;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValue;

/**
 * This class adapts a {@link net.sf.mmm.ui.toolkit.api.model.UIListModel} to
 * a swing {@link javax.swing.SpinnerModel}.
 * 
 * @param <E>
 *        is the templated type of the list-elements.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SpinnerModelAdapter<E> extends AbstractSpinnerModel implements
    UIWriteSelectionIndex, UIWriteSelectionValue<E>, UIListModelListener {

  /** the model to adapt */
  private UIListModel<E> model;

  /** the current selection index */
  private int index;

  /**
   * The constructor.
   * 
   * @param listModel
   *        is the model to adapt.
   */
  public SpinnerModelAdapter(UIListModel<E> listModel) {

    super();
    this.model = listModel;
    this.model.addListener(this);
  }

  /**
   * This method gets the adapted model.
   * 
   * @return the model.
   */
  public UIListModel<E> getModel() {

    return this.model;
  }

  /**
   * This method sets a new model.
   * 
   * @param newModel
   *        is the new model to set.
   */
  public void setModel(UIListModel<E> newModel) {

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
   */
  public Object getValue() {

    return getSelectedValue();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndex#getSelectedIndex()
   */
  public int getSelectedIndex() {

    return this.index;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex#setSelectedIndex(int)
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
   */
  @SuppressWarnings("unchecked")
  public void setValue(Object value) {

    setSelectedValue((E) value);
  }

  /**
   * @see javax.swing.SpinnerModel#getNextValue()
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
   */
  public Object getPreviousValue() {

    int prevIndex = this.index;
    if (this.index > 0) {
      prevIndex--;
    }
    return this.model.getElement(prevIndex);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.event.UIListModelListener#listModelChanged(net.sf.mmm.ui.toolkit.api.event.UIListModelEvent)
   */
  public void listModelChanged(UIListModelEvent event) {

    int count = this.model.getElementCount();
    if (this.index >= count) {
      this.index = count - 1;
    }
    fireStateChanged();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValue#getSelectedValue()
   */
  public E getSelectedValue() {

    return this.model.getElement(this.index);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValue#setSelectedValue(Object)
   */
  public void setSelectedValue(E newValue) {

    int i = this.model.getIndexOf(newValue);
    if (i < 0) {
      throw new IllegalArgumentException("Not in model: " + newValue);
    }
    setSelectedIndex(i);
  }

}
