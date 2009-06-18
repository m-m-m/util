/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents an URI pointing to the location of a
 * {@link net.sf.mmm.util.resource.api.DataResource}. A {@link ResourceUri} can
 * be {@link #getUri() represented as string} but consists of two parts: a
 * {@link #getSchemePrefix() scheme-prefix} and a {@link #getPath() path}.<br>
 * Here are some examples:
 * <table border="1">
 * <tr>
 * <th>{@link #getUri() uri}</th>
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
 * @since 1.0.2
 */
public class ResourceUri {

  /**
   * The {@link Pattern} for a {@link ResourceUri} {@link #getUri() as string}.
   */
  private static final Pattern SCHEME_PATTERN = Pattern
      .compile("([a-zA-Z][a-zA-Z0-9\\.\\-+]+:(//)?)(.*)");

  /** @see #getUri() */
  private final String uri;

  /** @see #getSchemePrefix() */
  private final String schemePrefix;

  /** @see #getPath() */
  private final String path;

  /**
   * The constructor.
   * 
   * @param uri is the {@link #getUri() URI}.
   */
  public ResourceUri(String uri) {

    super();
    this.uri = uri;
    Matcher matcher = SCHEME_PATTERN.matcher(uri);
    if (matcher.matches()) {
      this.schemePrefix = matcher.group(1);
      this.path = matcher.group(3);
    } else {
      this.schemePrefix = null;
      this.path = uri;
    }
  }

  /**
   * The constructor.
   * 
   * @param schemePrefix is the {@link #getSchemePrefix() scheme-prefix}.
   * @param path is the {@link #getPath() path}.
   */
  public ResourceUri(String schemePrefix, String path) {

    super();
    this.schemePrefix = schemePrefix;
    this.path = path;
    if (this.schemePrefix == null) {
      this.uri = this.path;
    } else {
      this.uri = this.schemePrefix + this.path;
    }
  }

  /**
   * This method gets this {@link ResourceUri} as string.
   * 
   * @return the URI as string.
   */
  public String getUri() {

    return this.uri;
  }

  /**
   * This method gets the <em>scheme-prefix</em> of a qualified
   * {@link ResourceUri}. The scheme-prefix has the following form:
   * <code>[a-zA-Z][a-zA-Z0-9\\.\\-+]+:(//)?</code>. The part before the colon
   * (:) is called the {@link java.net.URI#getScheme() scheme}.<br>
   * Examples for scheme-prefixes are:
   * <ul>
   * <li>classpath:</li>
   * <li>file://</li>
   * <li>http://</li>
   * <li>https://</li>
   * <li>ftp://</li>
   * </ul>
   * 
   * @return the schemePrefix or <code>null</code> if this {@link ResourceUri}
   *         is unqualified.
   */
  public String getSchemePrefix() {

    return this.schemePrefix;
  }

  /**
   * This method gets the path of this {@link ResourceUri}. This is the part of
   * the {@link #getUri() URI} after the {@link #getSchemePrefix()
   * scheme-prefix}.
   * 
   * @return the path.
   */
  public String getPath() {

    return this.path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.uri;
  }
}
