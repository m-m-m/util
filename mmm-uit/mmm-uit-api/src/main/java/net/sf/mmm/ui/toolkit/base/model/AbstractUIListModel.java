/* $Id$ */
package net.sf.mmm.ui.toolkit.base.model;

import java.util.List;
import java.util.Vector;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIListModel} interface.
 * 
 * @param <E>
 *        is the templated type of the elements in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIListModel<E> implements UIListModel<E> {

  /** the listeners of the model */
  private List<UIListModelListener> listeners;

  /**
   * The constructor.
   */
  public AbstractUIListModel() {

    super();
    this.listeners = new Vector<UIListModelListener>();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#addListener(net.sf.mmm.ui.toolkit.api.event.UIListModelListener)
   */
  public void addListener(UIListModelListener listener) {

    this.listeners.add(listener);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#removeListener(net.sf.mmm.ui.toolkit.api.event.UIListModelListener)
   */
  public void removeListener(UIListModelListener listener) {

    this.listeners.remove(listener);
  }

  /**
   * This method sends the given event to all registered listeners of this
   * model.
   * 
   * @param event
   *        is the event to send.
   */
  protected void fireChangeEvent(UIListModelEvent event) {

    for (int i = 0; i < this.listeners.size(); i++) {
      UIListModelListener listener = this.listeners.get(i);
      try {
        listener.listModelChanged(event);
      } catch (Throwable t) {
        handleListenerException(listener, t);
      }
    }
  }

  /**
   * This method is called by the <code>fireChangeEvent</code> method if a
   * listener caused an exception or error.
   * 
   * @param listener
   *        is the listener that threw the exception or error.
   * @param t
   *        is the exception or error.
   */
  protected abstract void handleListenerException(UIListModelListener listener, Throwable t);

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#toString(java.lang.Object)
   */
  public String toString(E element) {

    if (element == null) {
      return "";
    } else {
      return element.toString();
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getElementAsString(int)
   */
  public String getElementAsString(int index) {

    return toString(getElement(index));
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getIndexOf(Object)
   */
  public int getIndexOf(E element) {

    return -1;
  }

}
