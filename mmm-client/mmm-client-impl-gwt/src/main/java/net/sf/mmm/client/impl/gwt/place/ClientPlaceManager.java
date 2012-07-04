/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.impl.gwt.place;

import net.sf.mmm.client.impl.base.place.PlaceConstants;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

/**
 * This is the implementation of {@link com.gwtplatform.mvp.client.proxy.PlaceManager} used to bind the
 * DefaultPlace.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ClientPlaceManager extends PlaceManagerImpl {

  /** The default place. */
  private final PlaceRequest defaultPlaceRequest;

  /**
   * The constructor.
   * 
   * @param eventBus is the {@link EventBus}.
   * @param tokenFormatter is the {@link TokenFormatter}.
   */
  @Inject
  public ClientPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter) {

    super(eventBus, tokenFormatter);
    this.defaultPlaceRequest = new PlaceRequest(PlaceConstants.HOME);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void revealDefaultPlace() {

    revealPlace(this.defaultPlaceRequest, false);
  }
}
