/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

/**
 * This is a generic implementation of {@link HttpHeader}.
 *
 * @author hohwille
 * @since 8.5.0
 */
abstract class GenericHttpHeader extends AbstractHttpHeader {

  private final String name;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name} of this {@link HttpHeader}.
   * @param value the {@link #getValue() value} of this {@link HttpHeader}.
   */
  GenericHttpHeader(String name, String value) {
    super(value);
    this.name = name;
  }

  @Override
  public String getName() {

    return this.name;
  }

}
