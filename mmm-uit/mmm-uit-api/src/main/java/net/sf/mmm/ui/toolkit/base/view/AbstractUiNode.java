/* $Id: AbstractUINode.java 957 2011-02-21 22:18:03Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.event.UiEventListener;
import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.AbstractUiObject;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.UiNode} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiNode extends AbstractUiObject implements UiNode {

  /** the parent object */
  private UiNode parent;

  /**
   * the registered listeners (or <code>null</code> if no listener is
   * registered)
   */
  private List<UiEventListener> listeners;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parentObject is the {@link #getParent() parent} of this object. It
   *        may be <code>null</code>.
   */
  public AbstractUiNode(AbstractUiFactory uiFactory, UiNode parentObject) {

    super(uiFactory);
    this.parent = parentObject;
  }

  /**
   * {@inheritDoc}
   */
  public UiNode getParent() {

    return this.parent;
  }

  /**
   * This method sets the parent of this UI object. This is necessary if an
   * object was constructed by this factory (parent is <code>null</code>) and is
   * then added to a UI composite (or whenever it moves from one composite to
   * another).
   * 
   * @param newParent is the new parent of this object. It may be
   *        <code>null</code> to remove the object from the UI tree.
   */
  public void setParent(UiNode newParent) {

    this.parent = newParent;
  }

  /**
   * This method sets the parent of the given <code>chhildObject</code> to the
   * given <code>parentObject</code>. The method only exists for visibility
   * reasons (so the <code>setParent</code> method can be protected).
   * 
   * @param childObject is the UI object whos parent is to be set.
   * @param parentObject is the parent UI object to set.
   */
  protected void setParent(AbstractUiNode childObject, UiNode parentObject) {

    /*
     * UINode oldParent = childObject.getParent(); if (oldParent != null) { if
     * (oldParent.isComposite()) { ((UIComposite)
     * oldParent).removeComponent(childObject); } }
     */
    childObject.setParent(parentObject);
  }

  /**
   * {@inheritDoc}
   */
  public UiFrame getParentFrame() {

    if (getType() == UiFrame.TYPE) {
      // the only legal parent of frame is another frame (or null).
      return ((UiFrame) getParent());
    } else {
      if (getParent() == null) {
        return null;
      } else if (getParent().getType() == UiFrame.TYPE) {
        return ((UiFrame) getParent());
      } else {
        return getParent().getParentFrame();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public UiWindow getParentWindow() {

    if (this instanceof UiWindow) {
      return ((UiWindow) getParent());
    } else {
      if (getParent() == null) {
        return null;
      } else if (getParent() instanceof UiWindow) {
        return ((UiWindow) getParent());
      } else {
        return getParent().getParentWindow();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void addListener(UiEventListener listener) {

    synchronized (this) {
      if (this.listeners == null) {
        if (!doInitializeListener()) {
          return;
        }
        this.listeners = new ArrayList<UiEventListener>();
      }
      this.listeners.add(listener);
    }
  }

  /**
   * This method registers the listener adapter in the wrapped UI object. It is
   * called only once when the first listener is registered.
   * 
   * @return <code>false</code> if this UI object will never send any event
   *         therefore no listeners must be registered. If you override this
   *         method to initialize the listener adapter, you should return
   *         <code>true</code>.
   */
  protected boolean doInitializeListener() {

    return false;
  }

  /**
   * This method invokes an action by notifying all registered listeners.
   * 
   * @param action is the action that is invoked.
   */
  public void fireEvent(UiEventType action) {

    if (this.listeners != null) {
      // TODO: concurrency problem if a listener is added during loop
      for (int i = 0; i < this.listeners.size(); i++) {
        try {
          this.listeners.get(i).onEvent(this, action);
        } catch (RuntimeException t) {
          getFactory().handleEventError(t);
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void removeListener(UiEventListener listener) {

    synchronized (this) {
      if (this.listeners != null) {
        this.listeners.remove(listener);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void refresh() {

    refresh(UIRefreshEvent.DEFAULT);
  }

  /**
   * This method refreshes this node.
   * 
   * @param event is the event with details about the refresh.
   */
  public void refresh(UIRefreshEvent event) {

    // do nothing by default...
  }

}
