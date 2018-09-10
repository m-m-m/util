/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;

/**
 * This exception is thrown if a resource was requested that is NOT available.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class ResourceNotAvailableException extends RuntimeException implements AttributeReadMessageCode {

  private static final long serialVersionUID = 1L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ResourceNotAvailable";

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

    super("The resource '" + resourceUri + "' is not available!", nested);
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
