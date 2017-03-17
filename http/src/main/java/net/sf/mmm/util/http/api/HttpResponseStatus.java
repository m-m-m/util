/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the status of an {@link HttpResponse}. For details have a look at
 * <a href="https://tools.ietf.org/html/rfc7231#section-6">RFC 7231, section 6</a>.
 *
 * @see HttpResponse#getStatus()
 * @see #getCode()
 * @see #getReasonPhrase()
 *
 * @author hohwille
 * @since 8.4.0
 */
public class HttpResponseStatus {

  private static final Logger LOG = LoggerFactory.getLogger(HttpResponseStatus.class);

  private static final Map<Integer, HttpResponseStatus> STATUS_MAP = new HashMap<>();

  /** The {@link HttpResponseStatus status} <a href="">100 Continue</a>. */
  public static final HttpResponseStatus CONTINUE = new HttpResponseStatus(100, "Continue", true);

  /** The {@link HttpResponseStatus status} <a href="">101 Switching Protocols</a>. */
  public static final HttpResponseStatus SWITCHING_PROTOCOLS = new HttpResponseStatus(101, "Switching Protocols", true);

  /** The {@link HttpResponseStatus status} <a href="">200 OK</a>. */
  public static final HttpResponseStatus OK = new HttpResponseStatus(200, "OK", true);

  /** The {@link HttpResponseStatus status} <a href="">201 Created</a>. */
  public static final HttpResponseStatus CREATED = new HttpResponseStatus(201, "Created", true);

  /** The {@link HttpResponseStatus status} <a href="">202 Accepted</a>. */
  public static final HttpResponseStatus ACCEPTED = new HttpResponseStatus(202, "Accepted", true);

  /** The {@link HttpResponseStatus status} <a href="">203 Non-Authoritative Information</a>. */
  public static final HttpResponseStatus NONAUTHORITATIVE_INFORMATION = new HttpResponseStatus(203, "Non-Authoritative Information", true);

  /** The {@link HttpResponseStatus status} <a href="">No 204 Content</a>. */
  public static final HttpResponseStatus NO_CONTENT = new HttpResponseStatus(204, "No Content", true);

  /** The {@link HttpResponseStatus status} <a href="">205 Reset Content</a>. */
  public static final HttpResponseStatus RESET_CONTENT = new HttpResponseStatus(205, "Reset Content", true);

  /** The {@link HttpResponseStatus status} <a href="">206 Partial Content</a>. */
  public static final HttpResponseStatus PARTIAL_CONTENT = new HttpResponseStatus(206, "Partial Content", true);

  /**
   * The {@link HttpResponseStatus status} <a href="https://tools.ietf.org/html/rfc4918#section-11.1">207
   * Multi-Status</a>.
   */
  public static final HttpResponseStatus MULTI_STATUS = new HttpResponseStatus(207, "Multi-Status", true);

  /**
   * The {@link HttpResponseStatus status} <a href="https://tools.ietf.org/html/rfc5842#section-7.1">208 Already
   * Reported</a>.
   */
  public static final HttpResponseStatus ALREADY_REPORTED = new HttpResponseStatus(208, "Already Reported", true);

  /**
   * The {@link HttpResponseStatus status} <a href="https://tools.ietf.org/html/rfc3229#section-10.4.1">226 IM Used</a>.
   * The server has fulfilled a request for the resource, and the response is a representation of the result of one or
   * more instance-manipulations applied to the current instance.
   */
  public static final HttpResponseStatus IM_USED = new HttpResponseStatus(226, "IM Used", true);

  /** The {@link HttpResponseStatus status} <a href="">300 Multiple Choices</a>. */
  public static final HttpResponseStatus MULTIPLE_CHOICES = new HttpResponseStatus(300, "Multiple Choices", true);

  /** The {@link HttpResponseStatus status} <a href="">301 Moved Permanently</a>. */
  public static final HttpResponseStatus MOVED_PERMANENTLY = new HttpResponseStatus(301, "Moved Permanently", true);

  /** The {@link HttpResponseStatus status} <a href="">302 Moved Temporary</a>. */
  public static final HttpResponseStatus MOVED_TEMPORARILY = new HttpResponseStatus(302, "Moved Temporary", true);

  /** The {@link HttpResponseStatus status} <a href="">303 See other</a>. */
  public static final HttpResponseStatus SEE_OTHER = new HttpResponseStatus(303, "See Other", true);

  /** The {@link HttpResponseStatus status} <a href="">304 Not Modified</a>. */
  public static final HttpResponseStatus NOT_MODIFIED = new HttpResponseStatus(304, "Not Modified", true);

  /** The {@link HttpResponseStatus status} <a href="">305 Use Proxy</a>. */
  public static final HttpResponseStatus USE_PROXY = new HttpResponseStatus(305, "Use Proxy", true);

  /** The {@link HttpResponseStatus status} <a href="">307 Temporary Redirect</a>. */
  public static final HttpResponseStatus TEMPORARY_REDIRECT = new HttpResponseStatus(307, "Temporary Redirect", true);

  /** The {@link HttpResponseStatus status} <a href="">400 Bad Request</a>. */
  public static final HttpResponseStatus BAD_REQUEST = new HttpResponseStatus(400, "Bad Request", true);

  /** The {@link HttpResponseStatus status} <a href="">401 Unauthorized</a>. */
  public static final HttpResponseStatus UNAUTHORIZED = new HttpResponseStatus(401, "Unauthorized", true);

  /** The {@link HttpResponseStatus status} <a href="">402 Payment Required</a>. */
  public static final HttpResponseStatus PAYMENT_REQUIRED = new HttpResponseStatus(402, "Payment Required", true);

  /** The {@link HttpResponseStatus status} <a href="">403 Forbidden</a>. */
  public static final HttpResponseStatus FORBIDDEN = new HttpResponseStatus(403, "Forbidden", true);

  /** The {@link HttpResponseStatus status} <a href="">404 Not Found</a>. */
  public static final HttpResponseStatus NOT_FOUND = new HttpResponseStatus(404, "Not Found", true);

  /** The {@link HttpResponseStatus status} <a href="">405 Not Allowed</a>. */
  public static final HttpResponseStatus METHOD_NOT_ALLOWED = new HttpResponseStatus(405, "Not Allowed", true);

  /** The {@link HttpResponseStatus status} <a href="">406 Not Acceptable</a>. */
  public static final HttpResponseStatus NOT_ACCEPTABLE = new HttpResponseStatus(406, "Not Acceptable", true);

  /** The {@link HttpResponseStatus status} <a href="">407 Proxy Authentication Required</a>. */
  public static final HttpResponseStatus PROXY_AUTHENTICATION_REQUIRED = new HttpResponseStatus(407, "Proxy Authentication Required", true);

  /** The {@link HttpResponseStatus status} <a href="">408 Request Timeout</a>. */
  public static final HttpResponseStatus REQUEST_TIMEOUT = new HttpResponseStatus(408, "Request Timeout", true);

  /** The {@link HttpResponseStatus status} <a href="">409 Conflict</a>. */
  public static final HttpResponseStatus CONFLICT = new HttpResponseStatus(409, "Conflict", true);

  /** The {@link HttpResponseStatus status} <a href="">410 Gone</a>. */
  public static final HttpResponseStatus GONE = new HttpResponseStatus(410, "Gone", true);

  /** The {@link HttpResponseStatus status} <a href="">411 Length Required</a>. */
  public static final HttpResponseStatus LENGTH_REQUIRED = new HttpResponseStatus(411, "Length Required", true);

  /** The {@link HttpResponseStatus status} <a href="">412 Precondition Failed</a>. */
  public static final HttpResponseStatus PRECONDITION_FAILED = new HttpResponseStatus(412, "Precondition Failed", true);

  /** The {@link HttpResponseStatus status} <a href="">413 Request Entity Too Large</a>. */
  public static final HttpResponseStatus REQUEST_ENTITY_TOO_LARGE = new HttpResponseStatus(413, "Request Entity Too Large", true);

  /** The {@link HttpResponseStatus status} <a href="">414 Request URI Too Long</a>. */
  public static final HttpResponseStatus REQUEST_URI_TOO_LONG = new HttpResponseStatus(414, "Request URI Too Long", true);

  /** The {@link HttpResponseStatus status} <a href="">415 Unsupported Media Type</a>. */
  public static final HttpResponseStatus UNSUPPORTED_MEDIA_TYPE = new HttpResponseStatus(415, "Unsupported Media Type", true);

  /** The {@link HttpResponseStatus status} <a href="">416 Request Range Not Satisfiable</a>. */
  public static final HttpResponseStatus REQUEST_RANGE_NOT_SATISFIABLE = new HttpResponseStatus(416, "Request Range Not Satisfiable", true);

  /** The {@link HttpResponseStatus status} <a href="">417 Expectation Failed</a>. */
  public static final HttpResponseStatus EXPECTATION_FAILED = new HttpResponseStatus(417, "Expectation Failed", true);

  /** The {@link HttpResponseStatus status} <a href="">418 I'm a teapot</a>. */
  public static final HttpResponseStatus IM_A_TEAPOT = new HttpResponseStatus(418, "I'm a teapot", true);

  /** The {@link HttpResponseStatus status} <a href="">420 Enhance Your Calm</a>. */
  public static final HttpResponseStatus ENHANCE_YOUR_CALM = new HttpResponseStatus(420, "Enhance Your Calm", true);

  /**
   * The {@link HttpResponseStatus status}
   * <a href="https://tools.ietf.org/html/draft-ietf-httpbis-http2-15#section-9.1.2">421 Misdirected Request</a>
   */
  public static final HttpResponseStatus MISDIRECTED_REQUEST = new HttpResponseStatus(421, "Misdirected Request", true);

  /**
   * The {@link HttpResponseStatus status} <a href="https://tools.ietf.org/html/rfc4918#section-11.2">422 Unprocessable
   * Entity</a>.
   */
  public static final HttpResponseStatus UNPROCESSABLE_ENTITY = new HttpResponseStatus(422, "Unprocessable Entity", true);

  /**
   * The {@link HttpResponseStatus status} <a href="https://tools.ietf.org/html/rfc4918#section-11.3">423 Locked</a>.
   */
  public static final HttpResponseStatus LOCKED = new HttpResponseStatus(423, "Locked", true);

  /**
   * The {@link HttpResponseStatus status} <a href="https://tools.ietf.org/html/rfc4918#section-11.4">424 Locked</a>.
   */
  public static final HttpResponseStatus FAILED_DEPENDENCY = new HttpResponseStatus(424, "Failed Dependency", true);

  /**
   * The {@link HttpResponseStatus status} <a href="https://tools.ietf.org/html/rfc4918#section-11.4">425 Unordered
   * Collection</a>. (WebDAV, RFC3648)
   */
  public static final HttpResponseStatus UNORDERED_COLLECTION = new HttpResponseStatus(425, "Unordered Collection", true);

  /**
   * 426 Upgrade Required (RFC2817)
   */
  public static final HttpResponseStatus UPGRADE_REQUIRED = new HttpResponseStatus(426, "Upgrade Required", true);

  /**
   * 428 Precondition Required (RFC6585)
   */
  public static final HttpResponseStatus PRECONDITION_REQUIRED = new HttpResponseStatus(428, "Precondition Required", true);

  /**
   * 429 Too Many Requests (RFC6585)
   */
  public static final HttpResponseStatus TOO_MANY_REQUESTS = new HttpResponseStatus(429, "Too Many Requests", true);

  /**
   * 431 Request Header Fields Too Large (RFC6585)
   */
  public static final HttpResponseStatus REQUEST_HEADER_FIELDS_TOO_LARGE = new HttpResponseStatus(431, "Request Header Fields Too Large", true);

  /** The {@link HttpResponseStatus status} <a href="">Internal Server Error</a>. */
  public static final HttpResponseStatus INTERNAL_SERVER_ERROR = new HttpResponseStatus(500, "Internal Server Error", true);

  /** The {@link HttpResponseStatus status} <a href="">Not Implemented</a>. */
  public static final HttpResponseStatus NOT_IMPLEMENTED = new HttpResponseStatus(501, "Not Implemented", true);

  /** The {@link HttpResponseStatus status} <a href="">Bad Gateway</a>. */
  public static final HttpResponseStatus BAD_GATEWAY = new HttpResponseStatus(502, "Bad Gateway", true);

  /** The {@link HttpResponseStatus status} <a href="">Service Unavailable</a>. */
  public static final HttpResponseStatus SERVICE_UNAVAILABLE = new HttpResponseStatus(503, "Service Unavailable", true);

  /** The {@link HttpResponseStatus status} <a href="">Gateway Timeout</a>. */
  public static final HttpResponseStatus GATEWAY_TIMEOUT = new HttpResponseStatus(504, "Gateway Timeout", true);

  /** The {@link HttpResponseStatus status} <a href="">HTTP Version Not Supported</a>. */
  public static final HttpResponseStatus HTTP_VERSION_NOT_SUPPORTED = new HttpResponseStatus(505, "HTTP Version Not Supported", true);

  private final int code;

  private final String reasonPhrase;

  /**
   * The constructor.
   *
   * @param code - see {@link #getCode()}.
   * @param reasonPhrase - see {@link #getReasonPhrase()}.
   */
  private HttpResponseStatus(int code, String reasonPhrase, boolean register) {
    super();
    assert (code >= 100);
    assert (code <= 999);
    this.code = code;
    Objects.requireNonNull(reasonPhrase, "reasonPhrase");
    this.reasonPhrase = reasonPhrase;
    if (register) {
      STATUS_MAP.put(Integer.valueOf(code), this);
    }
  }

  /**
   * This method gets the <a href="https://tools.ietf.org/html/rfc7231#section-6">HTTP status code</a>.
   *
   * @return the HTTP statusCode.
   */
  public int getCode() {

    return this.code;
  }

  /**
   * @return the reason-phrase explaining the {@link #getCode() status-code}.
   */
  public String getReasonPhrase() {

    return this.reasonPhrase;
  }

  @Override
  public int hashCode() {

    return this.code;
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }
    HttpResponseStatus other = (HttpResponseStatus) obj;
    if (this.code != other.code) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return this.code + ' ' + this.reasonPhrase;
  }

  /**
   * @param statusCode - see {@link #getCode()}.
   * @return the {@link HttpResponseStatus} with the given {@link #getCode() code}.
   */
  public static HttpResponseStatus of(int statusCode) {

    return of(statusCode, null);
  }

  /**
   * @param statusCode - see {@link #getCode()}.
   * @param reason - see {@link #getReasonPhrase()}.
   * @return the {@link HttpResponseStatus} with the given {@link #getCode() code} and {@link #getReasonPhrase() reason
   *         phrase}.
   */
  public static HttpResponseStatus of(int statusCode, String reason) {

    Integer key = Integer.valueOf(statusCode);
    HttpResponseStatus status = STATUS_MAP.get(key);
    if (status == null) {
      String r = reason;
      if (r == null) {
        r = "Error" + statusCode;
      }
      status = new HttpResponseStatus(statusCode, r, false);
    } else if ((reason != null) && !status.reasonPhrase.equals(reason)) {
      LOG.info("Inofficial reason {} for HTTP status code {} - official is {}.", reason, key, status.reasonPhrase);
      status = new HttpResponseStatus(statusCode, reason, false);
    }
    return status;
  }

}
