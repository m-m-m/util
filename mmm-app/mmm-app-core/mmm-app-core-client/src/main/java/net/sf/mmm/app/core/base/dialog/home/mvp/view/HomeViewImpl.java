/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.core.base.dialog.home.mvp.view;

import net.sf.mmm.app.core.base.dialog.home.mvp.common.HomeView;
import net.sf.mmm.app.core.base.dialog.home.mvp.common.UiHandlersHome;
import net.sf.mmm.app.core.base.dialog.home.mvp.presenter.HomePresenter;
import net.sf.mmm.client.base.gwt.dialog.mvp.view.AbstractViewImpl;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link HomeView}.
 * 
 * @see net.sf.mmm.app.core.base.dialog.home.mvp.presenter.HomePresenter
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class HomeViewImpl extends AbstractViewImpl<UiHandlersHome> implements HomeView {

  /**
   * The constructor.
   */
  public HomeViewImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setInSlot(Object slot, Widget content) {

    if (slot == HomePresenter.SLOT_HEADER) {
      setSlotHeader(content);
    } else if (slot == HomePresenter.SLOT_MAIN) {
      setSlotMain(content);
    } else if (slot == HomePresenter.SLOT_FOOTER) {
      setSlotFooter(content);
    } else {
      super.setInSlot(slot, content);
    }
  }

  /**
   * TODO: javadoc
   * 
   * @param content
   */
  private void setSlotFooter(Widget content) {

    // TODO Auto-generated method stub

  }

  /**
   * TODO: javadoc
   * 
   * @param content
   */
  private void setSlotMain(Widget content) {

    // TODO Auto-generated method stub

  }

  /**
   * TODO: javadoc
   * 
   * @param content
   */
  private void setSlotHeader(Widget content) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Widget asWidget() {

    return null;
  }

}
