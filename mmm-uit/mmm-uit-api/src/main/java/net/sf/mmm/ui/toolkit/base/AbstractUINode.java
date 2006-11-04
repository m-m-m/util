/* $Id$ */
package net.sf.mmm.ui.toolkit.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListener;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.api.window.UIWindow;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UINode} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUINode extends AbstractUIObject implements UINode {

  /** the parent object */
  private UINode parent;

  /**
   * the registered listeners (or <code>null</code> if no listener is
   * registered)
   */
  private List<UIActionListener> listeners;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the
   *        {@link net.sf.mmm.ui.toolkit.api.UIObject#getFactory() factory}
   *        instance.
   * @param parentObject
   *        is the {@link net.sf.mmm.ui.toolkit.api.UINode#getParent() parent}
   *        that created this object. It may be <code>null</code>.
   */
  public AbstractUINode(UIFactory uiFactory, UINode parentObject) {

    super(uiFactory);
    this.parent = parentObject;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UINode#getParent()
   * 
   */
  public UINode getParent() {

    return this.parent;
  }

  /**
   * This method sets the parent of this UI object. This is neccessary if an
   * object was constructed by this factory (parent is <code>null</code>) and
   * is then added to a UI composite (or whenever it moves from one composite to
   * another).
   * 
   * @param newParent
   *        is the new parent of this object.
   */
  public void setParent(UINode newParent) {

    this.parent = newParent;
  }

  /**
   * This method sets the parent of the given <code>chhildObject</code> to the
   * given <code>parentObject</code>. The method only exists for visibility
   * reasons (so the <code>setParent</code> method can be protected).
   * 
   * @param childObject
   *        is the UI object whos parent is to be set.
   * @param parentObject
   *        is the parent UI object to set.
   */
  protected void setParent(AbstractUINode childObject, UINode parentObject) {

    /*
     * UINode oldParent = childObject.getParent(); if (oldParent != null) { if
     * (oldParent.isComposite()) { ((UIComposite)
     * oldParent).removeComponent(childObject); } }
     */
    childObject.setParent(parentObject);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UINode#getParentFrame()
   * 
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
   * @see net.sf.mmm.ui.toolkit.api.UINode#getParentWindow()
   * 
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
   * @see net.sf.mmm.ui.toolkit.api.UINode#addActionListener(net.sf.mmm.ui.toolkit.api.event.UIActionListener)
   * 
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
   * @param action
   *        is the action that is invoked.
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
   * @see net.sf.mmm.ui.toolkit.api.UINode#removeActionListener(net.sf.mmm.ui.toolkit.api.event.UIActionListener)
   * 
   */
  public void removeActionListener(UIActionListener listener) {

    synchronized (this) {
      if (this.listeners != null) {
        this.listeners.remove(listener);
      }
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UINode#refresh()
   * 
   */
  public void refresh() {

  // do nothing by default...
  }

}
