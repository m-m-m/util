/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.server;

import javax.inject.Named;

import net.sf.mmm.app.shared.GreetingService;
import net.sf.mmm.service.base.server.AbstractRemoteInvocationService;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since X 12.06.2012
 */
@Named
public class GreetingServiceImpl extends AbstractRemoteInvocationService implements GreetingService {

  /**
   * The constructor.
   */
  public GreetingServiceImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String greeting(String name) {

    return "Hello " + name;
  }

}
