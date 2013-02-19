/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainOpen;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;

/**
 * This is the {@link UiSingleWidgetButtonFactory} for {@link UiHandlerPlainOpen open} {@link UiWidgetButton
 * buttons}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetButtonFactoryOpen extends AbstractUiSingleWidgetButtonFactory<UiHandlerPlainOpen> {

  /**
   * The constructor.
   */
  public UiSingleWidgetButtonFactoryOpen() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<UiHandlerPlainOpen> getHandlerInterface() {

    return UiHandlerPlainOpen.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstance(UiHandlerPlain handler) {

    return (handler instanceof UiHandlerPlainOpen);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton create(UiContext context, final UiHandlerPlainOpen handler, boolean preventConfirmationPopup) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onOpen();
      }
    };
    return createButton(context, getBundle().labelOpen(), clickHandler, null, null);
  }

}
