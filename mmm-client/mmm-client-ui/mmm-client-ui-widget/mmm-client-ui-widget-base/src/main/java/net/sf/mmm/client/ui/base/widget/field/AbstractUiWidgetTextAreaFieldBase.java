/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteHeightInRows;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTextAreaFieldBase;

/**
 * This is the abstract base implementation for
 * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetTextAreaField} and
 * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetTextAreaFieldBase<ADAPTER extends UiWidgetAdapterTextAreaFieldBase> extends
    AbstractUiWidgetTextFieldBase<ADAPTER> implements AttributeWriteHeightInRows {

  /** @see #getHeightInRows() */
  private int heightInTextLines;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetTextAreaFieldBase(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.heightInTextLines = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<String> getValueClass() {

    return String.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.heightInTextLines != 0) {
      adapter.setHeightInRows(this.heightInTextLines);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInRows() {

    return this.heightInTextLines;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInRows(int lines) {

    this.heightInTextLines = lines;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setHeightInRows(lines);
    }
  }

}
