/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.impl;

import net.sf.mmm.nls.base.NlsRuntimeException;
import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * This is the exception thrown if a resource is required but is NOT available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ResourceMissingException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -3065138916833672449L;

  /**
   * The constructor.
   * 
   * @param resourceName is the name of the missing resource.
   */
  public ResourceMissingException(String resourceName) {

    super(NlsBundleUtilCore.ERR_RESOURCE_MISSING, resourceName);
  }

}
