/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UIFactoryRenamed;
import net.sf.mmm.ui.toolkit.api.UINodeRenamed;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListener;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.api.window.UIWindow;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UINodeRenamed} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUINode extends AbstractUIObject implements UINodeRenamed {

  /** the parent object */
  private UINodeRenamed parent;

  /**
   * the registered listeners (or <code>null</code> if no listener is
   * registered)
   */
  private List<UIActionListener> listeners;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parentObject is the
   *        {@link net.sf.mmm.ui.toolkit.api.UINodeRenamed#getParent() parent} that
   *        created this object. It may be <code>null</code>.
   */
  public AbstractUINode(AbstractUIFactory uiFactory, UINodeRenamed parentObject) {

    super(uiFactory);
    this.parent = parentObject;
  }

  /**
   * {@inheritDoc}
   */
  public UINodeRenamed getParent() {

    return this.parent;
  }

  /**
   * This method sets the parent of this UI object. This is neccessary if an
   * object was constructed by this factory (parent is <code>null</code>) and
   * is then added to a UI composite (or whenever it moves from one composite to
   * another).
   * 
   * @param newParent is the new parent of this object.
   */
  public void setParent(UINodeRenamed newParent) {

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
  protected void setParent(AbstractUINode childObject, UINodeRenamed parentObject) {

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
  public UIFrame getParentFrame() {

    if (getType() == UIFrame.TYPE) {
      if (getParent() == null) {
        return null;
      } else {
        // the only legal parent of frame is another frame.
        return ((UIFrame) getParent());
      }
    } else {
      if (getParent() == null) {
        return null;
      } else if (getParent().getType() == UIFrame.TYPE) {
        return ((UIFrame) getParent());
      } else {
        return getParent().getParentFrame();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public UIWindow getParentWindow() {

    if (isWindow()) {
      if (getParent() == null) {
        return null;
      } else {
        return ((UIWindow) getParent());
      }
    } else {
      if (getParent() == null) {
        return null;
      } else if (getParent().isWindow()) {
        return ((UIWindow) getParent());
      } else {
        return getParent().getParentWindow();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void addActionListener(UIActionListener listener) {

    synchronized (this) {
      if (this.listeners == null) {
        if (!doInitializeListener()) {
          return;
        }
        this.listeners = new ArrayList<UIActionListener>();
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
  public void invoke(ActionType action) {

    if (this.listeners != null) {
      for (int i = 0; i < this.listeners.size(); i++) {
        try {
          this.listeners.get(i).invoke(this, action);
        } catch (Throwable t) {
          t.printStackTrace();
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void removeActionListener(UIActionListener listener) {

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
