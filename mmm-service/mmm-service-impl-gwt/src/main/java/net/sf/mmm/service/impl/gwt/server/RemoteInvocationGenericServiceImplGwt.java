/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.gwt.server;

import javax.inject.Named;

import net.sf.mmm.service.base.RemoteInvocationGenericService;
import net.sf.mmm.service.impl.server.RemoteInvocationGenericServiceImpl;

/**
 * This class extends {@link RemoteInvocationGenericServiceImpl} with ready-to-use CDI annotations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(RemoteInvocationGenericService.CDI_NAME)
public class RemoteInvocationGenericServiceImplGwt extends RemoteInvocationGenericServiceImpl {

  /**
   * The constructor.
   */
  public RemoteInvocationGenericServiceImplGwt() {

    super();
  }

}
