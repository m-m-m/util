/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

/**
 * This is the abstract interface for an {@link UiWidget} that is a <em>composite</em>. Composite means that
 * is may have {@link #getChild(int) children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AbstractUiWidgetComposite extends UiWidget {

  /**
   * This method determines the index of the given <code>child</code> in the list of {@link #getChild(int)
   * children} of this composite.<br/>
   * The signature is by intention not using the generic to support delegation without suffering by the java
   * compiler.
   * 
   * @see java.util.List#indexOf(Object)
   * 
   * @param child is the potential {@link #getChild(int) child}.
   * @return the {@link #getChild(int) index} of the given <code>child</code> or <code>-1</code> if it is NOT
   *         a {@link #getChild(int) child} of this composite.
   */
  int getChildIndex(UiWidget child);

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
  UiWidget getChild(int index) throws IndexOutOfBoundsException;

  /**
   * This method gets the child with the given <code>id</code>.
   * 
   * @param childId is the {@link #getId() ID} of the requested {@link #getChild(int) child}.
   * @return the {@link #getChild(int) child} with the given <code>id</code> or <code>null</code> if no such
   *         {@link #getChild(int) child} exists.
   */
  UiWidget getChild(String childId);

  /**
   * This method gets the number of children in this composite.
   * 
   * @return the child count.
   */
  int getChildCount();

}
