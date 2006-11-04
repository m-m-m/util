/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.window;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the UIFrame interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFrameImpl extends UIWindowImpl implements UIFrame {

  /** the default style for the native SWT shell */
  private static final int DEFAULT_STYLE = SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE;

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
   * @param resizeable -
   *        if <code>true</code> the frame will be
   *        {@link #isResizeable() resizeable}.
   */
  public UIFrameImpl(UIFactorySwt uiFactory, UIFrameImpl parent, boolean resizeable) {

    super(uiFactory, parent, DEFAULT_STYLE, false, resizeable);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadMaximized#isMaximized()
   * 
   */
  public boolean isMaximized() {

    return getSwtWindow().getMaximized();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIFrame#setMaximized(boolean)
   * 
   */
  public void setMaximized(boolean maximize) {

    getSwtWindow().setMaximized(maximize);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   * 
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIFrame#setMinimized(boolean)
   * 
   */
  public void setMinimized(boolean minimize) {

    getSwtWindow().setMinimized(minimize);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIFrame#createFrame(java.lang.String,
   *      boolean)
   * 
   */
  public UIFrameImpl createFrame(String title, boolean resizeable) {

    UIFrameImpl frame = new UIFrameImpl(getFactory(), this, resizeable);
    frame.setTitle(title);
    return frame;
  }

}
