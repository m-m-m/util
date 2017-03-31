/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.lang.api.GenericBean;

/**
 * This is the {@link #HEADER_CONTENT_DISPOSITION Content-Disposition} {@link HttpHeader} according to
 * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17">RFC 2616, section 14.17</a>. For further
 * details see also <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.7">RFC 2616, section 3.7</a> as
 * well as <a href="https://www.w3.org/Protocols/rfc1341/7_2_Multipart.html">RFC 1341, section 7.2</a>.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class HttpHeaderContentType extends AbstractParameterizedHttpHeader implements HttpRequestHeader, HttpResponseHeader {

  /** An empty instance of {@link HttpHeaderUserAgent} that acts as factory. */
  public static final HttpHeaderContentType FACTORY = new HttpHeaderContentType("", "");

  /** The {@link #getName() name} of this {@link HttpHeader}. */
  public static final String HEADER = HttpHeader.HEADER_CONTENT_TYPE;

  /** The name of the {@link #getParameter(String) parameter} containing the {@link #getCharset() charset}. */
  public static final String PARAMETER_CHARSET = "charset";

  /** The name of the {@link #getParameter(String) parameter} containing the {@link #getBoundary() boundary}. */
  public static final String PARAMETER_BOUNDARY = "boundary";

  /**
   * The {@link #getType() type}
   * <a href="http://www.iana.org/assignments/media-types/media-types.xhtml#application">application</a>.
   */
  public static final String TYPE_APPLICATION = "application";

  /**
   * The {@link #getType() type}
   * <a href="http://www.iana.org/assignments/media-types/media-types.xhtml#audio">audio</a>.
   */
  public static final String TYPE_AUDIO = "audio";

  /**
   * The {@link #getType() type}
   * <a href="http://www.iana.org/assignments/media-types/media-types.xhtml#image">image</a>.
   */
  public static final String TYPE_IMAGE = "image";

  /**
   * The {@link #getType() type}
   * <a href="http://www.iana.org/assignments/media-types/media-types.xhtml#message">message</a>.
   */
  public static final String TYPE_MESSAGE = "message";

  /**
   * The {@link #getType() type}
   * <a href="http://www.iana.org/assignments/media-types/media-types.xhtml#model">model</a>.
   */
  public static final String TYPE_MODEL = "model";

  /**
   * The {@link #getType() type}
   * <a hef="http://www.iana.org/assignments/media-types/media-types.xhtml#multipart">multipart</a>.
   */
  public static final String TYPE_MULTIPART = "multipart";

  /**
   * The {@link #getType() type} <a href="http://www.iana.org/assignments/media-types/media-types.xhtml#text">text</a>.
   */
  public static final String TYPE_TEXT = "text";

  /**
   * The {@link #getType() type}
   * <a href="http://www.iana.org/assignments/media-types/media-types.xhtml#video">video</a>.
   */
  public static final String TYPE_VIDEO = "video";

  /**
   * The {@link #getMimetype() mimetype}
   * <a href="http://www.iana.org/assignments/media-types/application/json">application/json</a>.
   */
  public static final String MIMETYPE_APPLICATION_JSON = "application/json";

  /**
   * The {@link #getMimetype() mimetype}
   * <a href="http://www.iana.org/assignments/media-types/application/octet-stream">application/octet-stream</a>.
   */
  public static final String MIMETYPE_APPLICATION_OCTETSTREAM = "application/octet-stream";

  /**
   * The {@link #getMimetype() mimetype}
   * <a href="http://www.iana.org/assignments/media-types/application/pdf">application/pdf</a>.
   */
  public static final String MIMETYPE_APPLICATION_PDF = "application/pdf";

  /**
   * The {@link #getMimetype() mimetype}
   * <a href="www.iana.org/assignments/media-types/multipart/form-data">multipart/form-data</a>.
   */
  public static final String MIMETYPE_MULTIPART_FORMDATA = "multipart/form-data";

  /**
   * The {@link #getMimetype() mimetype}
   * <a href="https://www.w3.org/Protocols/rfc1341/7_2_Multipart.html">multipart/mixed</a>.
   */
  public static final String MIMETYPE_MULTIPART_MIXED = "multipart/mixed";

  /**
   * The {@link #getMimetype() mimetype} <a href="http://www.iana.org/assignments/media-types/text/html">text/html</a>.
   */
  public static final String MIMETYPE_TEXT_HTML = "text/html";

  /**
   * The {@link #getMimetype() mimetype} <a href="http://www.iana.org/assignments/media-types/text/css">text/css</a>.
   */
  public static final String MIMETYPE_TEXT_CSS = "text/css";

  /**
   * The {@link #getMimetype() mimetype}
   * <a href="http://www.iana.org/assignments/media-types/text/javascript">text/javascript</a>.
   */
  public static final String MIMETYPE_TEXT_JAVASCRIPT = "text/javascript";

  /**
   * The {@link #getMimetype() mimetype} <a href="http://www.iana.org/assignments/media-types/text/xml">text/xml</a>.
   */
  public static final String MIMETYPE_TEXT_XML = "text/xml";

  /**
   * The {@link #getMimetype() mimetype} <a href="https://tools.ietf.org/html/rfc2046#section-3">text/plain</a>.
   */
  public static final String MIMETYPE_TEXT_PLAIN = "text/plain";

  private final String type;

  private final String subtype;

  /**
   * The constructor.
   *
   * @param mimetype - see {@link #getMimetype()}.
   */
  public HttpHeaderContentType(String mimetype) {
    this(mimetype, new HashMap<>());
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param subtype - see {@link #getSubtype()}.
   */
  public HttpHeaderContentType(String type, String subtype) {
    this(type, subtype, new HashMap<>());
  }

  /**
   * The constructor.
   *
   * @param mimetype - see {@link #getMimetype()}.
   * @param parameters - see {@link #getParameter(String)}.
   */
  private HttpHeaderContentType(String mimetype, Map<String, Object> parameters) {
    super(parameters);
    int slashIndex = mimetype.indexOf('/');
    if (slashIndex > 0) {
      this.type = mimetype.substring(0, slashIndex);
      this.subtype = mimetype.substring(slashIndex + 1);
    } else {
      this.type = mimetype;
      this.subtype = null;
    }
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param subtype - see {@link #getSubtype()}.
   * @param parameters - see {@link #getParameter(String)}.
   */
  private HttpHeaderContentType(String type, String subtype, Map<String, Object> parameters) {
    super(parameters);
    this.type = type;
    this.subtype = subtype;
  }

  @Override
  public String getName() {

    return HEADER;
  }

  /**
   * @return the type is the primary token of the {@link #getMimetype() mimetype} (e.g. "text" from "text/html").
   */
  public String getType() {

    return this.type;
  }

  /**
   * @return the subtype is the secondary token of the {@link #getMimetype() mimetype} (e.g. "html" from "text/html").
   */
  public String getSubtype() {

    return this.subtype;
  }

  /**
   * @return the mimetype as {@link #getType() type}/{@link #getSubtype() subtype} (e.g. "text/html", "image/jpeg",
   *         "application/octet-stream", "multipart/form-data", or "multipart/mixed").
   */
  public String getMimetype() {

    if (isEmpty(this.subtype)) {
      return this.type;
    }
    return this.type + "/" + this.subtype;
  }

  @Override
  protected void calculateValueStart(StringBuilder buffer) {

    if (this.type != null) {
      buffer.append(this.type);
      if (hasParameters()) {
        buffer.append(getParameterSeparator());
        buffer.append(' ');
      }
    }
    super.calculateValueStart(buffer);
  }

  @Override
  public boolean isRequestHeader() {

    return true;
  }

  @Override
  public boolean isResponseHeader() {

    return true;
  }

  /**
   * @return the {@link #PARAMETER_CHARSET charset} {@link #getParameter(String) parameter value}.
   */
  public String getCharset() {

    return getParameterAsString(PARAMETER_CHARSET);
  }

  /**
   * @return the {@link #PARAMETER_BOUNDARY boundary} {@link #getParameter(String) parameter value}.
   */
  public String getBoundary() {

    return getParameterAsString(PARAMETER_BOUNDARY);
  }

  @Override
  protected AbstractHttpHeader withValue(String value) {

    return ofValue(value);
  }

  /**
   * @param headerValue the header {@link #getValues() value}.
   * @return the parsed {@link HttpHeaderContentType}.
   */
  public static HttpHeaderContentType ofValue(String headerValue) {

    String value = trim(headerValue);
    if (value == null) {
      return null;
    }
    final GenericBean<String> receiver = new GenericBean<>();
    Map<String, Object> parameters = FACTORY.parseParameters(value, x -> {
      receiver.setValue(x);
      return null;
    });
    String type = receiver.getValue();
    if ((type == null) && (parameters == null)) {
      return null;
    }
    return new HttpHeaderContentType(type, parameters);
  }

  /**
   * @param headers the {@link HttpHeader} to get this header from. May be {@code null}.
   * @return the {@link HttpHeaderContentType} form the given {@link HttpHeaders} or {@code null} if not present.
   */
  public static HttpHeaderContentType get(HttpHeaders headers) {

    if (headers == null) {
      return null;
    }
    HttpHeader header = headers.getHeader(HEADER);
    return (HttpHeaderContentType) header;
  }
}
