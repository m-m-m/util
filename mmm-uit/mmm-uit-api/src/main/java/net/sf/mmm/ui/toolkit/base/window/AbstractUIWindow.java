/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.window;

import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadSize;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuBar;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.api.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.AbstractUINode;
import net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenuBar;

/**
 * This is the base implementation of the UIWindow interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUIWindow extends AbstractUINode implements UiWindow {

  /** the composite content of this window */
  private UiComposite composite;

  /** the menu bar of this window */
  private AbstractUIMenuBar menuBar;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parentObject is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiNode#getParent() parent} that
   *        created this object. It may be <code>null</code>.
   */
  public AbstractUIWindow(AbstractUiFactory uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * This method gets access to read the size of the desktop.<br>
   * It is used by {@link #centerWindow()} and may be overridden in specific
   * scenarios.
   * 
   * @return read-access to the size of the desktop.
   */
  protected UiReadSize getDesktopSize() {

    return getFactory().getDisplay();
  }

  /**
   * {@inheritDoc}
   */
  public void centerWindow() {

    UiReadSize desktop = getDesktopSize();
    int xDiff = desktop.getWidth() - getWidth();
    int yDiff = desktop.getHeight() - getHeight();
    if (xDiff < 0) {
      xDiff = 0;
    }
    if (yDiff < 0) {
      yDiff = 0;
    }
    setPosition(xDiff / 2, yDiff / 2);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWindow() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public UiComposite getComposite() {

    return this.composite;
  }

  /**
   * This method registers the new composite and changes the parent of the old
   * and the new composite. The method should be called from the setComposite
   * method implementation.
   * 
   * @see UiWindow#setComposite(UiComposite)
   * 
   * @param newComposite is the composite to register.
   */
  public void registerComposite(UiComposite newComposite) {

    if (this.composite != null) {
      // The current composite is replaced by a new one. Set the parent of
      // the old (current) one to null.
      setParent((AbstractUINode) this.composite, null);
    }
    this.composite = newComposite;
    ((AbstractUINode) newComposite).setParent(this);
    // setParent((AbstractUINode) newComposite, this);
  }

  /**
   * {@inheritDoc}
   */
  public synchronized UiMenuBar getMenuBar() {

    if (this.menuBar == null) {
      synchronized (this) {
        if (this.menuBar == null) {
          this.menuBar = createMenuBar();
        }
      }
    }
    return this.menuBar;
  }

  /**
   * This method is called from the getMenuBar() method if the menu bar is
   * <code>null</code>. The method must create an empty menu bar.
   * 
   * @see UiWindow#getMenuBar()
   * 
   * @return the created menu bar.
   */
  protected abstract AbstractUIMenuBar createMenuBar();

  /**
   * {@inheritDoc}
   */
  public void showMessage(String message, String title, MessageType messageType, Throwable throwable) {

    // TODO Hack for the moment...
    throwable.printStackTrace();
    showMessage(message + "\n" + throwable.getMessage(), title, messageType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    if (this.menuBar != null) {
      this.menuBar.refresh(event);
    }
    if (this.composite != null) {
      ((AbstractUINode) this.composite).refresh(event);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    getFactory().removeWindow(this);
  }

}
