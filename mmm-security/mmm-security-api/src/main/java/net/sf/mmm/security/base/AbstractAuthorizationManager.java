/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.security.base;

import java.security.Principal;

import net.sf.mmm.security.api.AuthorizationManager;
import net.sf.mmm.security.api.PermissionDeniedException;
import net.sf.mmm.security.api.SecurityException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of the {@link AuthorizationManager}
 * interface.
 * 
 * @param <USER> is the generic type of the users to authorize.
 * @param <OPERATION> is the generic type of the operations to authorize.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractAuthorizationManager<USER extends Principal, OPERATION> extends
    AbstractLoggableComponent implements AuthorizationManager<USER, OPERATION> {

  /**
   * The constructor.
   */
  public AbstractAuthorizationManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void verifyPermission(USER user, OPERATION operation) throws SecurityException {

    if (!isPermitted(user, operation)) {
      throw new PermissionDeniedException(user, operation);
    }
  }
}
