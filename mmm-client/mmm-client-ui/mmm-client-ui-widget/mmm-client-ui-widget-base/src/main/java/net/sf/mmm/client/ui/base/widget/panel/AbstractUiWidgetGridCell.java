/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridCell;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalPanel;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetSingleMutableComposite;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterGridCell;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of {@link UiWidgetGridCell}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetGridCell<ADAPTER extends UiWidgetAdapterGridCell> extends
    AbstractUiWidgetSingleMutableComposite<ADAPTER, UiWidgetRegular> implements UiWidgetGridCell {

  /** @see #getColumnSpan() */
  private int columnSpan;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetGridCell(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.columnSpan = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.columnSpan != 1) {
      adapter.setColumnSpan(this.columnSpan);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getColumnSpan() {

    return this.columnSpan;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setColumnSpan(int columnSpan) {

    assert (columnSpan > 0);
    this.columnSpan = columnSpan;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setColumnSpan(columnSpan);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChildren(UiWidgetRegular... children) {

    NlsNullPointerException.checkNotNull("children", children);
    if (children.length == 0) {
      setChild(null);
    } else if (children.length == 1) {
      setChild(children[0]);
    } else {
      UiWidgetHorizontalPanel panel = getContext().getWidgetFactory().create(UiWidgetHorizontalPanel.class);
      for (UiWidgetRegular child : children) {
        panel.addChild(child);
      }
      setChild(panel);
    }
  }

}
