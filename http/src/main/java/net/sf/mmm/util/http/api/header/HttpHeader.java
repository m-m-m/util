/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.util.List;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.api.StringWritable;

/**
 * This is the interface for a header of <a href="https://de.wikipedia.org/wiki/Hypertext_Transfer_Protocol">HTTP</a>.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface HttpHeader extends StringWritable {

  /**
   * The newline sequence recommended by <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec2.html#sec2.2">RFC 2616
   * (section 2.2)</a>.
   */
  String CRLF = StringUtil.LINE_SEPARATOR_CRLF;

  /** The {@link #getName() name} of the <em>Cache-Control</em> header. */
  String HEADER_CACHE_CONTROL = "Cache-Control";

  /** The {@link #getName() name} of the <em>Connection</em> header. */
  String HEADER_CONNECTION = "Connection";

  /** The {@link #getName() name} of the {@link HttpHeaderContentDisposition}. */
  String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

  /** The {@link #getName() name} of the <em>Content-Length</em> header. */
  String HEADER_CONTENT_LENGTH = "Content-Length";

  /** The {@link #getName() name} of the <em>Content-MD5</em> header. */
  String HEADER_CONTENT_MD5 = "Content-MD5";

  /** The {@link #getName() name} of the <em>Content-Type</em> header. */
  String HEADER_CONTENT_TYPE = "Content-Type";

  /** The {@link #getName() name} of the <em>Date</em> header. */
  String HEADER_DATE = "Date";

  /** The {@link #getName() name} of the <em>Pragma</em> header. */
  String HEADER_PRAGMA = "Pragma";

  /** The {@link #getName() name} of the <em>Transfer-Encoding</em> header. */
  String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";

  /** The {@link #getName() name} of the <em>Via</em> header. */
  String HEADER_VIA = "Via";

  /** The {@link #getName() name} of the <em>Warning</em> header. */
  String HEADER_WARNING = "Warning";

  /** The {@link #getName() name} of the <em>X-Correlation-ID</em> header. */
  String HEADER_X_CORRELATION_ID = "X-Correlation-ID";

  /** The {@link #getName() name} of the <em>X-Request-ID</em> header. */
  String HEADER_X_REQUEST_ID = "X-Request-ID";

  /** The {@link #getName() name} of the <em>X-Robots-Tag</em> header. */
  String HEADER_X_ROBOTS_TAG = "X-Robots-Tag";

  /**
   * @return the name of the {@link HttpHeader} (e.g. "Content-Type").
   */
  String getName();

  /**
   * @see #getNext()
   * @return the (primary) value of this {@link HttpHeader}.
   */
  String getValue();

  /**
   * <b>ATTENTION:</b> This operation is more expensive than calling {@link #getValue()} and {@link #getNext()}. Avoid
   * calling this method multiple times.
   *
   * @see #getValue()
   * @see #getNext()
   * @return a {@link List} with all values of this {@link HttpHeader}.
   */
  List<String> getValues();

  /**
   * @see #isSupportingMultiValue()
   * @return the optional next {@link HttpHeader} of the same {@link #getName() type} or <code>null</code> in case of a
   *         {@link #getValue() single valued} {@link HttpHeader}.
   */
  HttpHeader getNext();

  /**
   * @return {@code true} if this {@link HttpHeader} accepts comma separated values ({@code #1} in spec.), {@code false}
   *         otherwise.
   */
  boolean isSupportingMultiValue();

  /**
   * @return {@code true} if this is a regular {@link HttpRequestHeader}, {@code false} otherwise.
   */
  boolean isRequestHeader();

  /**
   * @return {@code true} if this is a regular {@link HttpResponseHeader}, {@code false} otherwise.
   */
  boolean isResponseHeader();

}
