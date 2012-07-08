/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog.mvp.presenter;

import net.sf.mmm.client.base.gwt.GwtClientContext;
import net.sf.mmm.client.base.gwt.dialog.mvp.common.AbstractPopupView;
import net.sf.mmm.client.base.gwt.dialog.mvp.common.AbstractView;
import net.sf.mmm.client.base.gwt.dialog.mvp.common.UiHandlersAbstractPresenterWidget;
import net.sf.mmm.client.impl.gwt.gin.ClientGinjector;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 * This is the abstract base class for all {@link PresenterWidget}s using this framework.<br/>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VIEW> is the generic type of the {@link #getView() view}.
 */
public abstract class AbstractPresenterWidget<VIEW extends AbstractView> extends PresenterWidget<VIEW> implements
    UiHandlersAbstractPresenterWidget {

  /**
   * The constructor.
   * 
   * @param eventBus the {@link EventBus}.
   * @param view the {@link #getView() view}.
   */
  public AbstractPresenterWidget(EventBus eventBus, VIEW view) {

    super(eventBus, view);
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
   * @see #addToPopupSlot(PresenterWidget)
   * 
   * @param child is the {@link AbstractPopupPresenterWidget popup} to add.
   */
  public void addToPopupSlot(AbstractPopupPresenterWidget<? extends AbstractPopupView<?>> child) {

    addToPopupSlot((PresenterWidget<? extends PopupView>) child);
  }

}
