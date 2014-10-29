/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.menu;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;
import net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetDynamicComposite dynamic composite widget} that represents a
 * <em>menu</em>. A menu is something that is displayed by its {@link #getLabel() label} and opens a list of
 * {@link UiWidgetMenuItem menu items} if it is clicked. The user can click on one of these
 * {@link UiWidgetMenuItem menu items} to execute a specific functionality. <br>
 * A {@link UiWidgetMenu} is also a {@link UiWidgetMenuItem} so you can add it to another menu as a
 * <em>sub menu</em>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetMenu extends UiWidgetDynamicComposite<UiWidgetMenuItem>, UiWidgetMenuItem,
    AttributeWriteLabel, UiWidgetNative {

  /**
   * This method {@link #addChild(UiWidgetMenuItem) adds} a {@link UiWidgetMenuItemSeparator separator} to
   * this menu.
   */
  void addSeparator();

  /**
   * This method {@link #addChild(UiWidgetMenuItem) adds} a sub-{@link UiWidgetMenu menu} to this menu.
   * 
   * @param label is the {@link #getLabel() label} of the sub-menu.
   * @return the new sub-menu.
   */
  UiWidgetMenu addSubmenu(String label);

}
