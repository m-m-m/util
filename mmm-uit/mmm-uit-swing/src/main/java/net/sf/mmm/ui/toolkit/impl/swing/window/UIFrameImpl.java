/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.window;

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
   * @see net.sf.mmm.ui.toolkit.impl.awt.UIWindowImpl#getAwtWindow()
   */
  protected Window getAwtWindow() {

    return this.frame;
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
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.window.AbstractUIWindow#createMenuBar()
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
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadMaximized#isMaximized()
   */
  public boolean isMaximized() {

    return (this.frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIFrame#setMaximized(boolean)
   */
  public void setMaximized(boolean maximize) {

    int state = this.frame.getExtendedState();
    if (maximize) {
      state |= JFrame.MAXIMIZED_BOTH;
    } else {
      state &= ~JFrame.MAXIMIZED_BOTH;
    }
    this.frame.setExtendedState(state);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIFrame#setMinimized(boolean)
   */
  public void setMinimized(boolean minimize) {

  // TODO Auto-generated method stub

  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIFrame#createFrame(java.lang.String,
   *      boolean)
   */
  public UIFrame createFrame(String title, boolean resizeable) {

    return new UIFrameImpl((UIFactorySwing) getFactory(), this, title, resizeable);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#setComposite(net.sf.mmm.ui.toolkit.api.composite.UIComposite)
   */
  public void setComposite(UIComposite newComposite) {

    JComponent jComponent = ((AbstractUIComponent) newComposite).getSwingComponent();
    this.frame.setContentPane(jComponent);
    registerComposite(newComposite);
  }

}
