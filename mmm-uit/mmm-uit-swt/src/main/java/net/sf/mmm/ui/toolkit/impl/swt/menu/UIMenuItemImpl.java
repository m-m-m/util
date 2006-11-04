/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;

import net.sf.mmm.ui.toolkit.api.menu.UIMenuItem;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncMenuItemAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.menu.UIMenuItem} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenuItemImpl extends UISwtNode implements UIMenuItem {

  /** the SWT menu item */
  private final MenuItem menuItem;

  /** the synchron access to the menu-item */
  private final SyncMenuItemAccess syncAccess;

  /** the style */
  private final ButtonStyle style;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param text
   *        is the {@link #getText() text} of the menu item.
   * @param itemStyle
   *        is the style defining how the item is visualized and behaves.
   * @param item
   *        is the actual SWT menu-item.
   */
  public UIMenuItemImpl(UIFactorySwt uiFactory, UIMenuImpl parentObject, String text,
      ButtonStyle itemStyle, MenuItem item) {

    super(uiFactory, parentObject);
    int swtStyle = UIFactorySwt.convertButtonStyleForMenuItem(itemStyle);
    this.syncAccess = new SyncMenuItemAccess(uiFactory, swtStyle, item, text);
    this.style = itemStyle;
    this.menuItem = item;
  }

  /**
   * This method gets the unwrapped menu item.
   * 
   * @return the menu item.
   */
  protected MenuItem getItem() {

    return this.menuItem;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#doInitializeListener()
   * 
   */
  @Override
  protected boolean doInitializeListener() {

    this.syncAccess.addListener(SWT.Selection, createSwtListener());
    return true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItem#getText()
   * 
   */
  public String getText() {

    return this.syncAccess.getText();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   * 
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItem#getStyle()
   * 
   */
  public ButtonStyle getStyle() {

    return this.style;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlag#isSelected()
   * 
   */
  public boolean isSelected() {

    return this.syncAccess.isSelected();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlag#setSelected(boolean)
   * 
   */
  public void setSelected(boolean selected) {

    this.syncAccess.setSelected(selected);
  }

}
