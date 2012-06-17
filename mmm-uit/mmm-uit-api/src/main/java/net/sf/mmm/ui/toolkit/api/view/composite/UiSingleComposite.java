/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the abstract interface for a {@link UiComposite} that contains a
 * single {@link #getChild() child}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public abstract interface UiSingleComposite<CHILD extends UiElement> extends UiComposite<CHILD> {

  /**
   * This method will return <code>0</code> if {@link #getChild()} will return
   * <code>null</code>. Otherwise it will return <code>1</code>.
   * 
   * {@inheritDoc}
   */
  int getChildCount();

  /**
   * This method gets the single child of this composite.
   * 
   * @return the single child or <code>null</code> if not set.
   */
  CHILD getChild();

  /**
   * This method sets the single {@link #getChild() child}. The current
   * {@link #getChild() child} will be replaced (and is therefore removed from
   * the UI).
   * 
   * @param child is the child to set.
   */
  void setChild(CHILD child);

}
