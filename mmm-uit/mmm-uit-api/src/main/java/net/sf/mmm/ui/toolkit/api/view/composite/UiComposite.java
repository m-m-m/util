/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the interface of a UI component that contains other UI components.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiComposite<E extends UiElement> extends UiElement {

  /**
   * This method gets the number of sub-components in this composite component.
   * 
   * @return the component count.
   */
  int getChildCount();

  /**
   * This method gets the sub-component at the given index.
   * 
   * @param index is the position of the requested sub-component.
   * @return the component at the given index.
   * @throws IndexOutOfBoundsException if the given index is not in the range
   *         from <code>0</code> to <code>{@link #getChildCount()} - 1</code>.
   */
  E getChild(int index) throws IndexOutOfBoundsException;

}
