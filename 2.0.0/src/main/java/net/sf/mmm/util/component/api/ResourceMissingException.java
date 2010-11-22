/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This is the exception thrown if a {@link javax.annotation.Resource resource}
 * is required but is NOT available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
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

    super(NlsBundleUtilCore.ERR_RESOURCE_MISSING, toMap(KEY_RESOURCE, resourceName));
  }

  /**
   * The constructor.
   * 
   * @param resourceName is the name of the missing resource.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public ResourceMissingException(String resourceName, Throwable nested) {

    super(nested, NlsBundleUtilCore.ERR_RESOURCE_MISSING, toMap(KEY_RESOURCE, resourceName));
  }

}
