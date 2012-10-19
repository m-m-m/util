/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.adapter;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;

/**
 * This is the interface for a {@link UiWidgetAdapter} adapting
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetSwitchComposite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <CHILD> is the generic type of the {@link #addChild(UiWidget, int) children}.
 */
public interface UiWidgetAdapterSwitchComposite<WIDGET, CHILD extends UiWidget> extends
    UiWidgetAdapterDynamicComposite<WIDGET, CHILD> {

  /**
   * This method shows the {@link #addChild(UiWidget, int) child} with the given <code>index</code>.
   * 
   * @param index is the index of the child to show.
   */
  void showChild(int index);

}
