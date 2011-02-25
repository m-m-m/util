/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import java.awt.Frame;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.menu.UIMenuBarImpl;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.window.UiFrame} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIFrameImpl extends AbstractUiWindowImpl implements UiFrame {

  /** the swing frame */
  private final JFrame frame;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parent is the {@link net.sf.mmm.ui.toolkit.api.UiNode#getParent()
   *        parent} of this object (may be <code>null</code>).
   * @param title is the {@link #getTitle() title} of the frame.
   * @param resizeable - if <code>true</code> the frame will be
   *        {@link #isResizable() resizeable}.
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
  @Override
  protected JFrame getNativeWindow() {

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
  @Override
  protected UIMenuBarImpl createMenuBar() {

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
  public UiFrame createFrame(String title, boolean resizeable) {

    UIFrameImpl newFrame = new UIFrameImpl((UIFactorySwing) getFactory(), this, title, resizeable);
    getFactory().addWindow(newFrame);
    return newFrame;
  }

  /**
   * {@inheritDoc}
   */
  public void setComposite(UiComposite<? extends UiElement> newComposite) {

    JComponent jComponent = ((AbstractUiElement) newComposite).getSwingComponent();
    this.frame.setContentPane(jComponent);
    registerComposite(newComposite);
  }

}
