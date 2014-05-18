/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.home;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomVerticalPanel;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class HomeViewWidget extends UiWidgetCustomVerticalPanel {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   */
  public HomeViewWidget(UiContext context) {

    super(context);
    UiWidgetLabel label = context.getWidgetFactory().createLabel("Welcome to the wonderful world of m-m-m!");
    getDelegate().addChild(label);
  }

}
