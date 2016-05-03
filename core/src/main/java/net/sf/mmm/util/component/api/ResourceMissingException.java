/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

import net.sf.mmm.util.component.NlsBundleUtilComponentRoot;
import net.sf.mmm.util.exception.api.NlsRuntimeException;

/**
 * This is the exception thrown if a {@link javax.annotation.Resource resource} is required but is NOT available.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ResourceMissingException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -3065138916833672449L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ResMissing";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ResourceMissingException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param resourceName is the name of the missing resource.
   */
  public ResourceMissingException(String resourceName) {

    this(resourceName, null);
  }

  /**
   * The constructor.
   *
   * @param resourceName is the name of the missing resource.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public ResourceMissingException(String resourceName, Throwable nested) {

    super(nested, createBundle(NlsBundleUtilComponentRoot.class).errorResourceMissing(resourceName));
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
