/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.model;

import javax.swing.AbstractListModel;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.util.event.ChangeEvent.Type;

/**
 * This class adapts a {@link net.sf.mmm.ui.toolkit.api.model.UIListModel} to
 * a swing {@link javax.swing.ListModel}.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ListModelAdapter<E> extends AbstractListModel implements UIListModelListener {

  /** uid for serialization */
  private static final long serialVersionUID = -6062581165914420738L;

  /** the original list model */
  protected final UIListModel<E> model;

  /**
   * The constructor.
   * 
   * @param listModel
   *        is the list model to adapt to swing.
   */
  public ListModelAdapter(UIListModel<E> listModel) {

    super();
    this.model = listModel;
    this.model.addListener(this);
  }

  /**
   * @see javax.swing.ListModel#getElementAt(int)
   */
  public Object getElementAt(int index) {

    return this.model.getElement(index);
  }

  /**
   * @see javax.swing.ListModel#getSize()
   */
  public int getSize() {

    return this.model.getElementCount();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.event.UIListModelListener#listModelChanged(net.sf.mmm.ui.toolkit.api.event.UIListModelEvent)
   */
  public void listModelChanged(UIListModelEvent event) {

    if (event.getType() == Type.ADD) {
      fireIntervalAdded(this, event.getStartIndex(), event.getEndIndex());
    } else if (event.getType() == Type.REMOVE) {
      fireIntervalRemoved(this, event.getStartIndex(), event.getEndIndex());
      System.out.println(event.getStartIndex() + "-" + event.getEndIndex());
    } else if (event.getType() == Type.UPDATE) {
      fireContentsChanged(this, event.getStartIndex(), event.getEndIndex());
    }
  }

  /**
   * This method gets the list-model that is adapted.
   * 
   * @return the actual list-model.
   */
  public UIListModel<E> getModel() {

    return this.model;
  }

  /**
   * This method disposes this model adapter. It is called when the adapter is
   * NOT used anymore and can be eaten by the garbarge-collector. The
   * implementation of this method can tidy-up (e.g. remove registered
   * listeners).
   */
  public void dispose() {

    this.model.removeListener(this);
  }

}
