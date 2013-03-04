/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.IconConstants;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainPrevious;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the {@link UiSingleWidgetButtonFactory} for {@link UiHandlerPlainPrevious previous}
 * {@link UiWidgetButton buttons}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetButtonFactoryPrevious extends AbstractUiSingleWidgetButtonFactory<UiHandlerPlainPrevious> {

  /**
   * The constructor.
   */
  public UiSingleWidgetButtonFactoryPrevious() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<UiHandlerPlainPrevious> getHandlerInterface() {

    return UiHandlerPlainPrevious.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstance(UiHandlerPlain handler) {

    return (handler instanceof UiHandlerPlainPrevious);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton create(UiContext context, final UiHandlerPlainPrevious handler, boolean preventConfirmationPopup) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onPrevious(null);
      }
    };
    NlsMessage tooltipPrevious = getBundle().tooltipPrevious();
    UiWidgetImage icon = context.getWidgetFactoryAdvanced().createImage(IconConstants.ICON_BUTTON_PREVIOUS,
        tooltipPrevious.getLocalizedMessage());
    return createButton(context, null, clickHandler, tooltipPrevious, icon);
  }

}
