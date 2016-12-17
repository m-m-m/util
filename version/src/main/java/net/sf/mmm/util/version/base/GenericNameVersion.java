/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

import net.sf.mmm.util.version.api.NameVersion;

/**
 * This is a generic implementation of {@link NameVersion}.
 *
 * @author hohwille
 * @since 7.4.0
 */
public class GenericNameVersion extends AbstractNameVersion {

  private final String name;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param version the {@link #getVersion() version}.
   */
  public GenericNameVersion(String name, String version) {
    super(version);
    Objects.requireNonNull(name, "name");
    assert (!name.contains("/")) : name;
    this.name = name;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public GenericNameVersion withVersion(String newVersion) {

    if (Objects.equals(getVersion(), newVersion)) {
      return this;
    }
    return new GenericNameVersion(this.name, newVersion);
  }

  /**
   * @param newName the new {@link #getName() name}.
   * @return a copy of this object with the given {@link #getName() name}.
   */
  public GenericNameVersion withName(String newName) {

    if (Objects.equals(this.name, newName)) {
      return this;
    }
    return new GenericNameVersion(newName, getVersion());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getVersion(), this.name);
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    GenericNameVersion other = (GenericNameVersion) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return true;
  }

  /**
   * @param string the string to parse.
   * @return the parsed {@link List} of {@link GenericNameVersion}s.
   */
  public static List<? extends GenericNameVersion> ofAll(CharSequence string) {

    List<GenericNameVersion> segments = new ArrayList<>();
    Matcher matcher = NAME_VERSION_PATTERN.matcher(string);
    while (matcher.find()) {
      GenericNameVersion segment = ofMatcher(matcher);
      segments.add(segment);
    }
    return segments;
  }

  /**
   * @param string the string to parse. Should be a {@link #toString() string representation} of {@link NameVersion}.
   * @return the parsed {@link GenericNameVersion}.
   */
  public static GenericNameVersion of(CharSequence string) {

    Matcher matcher = NAME_VERSION_PATTERN.matcher(string);
    while (matcher.matches()) {
      return ofMatcher(matcher);
    }
    return null;
  }

  /**
   * @param string the string to parse.
   * @return the first {@link GenericNameVersion} that was found or {@code null} in none was found.
   */
  public static GenericNameVersion ofFirst(CharSequence string) {

    Matcher matcher = NAME_VERSION_PATTERN.matcher(string);
    if (matcher.find()) {
      return ofMatcher(matcher);
    }
    return null;
  }

  /**
   * @param matcher the {@link Matcher} from {@link #NAME_VERSION_PATTERN} that {@link Matcher#matches() matched} or
   *        {@link Matcher#find() found} a hit.
   * @return the {@link GenericNameVersion} instance.
   */
  public static GenericNameVersion ofMatcher(Matcher matcher) {

    String name = matcher.group(1);
    String version = matcher.group(2);
    return new GenericNameVersion(name, version);
  }

}
