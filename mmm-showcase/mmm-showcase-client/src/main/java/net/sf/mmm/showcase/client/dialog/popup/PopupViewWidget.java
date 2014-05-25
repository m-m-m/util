/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.popup;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.event.UiEventClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomGridPanel;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PopupViewWidget extends UiWidgetCustomGridPanel<Void> {

  /** The example popup. */
  private final ExamplePopup popup;

  /**
   * The constructor.
   *
   * @param context - see {@link #getContext()}.
   */
  public PopupViewWidget(UiContext context) {

    super(context, Void.class);
    this.popup = new ExamplePopup(context);
    UiWidgetFactory factory = context.getWidgetFactory();
    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiEventClick event) {

        PopupViewWidget.this.popup.centerWindow();
        PopupViewWidget.this.popup.open();
      }
    };
    UiWidgetButton button = factory.createButton("Open Popup", clickHandler);
    getDelegate().addChildren(button);
  }

}
