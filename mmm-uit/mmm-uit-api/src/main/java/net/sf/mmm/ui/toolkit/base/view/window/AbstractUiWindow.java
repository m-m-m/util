/* $Id: AbstractUIWindow.java 957 2011-02-21 22:18:03Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view.window;

import net.sf.mmm.ui.toolkit.api.attribute.UiReadSize;
import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.common.Visibility;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiNode;

/**
 * This is the base implementation of the UIWindow interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWindow extends AbstractUiNode implements UiWindow {

  /** the composite content of this window */
  private UiComposite<? extends UiElement> composite;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param parentObject is the {@link #getParent() parent} of this object. It
   *        may be <code>null</code>.
   */
  public AbstractUiWindow(AbstractUiFactory uiFactory, UiWindow parentObject) {

    super(uiFactory);
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
  @Override
  public Visibility getVisibility() {

    // no inheritance by default
    return doGetVisibility();
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
  public UiComposite<? extends UiElement> getComposite() {

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
  public void registerComposite(UiComposite<? extends UiElement> newComposite) {

    if (this.composite != null) {
      // The current composite is replaced by a new one. Set the parent of
      // the old (current) one to null.
      setParent((AbstractUiNode) this.composite, null);
    }
    this.composite = newComposite;
    ((AbstractUiNode) newComposite).setParent(this);
    // setParent((AbstractUINode) newComposite, this);
  }

  /**
   * {@inheritDoc}
   */
  public void showMessage(String message, String title, MessageType messageType, Throwable throwable) {

    // TODO Hack for the moment...
    showMessage(message + "\n" + throwable.getMessage(), title, messageType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    if (this.composite != null) {
      ((AbstractUiNode) this.composite).refresh(event);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    getFactory().removeWindow(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWindow getParent() {

    return (UiWindow) super.getParent();
  }

}
