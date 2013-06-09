/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalPanel;

/**
 * This is the base class for a generic {@link UiWidgetCustomPanel custom panel widget} using a
 * {@link UiWidgetVerticalPanel} as {@link #getDelegate() delegate}.<br/>
 * <b>ATTENTION:</b><br/>
 * This widget has no value and should only be used for (re-usable) generic layouts.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCustomVerticalPanel extends UiWidgetCustomPanel<UiWidgetVerticalPanel> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetCustomVerticalPanel(UiContext context) {

    this(context, context.getWidgetFactory().create(UiWidgetVerticalPanel.class));
  }

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustomVerticalPanel(UiContext context, UiWidgetVerticalPanel delegate) {

    super(context, delegate);
  }

}
