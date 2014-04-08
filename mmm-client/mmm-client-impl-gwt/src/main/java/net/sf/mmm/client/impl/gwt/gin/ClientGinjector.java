/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.impl.gwt.gin;

import net.sf.mmm.client.api.ClientComponents;
import net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * This is the {@link Ginjector} for mmm-client. It gives access to central components of the client
 * infrastructure.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@GinModules(ClientModule.class)
public interface ClientGinjector extends ClientComponents, Ginjector {

  /**
   * @return the instance of {@link RemoteInvocationServiceCaller}.
   */
  @Override
  RemoteInvocationServiceCaller getServiceCaller();

}
