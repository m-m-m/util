/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

/**
 * This is the abstract interface for a {@link UiWidgetComposite composite widget} that can contain
 * {@link #getChildCount() multiple} {@link #getChild(int) children} and allows to #re.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public interface UiWidgetMultiComposite<CHILD extends UiWidget> extends UiWidgetComposite<CHILD> {

  /**
   * This method removes the given <code>child</code> from this panel.
   * 
   * @see java.util.List#remove(Object)
   * 
   * @param child is the {@link UiWidget} to remove from this panel.
   * @return <code>true</code> if the given <code>child</code> has been removed successfully,
   *         <code>false</code> otherwise (it has NOT been previously added).
   */
  boolean removeChild(CHILD child);

  /**
   * This method removes the child at the given <code>index</code> from this panel.
   * 
   * @see java.util.List#remove(int)
   * 
   * @param index is the {@link #getChild(int) index} of the child to remove. It has to be in the range from
   *        <code>0</code> to <code>{@link #getChildCount()} - 1</code>.
   * @return the removed child that has previously been at the given <code>index</code>.
   */
  CHILD removeChild(int index);

}
