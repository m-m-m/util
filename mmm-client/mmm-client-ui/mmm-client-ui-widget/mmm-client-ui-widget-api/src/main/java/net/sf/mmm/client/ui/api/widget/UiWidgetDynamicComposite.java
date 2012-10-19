/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

/**
 * This is the interface for a {@link UiWidgetComposite composite widget} that allows to
 * {@link #addChild(UiWidget) add} and {@link #removeChild(UiWidget) remove} its children dynamically with no
 * additional arguments (e.g. layout information).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public interface UiWidgetDynamicComposite<CHILD extends UiWidget> extends UiWidgetMultiComposite<CHILD> {

  /**
   * This method adds the given <code>child</code> to the end of the {@link #getChild(int) child-list}. For
   * vertical layouts this will be the bottom and for horizontal layouts this will be the right.
   * 
   * @see java.util.List#add(Object)
   * 
   * @param child is the {@link UiWidget} to add as child of this composite.
   */
  void addChild(CHILD child);

  /**
   * This method adds the given <code>child</code> at the given <code>index</code>.
   * 
   * @see #addChild(UiWidget)
   * @see java.util.List#add(int, Object)
   * 
   * @param child is the {@link UiWidget} to add as child of this panel.
   * @param index is the {@link #getChild(int) index} of the new child. It has to be in the range from
   *        <code>0</code> to {@link #getChildCount()}.
   */
  void addChild(CHILD child, int index);

}
