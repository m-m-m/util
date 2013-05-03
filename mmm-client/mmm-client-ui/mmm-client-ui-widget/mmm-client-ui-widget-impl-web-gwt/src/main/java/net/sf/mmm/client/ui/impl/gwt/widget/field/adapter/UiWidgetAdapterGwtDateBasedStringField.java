/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.Date;

import net.sf.mmm.util.date.api.Iso8601UtilLimited;
import net.sf.mmm.util.date.base.Iso8601UtilLimitedImpl;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterDateBasedField} using GWT based on a
 * textual widget that takes the {@link Date} value as {@link String}. Therefore this class provides
 * conversion methods.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtDateBasedStringField<WIDGET extends Widget> extends
    UiWidgetAdapterGwtDateBasedField<WIDGET> {

  /** The instance of {@link Iso8601UtilLimited}. */
  private Iso8601UtilLimited iso8601Util;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtDateBasedStringField() {

    super();
  }

  /**
   * @return the instance of {@link Iso8601UtilLimited}.
   */
  protected Iso8601UtilLimited getIso8601Util() {

    if (this.iso8601Util == null) {
      // TODO hohwille
      // this.iso8601Util = getUiWidget().getContext().getContainer().get(Iso8601UtilLimited.class);
      this.iso8601Util = new Iso8601UtilLimitedImpl();
    }
    return this.iso8601Util;
  }

  /**
   * @return <code>true</code> if {@link Date} is with time, <code>false</code> otherwise.
   */
  protected abstract boolean hasTime();

  /**
   * Converts (formats) the given {@link Date} to a {@link String} in HTML5 compatible format.
   * 
   * @param date is the date to format.
   * @return the given {@link Date} as {@link String}.
   */
  protected String convertDateToString(Date date) {

    if (hasTime()) {
      return getIso8601Util().formatDate(date, true);
    } else {
      return getIso8601Util().formatDateTime(date, true, true, true);
    }
  }

  /**
   * Converts (parses) the given {@link String} to a {@link Date}.
   * 
   * @param dateValue is the {@link Date} given as {@link String}.
   * @return the converted {@link Date}.
   */
  protected Date convertStringToDate(String dateValue) {

    return getIso8601Util().parseDate(dateValue);
  }

}
