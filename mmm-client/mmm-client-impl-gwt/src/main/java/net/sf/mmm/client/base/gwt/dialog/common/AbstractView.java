/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog.common;

import net.sf.mmm.client.base.gwt.dialog.presenter.AbstractPresenter;
import net.sf.mmm.client.base.gwt.dialog.presenter.AbstractPresenterWidget;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;

/**
 * This is the interface for the {@link View} of an {@link AbstractPresenter} or
 * {@link AbstractPresenterWidget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <HANDLER> is the type of {@link #setUiHandlers(com.gwtplatform.mvp.client.UiHandlers) UiHandlers}
 */
public interface AbstractView<HANDLER extends UiHandlersAbstractPresenterWidget> extends View, HasUiHandlers<HANDLER> {

}
