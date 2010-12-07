/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This exception is thrown if a resource should be
 * {@link DataResource#openOutputStream() written} that is read-only or NOT
 * writable for any other reason.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class ResourceNotWritableException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -7527198711344080897L;

  /**
   * The constructor.
   * 
   * @param resourceUri is the URI of the resource that could NOT be written.
   */
  public ResourceNotWritableException(String resourceUri) {

    super(NlsBundleUtilCore.ERR_RESOURCE_NOT_WRITABLE, toMap(KEY_RESOURCE, resourceUri));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param resourceUri is the URI of the resource that could NOT be written.
   */
  public ResourceNotWritableException(Throwable nested, String resourceUri) {

    super(nested, NlsBundleUtilCore.ERR_RESOURCE_NOT_WRITABLE, toMap(KEY_RESOURCE, resourceUri));
  }

}
