/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * This exception is thrown if a resource was requested that is NOT available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ResourceNotAvailableException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -356811274649703298L;

  /**
   * The constructor.
   * 
   * @param absolutePath is the absolute path of the resource that could NOT be
   *        found.
   */
  public ResourceNotAvailableException(String absolutePath) {

    super(NlsBundleResource.ERR_RESOURCE_NOT_AVAILABLE, absolutePath);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param absolutePath is the absolute path of the resource that could NOT be
   *        found.
   */
  public ResourceNotAvailableException(Throwable nested, String absolutePath) {

    super(nested, NlsBundleResource.ERR_RESOURCE_NOT_AVAILABLE, absolutePath);
  }

}
