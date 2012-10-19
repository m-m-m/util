/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

/**
 * This is the interface for a {@link UiWidgetDynamicComposite dynamic composite widget} that only shows a
 * single {@link #getChild(int) child} at a time. You can {@link #showChild(int) switch} the displayed
 * {@link #getChild(int) child} programmatically.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public abstract interface UiWidgetSwitchComposite<CHILD extends UiWidget> extends UiWidgetDynamicComposite<CHILD> {

  /**
   * This method shows the {@link #getChild(int) child} with the given index.
   * 
   * @param index is the {@link #getChild(int) index} of the child to show. It has to be in the range from
   *        <code>0</code> to <code>{@link #getChildCount()} - 1</code>.
   */
  void showChild(int index);

  /**
   * This method shows the given {@link #getChild(int) child}.
   * 
   * @param child is the {@link #getChild(int) child} to show.
   * @return <code>true</code> if the given <code>child</code> was found and shown, <code>false</code> if the
   *         given widget is NOT a {@link #getChild(int) child} of this composite.
   */
  boolean showChild(CHILD child);

  /**
   * This method {@link #showChild(UiWidget) shows the child} identified by the given <code>id</code>.
   * 
   * @param id is the {@link #getId() ID} of the requested child.
   * @return <code>true</code> if the {@link #getChild(int) child} with the given <code>id</code> was found
   *         and shown, <code>false</code> if there is no such {@link #getChild(int) child} in this composite.
   */
  boolean showChild(String id);

}
