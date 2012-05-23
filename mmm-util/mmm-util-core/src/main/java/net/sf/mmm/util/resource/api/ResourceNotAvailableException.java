/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This exception is thrown if a resource was requested that is NOT available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class ResourceNotAvailableException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -356811274649703298L;

  /**
   * The constructor.
   * 
   * @param resourceUri is the URI of the resource that could NOT be found.
   */
  public ResourceNotAvailableException(String resourceUri) {

    this(null, resourceUri);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param resourceUri is the URI of the resource that could NOT be found.
   */
  public ResourceNotAvailableException(Throwable nested, String resourceUri) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorResourceNotAvailable(resourceUri));
  }

}
