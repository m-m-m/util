/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

import net.sf.mmm.util.version.api.NameVersionComment;

/**
 * This is a generic implementation of {@link NameVersionComment}.
 *
 * @author hohwille
 * @since 7.4.0
 */
public class GenericNameVersionComment extends GenericNameVersion implements NameVersionComment {

  private final String comment;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param version the {@link #getVersion() version}.
   * @param comment the {@link #getComment() comment}.
   */
  public GenericNameVersionComment(String name, String version, String comment) {
    super(name, version);
    this.comment = comment;
  }

  @Override
  public String getComment() {

    return this.comment;
  }

  @Override
  public int hashCode() {

    return Objects.hash(getName(), getVersion(), this.comment);
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    GenericNameVersionComment other = (GenericNameVersionComment) obj;
    if (!Objects.equals(this.comment, other.comment)) {
      return false;
    }
    return true;
  }

  @Override
  public GenericNameVersionComment withVersion(String newVersion) {

    if (Objects.equals(getVersion(), newVersion)) {
      return this;
    }
    return new GenericNameVersionComment(getName(), newVersion, this.comment);
  }

  @Override
  public GenericNameVersionComment withName(String newName) {

    if (Objects.equals(getName(), newName)) {
      return this;
    }
    return new GenericNameVersionComment(newName, getVersion(), this.comment);
  }

  /**
   * @param newComment the new {@link #getComment() comment}.
   * @return a copy of this object with the given {@link #getComment() comment}.
   */
  public GenericNameVersionComment withComment(String newComment) {

    if (Objects.equals(this.comment, newComment)) {
      return this;
    }
    return new GenericNameVersionComment(getName(), getVersion(), newComment);
  }

  @Override
  protected void doWrite(Appendable appendable, boolean fromToString) throws IOException {

    super.doWrite(appendable, fromToString);
    if (this.comment != null) {
      appendable.append(" (");
      appendable.append(this.comment);
      appendable.append(')');
    }
  }

  /**
   * @param string the string to parse. Should be a {@link #toString() string representation} of
   *        {@link NameVersionComment}.
   * @return the parsed {@link GenericNameVersionComment}.
   */
  public static GenericNameVersionComment of(CharSequence string) {

    Matcher matcher = NAME_VERSION_COMMENT_PATTERN.matcher(string);
    while (matcher.matches()) {
      return ofMatcher(matcher);
    }
    return null;
  }

  /**
   * @param string the string to parse.
   * @return the parsed {@link List} of {@link GenericNameVersionComment}s.
   */
  public static List<GenericNameVersionComment> ofAll(CharSequence string) {

    List<GenericNameVersionComment> segments = new ArrayList<>();
    Matcher matcher = NAME_VERSION_COMMENT_PATTERN.matcher(string);
    while (matcher.find()) {
      GenericNameVersionComment segment = ofMatcher(matcher);
      segments.add(segment);
    }
    return segments;
  }

  /**
   * @param string the string to parse.
   * @return the first {@link GenericNameVersionComment} that was found or {@code null} in none was found.
   */
  public static GenericNameVersionComment ofFirst(CharSequence string) {

    Matcher matcher = NAME_VERSION_COMMENT_PATTERN.matcher(string);
    if (matcher.find()) {
      return ofMatcher(matcher);
    }
    return null;
  }

  /**
   * @param matcher the {@link Matcher} from {@link #NAME_VERSION_COMMENT_PATTERN} that {@link Matcher#matches()
   *        matched} or {@link Matcher#find() found} a hit.
   * @return the {@link GenericNameVersionComment} instance.
   */
  public static GenericNameVersionComment ofMatcher(Matcher matcher) {

    String name = matcher.group(1).trim();
    String version = matcher.group(2);
    String comment = null;
    if (matcher.groupCount() >= 4) {
      comment = matcher.group(4);
    }
    return new GenericNameVersionComment(name, version, comment);
  }

}
