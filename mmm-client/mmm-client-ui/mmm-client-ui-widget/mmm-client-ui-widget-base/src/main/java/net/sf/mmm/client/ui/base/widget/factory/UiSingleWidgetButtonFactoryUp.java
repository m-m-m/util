/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.event.UiEventClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainUp;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the {@link net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetButtonFactory} for
 * {@link UiHandlerPlainUp up} {@link UiWidgetButton buttons}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetButtonFactoryUp extends AbstractUiSingleWidgetButtonFactory<UiHandlerPlainUp> {

  /**
   * The constructor.
   */
  public UiSingleWidgetButtonFactoryUp() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<UiHandlerPlainUp> getHandlerInterface() {

    return UiHandlerPlainUp.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstance(UiHandlerPlain handler) {

    return (handler instanceof UiHandlerPlainUp);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Boolean getShowIfEditable() {

    return Boolean.TRUE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton create(UiContext context, final UiHandlerPlainUp handler, boolean preventConfirmationPopup,
      final Object variant) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiEventClick event) {

        handler.onUp(variant);
      }
    };
    // TODO: label/tooltip ?
    NlsMessage tooltip = getBundle().labelUp();
    return createButton(context, null, clickHandler, tooltip, CssStyles.ICON_UP);
  }

}
