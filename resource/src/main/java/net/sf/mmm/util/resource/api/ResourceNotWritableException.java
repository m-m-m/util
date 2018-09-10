/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;

/**
 * This exception is thrown if a resource should be {@link DataResource#openOutputStream() written} that is
 * read-only or NOT writable for any other reason.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class ResourceNotWritableException extends RuntimeException implements AttributeReadMessageCode {

  private static final long serialVersionUID = 1L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ResourceNotWritable";

  /**
   * The constructor.
   *
   * @param resourceUri is the URI of the resource that could NOT be written.
   */
  public ResourceNotWritableException(String resourceUri) {

    this(null, resourceUri);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param resourceUri is the URI of the resource that could NOT be written.
   */
  public ResourceNotWritableException(Throwable nested, String resourceUri) {

    super("The resource '" + resourceUri + "' is not writable!", nested);
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
