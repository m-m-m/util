/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainSave;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;

/**
 * This is the {@link UiSingleWidgetButtonFactory} for {@link UiHandlerPlainSave save} {@link UiWidgetButton
 * buttons}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetButtonFactorySave extends AbstractUiSingleWidgetButtonFactory<UiHandlerPlainSave> {

  /**
   * The constructor.
   */
  public UiSingleWidgetButtonFactorySave() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<UiHandlerPlainSave> getHandlerInterface() {

    return UiHandlerPlainSave.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstance(UiHandlerPlain handler) {

    return (handler instanceof UiHandlerPlainSave);
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
  public UiWidgetButton create(UiContext context, final UiHandlerPlainSave handler, boolean preventConfirmationPopup) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onSave();
      }
    };
    return createButton(context, getBundle().labelSave(), clickHandler, null, null);
  }

}
