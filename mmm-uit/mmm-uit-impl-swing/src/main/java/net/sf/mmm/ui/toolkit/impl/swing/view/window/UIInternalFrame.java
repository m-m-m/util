/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import java.awt.Window;
import java.beans.PropertyVetoException;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadSize;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.menu.UIMenuBarImpl;

/**
 * This class is the implementation of an internal
 * {@link net.sf.mmm.ui.toolkit.api.view.window.UiFrame frame} using Swing as the UI
 * toolkit.
 * 
 * @see net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench#createFrame(String,
 *      boolean)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIInternalFrame extends UIWindow implements UiFrame, UiElement {

  /** the frame */
  private final JInternalFrame frame;

  /** the workbench */
  private final UIWorkbenchImpl workbench;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parentObject is the workbench that created this frame.
   * @param title is the {@link #getTitle() title} of the frame.
   * @param resizeable - if <code>true</code> the frame will be
   *        {@link #isResizeable() resizeable}.
   */
  public UIInternalFrame(UIFactorySwing uiFactory, UIWorkbenchImpl parentObject, String title,
      boolean resizeable) {

    super(uiFactory, parentObject);
    this.frame = new JInternalFrame(title, resizeable, true, resizeable, true);
    this.workbench = parentObject;
  }

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parentObject is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiNode#getParent() parent} that
   *        created this frame.
   * @param title is the {@link #getTitle() title} of the frame.
   * @param resizeable - if <code>true</code> the frame will be
   *        {@link #isResizeable() resizeable}.
   */
  public UIInternalFrame(UIFactorySwing uiFactory, UIInternalFrame parentObject, String title,
      boolean resizeable) {

    super(uiFactory, parentObject);
    this.frame = new JInternalFrame();
    this.workbench = parentObject.workbench;
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
  public UiFrame createFrame(String title, boolean resizeable) {

    UIInternalFrame internalFrame = new UIInternalFrame((UIFactorySwing) getFactory(), this, title,
        resizeable);
    return internalFrame;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMaximized() {

    return this.frame.isMaximum();
  }

  /**
   * {@inheritDoc}
   */
  public void setMaximized(boolean maximize) {

    if (this.frame.isMaximizable()) {
      try {
        this.frame.setMaximum(maximize);
      } catch (PropertyVetoException e) {
        // ignore this...
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setMinimized(boolean minimize) {

    if (this.frame.isIconifiable()) {
      try {
        this.frame.setIcon(minimize);
      } catch (PropertyVetoException e) {
        // ignore this...
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMinimized() {

    return this.frame.isIcon();
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
  public boolean isResizeable() {

    return this.frame.isResizable();
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(int width, int height) {

    this.frame.setSize(width, height);
  }

  /**
   * {@inheritDoc}
   */
  public void setPosition(int x, int y) {

    this.frame.setLocation(x, y);
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
  public int getHeight() {

    return this.frame.getHeight();
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    return this.frame.getWidth();
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    getFactory().removeWindow(this);
    this.frame.dispose();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDisposed() {

    return this.frame.isDisplayable();
  }

  /**
   * {@inheritDoc}
   */
  public int getX() {

    return this.frame.getX();
  }

  /**
   * {@inheritDoc}
   */
  public int getY() {

    return this.frame.getY();
  }

  /**
   * {@inheritDoc}
   */
  public void pack() {

    this.frame.pack();
  }

  /**
   * {@inheritDoc}
   */
  public void setComposite(UiComposite newComposite) {

    JComponent jComponent = ((AbstractUiElement) newComposite).getSwingComponent();
    this.frame.setContentPane(jComponent);
    registerComposite(newComposite);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isVisible() {

    return this.frame.isVisible();
  }

  /**
   * {@inheritDoc}
   */
  public void setVisible(boolean visible) {

    this.frame.setVisible(visible);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Window getAwtWindow() {

    return ((UIWorkbenchImpl) getParent()).getAwtWindow();
  }

  /**
   * This method gets the native swing object.
   * 
   * @return the swing internal frame.
   */
  public JInternalFrame getSwingInternalFrame() {

    return this.frame;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiReadSize getDesktopSize() {

    return this.workbench.getDesktopPanel();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEnabled() {

    return this.frame.isEnabled();
  }

  /**
   * {@inheritDoc}
   */
  public void setEnabled(boolean enabled) {

    this.frame.setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  public String getTooltipText() {

    return this.frame.getToolTipText();
  }

  /**
   * {@inheritDoc}
   */
  public void setTooltipText(String tooltip) {

    this.frame.setToolTipText(tooltip);
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredHeight() {

    return (int) this.frame.getPreferredSize().getHeight();
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredWidth() {

    return (int) this.frame.getPreferredSize().getWidth();
  }

}
