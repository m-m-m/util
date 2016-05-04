/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.resource.NlsBundleUtilResourceRoot;

/**
 * This exception is thrown if a resource was requested for an URI that is undefined or illegal (e.g.
 * "hppt:\\.com/...").
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class ResourceUriUndefinedException extends NlsRuntimeException {

  private static final long serialVersionUID = -1483011818509137119L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ResourceUriUndef";

  /**
   * The constructor.
   *
   * @param uri is the resource URI that is undefined or illegal.
   */
  public ResourceUriUndefinedException(String uri) {

    this(null, uri);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param uri is the resource URI that is undefined or illegal.
   */
  public ResourceUriUndefinedException(Throwable nested, String uri) {

    super(nested, createBundle(NlsBundleUtilResourceRoot.class).errorResourceUndefinedUri(uri));
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
