/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import java.io.OutputStream;

import net.sf.mmm.util.http.api.header.HttpHeaders;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.api.StringWritable;

/**
 * This is the abstract interface for an {@link HttpMessage}. It is either a {@link HttpRequest} sent from a client to a
 * server or a {@link HttpResponse} sent back from the server to the client.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface HttpMessage extends StringWritable {

  /**
   * The newline sequence recommended by <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec2.html#sec2.2">RFC 2616
   * (section 2.2)</a>.
   */
  String CRLF = StringUtil.LINE_SEPARATOR_CRLF;

  /**
   * @return the {@link HttpVersion}.
   */
  HttpVersion getVersion();

  /**
   * @return the {@link HttpHeaders} with the meta-data of this {@link HttpMessage}.
   */
  HttpHeaders getHeaders();

  /**
   * {@inheritDoc}
   *
   * <b>Attention:</b><br>
   * This operation is fine debugging. However, a full {@link HttpMessage} may contain binary data that can not be
   * written to an {@link Appendable}. Use {@link #write(java.io.OutputStream)} instead for writing an entire
   * {@link HttpMessage}.
   */
  @Override
  void write(Appendable appendable) throws RuntimeIoException;

  /**
   * @param out the {@link OutputStream} where to write the serialized representation of this object to.
   */
  void write(OutputStream out);

}
