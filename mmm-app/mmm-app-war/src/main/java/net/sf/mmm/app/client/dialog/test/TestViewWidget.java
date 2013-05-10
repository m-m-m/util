/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client.dialog.test;

import net.sf.mmm.app.client.DialogConstants;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.dialog.DialogManager;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomVerticalPanel;

/**
 * This is the implementation of the {@link TestDialogController#getView() home dialog view}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class TestViewWidget extends UiWidgetCustomVerticalPanel {

  private final DialogManager dialogManager;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public TestViewWidget(UiContext context, DialogManager dialogManager) {

    super(context);
    this.dialogManager = dialogManager;

    UiWidgetLabel label = context.getWidgetFactory().createLabel("This is a test!");
    getDelegate().addChild(label);

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        TestViewWidget.this.dialogManager.navigateTo(DialogConstants.DIALOG_ID_HOME);
      }
    };
    UiWidgetButton button = context.getWidgetFactory().createButton("Home", clickHandler);
    getDelegate().addChild(button);

  }

}
