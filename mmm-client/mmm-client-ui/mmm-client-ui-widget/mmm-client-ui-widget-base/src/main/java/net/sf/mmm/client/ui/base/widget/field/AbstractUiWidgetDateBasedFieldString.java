/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import java.util.Date;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetDateTimeField;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterDateBasedField;
import net.sf.mmm.util.date.api.Iso8601UtilLimited;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetDateBasedField} using an {@link #getWidgetAdapter()
 * adapter} {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField#getValue() based} on
 * {@link String}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetDateBasedFieldString<ADAPTER extends UiWidgetAdapterDateBasedField<String>>
    extends AbstractUiWidgetDateBasedField<ADAPTER, String> {

  /** The instance of {@link Iso8601UtilLimited}. */
  private Iso8601UtilLimited iso8601Util;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetDateBasedFieldString(AbstractUiContext context) {

    super(context);
    this.iso8601Util = context.getContainer().get(Iso8601UtilLimited.class);
  }

  /**
   * @return <code>true</code> if {@link Date} is with time, <code>false</code> otherwise.
   */
  protected boolean hasTime() {

    return (this instanceof UiWidgetDateTimeField);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String convertFromValue(Date widgetValue) {

    if (hasTime()) {
      return this.iso8601Util.formatDate(widgetValue, true);
    } else {
      return this.iso8601Util.formatDateTime(widgetValue, true, true, true);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Date convertToValue(String adapterValue) {

    return this.iso8601Util.parseDate(adapterValue);
  }

}
