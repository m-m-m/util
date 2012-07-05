/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog.mvp.presenter;

import net.sf.mmm.client.base.gwt.dialog.mvp.common.AbstractView;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 * This is the abstract base class for all {@link PresenterWidget}s using this framework.<br/>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VIEW> is the generic type of the {@link #getView() view}.
 */
public abstract class AbstractPresenterWidget<VIEW extends AbstractView> extends PresenterWidget<VIEW> {

  /**
   * The constructor.
   * 
   * @param eventBus
   * @param view
   */
  public AbstractPresenterWidget(EventBus eventBus, VIEW view) {

    super(eventBus, view);

  }

}
