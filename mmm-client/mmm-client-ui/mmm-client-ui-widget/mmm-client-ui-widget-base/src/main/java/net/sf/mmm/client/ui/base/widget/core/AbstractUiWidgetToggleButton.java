/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetToggleButton;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterToggleButton;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the abstract base implementation of {@link UiWidgetToggleButton}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetToggleButton<ADAPTER extends UiWidgetAdapterToggleButton> extends
    AbstractUiWidgetAbstractButton<ADAPTER, Boolean> implements UiWidgetToggleButton {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetToggleButton(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    addStyle(STYLE_TOGGLE_BUTTON);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<Boolean> getValueClass() {

    return Boolean.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    Boolean value = getRecentValue();
    if (value != null) {
      adapter.setValue(value);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Boolean doGetValue(Boolean template, ValidationState state) throws RuntimeException {

    if (hasWidgetAdapter()) {
      return getWidgetAdapter().getValue();
    } else {
      return getRecentValue();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(Boolean value, boolean forUser) {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setValue(value);
    }
  }

}
