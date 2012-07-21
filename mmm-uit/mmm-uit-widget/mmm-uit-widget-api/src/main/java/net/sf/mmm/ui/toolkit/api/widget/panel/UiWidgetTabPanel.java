/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetSwitchComposite;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetTab;

/**
 * This is the interface for a {@link UiWidgetPanel panel widget} that represents a <code>tab panel</code>. It
 * contains a number of {@link #getChild(int) children} but only shows one of them at a time. On the top it
 * shows a tab header with the {@link UiWidgetTab#getTitle() titles} of its {@link #getChild(int)
 * children}. The user can click on one of these tabs in order to see the actual
 * {@link UiWidgetTab#getChild() content} of the according tab.<br/>
 * This design might look a little complicated but gives a lot more flexibility for setting and changing
 * attributes of the {@link UiWidgetTab tab}. For convenience usage there is also
 * {@link #addChild(UiWidgetRegular, String)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetTabPanel extends UiWidgetDynamicPanel<UiWidgetTab>,
    UiWidgetSwitchComposite<UiWidgetTab>, UiWidgetReal {

  /**
   * This method adds the given {@link UiWidgetRegular} as new tab on the right of all existing tabs. This is
   * a convenience method for the following code:
   * 
   * <pre>
   * {@link UiWidgetTab} tab = {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory widgetFactory}.create({@link UiWidgetTab}.class);
   * tab.setTitle(title);
   * tab.setChild(child);
   * {@link UiWidgetTabPanel tabPanel}.addChild(tab);
   * </pre>
   * 
   * @param child is the {@link UiWidgetRegular child widget} to add. The given component instance must be
   *        created by the same factory.
   * @param title is the title that will be displayed in the tab.
   * @return the {@link UiWidgetTab} for this tab. It allows to change the title, set tooltip or image
   *         (icon), etc.
   */
  UiWidgetTab addChild(UiWidgetRegular child, String title);

}
