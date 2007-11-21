/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component;

import net.sf.mmm.util.nls.NlsRuntimeException;
import net.sf.mmm.util.nls.base.NlsBundleUtilCore;

/**
 * This is the exception thrown if an operation was invoked after the object was
 * initialized but has to be called before initialization.<br>
 * Typical this exception is thrown by a component if a setter
 * {@link javax.annotation.Resource injection} is performed after the
 * {@link javax.annotation.PostConstruct initialization}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AlreadyInitializedException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -2011255277429741541L;

  /**
   * The constructor.
   */
  public AlreadyInitializedException() {

    super(NlsBundleUtilCore.ERR_ALREADY_INITIALIZED);
  }

}
