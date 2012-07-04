/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt;

import net.sf.mmm.client.api.ClientComponents;
import net.sf.mmm.client.api.ClientContext;
import net.sf.mmm.client.api.session.ClientSession;

/**
 * This is an extension of {@link ClientContext} for GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GwtClientContext extends ClientContext {

  /**
   * The constructor.
   */
  public GwtClientContext() {

    super();
    setInstance(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setComponents(ClientComponents clientComponents) {

    // make package visible
    super.setComponents(clientComponents);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setSession(ClientSession session) {

    // make package visible
    super.setSession(session);
  }

}
