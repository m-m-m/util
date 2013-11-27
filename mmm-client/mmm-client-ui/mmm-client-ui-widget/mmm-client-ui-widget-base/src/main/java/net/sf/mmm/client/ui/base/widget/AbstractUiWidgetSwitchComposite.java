/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetSwitchComposite;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterSwitchComposite;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the abstract base implementation of {@link UiWidgetSwitchComposite}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetSwitchComposite<ADAPTER extends UiWidgetAdapterSwitchComposite<CHILD>, CHILD extends UiWidget>
    extends AbstractUiWidgetDynamicComposite<ADAPTER, CHILD> implements UiWidgetSwitchComposite<CHILD> {

  /** @see #showChild(int) */
  private int showChildIndex;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetSwitchComposite(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.showChildIndex = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (getChildCount() > 0) {
      adapter.showChild(this.showChildIndex);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showChild(int index) {

    if ((index < 0) && (index > getChildCount())) {
      throw new ValueOutOfRangeException(Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(getChildCount()),
          this + ".showChild(index)");
    }
    this.showChildIndex = index;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().showChild(index);
    }
  }

  /**
   * @return the index of the {@link #getChild(int) child} to {@link #showChild(int) show}.
   */
  protected int getShowChildIndex() {

    return this.showChildIndex;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean showChild(CHILD child) {

    int index = getChildIndex(child);
    if (index >= 0) {
      showChild(index);
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean showChild(String id) {

    CHILD child = getChild(id);
    if (child != null) {
      return showChild(child);
    }
    return false;
  }

}
