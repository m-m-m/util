/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

/**
 * This is the abstract interface for a {@link UiWidgetComposite composite widget} that contains a single
 * {@link #getChild() child}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public interface UiWidgetSingleComposite<CHILD extends UiWidget> extends UiWidgetComposite<CHILD> {

  /**
   * This method gets the single {@link #getChild(int) child} of this composite.
   * 
   * @return the single child or <code>null</code> if not set.
   */
  CHILD getChild();

  /**
   * This method sets the single {@link #getChild() child} of this widget. The current {@link #getChild()
   * child} will be replaced (and is therefore removed from the UI).
   * 
   * @param child is the new child to set.
   */
  void setChild(CHILD child);

  /**
   * {@inheritDoc}
   * 
   * @return <code>0</code> if {@link #getChild()} will return <code>null</code>, <code>1</code> otherwise.
   */
  @Override
  int getChildCount();

}
