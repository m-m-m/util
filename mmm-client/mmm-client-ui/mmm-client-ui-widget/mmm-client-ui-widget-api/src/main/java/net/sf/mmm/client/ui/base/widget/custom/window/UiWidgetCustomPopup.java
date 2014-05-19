/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetButtonPanel;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetPopup;

/**
 * This is the abstract base class for a {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom custom
 * widget} implementing {@link UiWidgetPopup}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomPopup extends UiWidgetCustomAbstractDialogWindow<UiWidgetPopup> implements
    UiWidgetPopup {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetCustomPopup(UiContext context) {

    super(context, context.getWidgetFactory().create(UiWidgetPopup.class));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButtonPanel getButtonPanel() {

    return getDelegate().getButtonPanel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton createAndAddCloseButton() {

    return getDelegate().createAndAddCloseButton();
  }

}
