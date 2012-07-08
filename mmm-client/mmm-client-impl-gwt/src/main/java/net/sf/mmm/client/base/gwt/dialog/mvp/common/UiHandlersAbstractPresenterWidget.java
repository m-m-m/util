/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog.mvp.common;

import net.sf.mmm.client.base.gwt.dialog.mvp.presenter.AbstractPopupPresenterWidget;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * This is the {@link UiHandlers} interface for
 * {@link net.sf.mmm.client.base.gwt.dialog.mvp.presenter.AbstractPresenterWidget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlersAbstractPresenterWidget extends UiHandlers {

  /**
   * This method opens the popup identified by the given {@link AbstractPopupPresenterWidget}. The popup will
   * have this presenter (widget) as parent. It will be centered on the screen and made visible if this
   * presenter (widget) is visible.
   * 
   * @see com.gwtplatform.mvp.client.PresenterWidget#addToPopupSlot(com.gwtplatform.mvp.client.PresenterWidget)
   * 
   * @param popup is the {@link AbstractPopupPresenterWidget} to add as Popup.
   */
  void openPopup(AbstractPopupPresenterWidget<? extends AbstractPopupView> popup);

}
