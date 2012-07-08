/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog.mvp.common;

import net.sf.mmm.util.lang.api.attribute.AttributeWriteTitle;

import com.gwtplatform.mvp.client.PopupView;

/**
 * This is the interface for the {@link AbstractView view} of a
 * {@link net.sf.mmm.client.api.dialog.Dialog#TYPE_POPUP popup} {@link net.sf.mmm.client.api.dialog.Dialog}.
 * 
 * @see PopupView
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <HANDLER> is the type of {@link #setUiHandlers(com.gwtplatform.mvp.client.UiHandlers) UiHandlers}
 */
public interface AbstractPopupView<HANDLER extends UiHandlersAbstractPresenterWidget> extends AbstractView<HANDLER>,
    PopupView, AttributeWriteTitle<String> {

  /**
   * The title is the text displayed in the header-bar of the popup window.
   * 
   * {@inheritDoc}
   */
  @Override
  String getTitle();

}
