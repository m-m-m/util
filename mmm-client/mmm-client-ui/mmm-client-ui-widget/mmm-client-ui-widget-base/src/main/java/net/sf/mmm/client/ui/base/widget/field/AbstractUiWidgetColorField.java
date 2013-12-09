/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.color.Color;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetColorField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterColorField;

/**
 * This is the abstract base implementation of {@link UiWidgetColorField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetColorField<ADAPTER extends UiWidgetAdapterColorField> extends
    AbstractUiWidgetField<ADAPTER, Color, Color> implements UiWidgetColorField {

  /** @see #getOptions() */
  private final List<Color> mutableOptions;

  /** @see #getOptions() */
  private final List<Color> options;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetColorField(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.mutableOptions = new ArrayList<Color>();
    this.options = Collections.unmodifiableList(this.mutableOptions);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (!this.options.isEmpty()) {
      adapter.setOptions(this.options);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<Color> getValueClass() {

    return Color.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOptions(List<Color> options) {

    this.mutableOptions.clear();
    this.mutableOptions.addAll(options);
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setOptions(this.options);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Color> getOptions() {

    return this.options;
  }

}
