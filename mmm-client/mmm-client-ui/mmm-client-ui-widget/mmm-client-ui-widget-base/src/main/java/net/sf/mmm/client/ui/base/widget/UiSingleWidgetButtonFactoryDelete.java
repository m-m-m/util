/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.UiPopupHelper;
import net.sf.mmm.client.ui.api.common.MessageSeverity;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainDelete;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.util.lang.api.Callback;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the {@link UiSingleWidgetButtonFactory} for {@link UiHandlerPlainDelete delete}
 * {@link UiWidgetButton buttons}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetButtonFactoryDelete extends AbstractUiSingleWidgetButtonFactory<UiHandlerPlainDelete> {

  /**
   * The constructor.
   */
  public UiSingleWidgetButtonFactoryDelete() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<UiHandlerPlainDelete> getHandlerInterface() {

    return UiHandlerPlainDelete.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstance(UiHandlerPlain handler) {

    return (handler instanceof UiHandlerPlainDelete);
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
  public UiWidgetButton create(final UiContext context, final UiHandlerPlainDelete handler,
      final boolean preventConfirmationPopup) {

    final NlsBundleClientUiRoot bundle = getBundle();
    NlsMessage labelDelete = bundle.labelDelete();
    final String labelDeleteText = labelDelete.getLocalizedMessage();
    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        if (preventConfirmationPopup) {
          handler.onDelete(null);
        } else {
          UiPopupHelper popupHelper = context.getPopupHelper();
          Callback<String> callback = new Callback<String>() {

            @Override
            public Void apply(String argument) {

              if (UiPopupHelper.BUTTON_ID_OK.equals(argument)) {
                handler.onDelete(null);
              }
              return null;
            }
          };
          String message = bundle.messageConfirmDelete().getLocalizedMessage();
          String title = bundle.titleDeletePopup().getLocalizedMessage();
          String cancel = bundle.labelCancel().getLocalizedMessage();
          popupHelper.showPopup(message, MessageSeverity.QUESTION, title, callback, labelDeleteText, cancel);
        }
      }
    };
    return createButton(context, labelDelete, clickHandler, null, null);
  }

}
