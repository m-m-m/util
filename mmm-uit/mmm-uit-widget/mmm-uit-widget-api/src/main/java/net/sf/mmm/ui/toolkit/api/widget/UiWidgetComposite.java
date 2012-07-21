/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

/**
 * This is the interface for an {@link UiWidget} that is a composite.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public abstract interface UiWidgetComposite<CHILD extends UiWidget> extends UiWidget {

  /**
   * This method determines the index of the given <code>child</code> in the list of {@link #getChild(int)
   * children} of this composite.
   * 
   * @see java.util.List#indexOf(Object)
   * 
   * @param child is the potential {@link #getChild(int) child}.
   * @return the {@link #getChild(int) index} of the given <code>child</code> or <code>-1</code> if it is NOT
   *         a {@link #getChild(int) child} of this composite.
   */
  int getChildIndex(CHILD child);

  /**
   * This method gets the child at the given index.
   * 
   * @see java.util.List#get(int)
   * 
   * @param index is the index of the requested child. Has to be in the range from <code>0</code> to
   *        <code>{@link #getChildCount()} - 1</code>.
   * @return the requested child.
   */
  CHILD getChild(int index);

  /**
   * This method gets the number of children in this composite.
   * 
   * @return the child count.
   */
  int getChildCount();

}
