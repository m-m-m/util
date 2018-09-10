/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;

/**
 * This exception is thrown if a resource was requested for an URI that is undefined or illegal (e.g.
 * "hppt:\\.com/...").
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class ResourceUriUndefinedException extends RuntimeException implements AttributeReadMessageCode {

  private static final long serialVersionUID = 1L;

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

    super("The resource URI '" + uri + "' is undefined!", nested);
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
