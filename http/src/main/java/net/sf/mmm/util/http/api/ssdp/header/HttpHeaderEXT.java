/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.ssdp.header;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.http.api.header.AbstractHttpHeader;
import net.sf.mmm.util.http.api.header.HttpHeader;
import net.sf.mmm.util.http.api.header.HttpRequestHeader;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 8.4.0
 */
public class HttpHeaderEXT extends AbstractHttpHeader implements HttpRequestHeader {

  private static final Logger LOG = LoggerFactory.getLogger(HttpHeaderEXT.class);

  /** The {@link #getName() name} of this {@link HttpHeader}. */
  public static final String HEADER = "EXT";

  /** The factory instance of this {@link HttpHeader}. */
  public static final HttpHeaderEXT FACTORY = new HttpHeaderEXT();

  /**
   * The constructor.
   */
  public HttpHeaderEXT() {
    super();
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

    return false;
  }

  @Override
  protected AbstractHttpHeader withValue(String newValue) {

    if (!isEmpty(newValue)) {
      LOG.error("Invalid EXT header - omitting value {}", newValue);
    }
    return FACTORY;
  }

}
