/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.server;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation for a {@link RemoteInvocationService}. It is recommended that your
 * server-side implementations of {@link RemoteInvocationService}s are derived from this class (however, this
 * is technically not required).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationService extends AbstractLoggableComponent implements
    RemoteInvocationService {

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationService() {

    super();
  }

}
