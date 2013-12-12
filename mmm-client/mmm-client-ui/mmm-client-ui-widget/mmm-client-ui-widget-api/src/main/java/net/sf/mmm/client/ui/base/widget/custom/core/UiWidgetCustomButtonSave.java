/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.core;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventClick;
import net.sf.mmm.client.ui.api.handler.action.UiHandlerActionSave;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.util.nls.api.NlsAccess;

/**
 * This is a {@link UiWidgetCustomButton} for the {@link UiHandlerActionSave#onSave(UiEvent) save} operation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCustomButtonSave extends UiWidgetCustomButton {

  /**
   * The constructor.
   * 
   * @param context - see {@link #getContext()}.
   * @param handler gets {@link UiHandlerActionSave#onSave(UiEvent) invoked} if the button is clicked.
   */
  public UiWidgetCustomButtonSave(UiContext context, final UiHandlerActionSave handler) {

    super(context);
    setLabel(NlsAccess.getBundleFactory().createBundle(NlsBundleClientUiRoot.class).labelSave().getLocalizedMessage());
    addClickHandler(new UiHandlerEventClick() {

      @Override
      public void onClick(UiEventClick event) {

        handler.onSave(event);
      }
    });
  }
}
