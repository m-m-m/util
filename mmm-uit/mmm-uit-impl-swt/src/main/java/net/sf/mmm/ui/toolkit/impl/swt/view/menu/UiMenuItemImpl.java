/* $Id: UIMenuItemImpl.java 978 2011-03-04 20:27:53Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.menu;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuItem;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.AbstractUiNodeSwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncMenuItemAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.menu.UiMenuItem} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiMenuItemImpl extends AbstractUiNodeSwt implements UiMenuItem {

  /** the SWT menu item */
  private final MenuItem menuItem;

  /** the synchronous access to the menu-item */
  private final SyncMenuItemAccess syncAccess;

  /** the style */
  private final ButtonStyle style;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param text is the {@link #getValue() text} of the menu item.
   * @param itemStyle is the style defining how the item is visualized and
   *        behaves.
   * @param item is the actual SWT menu-item.
   */
  public UiMenuItemImpl(UiFactorySwt uiFactory, String text, ButtonStyle itemStyle,
      MenuItem item) {

    super(uiFactory);
    int swtStyle = UiFactorySwt.convertButtonStyleForMenuItem(itemStyle);
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
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    this.syncAccess.addListener(SWT.Selection, createSwtListener());
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.syncAccess.getText();
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
  public ButtonStyle getButtonStyle() {

    return this.style;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSelected() {

    return this.syncAccess.isSelected();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    this.syncAccess.setSelected(selected);
  }

}
