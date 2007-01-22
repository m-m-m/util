/* $Id$ */
package net.sf.mmm.util.http;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents an HTTP message.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class HttpMessage {

  /** HTTP version prefix */
  public static final String VERSION_PREFIX = "HTTP/";

  /**
   * HTTP version 0.9 (should NOT be used anymore)
   */
  public static final String VERSION_0_9 = "0.9";

  /** HTTP version 1.0 (RFC 1945) */
  public static final String VERSION_1_0 = "1.0";

  /** HTTP version 1.1 (RFC 2616) */
  public static final String VERSION_1_1 = "1.1";

  /**
   * The {@link #getHeaderProperty(String) header-property}
   * <code>Content-Encoding</code>.
   */
  public static final String HEADER_PROPERTY_CONTENT_ENCODING = "Content-Encoding";

  /**
   * The {@link #getHeaderProperty(String) header-property}
   * <code>Content-Length</code>.
   */
  public static final String HEADER_PROPERTY_CONTENT_LENGTH = "Content-Length";

  /**
   * The {@link #getHeaderProperty(String) header-property}
   * <code>Content-Type</code>.
   */
  public static final String HEADER_PROPERTY_CONTENT_TYPE = "Content-Type";

  /**
   * The {@link #getHeaderProperty(String) header-property}
   * <code>Cache-Control</code>.
   */
  public static final String HEADER_PROPERTY_CACHE_CONTROL = "Cache-Control";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>Date</code>.
   */
  public static final String HEADER_PROPERTY_DATE = "Date";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>Expires</code>.
   */
  public static final String HEADER_PROPERTY_EXPIRES = "Expires";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>From</code>.
   */
  public static final String HEADER_PROPERTY_FROM = "From";

  /**
   * The {@link #getHeaderProperty(String) header-property}
   * <code>If-Modified-Since</code>.
   */
  public static final String HEADER_PROPERTY_IF_MODIFIED_SINCE = "If-Modified-Since";

  /**
   * The {@link #getHeaderProperty(String) header-property}
   * <code>Last-Modified</code>.
   */
  public static final String HEADER_PROPERTY_LAST_MODIFIED = "Last-Modified";

  /**
   * The {@link #getHeaderProperty(String) header-property}
   * <code>Location</code>.
   */
  public static final String HEADER_PROPERTY_LOCATION = "Location";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>Pragma</code>.
   */
  public static final String HEADER_PROPERTY_PRAGMA = "Pragma";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>Referer</code>.
   */
  public static final String HEADER_PROPERTY_REFERER = "Referer";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>Server</code>.
   */
  public static final String HEADER_PROPERTY_SERVER = "Server";

  /**
   * The {@link #getHeaderProperty(String) header-property}
   * <code>User-Agent</code>.
   */
  public static final String HEADER_PROPERTY_USER_AGENT = "User-Agent";

  /**
   * The {@link #getHeaderProperty(String) header-property}
   * <code>WWW-Authenticate</code>.
   */
  public static final String HEADER_PROPERTY_WWW_AUTHENTICATE = "WWW-Authenticate";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>Host</code>.
   */
  public static final String HEADER_PROPERTY_HOST = "Host";

  /** the line termination sequence used by HTTP */
  protected static final String CRLF = "\r\n";

  /** @see #getHeaderProperty(String) */
  private final Map<Object, String> header;

  /** @see #getVersion() */
  private String version;

  /**
   * The constructor
   */
  public HttpMessage() {

    super();
    this.header = new HashMap<Object, String>();
    this.version = VERSION_1_1;
  }

  /**
   * This method gets the HTTP version to use.
   * 
   * @see #VERSION_1_0
   * @see #VERSION_1_1
   * 
   * @return the HTTP version.
   */
  public String getVersion() {

    return this.version;
  }

  /**
   * This method sets the HTTP version to use.
   * 
   * @param httpVersion
   *        the HTTP version to set.
   */
  public void setVersion(String httpVersion) {

    this.version = httpVersion;
  }

  /**
   * This method converts the header property to an hash key.<br>
   * The result must ensure that its
   * {@link Object#toString() string-representation} is
   * {@link String#equalsIgnoreCase(String) case in-sensitive equal} to the
   * given <code>name</code>. Additionally it needs to have compliant
   * implementations for {@link Object#equals(Object) equals} and
   * {@link Object#hashCode() hashCode}.<br>
   * This implementations ensures that the original case of the header
   * <code>name</code> is NOT modified while
   * {@link #getHeaderProperty(String)} acts case in-sensitive.
   * 
   * @param name
   *        is the {@link #getHeaderProperty(String) header-name} to convert.
   * @return an object that will be used as hash-key for the header with the
   *         given <code>name</code>.
   */
  protected Object createHeaderNameHash(String name) {

    return new HeaderPropertyKey(name);
  }

  /**
   * This method gets the property with the given <code>name</code> from the
   * header of this HTTP message.
   * 
   * @param name
   *        is the name of the requested property.
   * @return the requested property or <code>null</code> if NOT set.
   */
  public String getHeaderProperty(String name) {

    return this.header.get(name);
  }

  /**
   * This method sets the HTTP header-property with the given <code>name</code>
   * to the given <code>value</code>.
   * 
   * @see #appendHeaderProperty(String, String)
   * 
   * @param name
   *        is the name of the property to set.
   * @param value
   *        is the value of the property to set.
   */
  public void setHeaderProperty(String name, String value) {

    this.header.put(createHeaderNameHash(name), value);
  }

  /**
   * This method appends the given <code>value</code> to the HTTP
   * header-property with the given <code>name</code>.
   * 
   * @param name
   * @param value
   */
  public void appendHeaderProperty(String name, String value) {

    String concated = this.header.get(name);
    if (concated == null) {
      concated = value;
    } else {
      concated = concated + value;
    }
    this.header.put(name, concated);
  }

  /**
   * This method gets the {@link #HEADER_PROPERTY_CONTENT_TYPE content-type}.
   * 
   * @return the content-type or <code>null</code> if NOT set.
   */
  public String getContentType() {

    return getHeaderProperty(HEADER_PROPERTY_CONTENT_TYPE);
  }

  /**
   * This method sets the {@link #HEADER_PROPERTY_CONTENT_TYPE content-type}.
   * 
   * @param contentType
   *        is the content-type to set.
   */
  public void setContentType(String contentType) {

    setHeaderProperty(HEADER_PROPERTY_CONTENT_TYPE, contentType);
  }

  /**
   * This method gets the {@link #HEADER_PROPERTY_CONTENT_LENGTH content-length}.
   * 
   * @return the content-length or <code>null</code> if NOT set.
   */
  public Long getContentLength() {

    String length = getHeaderProperty(HEADER_PROPERTY_CONTENT_LENGTH);
    if (length != null) {
      return Long.valueOf(length);
    }
    return null;
  }

  /**
   * This method sets the {@link #HEADER_PROPERTY_CONTENT_LENGTH content-length}.
   * 
   * @param contentLength
   *        is the content-length to set. It should be a non-negative value.
   */
  public void setContentLength(long contentLength) {

    setHeaderProperty(HEADER_PROPERTY_CONTENT_LENGTH, Long.toString(contentLength));
  }

  /**
   * This method gets the
   * {@link #HEADER_PROPERTY_CONTENT_ENCODING content-encoding}.
   * 
   * @return the content-encoding or <code>null</code> if NOT set.
   */
  public String getContentEncoding() {

    return getHeaderProperty(HEADER_PROPERTY_CONTENT_ENCODING);
  }

  /**
   * This method sets the
   * {@link #HEADER_PROPERTY_CONTENT_ENCODING content-encoding}.
   * 
   * @param encoding
   *        is the content-encoding to set.
   */
  public void setContentEncoding(String encoding) {

    setHeaderProperty(HEADER_PROPERTY_CONTENT_ENCODING, encoding);
  }

  /**
   * This method gets the {@link #HEADER_PROPERTY_DATE date}.
   * 
   * @return
   */
  public String getDate() {

    return getHeaderProperty(HEADER_PROPERTY_DATE);
  }

  /**
   * 
   * @param date
   */
  public void setDate(String date) {

    setHeaderProperty(HEADER_PROPERTY_DATE, date);
  }

  /**
   * This method writes the first line (request-line or status-line) of the HTTP
   * message.
   * 
   * @param buffer
   *        is the string-buffer where to append the first line.
   */
  protected abstract void writeFirstLine(StringBuffer buffer);

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuffer buffer = new StringBuffer();
    writeFirstLine(buffer);
    for (Object key : this.header.keySet()) {
      String value = this.header.get(key);
      buffer.append(key);
      buffer.append(": ");
      buffer.append(value);
      buffer.append(CRLF);
    }
    return buffer.toString();
  }

  /**
   * This inner class represents a string that can be used as hash key that
   * behaves case in-sensitive.
   */
  private static final class HeaderPropertyKey {

    /** the name of the property */
    private final String name;

    /** @see #hashCode() */
    private int hash;

    /**
     * The constructor
     * 
     * @param key
     *        is the name of the property.
     */
    public HeaderPropertyKey(String key) {

      super();
      this.name = key;
      this.hash = this.name.toUpperCase().hashCode();
    }

    /**
     * This implementation violates the contract of this method intentionally!
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

      if (obj != null) {
        String other = obj.toString();
        return (this.name.equalsIgnoreCase(other));
      }
      return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

      return this.hash;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

      return this.name;
    }

  }

}
