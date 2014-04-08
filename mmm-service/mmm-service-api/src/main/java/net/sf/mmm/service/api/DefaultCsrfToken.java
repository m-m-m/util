/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;

/**
 * This is the default implementation of {@link CsrfToken} it simply uses a {@link #getValue() string value}
 * as payload.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DefaultCsrfToken extends AbstractSimpleDatatype<String> implements CsrfToken {

  /** UID for serialization. */
  private static final long serialVersionUID = -8870853110501506000L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected DefaultCsrfToken() {

    super();
  }

  /**
   * The constructor.
   *
   * @param value is the actual {@link #getValue() value} of this token.
   */
  public DefaultCsrfToken(String value) {

    super(value);
  }

  /**
   * {@inheritDoc}
   *
   * @return the payload of this token.
   */
  @Override
  public String getValue() {

    return super.getValue();
  }

}
