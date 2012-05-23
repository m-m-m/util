/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This is the exception thrown if a unique {@link javax.annotation.Resource resource} is required but
 * multiple instances have been found.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class ResourceAmbiguousException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -6589910110649307658L;

  /**
   * The constructor.
   * 
   * @param resourceName is the name of the requested resource that is ambiguous.
   */
  public ResourceAmbiguousException(String resourceName) {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorResourceAmbiguous(resourceName));
  }

  /**
   * The constructor.
   * 
   * @param resourceName is the name of the requested resource that is ambiguous.
   * @param resourceIds are the IDs of the resources available. Should be at least two for ambiguity.
   */
  public ResourceAmbiguousException(String resourceName, String... resourceIds) {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorResourceAmbiguousWithIds(resourceName, resourceIds));
  }

}
