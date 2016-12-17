/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.api;

import java.util.regex.Pattern;

/**
 * Extends {@link NameVersion} with an optional {@link #getComment() comment}. In its {@link #toString() string
 * representation} the comment is enclosed in brances (if present). This form is used e.g. for segments of a
 * <em>User-Agent</em> HTTP header.
 *
 * @author hohwille
 * @since 7.4.0
 */
public interface NameVersionComment extends NameVersion {

  /** The regular expression {@link String} for the {@link #getComment()}. */
  String COMMENT_REGEX = "([(]([^)]+)[)])";

  /** The regular expression {@link Pattern} for the {@link #getComment()}. */
  Pattern COMMENT_PATTERN = Pattern.compile(COMMENT_REGEX);

  /** The regular expression {@link String} for a {@link NameVersionComment}. */
  String NAME_VERSION_COMMENT_REGEX = NAME_VERSION_REGEX + "[ ]*" + COMMENT_REGEX + "?";

  /** The regular expression {@link Pattern} for a {@link NameVersionComment}. */
  Pattern NAME_VERSION_COMMENT_PATTERN = Pattern.compile(NAME_VERSION_COMMENT_REGEX);

  /**
   * @return the optional comment or {@code null} if not present.
   */
  String getComment();

  /**
   * Gets the {@link String} representation of this object as described by the returns comment. If the
   * {@link #getComment() comment} is not present the form of {@link NameVersion#toString()} is returned.
   *
   * @return the string representation in the form "{@link #getName() name}/{@link #getVersion() version}
   *         ({@link #getComment() comment})" (without quotes).
   */
  @Override
  String toString();

}
