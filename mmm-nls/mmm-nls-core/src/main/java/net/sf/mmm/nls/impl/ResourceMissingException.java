/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.impl;

import net.sf.mmm.nls.base.NlsRuntimeException;


/**
 * This is the exception thrown if a resource is required but is NOT available.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ResourceMissingException extends NlsRuntimeException {
  
  /** to be used with resource bundle */
  public static final String ERR_RESOURCE_MISSING = "The required resource \"{0}\" is missing!";

  /** UID for serialization */
  private static final long serialVersionUID = -3065138916833672449L;
  
  /**
   * The constructor
   *
   * @param resourceName
   */
  public ResourceMissingException(String resourceName) {

    super(ERR_RESOURCE_MISSING, resourceName);
  }

}
