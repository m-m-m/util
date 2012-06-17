/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.filter.base.ListCharFilter;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

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

  /**
   * The {@link #getHeaderPropertyAttribute(String, String) header-property}
   * <code>max-age</code>.
   * 
   * @see #HEADER_PROPERTY_CACHE_CONTROL
   */
  public static final String HEADER_ATTRIBUTE_MAX_AGE = "max-age";

  /** the line termination sequence used by HTTP */
  protected static final String CRLF = "\r\n";

  /** char-filter accepting only ',' and ';' */
  protected static final CharFilter CHAR_FILTER_WHITELIST_COMMA_OR_SEMICOLON = new ListCharFilter(
      true, ',', ';');

  /** char-filter accepting everything except ',' and ';' */
  protected static final CharFilter CHAR_FILTER_BLACKLIST_COMMA_OR_SEMICOLON = new ListCharFilter(
      false, ',', ';');

  /** @see #getHeaderProperty(String) */
  private final Map<Object, String> header;

  /** @see #getVersion() */
  private String version;

  /**
   * The constructor.
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
   * This method sets the HTTP version to use.<br>
   * By {@link HttpMessage#HttpMessage() default} the version is
   * {@link #VERSION_1_1}.
   * 
   * @param httpVersion the HTTP version to set.
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
   * @param name is the {@link #getHeaderProperty(String) header-name} to
   *        convert.
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
   * @param name is the name of the requested property.
   * @return the requested property or <code>null</code> if NOT set.
   */
  public String getHeaderProperty(String name) {

    return this.header.get(createHeaderNameHash(name));
  }

  /**
   * This method sets a single attribute of the property with the given
   * <code>name</code> in the header of this HTTP message.<br>
   * 
   * @param name is the name of the property to manipulate.
   * @param attributeName is the name of the attribute to set.
   * @param attributeValue is the value of the attribute or the empty string
   *        ("") if the attribute is just a flag.
   */
  public void setHeaderPropertyAttribute(String name, String attributeName, String attributeValue) {

    if (attributeName.length() == 0) {
      throw new IllegalArgumentException("Attribute name must NOT be empty!");
    }
    String value = getHeaderProperty(name);
    if (value == null) {
      value = attributeName;
      if (attributeValue.length() > 0) {
        value = value + "=" + attributeValue;
      }
    } else {
      CharSequenceScanner parser = new CharSequenceScanner(value);
      boolean found = false;
      while (parser.hasNext()) {
        boolean startsWith = parser.expect(attributeName, true);
        if (startsWith) {
          int startIndex = parser.getCurrentIndex();
          // be tolerant and ommit spaces
          parser.skipWhile(' ');
          if (parser.hasNext()) {
            char c = parser.next();
            if ((c == '=') || (c == ';') || (c == ',')) {
              found = true;
              if (c == '=') {
                parser.skipWhile(CHAR_FILTER_BLACKLIST_COMMA_OR_SEMICOLON);
              }
              String substring = "";
              if (attributeValue.length() > 0) {
                substring = "=" + attributeValue;
              }
              value = parser.getReplaced(substring, startIndex, parser.getCurrentIndex());
            }
          } else {
            // string end...
            found = true;
            if (attributeValue.length() > 0) {
              value = value + "=" + attributeValue;
            }
          }
        }
        if (found) {
          break;
        } else {
          parser.skipWhile(CHAR_FILTER_BLACKLIST_COMMA_OR_SEMICOLON);
          // ensure to read over ',' / ';'
          parser.forceNext();
        }
      }
      if (!found) {
        StringBuffer buffer = new StringBuffer(value.length() + attributeName.length()
            + attributeValue.length() + 2);
        buffer.append(value);
        buffer.append(',');
        buffer.append(attributeName);
        if (attributeValue.length() > 0) {
          buffer.append('=');
          buffer.append(attributeValue);
        }
        value = buffer.toString();
      }
    }
    setHeaderProperty(name, value);
  }

  /**
   * This method gets a single attribute of the property with the given
   * <code>name</code> from the header of this HTTP message.<br>
   * E.g. the property {@link #HEADER_PROPERTY_CACHE_CONTROL Cache-Control} may
   * have the value <code>no-cache,no-store,max-age=0</code>. Then the
   * following code snipplet would return "0":
   * 
   * <pre>
   * {@link #getHeaderPropertyAttribute(String, String) getHeaderPropertyAttribute}({@link #HEADER_PROPERTY_CACHE_CONTROL "Cache-Control"}, {@link #HEADER_ATTRIBUTE_MAX_AGE "max-age"})
   * </pre>
   * 
   * @param name is the name of the requested property.
   * @param attributeName the name of the requested attribute.
   * @return the value of the requested attribute or <code>null</code> if the
   *         property is NOT set or does NOT contain the attribute. If the
   *         property is set but has no value, the empty string ("") is
   *         returned.
   */
  public String getHeaderPropertyAttribute(String name, String attributeName) {

    if (attributeName.length() == 0) {
      // avoid infinity loop
      return null;
    }
    String value = getHeaderProperty(name);
    if (value != null) {
      CharSequenceScanner parser = new CharSequenceScanner(value);
      while (parser.hasNext()) {
        if (parser.expect(attributeName, true)) {
          // be tolerant and ommit spaces
          parser.skipWhile(' ');
          char c = parser.forceNext();
          if (c == '=') {
            String attributeValue = parser.readWhile(CHAR_FILTER_BLACKLIST_COMMA_OR_SEMICOLON);
            if (attributeValue != null) {
              return attributeValue.trim();
            }
          } else if ((c == ';') || (c == ',')) {
            return "";
          }
        }
        parser.skipWhile(CHAR_FILTER_BLACKLIST_COMMA_OR_SEMICOLON);
        parser.forceNext();
      }
    }
    return null;
  }

  /**
   * This method sets the HTTP header-property with the given <code>name</code>
   * to the given <code>value</code>.
   * 
   * @see #appendHeaderProperty(String, String)
   * 
   * @param name is the name of the property to set.
   * @param value is the value of the property to set.
   */
  public void setHeaderProperty(String name, String value) {

    this.header.put(createHeaderNameHash(name), value);
  }

  /**
   * This method appends the given <code>value</code> to the HTTP
   * header-property with the given <code>name</code>.
   * 
   * @see #appendHeaderProperty(String, String, String)
   * 
   * @param name is the name of the property to append to.
   * @param appendValue is the value to append to the header-property.
   */
  public void appendHeaderProperty(String name, String appendValue) {

    appendHeaderProperty(name, appendValue, null);
  }

  /**
   * This method appends the given <code>value</code> to the HTTP
   * header-property with the given <code>name</code>. If the header-property
   * is NOT yet set, this method behaves like
   * {@link #setHeaderProperty(String, String) setHeaderProperty}(name,
   * appendValue).
   * 
   * @param name is the name of the property to append to.
   * @param appendValue is the value to append to the header-property.
   * @param separator if NOT <code>null</code> and the header property is
   *        already set, this string will be appended after the current value
   *        and before the given <code>appendValue</code>.
   */
  public void appendHeaderProperty(String name, String appendValue, String separator) {

    String value = getHeaderProperty(name);
    if (value == null) {
      value = appendValue;
    } else {
      if (separator == null) {
        value = value + appendValue;
      } else {
        StringBuffer buffer = new StringBuffer(value.length() + separator.length()
            + appendValue.length());
        buffer.append(value);
        buffer.append(separator);
        buffer.append(appendValue);
        value = buffer.toString();
      }
    }
    setHeaderProperty(name, value);
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
   * @param contentType is the content-type to set.
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
   * @param contentLength is the content-length to set. It should be a
   *        non-negative value.
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
   * @param encoding is the content-encoding to set.
   */
  public void setContentEncoding(String encoding) {

    setHeaderProperty(HEADER_PROPERTY_CONTENT_ENCODING, encoding);
  }

  /**
   * This method sets the {@link #HEADER_ATTRIBUTE_MAX_AGE maximum age} (lease
   * time) in seconds.<br>
   * 
   * @param seconds is the max-age in seconds.
   */
  public void setCacheControlMaxAge(int seconds) {

    setHeaderPropertyAttribute(HEADER_PROPERTY_CACHE_CONTROL, HEADER_ATTRIBUTE_MAX_AGE, Integer
        .toString(seconds));
  }

  /**
   * This method gets the {@link #HEADER_ATTRIBUTE_MAX_AGE maximum age} (lease
   * time) in seconds.
   * 
   * @return the maximum age in seconds or <code>-1</code> if the max-age is
   *         NOT set (properly).
   */
  public int getCacheControlMaxAge() {

    String age = getHeaderPropertyAttribute(HEADER_PROPERTY_CACHE_CONTROL, HEADER_ATTRIBUTE_MAX_AGE);
    int result = -1;
    if (age != null) {
      try {
        result = Integer.parseInt(age.trim());
      } catch (NumberFormatException e) {
        // ignore
      }
    }
    return result;
  }

  /**
   * This method gets the {@link #HEADER_PROPERTY_DATE date}.
   * 
   * TODO: implement HTTP date parser
   * 
   * @return the date or <code>null</code> if NOT set.
   */
  public String getDate() {

    return getHeaderProperty(HEADER_PROPERTY_DATE);
  }

  /**
   * This method sets the {@link #HEADER_PROPERTY_DATE date}.
   * 
   * TODO: implement HTTP date formatter
   * 
   * @param date is the date to set.
   */
  public void setDate(String date) {

    setHeaderProperty(HEADER_PROPERTY_DATE, date);
  }

  /**
   * This method writes the first line (request-line or status-line) of the HTTP
   * message.
   * 
   * @param buffer is the string-buffer where to append the first line.
   */
  protected abstract void writeFirstLine(StringBuffer buffer);

  /**
   * This method serializes this HTTP-message to the given string buffer.
   * 
   * @param buffer is the string buffer where to append this HTTP-message to.
   */
  public void serialize(StringBuffer buffer) {

    writeFirstLine(buffer);
    for (Object key : this.header.keySet()) {
      String value = this.header.get(key);
      buffer.append(key);
      buffer.append(": ");
      buffer.append(value);
      buffer.append(CRLF);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuffer buffer = new StringBuffer();
    serialize(buffer);
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
     * The constructor.
     * 
     * @param key is the name of the property.
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
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

      return this.hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

      return this.name;
    }

  }

}
