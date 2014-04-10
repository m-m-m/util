/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.UiPopupHelper;
import net.sf.mmm.client.ui.api.common.MessageSeverity;
import net.sf.mmm.client.ui.api.event.UiEventClick;
import net.sf.mmm.client.ui.api.handler.action.UiHandlerAction;
import net.sf.mmm.client.ui.api.handler.action.UiHandlerActionRemove;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.util.lang.api.function.Consumer;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the {@link net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetButtonFactory} for
 * {@link UiHandlerActionRemove remove} {@link UiWidgetButton buttons}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetButtonFactoryRemove extends AbstractUiSingleWidgetButtonFactory<UiHandlerActionRemove> {

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
  public Class<UiHandlerActionRemove> getHandlerInterface() {

    return UiHandlerActionRemove.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstance(UiHandlerAction handler) {

    return (handler instanceof UiHandlerActionRemove);
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
  public UiWidgetButton create(final UiContext context, final UiHandlerActionRemove handler,
      final boolean preventConfirmationPopup) {

    final NlsBundleClientUiRoot bundle = getBundle();

    NlsMessage labelRemove = bundle.labelRemove();
    final String labelRemoveText = labelRemove.getLocalizedMessage();
    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(final UiEventClick event) {

        if (preventConfirmationPopup) {
          handler.onRemove(event);
        } else {
          UiPopupHelper popupHelper = context.getPopupHelper();
          Consumer<String> callback = new Consumer<String>() {

            @Override
            public void accept(String argument) {

              if (UiPopupHelper.BUTTON_ID_OK.equals(argument)) {
                handler.onRemove(event);
              }
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
