/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.api;

import java.util.regex.Pattern;

import net.sf.mmm.util.lang.api.StringWritable;

/**
 * This is a simple container for a combination of a {@link #getName() name} together with its {@link #getVersion()
 * version}. A {@link NameVersion} implementation has to follow a {@link #toString() specified string representation}.
 * Examples for a {@link NameVersion} are "HTTP/2.0" or "UPnP/1.0".
 *
 * @see NameVersionComment
 *
 * @author hohwille
 * @since 7.4.0
 */
public interface NameVersion extends StringWritable {

  /**
   * The character used to separate {@link #getName() name} and {@link #getVersion() version} in {@link #toString()
   * string represnetations}.
   */
  public static final char NAME_VERSION_SEPARATOR = '/';

  /** The regular expression {@link String} for a {@link NameVersion}. */
  String NAME_VERSION_REGEX = "([\\w_#][^/ ()]*)/([\\w._~#+-]+)";

  /** The regular expression {@link Pattern} for a {@link NameVersion}. */
  Pattern NAME_VERSION_PATTERN = Pattern.compile(NAME_VERSION_REGEX);

  /**
   * @return the name of the {@link #getVersion() versioned} object (protocol, software product, etc.). Examples are
   *         "HTTP", "UPnP", "Mozilla", "FireFox", etc.
   */
  String getName();

  /**
   * @return the version (e.g. "50", "5.0", "9A334", "3.0+", "0.0.1-pre-alpha-2", etc.)
   */
  String getVersion();

  /**
   * Gets the {@link String} representation of this object as described by the returns comment. Please note that a
   * {@link NameVersionComment} is also a {@link NameVersion} but {@link NameVersionComment#toString() extends this
   * string representation}.
   *
   * @see NameVersionComment
   * @see #NAME_VERSION_SEPARATOR
   * @see #NAME_VERSION_PATTERN
   * @return the string representation in the form "{@link #getName() name}/{@link #getVersion() version}" (without
   *         quotes).
   */
  @Override
  String toString();

}
