/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.api;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * An {@link IocException} is thrown if anything goes wrong with
 * {@link ComponentProvider component} {@link IocContainer management}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class IocException extends NlsRuntimeException {

  /**
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   */
  public IocException(String newInternaitionalizedMessage, Object... newArguments) {

    super(newInternaitionalizedMessage, newArguments);
  }

  /**
   * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
   */
  public IocException(Throwable newNested, String newInternaitionalizedMessage,
      Object... newArguments) {

    super(newNested, newInternaitionalizedMessage, newArguments);
  }

}
