/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client.dialog.page;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetSlot;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomVerticalPanel;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PageViewWidget extends UiWidgetCustomVerticalPanel {

  /** @see #getSlotMainDialog() */
  private UiWidgetSlot slotMainDialog;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public PageViewWidget(UiContext context) {

    super(context);
    this.slotMainDialog = context.getWidgetFactory().create(UiWidgetSlot.class);
    getDelegate().addChild(this.slotMainDialog);
  }

  /**
   * @return the {@link UiWidgetSlot} for the main dialog.
   */
  public UiWidgetSlot getSlotMainDialog() {

    return this.slotMainDialog;
  }

}
