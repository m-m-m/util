/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

/**
 * This is the abstract interface for an {@link UiWidget} that is a composite.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public abstract interface UiWidgetComposite<CHILD extends UiWidget> extends UiWidgetAbstractComposite {

  /**
   * This method gets the child at the given index.
   * 
   * @see java.util.List#get(int)
   * 
   * @param index is the index of the requested child. Has to be in the range from <code>0</code> to
   *        <code>{@link #getChildCount()} - 1</code>.
   * @return the requested child.
   * @throws IndexOutOfBoundsException if the given <code>index</code> is out of bounds.
   */
  @Override
  CHILD getChild(int index) throws IndexOutOfBoundsException;

  /**
   * This method gets the child with the given <code>id</code>.
   * 
   * @param id is the {@link #getId() ID} of the requested {@link #getChild(int) child}.
   * @return the {@link #getChild(int) child} with the given <code>id</code> or <code>null</code> if no such
   *         {@link #getChild(int) child} exists.
   */
  @Override
  CHILD getChild(String id);

}
