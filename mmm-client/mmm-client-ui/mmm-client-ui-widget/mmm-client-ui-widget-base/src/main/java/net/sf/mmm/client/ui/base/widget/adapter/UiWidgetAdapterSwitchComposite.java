/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidget;

/**
 * This is the interface for a {@link UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.UiWidgetSwitchComposite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <CHILD> is the generic type of the {@link #addChild(UiWidget, int) children}.
 */
public interface UiWidgetAdapterSwitchComposite<CHILD extends UiWidget> extends UiWidgetAdapterDynamicComposite<CHILD> {

  /**
   * This method shows the {@link #addChild(UiWidget, int) child} with the given <code>index</code>.
   * 
   * @param index is the index of the child to show.
   */
  void showChild(int index);

}
