/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http;

/**
 * This class represents an HTTP response message.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class HttpResponse extends HttpMessage {

  /** The {@link #getStatusCode() status-code} for {@code Continue}. */
  public static final int STATUS_CODE_CONTINUE = 100;

  /** The {@link #getStatusCode() status-code} for {@code Switching Protocols}. */
  public static final int STATUS_CODE_SWITCHING_PROTOCOLS = 101;

  /** The {@link #getStatusCode() status-code} for {@code OK}. */
  public static final int STATUS_CODE_OK = 200;

  /** The {@link #getStatusCode() status-code} for {@code Created}. */
  public static final int STATUS_CODE_CREATED = 201;

  /** The {@link #getStatusCode() status-code} for {@code Accepted}. */
  public static final int STATUS_CODE_ACCEPTED = 202;

  /** The {@link #getStatusCode() status-code} for {@code Non-Authoritative Information}. */
  public static final int STATUS_CODE_NONAUTHORITATIVE_INFORMATION = 203;

  /** The {@link #getStatusCode() status-code} for {@code No Content}. */
  public static final int STATUS_CODE_NO_CONTENT = 204;

  /** The {@link #getStatusCode() status-code} for {@code Reset Content}. */
  public static final int STATUS_CODE_RESET_CONTENT = 205;

  /** The {@link #getStatusCode() status-code} for {@code Partial Content}. */
  public static final int STATUS_CODE_PARTIAL_CONTENT = 206;

  /** The {@link #getStatusCode() status-code} for {@code Multiple Choices}. */
  public static final int STATUS_CODE_MULTIPLE_CHOICES = 300;

  /** The {@link #getStatusCode() status-code} for {@code Moved Permanently}. */
  public static final int STATUS_CODE_MOVED_PERMANENTLY = 301;

  /** The {@link #getStatusCode() status-code} for {@code Moved Temporary}. */
  public static final int STATUS_CODE_MOVED_TEMPORARILY = 302;

  /** The {@link #getStatusCode() status-code} for {@code See other}. */
  public static final int STATUS_CODE_SEE_OTHER = 303;

  /** The {@link #getStatusCode() status-code} for {@code Not Modified}. */
  public static final int STATUS_CODE_NOT_MODIFIED = 304;

  /** The {@link #getStatusCode() status-code} for {@code Use Proxy}. */
  public static final int STATUS_CODE_USE_PROXY = 305;

  /** The {@link #getStatusCode() status-code} for {@code Temporary Redirect}. */
  public static final int STATUS_CODE_TEMPORARY_REDIRECT = 307;

  /** The {@link #getStatusCode() status-code} for {@code Bad Request}. */
  public static final int STATUS_CODE_BAD_REQUEST = 400;

  /** The {@link #getStatusCode() status-code} for {@code Unauthorized}. */
  public static final int STATUS_CODE_UNAUTHORIZED = 401;

  /** The {@link #getStatusCode() status-code} for {@code Payment Required}. */
  public static final int STATUS_CODE_PAYMENT_REQUIRED = 402;

  /** The {@link #getStatusCode() status-code} for {@code Forbidden}. */
  public static final int STATUS_CODE_FORBIDDEN = 403;

  /** The {@link #getStatusCode() status-code} for {@code Not Found}. */
  public static final int STATUS_CODE_NOT_FOUND = 404;

  /** The {@link #getStatusCode() status-code} for {@code Not Allowed}. */
  public static final int STATUS_CODE_METHOD_NOT_ALLOWED = 405;

  /** The {@link #getStatusCode() status-code} for {@code Not Acceptable}. */
  public static final int STATUS_CODE_NOT_ACCEPTABLE = 406;

  /** The {@link #getStatusCode() status-code} for {@code Proxy Authentication Required}. */
  public static final int STATUS_CODE_PROXY_AUTHENTICATION_REQUIRED = 407;

  /** The {@link #getStatusCode() status-code} for {@code Request Timeout}. */
  public static final int STATUS_CODE_REQUEST_TIMEOUT = 408;

  /** The {@link #getStatusCode() status-code} for {@code Conflict}. */
  public static final int STATUS_CODE_CONFLICT = 409;

  /** The {@link #getStatusCode() status-code} for {@code Gone}. */
  public static final int STATUS_CODE_GONE = 410;

  /** The {@link #getStatusCode() status-code} for {@code Length Required}. */
  public static final int STATUS_CODE_LENGTH_REQUIRED = 411;

  /** The {@link #getStatusCode() status-code} for {@code Precondition Failed}. */
  public static final int STATUS_CODE_PRECONDITION_FAILED = 412;

  /** The {@link #getStatusCode() status-code} for {@code Request Entity Too Large}. */
  public static final int STATUS_CODE_REQUEST_ENTITY_TOO_LARGE = 413;

  /** The {@link #getStatusCode() status-code} for {@code Request URI Too Long}. */
  public static final int STATUS_CODE_REQUEST_URI_TOO_LONG = 414;

  /** The {@link #getStatusCode() status-code} for {@code Unsupported Media Type}. */
  public static final int STATUS_CODE_UNSUPPORTED_MEDIA_TYPE = 415;

  /** The {@link #getStatusCode() status-code} for {@code Request Range Not Satisfiable}. */
  public static final int STATUS_CODE_REQUEST_RANGE_NOT_SATISFIABLE = 416;

  /** The {@link #getStatusCode() status-code} for {@code Expectation Failed}. */
  public static final int STATUS_CODE_EXPECTATION_FAILED = 417;

  /** The {@link #getStatusCode() status-code} for {@code Internal Server Error}. */
  public static final int STATUS_CODE_INTERNAL_SERVER_ERROR = 500;

  /** The {@link #getStatusCode() status-code} for {@code Not Implemented}. */
  public static final int STATUS_CODE_NOT_IMPLEMENTED = 501;

  /** The {@link #getStatusCode() status-code} for {@code Bad Gateway}. */
  public static final int STATUS_CODE_BAD_GATEWAY = 502;

  /** The {@link #getStatusCode() status-code} for {@code Service Unavailable}. */
  public static final int STATUS_CODE_SERVICE_UNAVAILABLE = 503;

  /** The {@link #getStatusCode() status-code} for {@code Gateway Timeout}. */
  public static final int STATUS_CODE_GATEWAY_TIMEOUT = 504;

  /** The {@link #getStatusCode() status-code} for {@code HTTP Version Not Supported}. */
  public static final int STATUS_CODE_HTTP_VERSION_NOT_SUPPORTED = 505;

  private int statusCode;

  private String reasonPhrase;

  /**
   * The constructor.
   */
  public HttpResponse() {

    super();
    this.statusCode = 200;
    this.reasonPhrase = "";
  }

  /**
   * This method gets the HTTP status code.
   * 
   * @return the HTTP statusCode.
   */
  public int getStatusCode() {

    return this.statusCode;
  }

  /**
   * This method sets the HTTP status code.
   * 
   * @see #getStatusCode()
   * 
   * @param httpStatusCode the HTTP statusCode to set.
   */
  public void setStatusCode(int httpStatusCode) {

    this.statusCode = httpStatusCode;
  }

  /**
   * This method gets the reason-phrase explaining the
   * {@link #getStatusCode() status-code}.
   * 
   * @return the reason-phrase.
   */
  public String getReasonPhrase() {

    return this.reasonPhrase;
  }

  /**
   * This method sets the reason-phrase.
   * 
   * @see #getReasonPhrase()
   * 
   * @param reason the reason-phrase to set.
   */
  public void setReasonPhrase(String reason) {

    this.reasonPhrase = reason;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void writeFirstLine(StringBuffer buffer) {

    buffer.append(VERSION_PREFIX);
    buffer.append(getVersion());
    buffer.append(' ');
    // code must be >= 100
    buffer.append(this.statusCode);
    buffer.append(' ');
    buffer.append(this.reasonPhrase);
    buffer.append(CRLF);
  }

}
