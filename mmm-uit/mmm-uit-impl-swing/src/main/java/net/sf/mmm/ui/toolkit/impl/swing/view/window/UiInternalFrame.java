/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import java.awt.Container;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadSizeInPixel;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.base.view.window.AbstractUiWindow;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.menu.UiMenuBarImpl;

/**
 * This class is the implementation of an internal
 * {@link net.sf.mmm.ui.toolkit.api.view.window.UiFrame frame} using Swing as
 * the UI toolkit.
 * 
 * @see net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench#createFrame(String,
 *      boolean)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiInternalFrame extends AbstractUiWindow<JInternalFrame> implements UiFrame {

  /** @see #getAdapter() */
  private final UiWindowAdapterSwingInternalFrame adapter;

  /** @see #getMenuBar() */
  private UiMenuBarImpl menuBar;

  /** the workbench */
  private final UiWorkbenchImpl workbench;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parent is the workbench that created this frame.
   * @param title is the {@link #getTitle() title} of the frame.
   * @param resizable - if <code>true</code> the frame will be
   *        {@link #isResizable() resizable}.
   */
  public UiInternalFrame(UiFactorySwing uiFactory, UiWorkbenchImpl parent, String title,
      boolean resizable) {

    super(uiFactory);
    JInternalFrame frame = new JInternalFrame(title, resizable, true, resizable, true);
    this.adapter = new UiWindowAdapterSwingInternalFrame(this, frame);
    this.workbench = parent;
    setParent(parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFrame getParent() {

    return (UiFrame) super.getParent();
  }

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parent is the
   *        {@link net.sf.mmm.ui.toolkit.api.view.UiNode#getParent() parent}
   *        that created this frame.
   * @param title is the {@link #getTitle() title} of the frame.
   * @param resizable - if <code>true</code> the frame will be
   *        {@link #isResizable() resizable}.
   */
  public UiInternalFrame(UiFactorySwing uiFactory, UiInternalFrame parent, String title,
      boolean resizable) {

    super(uiFactory);
    JInternalFrame frame = new JInternalFrame();
    this.adapter = new UiWindowAdapterSwingInternalFrame(this, frame);
    this.workbench = parent.workbench;
    setParent(parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWindowAdapterSwingInternalFrame getAdapter() {

    return this.adapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    getDelegate().addInternalFrameListener(getAdapter());
    return super.doInitializeListener();
  }

  /**
   * {@inheritDoc}
   */
  public UiFrame createFrame(String title, boolean resizeable) {

    UiInternalFrame internalFrame = new UiInternalFrame((UiFactorySwing) getFactory(), this, title,
        resizeable);
    return internalFrame;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMaximized() {

    return getDelegate().isMaximum();
  }

  /**
   * {@inheritDoc}
   */
  public void setMaximized(boolean maximize) {

    JInternalFrame delegate = getDelegate();
    if (delegate.isMaximizable()) {
      try {
        delegate.setMaximum(maximize);
      } catch (PropertyVetoException e) {
        // ignore this...
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setMinimized(boolean minimize) {

    JInternalFrame delegate = getDelegate();
    if (delegate.isIconifiable()) {
      try {
        delegate.setIcon(minimize);
      } catch (PropertyVetoException e) {
        // ignore this...
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMinimized() {

    return getDelegate().isIcon();
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
          JInternalFrame delegate = getDelegate();
          JMenuBar jMenuBar = delegate.getJMenuBar();
          if (jMenuBar == null) {
            jMenuBar = new JMenuBar();
            delegate.setJMenuBar(jMenuBar);
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
  @SuppressWarnings("rawtypes")
  public void setComposite(UiComposite<? extends UiElement> newComposite) {

    Container topDelegate = (Container) ((AbstractUiElement) newComposite).getAdapter()
        .getToplevelDelegate();
    getDelegate().setContentPane(topDelegate);
    doSetComposite(newComposite);
  }

  //
  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public Window getDelegate() {
  //
  // UIWorkbenchImpl parent = (UIWorkbenchImpl) getParent();
  // if (parent == null) {
  // // should actually never happen...
  // return null;
  // }
  // return parent.getDelegate();
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AttributeReadSizeInPixel getDesktopSize() {

    return this.workbench;
  }

}
