/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainCancel;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;

/**
 * This is the {@link UiSingleWidgetButtonFactory} for {@link UiHandlerPlainCancel cancel}
 * {@link UiWidgetButton buttons}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetButtonFactoryCancel extends AbstractUiSingleWidgetButtonFactory<UiHandlerPlainCancel> {

  /**
   * The constructor.
   */
  public UiSingleWidgetButtonFactoryCancel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<UiHandlerPlainCancel> getHandlerInterface() {

    return UiHandlerPlainCancel.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstance(UiHandlerPlain handler) {

    return (handler instanceof UiHandlerPlainCancel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton create(UiContext context, final UiHandlerPlainCancel handler, boolean preventConfirmationPopup) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onCancel();
      }
    };
    return createButton(context, getBundle().labelCancel(), clickHandler, null, null);
  }

}
