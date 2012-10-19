/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetSwitchComposite;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterSwitchComposite;

/**
 * This is the abstract base implementation of {@link UiWidgetSwitchComposite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public abstract class AbstractUiWidgetSwitchComposite<ADAPTER extends UiWidgetAdapterSwitchComposite<?, CHILD>, CHILD extends UiWidget>
    extends AbstractUiWidgetDynamicComposite<ADAPTER, CHILD> implements UiWidgetSwitchComposite<CHILD> {

  /** @see #showChild(int) */
  private int showChildIndex;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetSwitchComposite(AbstractUiWidgetFactory<?> factory) {

    super(factory);
    this.showChildIndex = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.showChildIndex > 0) {
      adapter.showChild(this.showChildIndex);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showChild(int index) {

    this.showChildIndex = index;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().showChild(index);
    }
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
