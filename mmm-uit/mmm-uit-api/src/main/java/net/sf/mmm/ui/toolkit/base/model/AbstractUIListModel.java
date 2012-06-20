/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.model;

import java.util.List;
import java.util.Vector;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.util.event.api.ChangeType;

/**
 * This is the abstract base implementation of the {@link net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel}
 * interface.
 * 
 * @param <E> is the templated type of the elements in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUIListModel<E> implements UiListMvcModel<E> {

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
   * {@inheritDoc}
   */
  @Override
  public void addListener(UIListModelListener listener) {

    this.listeners.add(listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeListener(UIListModelListener listener) {

    this.listeners.remove(listener);
  }

  /**
   * This method sends the given event to all registered listeners of this model.
   * 
   * @param event is the event to send.
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
   * This method creates an event for the given change and sends it to all registered listeners of this model.
   * 
   * @param type is the type change.
   * @param startIndex is the index of the first item that has changed.
   * @param endIndex is the index of the last item that has changed.
   */
  protected void fireChangeEvent(ChangeType type, int startIndex, int endIndex) {

    fireChangeEvent(new UIListModelEvent(type, startIndex, endIndex));
  }

  /**
   * This method is called by the <code>fireChangeEvent</code> method if a listener caused an exception or
   * error.
   * 
   * @param listener is the listener that threw the exception or error.
   * @param t is the exception or error.
   */
  protected void handleListenerException(UIListModelListener listener, Throwable t) {

    // TODO: log?
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(E element) {

    return element.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getNullString() {

    return "";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getElementAsString(int index) {

    E element = getElement(index);
    if (element == null) {
      // actually elements should NOT be null...
      return getNullString();
    }
    return toString(element);
  }

}
