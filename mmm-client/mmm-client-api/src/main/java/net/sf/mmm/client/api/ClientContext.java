/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.api;

import net.sf.mmm.client.api.session.ClientSession;
import net.sf.mmm.util.component.api.ComponentContainer;

/**
 * This class gives {@link #getInstance() static access} to central client infrastructure.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ClientContext {

  /** @see #getInstance() */
  private static ClientContext instance;

  /** @see #getComponents() */
  private ClientComponents components;

  /** @see #getContainer() */
  private ComponentContainer container;

  /** @see #getSession() */
  private ClientSession session;

  /**
   * The constructor.
   */
  public ClientContext() {

    super();
  }

  /**
   * @see #getContainer()
   * 
   * @return the {@link ClientComponents}.
   */
  public ClientComponents getComponents() {

    return this.components;
  }

  /**
   * @param clientComponents is the clientComponents to set
   */
  protected void setComponents(ClientComponents clientComponents) {

    this.components = clientComponents;
  }

  /**
   * @return the session
   */
  public ClientSession getSession() {

    return this.session;
  }

  /**
   * @param session is the session to set
   */
  protected void setSession(ClientSession session) {

    this.session = session;
  }

  /**
   * @return the instance of {@link ComponentContainer}.
   */
  public ComponentContainer getContainer() {

    return this.container;
  }

  /**
   * @param container is the container to set
   */
  protected void setContainer(ComponentContainer container) {

    this.container = container;
  }

  /**
   * @return the instance of {@link ClientContext}.
   */
  public static ClientContext getInstance() {

    return instance;
  }

  /**
   * @param instance is the instance to set
   */
  protected static void setInstance(ClientContext instance) {

    ClientContext.instance = instance;
  }

}
