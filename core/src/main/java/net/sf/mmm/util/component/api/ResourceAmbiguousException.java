/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

import net.sf.mmm.util.component.NlsBundleUtilComponentRoot;
import net.sf.mmm.util.exception.api.NlsRuntimeException;

/**
 * This is the exception thrown if a unique {@link javax.annotation.Resource resource} is required but multiple
 * instances have been found.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class ResourceAmbiguousException extends NlsRuntimeException {

  private static final long serialVersionUID = -6589910110649307658L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ResAmbiguos";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ResourceAmbiguousException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param resourceName is the name of the requested resource that is ambiguous.
   */
  public ResourceAmbiguousException(String resourceName) {

    super(createBundle(NlsBundleUtilComponentRoot.class).errorResourceAmbiguous(resourceName));
  }

  /**
   * The constructor.
   *
   * @param resourceName is the name of the requested resource that is ambiguous.
   * @param resourceIds are the IDs of the resources available. Should be at least two for ambiguity.
   */
  public ResourceAmbiguousException(String resourceName, String... resourceIds) {

    super(createBundle(NlsBundleUtilComponentRoot.class).errorResourceAmbiguousWithIds(resourceName, resourceIds));
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
