/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Objects;

import net.sf.mmm.util.http.api.header.AbstractHttpHeaders;
import net.sf.mmm.util.http.api.header.HttpHeaderContentType;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * The abstract base implementation of {@link HttpMessage}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.5.0
 */
public abstract class AbstractHttpMessage extends AbstractHttpObject implements HttpMessage {

  /** The default {@link #getCharset() charset}. */
  private static final String DEFAULT_CHARSET = "US-ASCII";

  private AbstractHttpHeaders headers;

  private final HttpVersion version;

  /**
   * The constructor.
   *
   * @param version the {@link HttpVersion}.
   * @param headers the {@link #getHeaders() HTTP headers}.
   */
  public AbstractHttpMessage(HttpVersion version, AbstractHttpHeaders headers) {

    super();
    if (headers == null) {
      this.headers = AbstractHttpHeaders.newInstance();
    } else {
      this.headers = headers;
    }
    Objects.requireNonNull(version, "version");
    this.version = version;
  }

  @Override
  public HttpVersion getVersion() {

    return this.version;
  }

  @Override
  public AbstractHttpHeaders getHeaders() {

    return this.headers;
  }

  @Override
  public AbstractHttpMessage setImmutable() {

    if (!isImmutable()) {
      super.setImmutable();
      this.headers.setImmutable();
    }
    return this;
  }

  String getCharset() {

    String charset = null;
    HttpHeaderContentType contentType = HttpHeaderContentType.get(this.headers);
    if (contentType != null) {
      charset = contentType.getCharset();
    }
    if (charset == null) {
      charset = DEFAULT_CHARSET;
    }
    return charset;
  }

  /**
   * This method writes the first line (request-line or status-line) of this {@link HttpMessage}.
   *
   * @param appendable is the {@link Appendable} (e.g. {@link StringBuilder} or {@link java.io.Writer}) where to
   *        {@link Appendable#append(char) append} this {@link HttpMessage} to.
   * @throws IOException if an output error occurred whilst writing the data.
   */
  protected abstract void writeFirstLine(Appendable appendable) throws IOException;

  @Override
  protected void doWrite(Appendable appendable, boolean fromToString) throws IOException {

    writeFirstLine(appendable);
    this.headers.write(appendable);
    // TODO write body
  }

  @Override
  public void write(OutputStream out) {

    String charset = getCharset();
    try (Writer writer = new OutputStreamWriter(out, charset)) {
      // TODO binary entities as body...
      write(writer);
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException(charset, e);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

}
