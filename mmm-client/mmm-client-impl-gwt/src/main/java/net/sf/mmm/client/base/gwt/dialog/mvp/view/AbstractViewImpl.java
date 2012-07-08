/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog.mvp.view;

import net.sf.mmm.client.base.gwt.GwtClientContext;
import net.sf.mmm.client.base.gwt.dialog.mvp.common.AbstractView;
import net.sf.mmm.client.base.gwt.dialog.mvp.common.UiHandlersAbstractPresenterWidget;
import net.sf.mmm.client.impl.gwt.gin.ClientGinjector;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * This is the abstract base class for all {@link AbstractView view implementations} using this framework.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <HANDLER> is the generic type of the {@link #getUiHandlers() UI handler}.
 */
public abstract class AbstractViewImpl<HANDLER extends UiHandlersAbstractPresenterWidget> extends
    ViewWithUiHandlers<HANDLER> implements AbstractView<HANDLER> {

  /**
   * The constructor.
   */
  public AbstractViewImpl() {

    super();
  }

  /**
   * This method gets the {@link ClientGinjector} that allows to access components such as the
   * {@link ClientGinjector#getServiceCaller() service caller}.
   * 
   * @return the {@link ClientGinjector}.
   */
  protected ClientGinjector getComponents() {

    return GwtClientContext.getInstance().getComponents();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onError(Throwable error) {

    getComponents().getPopupManager().showPopup(error);
  }

}
