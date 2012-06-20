/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.model;

import java.util.List;
import java.util.Vector;

import net.sf.mmm.ui.toolkit.api.event.UITreeModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UITreeModelListener;
import net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel;
import net.sf.mmm.util.event.api.ChangeType;

/**
 * This is the abstract base implementation of the {@link net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel}
 * interface.
 * 
 * @param <N> is the templated type of the tree nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUITreeModel<N> implements UiTreeMvcModel<N> {

  /** the listeners of the model */
  private List<UITreeModelListener<N>> listeners;

  /**
   * The constructor.
   */
  public AbstractUITreeModel() {

    super();
    this.listeners = new Vector<UITreeModelListener<N>>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addListener(UITreeModelListener<N> listener) {

    this.listeners.add(listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeListener(UITreeModelListener<N> listener) {

    this.listeners.remove(listener);
  }

  /**
   * This method sends the given event to all registered listeners of this model.
   * 
   * @param event is the event to send.
   */
  protected void fireChangeEvent(UITreeModelEvent<N> event) {

    for (int i = 0; i < this.listeners.size(); i++) {
      UITreeModelListener<N> listener = this.listeners.get(i);
      try {
        listener.treeModelChanged(event);
      } catch (Throwable t) {
        handleListenerException(listener, t);
      }
    }
  }

  /**
   * This method creates an event for the given change and sends it to all registered listeners of this model.
   * 
   * @param type is the type change.
   * @param node is the node that changed.
   */
  protected void fireChangeEvent(ChangeType type, N node) {

    fireChangeEvent(new UITreeModelEvent<N>(type, node));
  }

  /**
   * {@inheritDoc}
   * 
   * ATTENTION: This method assumes the {@link Object#getClass() class} of the {@link #getRootNode()
   * root-node} as node-type. Please override this method if this is NOT applicable.
   */
  @Override
  @SuppressWarnings("unchecked")
  public Class<? extends N> getNodeType() {

    return (Class<? extends N>) getRootNode().getClass();
  }

  /**
   * This method is called by the {@link #fireChangeEvent(UITreeModelEvent)} method if a listener caused an
   * exception or error.
   * 
   * @param listener is the listener that threw the exception or error.
   * @param t is the exception or error.
   */
  protected void handleListenerException(UITreeModelListener listener, Throwable t) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(N node) {

    if (node == null) {
      // this is an error...
      return "NULL";
    } else {
      return node.toString();
    }
  }

}
