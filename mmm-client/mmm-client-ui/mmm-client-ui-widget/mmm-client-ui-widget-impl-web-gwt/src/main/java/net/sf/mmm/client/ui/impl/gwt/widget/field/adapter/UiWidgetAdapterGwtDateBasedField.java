/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.Date;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterDateBasedField;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base implementation of {@link UiWidgetAdapterDateBasedField} using GWT.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtDateBasedField<WIDGET extends Widget> extends
    UiWidgetAdapterGwtField<WIDGET, Date, Date> implements UiWidgetAdapterDateBasedField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtDateBasedField() {

    super();
  }

}
