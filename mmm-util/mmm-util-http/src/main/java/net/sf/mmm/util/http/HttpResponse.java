/* $Id$ */
package net.sf.mmm.util.http;

/**
 * This class represents an HTTP response message.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class HttpResponse extends HttpMessage {

  /**
   * The {@link #getStatusCode() status-code} for <code>Continue</code>.
   */
  public static final int STATUS_CODE_CONTINUE = 100;

  /**
   * The {@link #getStatusCode() status-code} for
   * <code>Switching Protocols</code>.
   */
  public static final int STATUS_CODE_SWITCHING_PROTOCOLS = 101;

  /**
   * The {@link #getStatusCode() status-code} for <code>OK</code>.
   */
  public static final int STATUS_CODE_OK = 200;

  /**
   * The {@link #getStatusCode() status-code} for <code>Created</code>.
   */
  public static final int STATUS_CODE_CREATED = 201;

  /**
   * The {@link #getStatusCode() status-code} for <code>Accepted</code>.
   */
  public static final int STATUS_CODE_ACCEPTED = 202;

  /**
   * The {@link #getStatusCode() status-code} for
   * <code>Non-Authoritative Information</code>.
   */
  public static final int STATUS_CODE_NONAUTHORITATIVE_INFORMATION = 203;

  /**
   * The {@link #getStatusCode() status-code} for <code>No Content</code>.
   */
  public static final int STATUS_CODE_NO_CONTENT = 204;

  public static final int STATUS_CODE_RESET_CONTENT = 205;

  public static final int STATUS_CODE_PARTIAL_CONTENT = 206;

  public static final int STATUS_CODE_MULTIPLE_CHOICES = 300;

  public static final int STATUS_CODE_MOVED_PERMANENTLY = 301;

  public static final int STATUS_CODE_MOVED_TEMPORARILY = 302;

  public static final int STATUS_CODE_SEE_OTHER = 303;

  public static final int STATUS_CODE_NOT_MODIFIED = 304;

  public static final int STATUS_CODE_USE_PROXY = 305;

  public static final int STATUS_CODE_TEMPORARY_REDIRECT = 307;

  public static final int STATUS_CODE_BAD_REQUEST = 400;

  public static final int STATUS_CODE_UNAUTHORIZED = 401;

  public static final int STATUS_CODE_PAYMENT_REQUIRED = 402;

  public static final int STATUS_CODE_FORBIDDEN = 403;

  public static final int STATUS_CODE_NOT_FOUND = 404;

  public static final int STATUS_CODE_METHOD_NOT_ALLOWED = 405;

  public static final int STATUS_CODE_NOT_ACCEPTABLE = 406;

  public static final int STATUS_CODE_PROXY_AUTHENTICATION_REQUIRED = 407;

  public static final int STATUS_CODE_REQUEST_TIMEOUT = 408;

  public static final int STATUS_CODE_CONFLICT = 409;

  public static final int STATUS_CODE_GONE = 410;

  public static final int STATUS_CODE_LENGTH_REQUIRED = 411;

  public static final int STATUS_CODE_PRECONDITION_FAILED = 412;

  public static final int STATUS_CODE_REQUEST_ENTITY_TOO_LARGE = 413;

  public static final int STATUS_CODE_REQUEST_URI_TO_LONG = 414;

  public static final int STATUS_CODE_UNSUPPORTED_MEDIA_TYPE = 415;

  public static final int STATUS_CODE_REQUEST_RANGE_NOT_SATISFIABLE = 416;

  public static final int STATUS_CODE_EXPECTATION_FAILED = 417;

  public static final int STATUS_CODE_INTERNAL_SERVER_ERROR = 500;

  public static final int STATUS_CODE_NOT_IMPLEMENTED = 501;

  public static final int STATUS_CODE_BAD_GATEWAY = 502;

  public static final int STATUS_CODE_SERVICE_UNAVAILABLE = 503;

  public static final int STATUS_CODE_GATEWAY_TIMEOUT = 504;

  public static final int STATUS_CODE_HTTP_VERSION_NOT_SUPPORTED = 505;

  /** @see #getStatusCode() */
  private int statusCode;

  /** */
  private String reasonPhrase;

  /**
   * The constructor
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
   * @param httpStatusCode
   *        the HTTP statusCode to set.
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
   * @param reason
   *        the reason-phrase to set.
   */
  public void setReasonPhrase(String reason) {

    this.reasonPhrase = reason;
  }

  /**
   * @see net.sf.mmm.util.http.HttpMessage#writeFirstLine(java.lang.StringBuffer)
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
