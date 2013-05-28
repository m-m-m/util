/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client.dialog.home;

import net.sf.mmm.app.client.dialog.MmmDialogConstants;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.dialog.DialogManager;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomVerticalPanel;

/**
 * This is the implementation of the {@link HomeDialogController#getView() home dialog view}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class HomeViewWidget extends UiWidgetCustomVerticalPanel {

  /** The instance of {@link DialogManager}. */
  private final DialogManager dialogManager;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param dialogManager is the instance of {@link DialogManager}.
   */
  public HomeViewWidget(UiContext context, DialogManager dialogManager) {

    super(context);
    this.dialogManager = dialogManager;

    UiWidgetLabel label = context.getWidgetFactory().createLabel("Hello World!");
    getDelegate().addChild(label);

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        HomeViewWidget.this.dialogManager.navigateTo(MmmDialogConstants.DIALOG_ID_TEST);
      }
    };
    UiWidgetButton button = context.getWidgetFactory().createButton("Test", clickHandler);
    getDelegate().addChild(button);
  }

}
