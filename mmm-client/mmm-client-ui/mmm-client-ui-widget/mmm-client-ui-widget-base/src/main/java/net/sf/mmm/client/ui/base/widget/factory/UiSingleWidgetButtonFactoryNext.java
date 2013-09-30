/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.event.UiEventClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainNext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the {@link net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetButtonFactory} for
 * {@link UiHandlerPlainNext next} {@link UiWidgetButton buttons}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetButtonFactoryNext extends AbstractUiSingleWidgetButtonFactory<UiHandlerPlainNext> {

  /**
   * The constructor.
   */
  public UiSingleWidgetButtonFactoryNext() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<UiHandlerPlainNext> getHandlerInterface() {

    return UiHandlerPlainNext.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstance(UiHandlerPlain handler) {

    return (handler instanceof UiHandlerPlainNext);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton create(UiContext context, final UiHandlerPlainNext handler, boolean preventConfirmationPopup,
      final Object variant) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiEventClick event) {

        handler.onNext(variant);
      }
    };
    NlsMessage tooltipNext = getBundle().tooltipNext();
    return createButton(context, null, clickHandler, tooltipNext, CssStyles.ICON_STEP_NEXT);
  }

}
