/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.path.api;

/**
 * This interface represents an URI pointing to the location of a {@link java.nio.file.Path}. A
 * {@link PathUri} can be {@link #getUri() represented as string} but consists of two parts: a
 * {@link #getSchemePrefix() scheme-prefix} and a {@link #getPath() path} . <br>
 * Here are some examples:
 * <table border="1">
 * <tr>
 * <th>{@link #getUri() URI}</th>
 * <th>{@link #getSchemePrefix() scheme-prefix}</th>
 * <th>{@link #getPath() path}</th>
 * </tr>
 * <tr>
 * <td>file:///tmp/config.xml</td>
 * <td>file://</td>
 * <td>/tmp/config.xml</td>
 * </tr>
 * <tr>
 * <td>http://m-m-m.sourceforge.net/maven/index.html</td>
 * <td>http://</td>
 * <td>m-m-m.sourceforge.net/maven/index.html</td>
 * </tr>
 * <tr>
 * <td>classpath:net/sf/mmm/util/beans-core.xml</td>
 * <td>classpath:</td>
 * <td>net/sf/mmm/util/beans-core.xml</td>
 * </tr>
 * <tr>
 * <td>/home/mmm/.profile</td>
 * <td>&lt;null&gt;</td>
 * <td>/home/mmm/.profile</td>
 * </tr>
 * <tr>
 * <td>../foo/bar.ext</td>
 * <td>&lt;null&gt;</td>
 * <td>../foo/bar.ext</td>
 * </tr>
 * <tr>
 * <td>C:\WINDOWS\system32\drivers\etc\hosts</td>
 * <td>&lt;null&gt;</td>
 * <td>C:\WINDOWS\system32\drivers\etc\hosts</td>
 * </tr>
 * </table>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public interface PathUri {

  /**
   * The {@link #getSchemePrefix() scheme prefix} for a {@link java.nio.file.Path} on the regular
   * {@link java.nio.file.FileSystem} (see {@link java.io.File}).
   */
  String SCHEME_PREFIX_FILE = "file://";

  /**
   * The {@link #getSchemePrefix() scheme prefix} for a {@link java.nio.file.Path} on the classpath (see
   * {@link ClassLoader} for details).
   */
  String SCHEME_PREFIX_CLASSPATH = "classpath:";

  /**
   * The {@link #getSchemePrefix() scheme prefix} for a {@link java.nio.file.Path} on the web accessed via
   * HTTP.
   */
  String SCHEME_PREFIX_HTTP = "http://";

  /**
   * The {@link #getSchemePrefix() scheme prefix} for a {@link java.nio.file.Path} on the web accessed via
   * HTTPS.
   */
  String SCHEME_PREFIX_HTTPS = "https://";

  /**
   * The {@link #getSchemePrefix() scheme prefix} for a {@link java.nio.file.Path} on the network accessed via
   * FTP.
   */
  String SCHEME_PREFIX_FTP = "ftp://";

  /**
   * This method gets this {@link PathUri} as string.
   *
   * @return the URI as string.
   */
  String getUri();

  /**
   * This method gets the <em>scheme-prefix</em> of a qualified {@link PathUri}. The scheme-prefix has the
   * following form: {@code [a-zA-Z][a-zA-Z0-9\\.\\-+]+:(//)?}. The part before the colon (:) is called
   * the {@link java.net.URI#getScheme() scheme}.<br>
   * Examples for scheme-prefixes are:
   * <ul>
   * <li>classpath:</li>
   * <li>file://</li>
   * <li>http://</li>
   * <li>https://</li>
   * <li>ftp://</li>
   * </ul>
   *
   * @return the schemePrefix or {@code null} if this {@link PathUri} is unqualified.
   */
  String getSchemePrefix();

  /**
   * This method gets the path of this {@link PathUri}. It is the part of the {@link #getUri() URI} after the
   * {@link #getSchemePrefix() scheme-prefix}.
   *
   * @return the path.
   */
  String getPath();

  String toString();

}
