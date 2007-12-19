/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.impl;

import net.sf.mmm.content.value.api.Version;
import net.sf.mmm.util.BasicUtil;
import net.sf.mmm.util.value.ValueParseException;
import net.sf.mmm.util.value.ValueParseStringException;

/**
 * This is the implementation of the {@link Version} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class VersionImpl implements Version {

  /** the delimiter used in the string representation */
  private static final char SEPARATOR_CHAR = '.';

  /** the delimiter used in the string representation to separate the suffix */
  private static final char SUFFIX_SEPARATOR_CHAR = '-';

  /** the user's comment for this version */
  private final String comment;

  /** the major version number */
  private final int major;

  /** the minor version number */
  private final int minor;

  /** the milli version number */
  private final int milli;

  /** the micro version number */
  private final int micro;

  /** the version suffix */
  private final String suffix;

  /**
   * The constructor.
   * 
   * @param userComment is the comment of the user who created this version.
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * @param milliVersion is the {@link Version#getMilliVersion() milli-version}
   *        number.
   * @param microVersion is the {@link Version#getMicroVersion() micro-version}
   *        number.
   * @param versionSuffix is the suffix appended to the version.
   */
  public VersionImpl(String userComment, int majorVersion, int minorVersion, int milliVersion,
      int microVersion, String versionSuffix) {

    super();
    if (majorVersion < 0) {
      throw new IllegalArgumentException();
    }
    if (minorVersion < -1) {
      throw new IllegalArgumentException();
    }
    if (milliVersion < -1) {
      throw new IllegalArgumentException();
    }
    if (microVersion < -1) {
      throw new IllegalArgumentException();
    }
    this.major = majorVersion;
    this.minor = minorVersion;
    this.milli = milliVersion;
    this.micro = microVersion;
    this.suffix = versionSuffix;
    this.comment = userComment;
  }

  /**
   * The constructor.
   * 
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * @param milliVersion is the {@link Version#getMilliVersion() milli-version}
   *        number.
   * @param microVersion is the {@link Version#getMicroVersion() micro-version}
   *        number.
   * @param versionSuffix is the suffix appended to the version.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion, int milliVersion, int microVersion,
      String versionSuffix) {

    this(null, majorVersion, minorVersion, milliVersion, microVersion, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @param userComment is the comment of the user who created this version.
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * @param milliVersion is the {@link Version#getMilliVersion() milli-version}
   *        number.
   * @param microVersion is the {@link Version#getMicroVersion() micro-version}
   *        number.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion, int minorVersion, int milliVersion,
      int microVersion) {

    this(userComment, majorVersion, minorVersion, milliVersion, microVersion, null);
  }

  /**
   * The constructor.
   * 
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * @param milliVersion is the {@link Version#getMilliVersion() milli-version}
   *        number.
   * @param microVersion is the {@link Version#getMicroVersion() micro-version}
   *        number.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion, int milliVersion, int microVersion) {

    this(null, majorVersion, minorVersion, milliVersion, microVersion, null);
  }

  /**
   * The constructor.
   * 
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * @param milliVersion is the {@link Version#getMilliVersion() milli-version}
   *        number.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion, int milliVersion) {

    this(null, majorVersion, minorVersion, milliVersion, -1, null);
  }

  /**
   * The constructor.
   * 
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * @param milliVersion is the {@link Version#getMilliVersion() milli-version}
   *        number.
   * @param versionSuffix is the suffix appended to the version.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion, int milliVersion, String versionSuffix) {

    this(null, majorVersion, minorVersion, milliVersion, -1, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @param userComment is the comment of the user who created this version.
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * @param milliVersion is the {@link Version#getMilliVersion() milli-version}
   *        number.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion, int minorVersion, int milliVersion) {

    this(userComment, majorVersion, minorVersion, milliVersion, -1, null);
  }

  /**
   * The constructor.
   * 
   * @param userComment is the comment of the user who created this version.
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * @param milliVersion is the {@link Version#getMilliVersion() milli-version}
   *        number.
   * @param versionSuffix is the suffix appended to the version.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion, int minorVersion, int milliVersion,
      String versionSuffix) {

    this(userComment, majorVersion, minorVersion, milliVersion, -1, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion) {

    this(null, majorVersion, minorVersion, -1, -1, null);
  }

  /**
   * The constructor.
   * 
   * @param userComment is the comment of the user who created this version.
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion, int minorVersion) {

    this(userComment, majorVersion, minorVersion, -1, -1, null);
  }

  /**
   * The constructor.
   * 
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * @param versionSuffix is the suffix appended to the version.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion, String versionSuffix) {

    this(null, majorVersion, minorVersion, -1, -1, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @param userComment is the comment of the user who created this version.
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param minorVersion is the {@link Version#getMinorVersion() minor-version}
   *        number
   * @param versionSuffix is the suffix appended to the version.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion, int minorVersion, String versionSuffix) {

    this(userComment, majorVersion, minorVersion, -1, -1, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion) {

    this(null, majorVersion, -1, -1, -1, null);
  }

  /**
   * The constructor.
   * 
   * @param userComment is the comment of the user who created this version.
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion) {

    this(userComment, majorVersion, -1, -1, -1, null);
  }

  /**
   * The constructor.
   * 
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param versionSuffix is the suffix appended to the version.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, String versionSuffix) {

    this(null, majorVersion, -1, -1, -1, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @param userComment is the comment of the user who created this version.
   * @param majorVersion is the {@link Version#getMajorVersion() major-version}
   *        number.
   * @param versionSuffix is the suffix appended to the version.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion, String versionSuffix) {

    this(userComment, majorVersion, -1, -1, -1, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @param versionAsString is the version as String as retrieved by
   *        {@link Version#toString()}.
   * @throws ValueParseException if the given string is invalid and could not be
   *         parsed.
   */
  public VersionImpl(String versionAsString) throws ValueParseException {

    super();
    int majorVersion = 0;
    int minorVersion = -1;
    int milliVersion = -1;
    int microVersion = -1;
    String versionSuffix = null;

    // StringTokenizer is in-acceptable, because it hides multiple
    // occurrences of the delimiter if return token is set to false.
    StringBuffer token = new StringBuffer();
    int end = versionAsString.length() - 1;
    int pos = 0;
    for (int i = 0; i <= end; i++) {
      char c = versionAsString.charAt(i);
      if (i == end) {
        token.append(c);
        c = SEPARATOR_CHAR;
      } else if (c == SUFFIX_SEPARATOR_CHAR) {
        versionSuffix = versionAsString.substring(i + 1);
        i = end;
        c = SEPARATOR_CHAR;
      }
      if (c == SEPARATOR_CHAR) {
        String s = token.toString();
        token.setLength(0);
        try {
          if (pos == 0) {
            majorVersion = Integer.parseInt(s);
          } else if (pos == 1) {
            minorVersion = Integer.parseInt(s);
          } else if (pos == 2) {
            milliVersion = Integer.parseInt(s);
          } else if (pos == 3) {
            microVersion = Integer.parseInt(s);
          }
        } catch (NumberFormatException e) {
          throw new ValueParseStringException(versionAsString, VersionImpl.class, VALUE_NAME, e);
        }
        pos++;
      } else {
        token.append(c);
      }
    }

    this.major = majorVersion;
    this.minor = minorVersion;
    this.milli = milliVersion;
    this.micro = microVersion;
    this.suffix = versionSuffix;
    this.comment = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other) {

    if ((other != null) && (other instanceof Version)) {
      Version otherVersion = (Version) other;
      if (getMajorVersion() == otherVersion.getMajorVersion()
          && getMinorVersion() == otherVersion.getMinorVersion()
          && getMicroVersion() == otherVersion.getMicroVersion()) {
        return (BasicUtil.getInstance()
            .isEqual(getVersionSuffix(), otherVersion.getVersionSuffix()));
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    int hash = 0;
    if (this.suffix != null) {
      hash = (hash * 31) + this.suffix.hashCode();
    }
    hash = (hash * 31) + getMicroVersion();
    hash = (hash * 31) + getMilliVersion();
    hash = (hash * 31) + getMinorVersion();
    hash = (hash * 31) + getMajorVersion();
    return hash;
  }

  /**
   * {@inheritDoc}
   */
  public int getMajorVersion() {

    return this.major;
  }

  /**
   * {@inheritDoc}
   */
  public int getMinorVersion() {

    if (this.minor == -1) {
      return 0;
    }
    return this.minor;
  }

  /**
   * {@inheritDoc}
   */
  public int getMilliVersion() {

    if (this.milli == -1) {
      return 0;
    }
    return this.milli;
  }

  /**
   * {@inheritDoc}
   */
  public int getMicroVersion() {

    if (this.micro == -1) {
      return 0;
    }
    return this.micro;
  }

  /**
   * This method gets the version suffix.
   * 
   * @return the suffix.
   */
  public String getVersionSuffix() {

    return this.suffix;
  }

  /**
   * {@inheritDoc}
   */
  public String getComment() {

    return this.comment;
  }

  /**
   * This method gets the rating for the given <code>suffix</code>.
   * 
   * @param suffix is the {@link #getVersionSuffix() version suffix}. It may be
   *        <code>null</code>.
   * @return the rating of the given suffix. If two versions are equal except
   *         for their suffix, the version with the higher
   *         {@link #getSuffixRating(String) suffix-rating} is
   *         {@link #isHigherThan(Version) higher} than the other.
   */
  protected static int getSuffixRating(String suffix) {

    if (suffix == null) {
      return 6;
    }
    String lowerSuffix = suffix.toLowerCase();
    if (SUFFIX_ALPHA.equals(lowerSuffix)) {
      return 0;
    } else if (SUFFIX_BETA.equals(lowerSuffix)) {
      return 1;
    } else if (SUFFIX_GAMMA.equals(lowerSuffix)) {
      return 2;
    } else if (lowerSuffix.startsWith(SUFFIX_PRE_RELEASE)) {
      return 3;
    } else if (lowerSuffix.startsWith(SUFFIX_RELEASE_CANDIDATE)) {
      return 4;
    } else {
      return 5;
    }
  }

  /**
   * 
   * @param higherSuffix
   * @param lowerSuffix
   * @return
   */
  protected static boolean isHigher(String higherSuffix, String lowerSuffix) {

    if (higherSuffix == null) {
      return (lowerSuffix != null);
    }
    int higherRating = getSuffixRating(higherSuffix);
    int lowerRating = getSuffixRating(lowerSuffix);
    if (higherRating == lowerRating) {
      return (higherSuffix.compareToIgnoreCase(lowerSuffix) > 0);
    } else {
      return (higherRating > lowerRating);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isHigherThan(Version other) {

    if (getMajorVersion() == other.getMajorVersion()) {
      if (getMinorVersion() == other.getMinorVersion()) {
        if (getMilliVersion() == other.getMilliVersion()) {
          if (getMicroVersion() == other.getMicroVersion()) {
            return isHigher(getVersionSuffix(), other.getVersionSuffix());
          } else {
            return (getMicroVersion() > other.getMicroVersion());
          }
        } else {
          return (getMilliVersion() > other.getMilliVersion());
        }
      } else {
        return (getMinorVersion() > other.getMinorVersion());
      }
    } else {
      return (getMajorVersion() > other.getMajorVersion());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuffer result = new StringBuffer();
    result.append(this.major);
    if (this.minor != -1) {
      result.append(SEPARATOR_CHAR);
      result.append(this.minor);
      if (this.milli != -1) {
        result.append(SEPARATOR_CHAR);
        result.append(this.milli);
        if (this.micro != -1) {
          result.append(SEPARATOR_CHAR);
          result.append(this.micro);
        }
      }
    }
    if (this.suffix != null) {
      result.append(SUFFIX_SEPARATOR_CHAR);
      result.append(this.suffix);
    }
    return result.toString();
  }

}
