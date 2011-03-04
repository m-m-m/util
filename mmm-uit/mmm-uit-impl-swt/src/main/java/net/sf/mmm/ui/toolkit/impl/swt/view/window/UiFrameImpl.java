/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.window;

import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.menu.UiMenuBarImpl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;

/**
 * This class is the implementation of the UIFrame interface using SWT as the UI
 * toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiFrameImpl extends AbstractUiWindowSwt implements UiFrame {

  /** the default style for the native SWT shell */
  private static final int DEFAULT_STYLE = SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE;

  /** @see #getMenuBar() */
  private UiMenuBarImpl menuBar;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parent is the
   *        {@link net.sf.mmm.ui.toolkit.api.view.UiNode#getParent() parent} of
   *        this object (may be <code>null</code>).
   * @param resizeable - if <code>true</code> the frame will be
   *        {@link #isResizable() resizeable}.
   */
  public UiFrameImpl(UiFactorySwt uiFactory, UiFrameImpl parent, boolean resizeable) {

    super(uiFactory, parent, DEFAULT_STYLE, false, resizeable);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMaximized() {

    // return getSwtWindow().getMaximized();
    return getSyncAccess().getMaximized();
  }

  /**
   * {@inheritDoc}
   */
  public void setMaximized(boolean maximize) {

    // getSwtWindow().setMaximized(maximize);
    getSyncAccess().setMaximized(maximize);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public void setMinimized(boolean minimize) {

    // getSwtWindow().setMinimized(minimize);
    getSyncAccess().setMinimized(minimize);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMinimized() {

    return getSyncAccess().getMinimized();
  }

  /**
   * {@inheritDoc}
   */
  public UiFrameImpl createFrame(String title, boolean resizeable) {

    UiFrameImpl frame = new UiFrameImpl(getFactory(), this, resizeable);
    frame.setTitle(title);
    getFactory().addWindow(frame);
    return frame;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFrame getParent() {

    return (UiFrame) super.getParent();
  }

  /**
   * {@inheritDoc}
   */
  public UiMenuBarImpl getMenuBar() {

    if (this.menuBar == null) {
      synchronized (this) {
        if (this.menuBar == null) {
          Menu menu = getSyncAccess().createMenuBar();
          this.menuBar = new UiMenuBarImpl(getFactory(), this, menu);
        }
      }
    }
    return this.menuBar;
  }

}
