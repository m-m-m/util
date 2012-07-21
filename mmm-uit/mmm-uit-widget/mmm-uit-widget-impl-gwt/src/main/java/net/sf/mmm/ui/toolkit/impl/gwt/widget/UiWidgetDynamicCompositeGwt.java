/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetDynamicComposite;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetDynamicComposite} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public abstract class UiWidgetDynamicCompositeGwt<WIDGET extends Widget, CHILD extends UiWidget> extends
    UiWidgetCompositeGwt<WIDGET, CHILD> implements UiWidgetDynamicComposite<CHILD> {

  /** @see #getChild(int) */
  private final List<CHILD> children;

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public UiWidgetDynamicCompositeGwt(WIDGET widget) {

    super(widget);
    this.children = new ArrayList<CHILD>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeChild(CHILD child) {

    int index = getChildIndex(child);
    if (index >= 0) {
      removeChild(index);
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final CHILD removeChild(int index) {

    CHILD child = this.children.remove(index);
    if (child != null) {
      doRemoveChild(index, child);
    }
    return child;
  }

  /**
   * This method gets invoked from {@link #removeChild(UiWidget)} and {@link #removeChild(int)} to actually
   * remove the child from the UI.
   * 
   * @param index is the index of the child to remove.
   * @param child is the child to remove.
   */
  protected void doRemoveChild(int index, CHILD child) {

    // TODO
    // child.removeFromParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int getChildIndex(CHILD child) {

    return this.children.indexOf(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final CHILD getChild(int index) {

    return this.children.get(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int getChildCount() {

    return this.children.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(CHILD child) {

    this.children.add(child);
    doAddChild(child, -1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(CHILD child, int index) {

    this.children.add(index, child);
    doAddChild(child, index);
  }

  /**
   * This method is called from {@link #addChild(UiWidget)} or {@link #addChild(UiWidget, int)}. It can be
   * overridden to implement custom logic (e.g. attaching the child to {@link #getWidget()}).
   * 
   * @param child is the new {@link #getChild(int) child} to add or insert.
   * @param index <code>-1</code> to add the child at the end, otherwise the index where to
   *        {@link #addChild(UiWidget, int) insert} the child.
   */
  protected void doAddChild(CHILD child, int index) {

    // nothing to do by default...
  }

}
