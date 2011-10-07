/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import java.awt.Container;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.menu.UiMenuBarImpl;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.window.UiFrame} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiFrameImpl extends AbstractUiWindowSwing<JFrame> implements UiFrame {

  /** @see #getAdapter() */
  private final UiWindowAdapterSwingFrame adapter;

  /** @see #getMenuBar() */
  private UiMenuBarImpl menuBar;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parent is the {@link #getParent() parent} of this object (may be
   *        <code>null</code>).
   * @param title is the {@link #getTitle() title} of the frame.
   * @param resizable - if <code>true</code> the frame will be
   *        {@link #isResizable() resizable}.
   */
  public UiFrameImpl(UiFactorySwing uiFactory, UiFrameImpl parent, String title, boolean resizable) {

    super(uiFactory);
    JFrame frame = new JFrame(uiFactory.getAwtGc());
    frame.setResizable(resizable);
    if (title != null) {
      frame.setTitle(title);
    }
    this.adapter = new UiWindowAdapterSwingFrame(this, frame);
    setParent(parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWindowAdapterSwingFrame getAdapter() {

    return this.adapter;
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
  public UiMenuBarImpl getMenuBar() {

    if (this.menuBar == null) {
      synchronized (this) {
        if (this.menuBar == null) {
          JMenuBar jMenuBar = getDelegate().getJMenuBar();
          if (jMenuBar == null) {
            jMenuBar = new JMenuBar();
            getDelegate().setJMenuBar(jMenuBar);
          }
          this.menuBar = new UiMenuBarImpl((UiFactorySwing) getFactory(), jMenuBar);
        }
      }
    }
    return this.menuBar;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMaximized() {

    return (getDelegate().getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;
  }

  /**
   * {@inheritDoc}
   */
  public void setMaximized(boolean maximize) {

    int state = getDelegate().getExtendedState();
    if (maximize) {
      state |= Frame.MAXIMIZED_BOTH;
    } else {
      state &= ~Frame.MAXIMIZED_BOTH;
    }
    getDelegate().setExtendedState(state);
  }

  /**
   * {@inheritDoc}
   */
  public void setMinimized(boolean minimize) {

    int state = getDelegate().getExtendedState();
    if (minimize) {
      state |= Frame.ICONIFIED;
    } else {
      state &= ~Frame.ICONIFIED;
    }
    getDelegate().setExtendedState(state);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMinimized() {

    return (getDelegate().getExtendedState() & Frame.ICONIFIED) == Frame.ICONIFIED;
  }

  /**
   * {@inheritDoc}
   */
  public UiFrame createFrame(String title, boolean resizeable) {

    UiFrameImpl newFrame = new UiFrameImpl((UiFactorySwing) getFactory(), this, title, resizeable);
    getFactory().addWindow(newFrame);
    return newFrame;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  public void setComposite(UiComposite<? extends UiElement> newComposite) {

    Container topDelegate = (Container) ((AbstractUiElement) newComposite).getAdapter()
        .getToplevelDelegate();
    getDelegate().setContentPane(topDelegate);
    doSetComposite(newComposite);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFrame getParent() {

    return (UiFrame) super.getParent();
  }

}
