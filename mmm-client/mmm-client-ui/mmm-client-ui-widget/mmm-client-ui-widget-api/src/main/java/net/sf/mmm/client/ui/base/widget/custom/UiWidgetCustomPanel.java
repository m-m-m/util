/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetPanel;

/**
 * This is the base class for a generic {@link UiWidgetCustomCompositeNoValue custom regular composite widget}
 * using a {@link UiWidgetPanel panel} as {@link #getDelegate() delegate}.<br/>
 * <b>ATTENTION:</b><br/>
 * This widget has no value and should only be used for (re-usable) generic layouts.
 * 
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCustomPanel<DELEGATE extends UiWidgetPanel<UiWidgetRegular>> extends
    UiWidgetCustomCompositeNoValue<UiWidgetRegular, DELEGATE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustomPanel(UiContext context, DELEGATE delegate) {

    super(context, delegate);
  }

}
