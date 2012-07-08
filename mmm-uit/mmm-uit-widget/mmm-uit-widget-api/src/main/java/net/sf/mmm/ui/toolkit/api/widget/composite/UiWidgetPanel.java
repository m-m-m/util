/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.composite;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;

/**
 * This is the interface for an {@link UiWidgetCompositeRegular} that represents a <em>panel</em>. A panel is
 * a widget that acts as a container for other widgets organizing them in a specific layout. Such widget is
 * also called container or layout.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetPanel extends UiWidgetCompositeRegular<UiWidgetRegular> {

  /**
   * This method adds the given <code>child</code> to the end of the {@link #getChild(int) child-list}. For
   * vertical layouts this will be the bottom and for horizontal layouts this will be the right.
   * 
   * @see java.util.List#add(Object)
   * 
   * @param child is the {@link UiWidgetRegular} to add as child of this panel.
   */
  void addChild(UiWidgetRegular child);

  /**
   * This method adds the given <code>child</code> at the given <code>index</code>.
   * 
   * @see #addChild(UiWidgetRegular)
   * @see java.util.List#add(int, Object)
   * 
   * @param child is the {@link UiWidgetRegular} to add as child of this panel.
   * @param index is the {@link #getChild(int) index} of the new child. It has to be in the range from
   *        <code>0</code> to {@link #getChildCount()}.
   */
  void addChild(UiWidgetRegular child, int index);

  /**
   * This method removes the given <code>child</code> from this panel.
   * 
   * @see java.util.List#remove(Object)
   * 
   * @param child is the {@link UiWidgetRegular} to remove from this panel.
   * @return <code>true</code> if the given <code>child</code> has been removed successfully,
   *         <code>false</code> otherwise (it has NOT been previously added).
   */
  boolean removeChild(UiWidgetRegular child);

  /**
   * This method removes the child at the given <code>index</code> from this panel.
   * 
   * @see java.util.List#remove(int)
   * 
   * @param index is the {@link #getChild(int) index} of the child to remove. It has to be in the range from
   *        <code>0</code> to <code>{@link #getChildCount()} - 1</code>.
   * @return the removed child that has previously been at the given <code>index</code>.
   */
  UiWidgetRegular removeChild(int index);

}
