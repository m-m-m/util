/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.menu;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetDynamicComposite;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegularComposite;

/**
 * This is the interface for a {@link UiWidgetRegularComposite composite widget} that represents a
 * <em>menu bar</em>. It is {@link #getChild(int) contains} {@link UiWidgetMenu menus} that are displayed by
 * their title in the menu bar.<br/>
 * <b>ATTENTION:</b><br/>
 * A {@link UiWidgetMenuBar} should typically be created via
 * {@link net.sf.mmm.ui.toolkit.api.widget.window.UiWidgetMainWindow#getMenuBar()}. For portability and
 * usability you should avoid creating it manually via
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory}. However, if you are using GWT with GWTP it might
 * make sense to do so.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetMenuBar extends UiWidgetDynamicComposite<UiWidgetMenu>,
    UiWidgetRegularComposite<UiWidgetMenu>, UiWidgetReal {

  // nothing to add

}
