/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterDynamicComposite;

/**
 * This is the abstract base implementation of {@link UiWidgetDynamicComposite}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetDynamicComposite<ADAPTER extends UiWidgetAdapterDynamicComposite<?, CHILD>, CHILD extends UiWidget>
    extends AbstractUiWidgetComposite<ADAPTER, CHILD> implements UiWidgetDynamicComposite<CHILD> {

  /** @see #getChild(int) */
  private final List<CHILD> children;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetDynamicComposite(AbstractUiContext context) {

    super(context);
    this.children = new ArrayList<CHILD>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    for (CHILD child : this.children) {
      adapter.addChild(child, -1);
    }
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
      removeFromParent(child);
      if (hasWidgetAdapter()) {
        getWidgetAdapter().removeChild(child, index);
      }
    }
    return child;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int getChildIndex(UiWidget child) {

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
    if (hasWidgetAdapter()) {
      getWidgetAdapter().addChild(child, -1);
    }
    doAddChild(child, -1);
    setParent(child, this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(CHILD child, int index) {

    this.children.add(index, child);
    doAddChild(child, index);
    setParent(child, this);
  }

  /**
   * This method is called from {@link #addChild(UiWidget)} or {@link #addChild(UiWidget, int)}. It can be
   * overridden to implement custom logic (e.g. attaching the child to the
   * {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter}).
   * 
   * @param child is the new {@link #getChild(int) child} to add or insert.
   * @param index <code>-1</code> to add the child at the end, otherwise the index where to
   *        {@link #addChild(UiWidget, int) insert} the child.
   */
  protected void doAddChild(CHILD child, int index) {

    // nothing to do by default...
  }

}
