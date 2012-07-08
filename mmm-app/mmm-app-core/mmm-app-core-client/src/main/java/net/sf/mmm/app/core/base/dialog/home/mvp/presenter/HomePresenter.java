/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.core.base.dialog.home.mvp.presenter;

import javax.inject.Inject;

import net.sf.mmm.app.core.base.dialog.home.mvp.common.HomeView;
import net.sf.mmm.app.core.base.dialog.home.mvp.presenter.HomePresenter.MyProxy;
import net.sf.mmm.client.api.dialog.Dialog;
import net.sf.mmm.client.base.gwt.dialog.mvp.presenter.AbstractPresenter;
import net.sf.mmm.client.impl.base.place.PlaceConstants;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * This is the {@link AbstractPresenter presenter} for the {@link Dialog#TYPE_HOME home page}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class HomePresenter extends AbstractPresenter<HomeView, MyProxy> {

  /** The {@link ContentSlot} for the {@link Dialog#TYPE_MAIN main} {@link Dialog}s. */
  @ContentSlot
  public static final Type<RevealContentHandler<?>> SLOT_MAIN = new Type<RevealContentHandler<?>>();

  /** The {@link ContentSlot} for the {@link Dialog#TYPE_HEADER header} {@link Dialog}. */
  @ContentSlot
  public static final Type<RevealContentHandler<?>> SLOT_HEADER = new Type<RevealContentHandler<?>>();

  /** The {@link ContentSlot} for the {@link Dialog#TYPE_FOOTER footer} {@link Dialog}. */
  @ContentSlot
  public static final Type<RevealContentHandler<?>> SLOT_FOOTER = new Type<RevealContentHandler<?>>();

  /** The {@link ContentSlot} for the {@link Dialog#TYPE_NAVIGATION navigation} {@link Dialog}. */
  @ContentSlot
  public static final Type<RevealContentHandler<?>> SLOT_NAVIGATION = new Type<RevealContentHandler<?>>();

  /**
   * The constructor.
   * 
   * @param eventBus the {@link EventBus}.
   * @param view the {@link #getView() view}.
   * @param proxy the {@link #getProxy() proxy}.
   */
  @Inject
  public HomePresenter(EventBus eventBus, HomeView view, MyProxy proxy) {

    super(eventBus, view, proxy);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return TYPE_MAIN;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "Multi-Media-Manager";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return PlaceConstants.HOME;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void revealInParent() {

    RevealRootContentEvent.fire(this, this);
  }

  /**
   * The {@link ProxyPlace}.
   */
  @ProxyStandard
  @NameToken(PlaceConstants.HOME)
  public interface MyProxy extends ProxyPlace<HomePresenter> {
    // nothing to add
  }

}
