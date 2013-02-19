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
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainRemove;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.util.lang.api.Callback;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the {@link UiSingleWidgetButtonFactory} for {@link UiHandlerPlainRemove remove}
 * {@link UiWidgetButton buttons}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetButtonFactoryRemove extends AbstractUiSingleWidgetButtonFactory<UiHandlerPlainRemove> {

  /**
   * The constructor.
   */
  public UiSingleWidgetButtonFactoryRemove() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<UiHandlerPlainRemove> getHandlerInterface() {

    return UiHandlerPlainRemove.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstance(UiHandlerPlain handler) {

    return (handler instanceof UiHandlerPlainRemove);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton create(final UiContext context, final UiHandlerPlainRemove handler,
      final boolean preventConfirmationPopup) {

    final NlsBundleClientUiRoot bundle = getBundle();

    NlsMessage labelRemove = bundle.labelRemove();
    final String labelRemoveText = labelRemove.getLocalizedMessage();
    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        if (preventConfirmationPopup) {
          handler.onRemove();
        } else {
          UiPopupHelper popupHelper = context.getPopupHelper();
          Callback<String> callback = new Callback<String>() {

            @Override
            public Void apply(String argument) {

              if (UiPopupHelper.BUTTON_ID_OK.equals(argument)) {
                handler.onRemove();
              }
              return null;
            }
          };
          String message = bundle.messageConfirmRemove().getLocalizedMessage();
          String title = bundle.titleRemovePopup().getLocalizedMessage();
          String cancel = bundle.labelCancel().getLocalizedMessage();
          popupHelper.showPopup(message, MessageSeverity.QUESTION, title, callback, labelRemoveText, cancel);
        }
      }
    };
    return createButton(context, labelRemove, clickHandler, null, null);
  }

}
