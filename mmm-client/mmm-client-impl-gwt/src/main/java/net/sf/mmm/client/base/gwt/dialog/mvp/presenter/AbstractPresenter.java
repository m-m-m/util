/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog.mvp.presenter;

import net.sf.mmm.client.base.gwt.GwtClientContext;
import net.sf.mmm.client.base.gwt.dialog.mvp.common.AbstractPopupView;
import net.sf.mmm.client.base.gwt.dialog.mvp.common.AbstractView;
import net.sf.mmm.client.base.gwt.dialog.mvp.common.UiHandlersAbstractPresenter;
import net.sf.mmm.client.impl.gwt.gin.ClientGinjector;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * This is the abstract base class for all {@link Presenter}s using this framework.<br/>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VIEW> is the generic type of the {@link #getView() view}.
 * @param <PROXY> is the generic type of the {@link #getProxy() proxy}.
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractPresenter<VIEW extends AbstractView, PROXY extends Proxy<?>> extends
    Presenter<VIEW, PROXY> implements UiHandlersAbstractPresenter {

  /**
   * The constructor.
   * 
   * @param eventBus the {@link EventBus}.
   * @param view the {@link #getView() view}.
   * @param proxy the {@link #getProxy() proxy}.
   */
  @SuppressWarnings("unchecked")
  public AbstractPresenter(EventBus eventBus, VIEW view, PROXY proxy) {

    super(eventBus, view, proxy);
    view.setUiHandlers(this);
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
  public void openPopup(AbstractPopupPresenterWidget<? extends AbstractPopupView> popup) {

    addToPopupSlot(popup);
  }

}
