/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.window;

import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.api.window.UIWorkbench;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UIDesktopPanel;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.window.UIWorkbench} interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIWorkbenchImpl extends UIFrameImpl implements UIWorkbench {

  /** the workbench pane */
  private final UIDesktopPanel workbench;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UIObject#getFactory() factory}
   *        instance.
   * @param title is the {@link #getTitle() title} of the frame.
   * @param resizeable - if <code>true</code> the frame will be
   *        {@link #isResizeable() resizeable}.
   */
  public UIWorkbenchImpl(UIFactorySwing uiFactory, String title, boolean resizeable) {

    super(uiFactory, null, title, resizeable);
    // UIManager.put("swing.boldMetal", Boolean.FALSE);
    // JFrame.setDefaultLookAndFeelDecorated(true);
    // Toolkit.getDefaultToolkit().setDynamicLayout(true);
    // UIManager.put("swing.boldMetal", Boolean.FALSE);
    this.workbench = new UIDesktopPanel(uiFactory, this);
    super.setComposite(this.workbench);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return UIWorkbench.TYPE;
  }

  /**
   * This method gets the desktop panel of this workbench.
   * 
   * @return the desktop panel.
   */
  public UIDesktopPanel getDesktopPanel() {

    return this.workbench;
  }

  /**
   * {@inheritDoc}
   */
  public void setComposite(UIComposite newComposite) {

    // TODO...
    throw new IllegalArgumentException("This method is not applicable for " + UIWorkbenchImpl.class);
  }

  /**
   * {@inheritDoc}
   */
  public UIFrame createFrame(String title, boolean resizeable) {

    UIInternalFrame internalFrame = new UIInternalFrame((UIFactorySwing) getFactory(), this, title,
        resizeable);
    this.workbench.add(internalFrame);
    getFactory().addWindow(internalFrame);
    return internalFrame;
  }

}
