/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.shared;

import javax.annotation.security.PermitAll;

import net.sf.mmm.service.api.rpc.RemoteInvocationService;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since X 12.06.2012
 */
public interface GreetingService extends RemoteInvocationService {

  @PermitAll
  String greeting(String name);

}
