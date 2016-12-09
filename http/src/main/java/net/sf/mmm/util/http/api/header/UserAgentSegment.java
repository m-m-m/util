/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a simple container for a name/version pair with an optional comment.
 *
 * @author hohwille
 * @since 8.4.0
 */
public final class UserAgentSegment {

  private static final String REGEX_NAME_VERSION = "([\\w_+-]+)/([\\w._+-]+)";

  static final Pattern PATTERN_NAME_VERSION = Pattern.compile(REGEX_NAME_VERSION);

  private static final String REGEX_COMMENT = "([(]([^)]+)[)])";

  static final Pattern PATTERN_COMMENT = Pattern.compile(REGEX_COMMENT);

  private static final String REGEX = REGEX_NAME_VERSION + "[ ]*" + REGEX_COMMENT + "?";

  static final Pattern PATTERN = Pattern.compile(REGEX);

  private final String name;

  private final String version;

  private final String comment;

  /**
   * The constructor.
   *
   * @param name the name.
   * @param version the version.
   * @param comment an optional comment or {@code null}.
   */
  public UserAgentSegment(String name, String version, String comment) {
    super();
    this.name = name;
    this.version = version;
    this.comment = comment;
  }

  /**
   * @return the name (e.g. "Firefox", "Mozilla", etc.).
   */
  public String getName() {

    return this.name;
  }

  /**
   * @return the version (e.g. "50", "5.0", "9A334", etc.)
   */
  public String getVersion() {

    return this.version;
  }

  /**
   * @return the optional comment or {@code null} if not present.
   */
  public String getComment() {

    return this.comment;
  }

  /**
   * @param newVersion the new {@link #getVersion() version}.
   * @return a copy of this {@link UserAgentSegment} with the given {@link #getVersion() version}.
   */
  public UserAgentSegment withVersion(String newVersion) {

    if (Objects.equals(this.version, newVersion)) {
      return this;
    }
    return new UserAgentSegment(this.name, newVersion, this.comment);
  }

  /**
   * @param newName the new {@link #getName() name}.
   * @return a copy of this {@link UserAgentSegment} with the given {@link #getName() name}.
   */
  public UserAgentSegment withName(String newName) {

    if (Objects.equals(this.name, newName)) {
      return this;
    }
    return new UserAgentSegment(newName, this.version, this.comment);
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != UserAgentSegment.class) {
      return false;
    }
    UserAgentSegment other = (UserAgentSegment) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.version, other.version)) {
      return false;
    }
    if (!Objects.equals(this.comment, other.comment)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {

    return Objects.hash(this.name, this.version, this.comment);
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder(this.name);
    buffer.append('/');
    buffer.append(this.version);
    if (this.comment != null) {
      buffer.append(" (");
      buffer.append(this.comment);
      buffer.append(')');
    }
    return buffer.toString();
  }

  /**
   * @param userAgent the user agent string (see {@link HttpHeaderUserAgent}).
   * @return the parsed {@link List} of {@link UserAgentSegment}s.
   */
  public static List<UserAgentSegment> ofUserAgent(String userAgent) {

    List<UserAgentSegment> segments = new ArrayList<>();
    Matcher matcher = PATTERN.matcher(userAgent);
    while (matcher.find()) {
      UserAgentSegment segment = toSegment(matcher);
      segments.add(segment);
    }
    return segments;
  }

  private static UserAgentSegment toSegment(Matcher matcher) {

    String name = matcher.group(1);
    String version = matcher.group(2);
    String comment = null;
    if (matcher.groupCount() == 4) {
      comment = matcher.group(4);
    }
    UserAgentSegment segment = new UserAgentSegment(name, version, comment);
    return segment;
  }

  static UserAgentSegment first(CharSequence string) {

    Matcher matcher = PATTERN.matcher(string);
    if (matcher.find()) {
      return toSegment(matcher);
    }
    return null;
  }

}
