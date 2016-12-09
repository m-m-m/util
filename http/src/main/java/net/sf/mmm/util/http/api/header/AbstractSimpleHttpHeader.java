/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

/**
 * Abstract
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class AbstractSimpleHttpHeader extends AbstractHttpHeader {

  private final String value;

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value} of this {@link HttpHeader}.
   */
  public AbstractSimpleHttpHeader(String value) {
    super();
    this.value = value;
    assert (!isEmpty(value.trim()));
  }

  @Override
  public String getValue() {

    return this.value;
  }

}
