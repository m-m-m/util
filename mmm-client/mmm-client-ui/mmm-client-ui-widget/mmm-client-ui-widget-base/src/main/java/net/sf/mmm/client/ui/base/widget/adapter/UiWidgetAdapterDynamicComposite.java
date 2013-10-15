/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidget;

/**
 * This is the interface for a {@link UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite}.
 * 
 * @param <CHILD> is the generic type of the {@link #addChild(UiWidget, int) children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetAdapterDynamicComposite<CHILD extends UiWidget> extends
    UiWidgetAdapterComposite<CHILD> {

  /**
   * This method adds the given <code>child</code> at the given <code>index</code>.
   * 
   * @see net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite#addChild(UiWidget, int)
   * @see java.util.List#add(int, Object)
   * 
   * @param child is the {@link UiWidget} to add as child of this panel.
   * @param index <code>-1</code> to add the <code>child</code> at the end, otherwise the index where to
   *        insert the given <code>child</code>.
   */
  void addChild(CHILD child, int index);

  /**
   * This method removes the given <code>child</code> from the {@link #getToplevelWidget() widget}.
   * 
   * @param child is the child to remove.
   * @param index is the index of the child to remove.
   */
  void removeChild(CHILD child, int index);

}
