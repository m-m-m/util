/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetSingleComposite;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetSingleComposite} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public abstract class UiWidgetSingleCompositeGwt<WIDGET extends Widget, CHILD extends UiWidget> extends
    UiWidgetCompositeGwt<WIDGET, CHILD> implements UiWidgetSingleComposite<CHILD> {

  /** @see #getChild() */
  private CHILD child;

  /**
   * The constructor.
   */
  public UiWidgetSingleCompositeGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int getChildIndex(CHILD childWidget) {

    if ((childWidget == this.child) && (childWidget != null)) {
      return 0;
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final CHILD getChild(int index) {

    if (index == 0) {
      if (this.child != null) {
        return this.child;
      }
    }
    throw new IndexOutOfBoundsException(Integer.toString(index));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final CHILD getChild() {

    return this.child;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setChild(CHILD child) {

    doSetChild(this.child, child);
    this.child = child;
  }

  /**
   * This method is called from {@link #setChild(UiWidget)}. It can be overridden to implement custom logic
   * (e.g. attaching the child to {@link #getWidget()}).
   * 
   * @param oldChild is the old {@link #getChild() child} that is replaced or <code>null</code> if no child
   *        was set.
   * @param newChild is the new {@link #getChild() child}.
   */
  protected void doSetChild(CHILD oldChild, CHILD newChild) {

    // nothing to do by default...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getChildCount() {

    if (this.child == null) {
      return 0;
    } else {
      return 1;
    }
  }

}
