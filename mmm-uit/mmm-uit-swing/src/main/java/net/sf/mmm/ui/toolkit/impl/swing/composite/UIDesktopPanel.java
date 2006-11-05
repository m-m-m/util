/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;

import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIInternalFrame;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIWorkbenchImpl;

/**
 * This is the implementation of a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIComposite} interface for the
 * {@link net.sf.mmm.ui.toolkit.api.window.UIWindow#getComposite() content} of
 * a {@link net.sf.mmm.ui.toolkit.api.window.UIWorkbench}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDesktopPanel extends UIMultiComposite {

  /** the workbench pane */
  private final JDesktopPane workbench;

  /** the default layer used to add internal frames */
  private static final Integer DEFAULT_LAYER = new Integer(1);

  /**
   * The constructor.
   * 
   * @param uiFactory
   * @param parentObject
   */
  public UIDesktopPanel(UIFactorySwing uiFactory, UIWorkbenchImpl parentObject) {

    super(uiFactory, parentObject);
    this.workbench = new JDesktopPane();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  @Override
  public JComponent getSwingComponent() {

    return this.workbench;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return "DesktopPanel";
  }

  /**
   * This method adds the given internal frame to this composite.
   * 
   * @param internalFrame
   *        is the frame to add.
   */
  public void add(UIInternalFrame internalFrame) {

    this.components.add(internalFrame);
    this.workbench.add(internalFrame.getSwingInternalFrame(), DEFAULT_LAYER);
  }

}
