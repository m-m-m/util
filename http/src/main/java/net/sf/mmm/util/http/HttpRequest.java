/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http;

/**
 * This class represents an HTTP request message.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class HttpRequest extends HttpMessage {

  /** the GET method */
  public static final String METHOD_GET = "GET";

  /** the POST method */
  public static final String METHOD_HEAD = "HEAD";

  /** the POST method */
  public static final String METHOD_POST = "POST";

  /** the PUT method */
  public static final String METHOD_PUT = "PUT";

  /** the DELETE method */
  public static final String METHOD_DELETE = "DELETE";

  /** the OPTIONS method */
  public static final String METHOD_OPTIONS = "OPTIONS";

  /** the TRACE method */
  public static final String METHOD_TRACE = "TRACE";

  /** the CONNECT method */
  public static final String METHOD_CONNECT = "CONNECT";

  /** the NOTIFY method */
  public static final String METHOD_NOTIFY = "NOTIFY";

  /** the M-SEARCH method */
  public static final String METHOD_M_SEARCH = "M-SEARCH";

  private  String method;

  private  String uri;

  /**
   * The constructor.
   */
  public HttpRequest() {

    super();
    this.method = null;
    this.uri = null;
  }

  /**
   * This method gets the HTTP method to use. <br>
   * The typical methods are {@link #METHOD_GET GET}, {@link #METHOD_HEAD HEAD},
   * {@link #METHOD_POST POST}.
   * 
   * @return the HTTP method.
   */
  public String getMethod() {

    return this.method;
  }

  /**
   * This method sets the HTTP {@link #getMethod() method} to use.
   * 
   * @param httpMethod the HTTP method to set.
   */
  public void setMethod(String httpMethod) {

    this.method = httpMethod;
  }

  /**
   * This method gets the requested URI (e.g. "/").
   * 
   * @return the requested URI.
   */
  public String getUri() {

    return this.uri;
  }

  /**
   * This method sets the requested URI.
   * 
   * @see #getUri()
   * 
   * @param requestUri the uri to set
   */
  public void setUri(String requestUri) {

    this.uri = requestUri;
  }

  @Override
  protected void writeFirstLine(StringBuffer buffer) {

    buffer.append(this.method);
    buffer.append(' ');
    buffer.append(this.uri);
    buffer.append(' ');
    buffer.append(VERSION_PREFIX);
    buffer.append(getVersion());
    buffer.append(CRLF);
  }

}
