/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import net.sf.mmm.util.version.base.AbstractNameVersion;

/**
 * This class represents a {@link HttpVersion} what is a particular version of the protocol {@link Http}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class HttpVersion extends AbstractNameVersion {

  private static final Map<String, HttpVersion> VERSION_MAP = new HashMap<>();

  /**
   * The {@link HttpVersion} <a href="https://www.w3.org/Protocols/HTTP/AsImplemented.html">0.9</a>.
   *
   * @deprecated this version is obsolete and shall not be used anymore.
   */
  @Deprecated
  public static final HttpVersion HTTP_0_9 = new HttpVersion("0.9", true);

  /**
   * The {@link HttpVersion} <a href="https://www.w3.org/Protocols/HTTP/1.0/spec.html">1.0</a>. This version is obsolete
   * and shall not be used anymore. Where suitable use the modern and secure transport via {@link #HTTP_2_0}. Otherwise
   * (e.g. for limited devices, legacy compatibility or simple unencrypted transport in internal networks) use
   * {@link #HTTP_1_1}.
   */
  public static final HttpVersion HTTP_1_0 = new HttpVersion("1.0", true);

  /**
   * The {@link HttpVersion} <a href="https://www.w3.org/Protocols/rfc2616/rfc2616.html">1.1 (RFC 2616)</a>. See also
   * <a href="https://tools.ietf.org/html/rfc7230">RFC 7230</a>, <a href="https://tools.ietf.org/html/rfc7231">RFC
   * 7231</a>, etc. <br>
   * Where suitable use the modern and secure transport via {@link #HTTP_2_0}.
   */
  public static final HttpVersion HTTP_1_1 = new HttpVersion("1.1", true);

  /** The {@link HttpVersion} <a href="https://tools.ietf.org/html/rfc7540">2.0</a>. */
  public static final HttpVersion HTTP_2_0 = new HttpVersion("2.0", true);

  static final String HTTP = "HTTP";

  /**
   * The constructor.
   *
   * @param version the {@link #getVersion() version}.
   */
  private HttpVersion(String version, boolean register) {
    super(version);
    if (register) {
      VERSION_MAP.put(version, this);
    }
  }

  @Override
  public String getName() {

    return HTTP;
  }

  /**
   * <b>ATTENTION:</b> Please use one of the predefined version constants such as {@link #HTTP_2_0} instead of this
   * method. Only use this method for specific edge cases e.g. to reuse parts of this library with a newer version that
   * has not (yet) been introduced here or to parse an {@link HttpRequest}.
   *
   * @param httpVersion the {@link #getVersion() version}.
   * @return the {@link HttpVersion}.
   */
  public HttpVersion ofVersion(String httpVersion) {

    Objects.requireNonNull(httpVersion, "httpVersion");
    HttpVersion result = VERSION_MAP.get(httpVersion);
    if (result == null) {
      String[] segments = httpVersion.split(".");
      if (segments.length <= 0) {
        throw new IllegalArgumentException(httpVersion);
      }
      try {
        int majorVersion = Integer.parseInt(segments[0]);
        if (majorVersion <= 2) {
          throw new IllegalArgumentException(httpVersion);
        }
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(httpVersion, e);
      }
      return new HttpVersion(httpVersion, false);
    }
    return result;
  }

}
