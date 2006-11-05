/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.menu.UIMenu;
import net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenuBar;
import net.sf.mmm.ui.toolkit.base.window.AbstractUIWindow;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the UIMenuBar interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenuBarImpl extends AbstractUIMenuBar {

  /** the swing menu bar */
  private JMenuBar menuBar;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param jMenuBar
   *        is the swing menu bar to wrap.
   */
  public UIMenuBarImpl(UIFactorySwing uiFactory, AbstractUIWindow parentObject, JMenuBar jMenuBar) {

    super(uiFactory, parentObject);
    this.menuBar = jMenuBar;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenuBar#createMenu(java.lang.String)
   */
  protected UIMenu createMenu(String name) {

    JMenu menu = new JMenu(name);
    this.menuBar.add(menu);
    return new UIMenuImpl((UIFactorySwing) getFactory(), this, menu);
  }

}
