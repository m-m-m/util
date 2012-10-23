/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog.mvp.presenter;

import net.sf.mmm.client.api.dialog.Dialog;
import net.sf.mmm.client.base.gwt.dialog.mvp.common.AbstractPopupView;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

import com.google.gwt.event.shared.EventBus;

/**
 * This is the abstract base class for all {@link AbstractPresenterWidget presenter widgets} that should be
 * {@link #openPopup(AbstractPopupPresenterWidget) opened in a popup window}.<br/>
 * <b>NOTE:</b><br/>
 * For simple popups that just show a message with one or multiple buttons, you should use
 * {@link net.sf.mmm.client.ui.api.SimplePopupManager} (see
 * {@link net.sf.mmm.client.base.gwt.GwtClientContext#getComponents()}). This class is only for real popup
 * dialogs that allow to display and potentially edit complex data.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VIEW> is the generic type of the {@link #getView() view}.
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractPopupPresenterWidget<VIEW extends AbstractPopupView> extends
    AbstractPresenterWidget<VIEW> {

  /**
   * The constructor.
   * 
   * @param eventBus the {@link EventBus}.
   * @param view the {@link #getView() view}.
   */
  public AbstractPopupPresenterWidget(EventBus eventBus, VIEW view) {

    super(eventBus, view);
  }

  /**
   * This method opens this {@link AbstractPopupPresenterWidget} (or more precisely its {@link #getView()
   * view}) in a {@link #openPopup(AbstractPopupPresenterWidget) popup window}.
   */
  public void open() {

    Dialog dialog = getComponents().getDialogManager().getCurrentMainDialog();
    if (dialog == null) {
      dialog = getComponents().getDialogManager().getCurrentDialog(Dialog.TYPE_HOME);
      NlsNullPointerException.checkNotNull(Dialog.class, dialog);
    }
    AbstractPresenterWidget<?> presenterWidget = (AbstractPresenterWidget<?>) dialog;
    presenterWidget.openPopup(this);
  }

}
