/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.window;

import java.awt.Window;
import java.beans.PropertyVetoException;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBar;
import net.sf.mmm.ui.toolkit.api.state.UIReadSize;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.menu.UIMenuBarImpl;

/**
 * This class is the implementation of an internal
 * {@link net.sf.mmm.ui.toolkit.api.window.UIFrame frame} using Swing as the
 * UI toolkit.
 * 
 * @see net.sf.mmm.ui.toolkit.api.window.UIWorkbench#createFrame(String,
 *      boolean)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIInternalFrame extends UIWindow implements UIFrame, UIComponent {

  /** the frame */
  private final JInternalFrame frame;

  /** the workbench */
  private final UIWorkbenchImpl workbench;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the
   *        {@link net.sf.mmm.ui.toolkit.api.UIObject#getFactory() factory}
   *        instance.
   * @param parentObject
   *        is the workbench that created this frame.
   * @param title
   *        is the {@link #getTitle() title} of the frame.
   * @param resizeable -
   *        if <code>true</code> the frame will be
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
   * @param uiFactory
   *        is the
   *        {@link net.sf.mmm.ui.toolkit.api.UIObject#getFactory() factory}
   *        instance.
   * @param parentObject
   *        is the {@link net.sf.mmm.ui.toolkit.api.UINode#getParent() parent}
   *        that created this frame.
   * @param title
   *        is the {@link #getTitle() title} of the frame.
   * @param resizeable -
   *        if <code>true</code> the frame will be
   *        {@link #isResizeable() resizeable}.
   */
  public UIInternalFrame(UIFactorySwing uiFactory, UIInternalFrame parentObject, String title,
      boolean resizeable) {

    super(uiFactory, parentObject);
    this.frame = new JInternalFrame();
    this.workbench = parentObject.workbench;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.window.AbstractUIWindow#createMenuBar()
   */
  @Override
  protected UIMenuBar createMenuBar() {

    JMenuBar menuBar = this.frame.getJMenuBar();
    if (menuBar == null) {
      menuBar = new JMenuBar();
      this.frame.setJMenuBar(menuBar);
    }
    return new UIMenuBarImpl((UIFactorySwing) getFactory(), this, menuBar);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIFrame#createFrame(java.lang.String,
   *      boolean)
   */
  public UIFrame createFrame(String title, boolean resizeable) {

    UIInternalFrame internalFrame = new UIInternalFrame((UIFactorySwing) getFactory(), this, title,
        resizeable);
    return internalFrame;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadMaximized#isMaximized()
   */
  public boolean isMaximized() {

    return this.frame.isMaximum();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIFrame#setMaximized(boolean)
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
   * @see net.sf.mmm.ui.toolkit.api.window.UIFrame#setMinimized(boolean)
   */
  public void setMinimized(boolean minimize) {

    try {
      this.frame.setIcon(minimize);
    } catch (PropertyVetoException e) {
      // ignore this...
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSize#isResizeable()
   */
  public boolean isResizeable() {

    return this.frame.isResizable();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSize#setSize(int, int)
   */
  public void setSize(int width, int height) {

    this.frame.setSize(width, height);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWritePosition#setPosition(int,
   *      int)
   */
  public void setPosition(int x, int y) {

    this.frame.setLocation(x, y);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTitle#getTitle()
   */
  public String getTitle() {

    return this.frame.getTitle();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTitle#setTitle(java.lang.String)
   */
  public void setTitle(String newTitle) {

    this.frame.setTitle(newTitle);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getHeight()
   */
  public int getHeight() {

    return this.frame.getHeight();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getWidth()
   */
  public int getWidth() {

    return this.frame.getWidth();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed#dispose()
   */
  public void dispose() {

    this.frame.dispose();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed#isDisposed()
   */
  public boolean isDisposed() {

    return this.frame.isDisplayable();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPosition#getX()
   */
  public int getX() {

    return this.frame.getX();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPosition#getY()
   */
  public int getY() {

    return this.frame.getY();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#pack()
   */
  public void pack() {

    this.frame.pack();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#setComposite(net.sf.mmm.ui.toolkit.api.composite.UIComposite)
   */
  public void setComposite(UIComposite newComposite) {

    JComponent jComponent = ((AbstractUIComponent) newComposite).getSwingComponent();
    this.frame.setContentPane(jComponent);
    registerComposite(newComposite);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteVisible#isVisible()
   */
  public boolean isVisible() {

    return this.frame.isVisible();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteVisible#setVisible(boolean)
   */
  public void setVisible(boolean visible) {

    this.frame.setVisible(visible);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.awt.UIWindowImpl#getAwtWindow()
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
   * @see net.sf.mmm.ui.toolkit.base.window.AbstractUIWindow#getDesktopSize()
   */
  @Override
  protected UIReadSize getDesktopSize() {

    return this.workbench.getDesktopPanel();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadEnabled#isEnabled()
   */
  public boolean isEnabled() {

    return this.frame.isEnabled();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEnabled#setEnabled(boolean)
   */
  public void setEnabled(boolean enabled) {

    this.frame.setEnabled(enabled);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTooltip#getTooltipText()
   */
  public String getTooltipText() {

    return this.frame.getToolTipText();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTooltip#setTooltipText(java.lang.String)
   */
  public void setTooltipText(String tooltip) {

    this.frame.setToolTipText(tooltip);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSize#getPreferredHeight()
   */
  public int getPreferredHeight() {

    return (int) this.frame.getPreferredSize().getHeight();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSize#getPreferredWidth()
   */
  public int getPreferredWidth() {

    return (int) this.frame.getPreferredSize().getWidth();
  }

}
