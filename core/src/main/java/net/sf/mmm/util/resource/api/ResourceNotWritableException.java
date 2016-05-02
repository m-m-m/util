/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.resource.NlsBundleUtilResourceRoot;

/**
 * This exception is thrown if a resource should be {@link DataResource#openOutputStream() written} that is
 * read-only or NOT writable for any other reason.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class ResourceNotWritableException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -7527198711344080897L;

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

    super(nested, createBundle(NlsBundleUtilResourceRoot.class).errorResourceNotWritable(resourceUri));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
