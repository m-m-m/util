/* $Id: AbstractUINode.java 957 2011-02-21 22:18:03Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteVisible;
import net.sf.mmm.ui.toolkit.api.common.EnabledState;
import net.sf.mmm.ui.toolkit.api.common.VisibleState;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.event.UiEventListener;
import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.AbstractUiObject;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.UiNode} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiNode extends AbstractUiObject implements UiNode, UiWriteVisible,
    UiWriteEnabled {

  /** the parent object */
  private UiNode parent;

  /** @see #getVisibleState() */
  private VisibleState visibleState;

  /** @see #getEnabledState() */
  private EnabledState enabledState;

  /** @see #isDisposed() */
  private boolean disposed;

  /** @see #getStyles() */
  private Set<String> stylesSet;

  /**
   * the registered listeners (or <code>null</code> if no listener is
   * registered)
   */
  private List<UiEventListener> listeners;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiNode(AbstractUiFactory uiFactory) {

    super(uiFactory);
    this.visibleState = VisibleState.VISIBLE;
    this.enabledState = EnabledState.ENABLED;
  }

  /**
   * {@inheritDoc}
   */
  public UiNode getParent() {

    return this.parent;
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    if (!this.disposed) {
      setParent(null);
      this.disposed = true;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDisposed() {

    return this.disposed;
  }

  /**
   * This method gets the raw internal {@link VisibleState} without influence
   * from the {@link #getParent() parent}.
   * 
   * @return the raw {@link VisibleState}.
   */
  protected VisibleState doGetVisibleState() {

    return this.visibleState;
  }

  /**
   * This method allows to determines if this object has been made invisible. It
   * always returns <code>false</code> by default. You can override it in
   * {@link UiNode} implementations that can be made invisible by the user, e.g.
   * a {@link UiWindow} that can be closed.
   * 
   * @return <code>true</code> if invisible and visibility can be influenced by
   *         the user, <code>false</code> otherwise.
   */
  protected boolean doIsInvisible() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public VisibleState getVisibleState() {

    if (this.visibleState.isVisible()) {
      if (doIsInvisible()) {
        // this object has been made invisible by the user in the meantime...
        this.visibleState = VisibleState.HIDDEN;
      } else if (this.parent != null) {
        if (!this.parent.getVisibleState().isVisible()) {
          return VisibleState.BLOCKED;
        }
      }
    }
    return this.visibleState;
  }

  /**
   * {@inheritDoc}
   */
  public final boolean isVisible() {

    return getVisibleState().isVisible();
  }

  /**
   * This method shows or hides the underlying UI object according to the given
   * <code>visible</code> flag. It is invoked from {@link #setVisible(boolean)}
   * if the local visible-flag of this object changed.<br/>
   * This default implementation throws a {@link UnsupportedOperationException}.
   * You need to override this in subclasses that implement
   * {@link UiWriteVisible}.
   * 
   * @param visible - <code>true</code> if the object shall be shown,
   *        <code>false</code> if it shall be hidden.
   */
  protected void doSetVisible(boolean visible) {

    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  public final void setVisible(boolean visible) {

    switch (this.visibleState) {
      case VISIBLE:
        if (!visible) {
          doSetVisible(visible);
          this.visibleState = VisibleState.HIDDEN;
        } else if (doIsInvisible()) {
          doSetVisible(visible);
        }
        break;
      case HIDDEN:
        if (visible) {
          if (!getVisibleState().isVisible()) {
            doSetVisible(visible);
          }
          this.visibleState = VisibleState.VISIBLE;
        }
        break;
      case BLOCKED:
        if (!visible) {
          this.visibleState = VisibleState.HIDDEN;
        }
        break;
      default :
        throw new IllegalCaseException(VisibleState.class, this.visibleState);
    }
  }

  /**
   * This method enables or disables the underlying UI object according to the
   * given <code>enabled</code> flag. It is invoked from
   * {@link #setEnabled(boolean)} if the local enabled-flag of this object
   * changed.<br/>
   * This default implementation throws a {@link UnsupportedOperationException}.
   * You need to override this in subclasses that implement
   * {@link UiWriteEnabled}.
   * 
   * @param enabled - <code>true</code> if the object shall be enabled,
   *        <code>false</code> if it shall be disabled.
   */
  protected void doSetEnabled(boolean enabled) {

    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  public final void setEnabled(boolean enabled) {

    switch (this.enabledState) {
      case ENABLED:
        if (!enabled) {
          doSetEnabled(false);
          this.enabledState = EnabledState.DISABLED;
        }
        break;
      case DISABLED:
        if (enabled) {
          if ((this.parent != null) && (!this.parent.isEnabled())) {
            this.enabledState = EnabledState.BLOCKED;
          } else {
            doSetEnabled(true);
            this.enabledState = EnabledState.ENABLED;
          }
        }
        break;
      case BLOCKED:
        if (!enabled) {
          this.enabledState = EnabledState.DISABLED;
        }
        break;
      default :
        throw new IllegalCaseException(EnabledState.class, this.enabledState);
    }
  }

  /**
   * This method updates the {@link #getEnabledState() enabled-state} after the
   * {@link #getParent() parent} has changed it. This should be invoked for all
   * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite#getChild(int)
   * children} of a {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite}
   * in {@link #doSetEnabled(boolean)}.
   */
  public void updateEnabledFromParent() {

    if (this.parent != null) {
      if (this.parent.isEnabled()) {
        if (this.enabledState == EnabledState.BLOCKED) {
          doSetEnabled(true);
          this.enabledState = EnabledState.ENABLED;
        }
      } else {
        if (this.enabledState == EnabledState.ENABLED) {
          doSetEnabled(false);
          this.enabledState = EnabledState.BLOCKED;
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public EnabledState getEnabledState() {

    return this.enabledState;
  }

  /**
   * {@inheritDoc}
   */
  public final boolean isEnabled() {

    return getEnabledState().isEnabled();
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

    if (this.disposed) {
      throw new IllegalStateException("Object is disposed!");
    }
    this.parent = newParent;
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

  /**
   * This method gets the {@link #getStyles() styles} as {@link Set}.
   * 
   * @return the {@link #getStyles() styles} as {@link Set}.
   */
  protected Set<String> getStylesSet() {

    return this.stylesSet;
  }

  /**
   * {@inheritDoc}
   */
  public void setStyles(String styles) {

    if (this.stylesSet != null) {
      this.stylesSet.clear();
    }
    if ((styles == null) || (styles.length() == 0)) {
      return;
    }
    String[] strings = styles.split(" ");
    if (strings.length == 1) {
      doSetStyles(strings[0]);
    } else if (strings.length > 1) {
      if (this.stylesSet == null) {
        this.stylesSet = new HashSet<String>();
      }
      for (String style : strings) {
        if (!style.isEmpty()) {
          this.stylesSet.add(style);
        }
      }
      StringBuilder sb = new StringBuilder(styles.length());
      for (String style : this.stylesSet) {
        if (sb.length() > 0) {
          sb.append(' ');
        }
        sb.append(style);
      }
      doSetStyles(sb.toString());
    }
  }

  /**
   * {@inheritDoc}
   */
  public void addStyle(String style) {

    if (style.isEmpty()) {
      return;
    }
    if (style.contains(" ")) {
      throw new IllegalArgumentException();
    }
    String currentStyles = getStyles();
    if (this.stylesSet == null) {
      if (currentStyles.isEmpty()) {
        doSetStyles(style);
      } else if (!currentStyles.equals(style)) {
        this.stylesSet = new HashSet<String>();
        this.stylesSet.add(currentStyles);
        this.stylesSet.add(style);
        doSetStyles(currentStyles + " " + style);
      }
    } else {
      boolean added = this.stylesSet.add(style);
      if (added) {
        doSetStyles(currentStyles + " " + style);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void removeStyle(String style) {

    if (style.isEmpty()) {
      return;
    }
    if (style.contains(" ")) {
      throw new IllegalArgumentException();
    }
    String currentStyles = getStyles();
    if (this.stylesSet == null) {
      if (currentStyles.equals(style)) {
        doSetStyles("");
      }
    } else {
      boolean removed = this.stylesSet.remove(style);
      if (removed) {
        StringBuilder sb = new StringBuilder(currentStyles.length() - style.length() - 1);
        for (String s : this.stylesSet) {
          if (sb.length() > 0) {
            sb.append(' ');
          }
          sb.append(s);
        }
        doSetStyles(sb.toString());
      }
    }

  }

}
