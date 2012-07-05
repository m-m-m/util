/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog.mvp.view;

import net.sf.mmm.client.base.gwt.dialog.mvp.common.AbstractView;
import net.sf.mmm.client.base.gwt.dialog.mvp.common.UiHandlersAbstractPresenterWidget;

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

}
