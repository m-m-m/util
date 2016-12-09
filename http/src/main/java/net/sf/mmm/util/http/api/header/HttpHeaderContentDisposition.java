/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import net.sf.mmm.util.lang.api.GenericBean;

/**
 * This class represents the HTTP {@link #HEADER_CONTENT_DISPOSITION Content-Disposition} header according to
 * <a href="https://www.ietf.org/rfc/rfc2183.txt">RFC 2183</a> (and <a href="https://www.ietf.org/rfc/rfc1806.txt">RFC
 * 1806</a>). It contains the meta-data of an uploaded or downloaded file. See also
 * <a href="http://www.iana.org/assignments/cont-disp/cont-disp.xhtml">Content Disposition Values and Parameters</a>.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class HttpHeaderContentDisposition extends AbstractParameterizedHttpHeader implements HttpRequestHeader, HttpResponseHeader {

  /** The name of the {@link #getParameter(String) parameter} containing the {@link #getFilename() filename}. */
  public static final String PARAMETER_FILENAME = "filename";

  /**
   * The name of the {@link #getParameter(String) parameter} containing the {@link #getSize() size}. Typically addressed
   * with {@link #HEADER_CONTENT_LENGTH} instead.
   */
  public static final String PARAMETER_SIZE = "size";

  /**
   * The name of the {@link #getParameter(String) parameter} containing the {@link #getCreationDate() creation-date}.
   */
  public static final String PARAMETER_CREATION_DATE = "creation-date";

  /**
   * The name of the {@link #getParameter(String) parameter} containing the {@link #getModificationDate()
   * modification-date}.
   */
  public static final String PARAMETER_MODIFICATION_DATE = "modification-date";

  /** The name of the {@link #getParameter(String) parameter} containing the {@link #getReadDate() read-date}. */
  public static final String PARAMETER_READ_DATE = "read-date";

  /** The name of the {@link #getParameter(String) parameter} containing the original field name in HTML form. */
  public static final String PARAMETER_NAME = "name";

  /** The name of the {@link #getParameter(String) parameter} containing the type or use of audio content. */
  public static final String PARAMETER_VOICE = "voice";

  /**
   * The name of the {@link #getParameter(String) parameter} containing the handling (whether or not processing is
   * required).
   */
  public static final String PARAMETER_HANDLING = "handling";

  /**
   * The name of the {@link #getParameter(String) parameter} containing the preview-type (Internet media type of the
   * preview output desired from a processor by the author of the MIME content).
   */
  public static final String PARAMETER_PREVIEW_TYPE = "preview-type";

  /** The {@link #getType() type} <em>inline</em>. */
  public static final String TYPE_INLINE = "inline";

  /** The {@link #getType() type} <em>attachment</em>. */
  public static final String TYPE_ATTACHMENT = "attachment";

  /** The {@link #getType() type} <em>form-data</em>. */
  public static final String TYPE_FORM_DATA = "form-data";

  /** The {@link #getType() type} <em>signal</em>. */
  public static final String TYPE_SIGNAL = "signal";

  /** The {@link #getType() type} <em>alert</em>. */
  public static final String TYPE_ALERT = "alert";

  /** The {@link #getType() type} <em>icon</em>. */
  public static final String TYPE_ICON = "icon";

  /** The {@link #getType() type} <em>render</em>. */
  public static final String TYPE_RENDER = "render";

  /** The {@link #getType() type} <em>recipient-list-history</em>. */
  public static final String TYPE_RECIPIENT_LIST_HISTORY = "recipient-list-history";

  /** The {@link #getType() type} <em>session</em>. */
  public static final String TYPE_SESSION = "session";

  /** The {@link #getType() type} <em>aib</em> (Authenticated Identity Body). */
  public static final String TYPE_AIB = "aib";

  /** The {@link #getType() type} <em>early-session</em>. */
  public static final String TYPE_EARLY_SESSION = "early-session";

  /** The {@link #getType() type} <em>recipient-list</em>. */
  public static final String TYPE_RECIPIENT_LIST = "recipient-list";

  /** The {@link #getType() type} <em>notification</em>. */
  public static final String TYPE_NOTIFICATION = "notification";

  /** The {@link #getType() type} <em>by-reference</em>. */
  public static final String TYPE_BY_REFERENCE = "by-reference";

  /** The {@link #getType() type} <em>info-package</em>. */
  public static final String TYPE_INFO_PACKAGE = "info-package";

  /** The {@link #getType() type} <em>recording-session</em>. */
  public static final String TYPE_RECORDING_SESSION = "recording-session";

  private static final HttpHeaderContentDisposition EMPTY = new HttpHeaderContentDisposition("", Collections.<String, Object> emptyMap());

  private final String type;

  /**
   * The constructor.
   */
  private HttpHeaderContentDisposition(String type, Map<String, Object> parameters) {
    super(parameters);
    this.type = type;
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param filename - see {@link #getFilename()}.
   */
  public HttpHeaderContentDisposition(String type, String filename) {

    this(type, filename, null, null, null, null);
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param filename - see {@link #getFilename()}.
   * @param size - see {@link #getSize()}.
   * @param creationDate - see {@link #getCreationDate()}.
   * @param modificationDate - see {@link #getModificationDate()}.
   * @param readDate - see {@link #getReadDate()}.
   */
  public HttpHeaderContentDisposition(String type, String filename, Long size, Instant creationDate, Instant modificationDate, Instant readDate) {
    super();
    this.type = type;
    setParameter(PARAMETER_FILENAME, filename);
    setParameter(PARAMETER_SIZE, size);
    setParameter(PARAMETER_CREATION_DATE, creationDate);
    setParameter(PARAMETER_MODIFICATION_DATE, creationDate);
    setParameter(PARAMETER_READ_DATE, creationDate);
  }

  @Override
  public String getName() {

    return HEADER_CONTENT_DISPOSITION;
  }

  @Override
  public boolean isRequestHeader() {

    return true;
  }

  @Override
  public boolean isResponseHeader() {

    return true;
  }

  @Override
  protected boolean isSeparateParametersByComma() {

    return false;
  }

  /**
   * @return the type
   */
  public String getType() {

    return this.type;
  }

  /**
   * @return {@code true} if {@link #getType()} is {@link #TYPE_ATTACHMENT}, {@code false} otherwise.
   */
  public boolean isTypeAttachment() {

    return TYPE_ATTACHMENT.equals(this.type);
  }

  /**
   * @return {@code true} if {@link #getType()} is {@link #TYPE_INLINE}, {@code false} otherwise.
   */
  public boolean isTypeInline() {

    return TYPE_INLINE.equals(this.type);
  }

  /**
   * @return {@code true} if {@link #getType()} is {@link #TYPE_FORM_DATA}, {@code false} otherwise.
   */
  public boolean isTypeFormData() {

    return TYPE_FORM_DATA.equals(this.type);
  }

  /**
   * @return the {@link #PARAMETER_FILENAME filename} {@link #getParameter(String) parameter value}.
   */
  public String getFilename() {

    return getParameterAsString(PARAMETER_FILENAME);
  }

  /**
   * @return the {@link #PARAMETER_SIZE size} {@link #getParameter(String) parameter value} or {@code -1} if undefined.
   *         The size is in octets which are bytes or regular platforms.
   */
  public Long getSize() {

    return getParameterAsLong(PARAMETER_SIZE);
  }

  /**
   * @return the {@link #PARAMETER_CREATION_DATE creation-date} {@link #getParameter(String) parameter value} or
   *         <code>null</code> if undefined.
   */
  public Instant getCreationDate() {

    return getParameterAsInstant(PARAMETER_CREATION_DATE);
  }

  /**
   * @return the {@link #PARAMETER_MODIFICATION_DATE modification-date} {@link #getParameter(String) parameter value} or
   *         <code>null</code> if undefined.
   */
  public Instant getModificationDate() {

    return getParameterAsInstant(PARAMETER_MODIFICATION_DATE);
  }

  /**
   * @return the {@link #PARAMETER_READ_DATE read-date} {@link #getParameter(String) parameter value} or
   *         <code>null</code> if undefined.
   */
  public Instant getReadDate() {

    return getParameterAsInstant(PARAMETER_READ_DATE);
  }

  @Override
  public String getValue() {

    StringBuilder buffer = new StringBuilder();
    if (this.type != null) {
      buffer.append(this.type);
      if (hasParameters()) {
        buffer.append(getParameterSeparator());
        buffer.append(' ');
      }
    }
    formatParameters(buffer);
    return buffer.toString();
  }

  /**
   * @param headerValue the value of the {@link #HEADER_CONTENT_DISPOSITION Content-Disposition HTTP header}.
   * @return the parsed {@link HttpHeaderContentDisposition}.
   */
  public static HttpHeaderContentDisposition ofValue(String headerValue) {

    String value = trim(headerValue);
    if (value == null) {
      return null;
    }
    final GenericBean<String> receiver = new GenericBean<>();
    Map<String, Object> parameters = EMPTY.parseParameters(value, x -> {
      receiver.setValue(x);
      return null;
    });
    String type = receiver.getValue();
    if ((type == null) && (parameters == null)) {
      return null;
    }
    return new HttpHeaderContentDisposition(type, parameters);
  }

  /**
   * @param type the {@link #getType() type}.
   * @param parameters the {@link #getParameter(String) parameters} as {@link Map}.
   * @return the new {@link HttpHeaderContentDisposition}.
   */
  public static HttpHeaderContentDisposition of(String type, Map<String, Object> parameters) {

    return new HttpHeaderContentDisposition(type, parameters);
  }

  static class Factory extends AbstractHttpHeaderFactory {

    Factory() {
      super(HEADER_CONTENT_DISPOSITION);
    }

    @Override
    AbstractHttpHeader create(String value) {

      return ofValue(value);
    }
  }

}
