/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog.presenter;

import net.sf.mmm.client.base.gwt.dialog.common.AbstractView;
import net.sf.mmm.client.base.gwt.dialog.common.UiHandlersAbstractPresenter;

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
public abstract class AbstractPresenter<VIEW extends AbstractView, PROXY extends Proxy<?>> extends
    Presenter<VIEW, PROXY> implements UiHandlersAbstractPresenter {

  /**
   * The constructor.
   * 
   * @param eventBus the {@link EventBus}.
   * @param view the {@link #getView() view}.
   * @param proxy the {@link #getProxy() proxy}.
   */
  public AbstractPresenter(EventBus eventBus, VIEW view, PROXY proxy) {

    super(eventBus, view, proxy);
  }

}
