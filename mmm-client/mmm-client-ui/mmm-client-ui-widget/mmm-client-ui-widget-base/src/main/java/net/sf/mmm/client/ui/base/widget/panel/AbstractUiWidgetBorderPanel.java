/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetBorderPanel;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetSingleMutableComposite;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterBorderPanel;

/**
 * This is the abstract base implementation of {@link UiWidgetBorderPanel}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetBorderPanel<ADAPTER extends UiWidgetAdapterBorderPanel> extends
    AbstractUiWidgetSingleMutableComposite<ADAPTER, UiWidgetRegular> implements UiWidgetBorderPanel {

  /** @see #getLabel() */
  private String label;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetBorderPanel(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.label != null) {
      adapter.setLabel(this.label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.label = label;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setLabel(label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return this.label;
  }

}
