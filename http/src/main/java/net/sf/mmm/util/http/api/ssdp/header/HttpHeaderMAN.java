/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.ssdp.header;

import net.sf.mmm.util.http.api.header.AbstractHttpHeader;
import net.sf.mmm.util.http.api.header.HttpHeader;
import net.sf.mmm.util.http.api.header.HttpRequestHeader;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 8.5.0
 */
public class HttpHeaderMAN extends AbstractHttpHeader implements HttpRequestHeader {

  /** The {@link #getName() name} of this {@link HttpHeader}. */
  public static final String HEADER = "MAN";

  /** The factory instance of this {@link HttpHeader}. */
  static final HttpHeaderMAN FACTORY = new HttpHeaderMAN();

  /**
   * The constructor.
   */
  public HttpHeaderMAN() {
    super();
  }

  /**
   * The constructor.
   *
   * @param value
   */
  public HttpHeaderMAN(String value) {
    super(value);
    // TODO Auto-generated constructor stub
  }

  @Override
  public String getName() {

    return HEADER;
  }

  @Override
  public boolean isRequestHeader() {

    return true;
  }

  @Override
  public boolean isResponseHeader() {

    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected AbstractHttpHeader withValue(String newValue) {

    // TODO Auto-generated method stub
    return null;
  }

}
