/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.window;

import java.awt.Frame;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBar;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.menu.UIMenuBarImpl;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.window.UIFrame} interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFrameImpl extends UIWindow implements UIFrame {

  /** the swing frame */
  private final JFrame frame;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the
   *        {@link net.sf.mmm.ui.toolkit.api.UIObject#getFactory() factory}
   *        instance.
   * @param parent
   *        is the {@link UINode#getParent() parent} of this object (may be
   *        <code>null</code>).
   * @param title
   *        is the {@link #getTitle() title} of the frame.
   * @param resizeable -
   *        if <code>true</code> the frame will be
   *        {@link #isResizeable() resizeable}.
   */
  public UIFrameImpl(UIFactorySwing uiFactory, UIFrameImpl parent, String title, boolean resizeable) {

    super(uiFactory, parent);
    this.frame = new JFrame(uiFactory.getAwtGc());
    this.frame.setResizable(resizeable);
    if (title != null) {
      this.frame.setTitle(title);
    }
  }

  /**
   * {@inheritDoc}
   */
  protected Window getAwtWindow() {

    return this.frame;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.frame.getTitle();
  }

  /**
   * {@inheritDoc}
   */
  public void setTitle(String newTitle) {

    this.frame.setTitle(newTitle);
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
  protected UIMenuBar createMenuBar() {

    JMenuBar menuBar = this.frame.getJMenuBar();
    if (menuBar == null) {
      menuBar = new JMenuBar();
      this.frame.setJMenuBar(menuBar);
    }
    return new UIMenuBarImpl((UIFactorySwing) getFactory(), this, menuBar);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMaximized() {

    return (this.frame.getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;
  }

  /**
   * {@inheritDoc}
   */
  public void setMaximized(boolean maximize) {

    int state = this.frame.getExtendedState();
    if (maximize) {
      state |= Frame.MAXIMIZED_BOTH;
    } else {
      state &= ~Frame.MAXIMIZED_BOTH;
    }
    this.frame.setExtendedState(state);
  }

  /**
   * {@inheritDoc}
   */
  public void setMinimized(boolean minimize) {
    int state = this.frame.getExtendedState();
    if (minimize) {
      state |= Frame.ICONIFIED;
    } else {
      state &= ~Frame.ICONIFIED;
    }
    this.frame.setExtendedState(state);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMinimized() {
  
    return (this.frame.getExtendedState() & Frame.ICONIFIED) == Frame.ICONIFIED;
  }
  
  /**
   * {@inheritDoc}
   */
  public UIFrame createFrame(String title, boolean resizeable) {

    return new UIFrameImpl((UIFactorySwing) getFactory(), this, title, resizeable);
  }

  /**
   * {@inheritDoc}
   */
  public void setComposite(UIComposite newComposite) {

    JComponent jComponent = ((AbstractUIComponent) newComposite).getSwingComponent();
    this.frame.setContentPane(jComponent);
    registerComposite(newComposite);
  }

}
