/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;

import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIInternalFrame;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIWorkbenchImpl;

/**
 * This is the implementation of a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIComposite} interface for the
 * {@link net.sf.mmm.ui.toolkit.api.window.UIWindow#getComposite() content} of a
 * {@link net.sf.mmm.ui.toolkit.api.window.UIWorkbench}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDesktopPanel extends AbstractUIMultiComposite {

  /** the workbench pane */
  private final JDesktopPane workbench;

  /** the default layer used to add internal frames */
  private static final Integer DEFAULT_LAYER = new Integer(1);

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public UIDesktopPanel(UIFactorySwing uiFactory, UIWorkbenchImpl parentObject) {

    super(uiFactory, parentObject);
    this.workbench = new JDesktopPane();
    initialize();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.workbench;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return "DesktopPanel";
  }

  /**
   * This method adds the given internal frame to this composite.
   * 
   * @param internalFrame is the frame to add.
   */
  public void add(UIInternalFrame internalFrame) {

    doAddComponent(internalFrame);
    this.workbench.add(internalFrame.getSwingInternalFrame(), DEFAULT_LAYER);
  }

}
