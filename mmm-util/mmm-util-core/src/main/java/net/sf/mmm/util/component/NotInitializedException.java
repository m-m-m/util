/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component;

import net.sf.mmm.util.nls.NlsRuntimeException;
import net.sf.mmm.util.nls.base.NlsBundleUtilCore;

/**
 * This is the exception thrown if an operation was invoked before the object
 * was initialized but has to be called after initialization.<br>
 * Typical this exception is thrown by a component if it was used (a method
 * declared in the components interface was called) before the
 * {@link javax.annotation.PostConstruct initialization}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NotInitializedException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -2684272742410884991L;

  /**
   * The constructor.
   */
  public NotInitializedException() {

    super(NlsBundleUtilCore.ERR_NOT_INITIALIZED);
  }

}
