/* $Id$ */
package net.sf.mmm.content.value.impl;

import net.sf.mmm.content.value.api.VersionIF;
import net.sf.mmm.util.BasicUtil;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueParseStringException;
import net.sf.mmm.value.base.AbstractValueManager;

/**
 * This class implements a unique id value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class VersionImpl implements VersionIF {

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
   * @param userComment
   *        is the comment of the user who created this version.
   * @param majorVersion
   *        is the {@link VersionIF#getMajorVersion() major-version} number.
   * @param minorVersion
   *        is the {@link VersionIF#getMinorVersion() minor-version} number
   * @param milliVersion
   *        is the {@link VersionIF#getMilliVersion() milli-version} number.
   * @param microVersion
   *        is the {@link VersionIF#getMicroVersion() micro-version} number.
   * @param versionSuffix
   *        is the suffix appended to the version.
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
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion, int milliVersion, int microVersion,
      String versionSuffix) {

    this(null, majorVersion, minorVersion, milliVersion, microVersion, versionSuffix);
  }

  /**
   * The constructor.
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
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion, int milliVersion, int microVersion) {

    this(null, majorVersion, minorVersion, milliVersion, microVersion, null);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion, int milliVersion) {

    this(null, majorVersion, minorVersion, milliVersion, -1, null);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion, int milliVersion, String versionSuffix) {

    this(null, majorVersion, minorVersion, milliVersion, -1, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion, int minorVersion, int microVersion) {

    this(userComment, majorVersion, minorVersion, -1, microVersion, null);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion, int minorVersion, int microVersion,
      String versionSuffix) {

    this(userComment, majorVersion, minorVersion, -1, microVersion, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion) {

    this(null, majorVersion, minorVersion, -1, -1, null);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion, int minorVersion) {

    this(userComment, majorVersion, minorVersion, -1, -1, null);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, int minorVersion, String versionSuffix) {

    this(null, majorVersion, minorVersion, -1, -1, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion, int minorVersion, String versionSuffix) {

    this(userComment, majorVersion, minorVersion, -1, -1, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion) {

    this(null, majorVersion, -1, -1, -1, null);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion) {

    this(userComment, majorVersion, -1, -1, -1, null);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(int majorVersion, String versionSuffix) {

    this(null, majorVersion, -1, -1, -1, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @see #VersionImpl(String, int, int, int, int, String)
   */
  public VersionImpl(String userComment, int majorVersion, String versionSuffix) {

    this(userComment, majorVersion, -1, -1, -1, versionSuffix);
  }

  /**
   * The constructor.
   * 
   * @param versionAsString
   *        is the version as String as retrieved by
   *        {@link VersionIF#toString()}.
   * @throws ValueParseException
   *         if the given string is invalid and could not be parsed.
   */
  public VersionImpl(String versionAsString) throws ValueParseException {

    super();
    int majorVersion = 0;
    int minorVersion = -1;
    int milliVersion = -1;
    int microVersion = -1;
    String versionSuffix = null;

    // StringTokenizer is inacceptable, because it hides multiple
    // occurences of the delimiter if return token is set to false.
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
          throw new ValueParseStringException(versionAsString, IdImpl.class, VALUE_NAME, e);
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
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object other) {

    if ((other != null) && (other instanceof VersionIF)) {
      VersionIF otherVersion = (VersionIF) other;
      if (getMajorVersion() == otherVersion.getMajorVersion()
          && getMinorVersion() == otherVersion.getMinorVersion()
          && getMicroVersion() == otherVersion.getMicroVersion()) {
        return (BasicUtil.equals(getVersionSuffix(), otherVersion.getVersionSuffix()));
      }
    }
    return false;
  }

  /**
   * @see java.lang.Object#hashCode()
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
   * @see net.sf.mmm.content.value.api.VersionIF#getMajorVersion()
   */
  public int getMajorVersion() {

    return this.major;
  }

  /**
   * @see net.sf.mmm.content.value.api.VersionIF#getMinorVersion()
   */
  public int getMinorVersion() {

    if (this.minor == -1) {
      return 0;
    }
    return this.minor;
  }

  /**
   * @see net.sf.mmm.content.value.api.VersionIF#getMilliVersion()
   */
  public int getMilliVersion() {

    if (this.milli == -1) {
      return 0;
    }
    return this.milli;
  }

  /**
   * @see net.sf.mmm.content.value.api.VersionIF#getMicroVersion()
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
   * @see net.sf.mmm.content.value.api.VersionIF#getComment()
   */
  public String getComment() {

    return this.comment;
  }

  /**
   * 
   * @param suffix
   * @return
   */
  public static int getSuffixRating(String suffix) {

    if (suffix == null) {
      return 6;
    }
    String lowerSuffix = suffix.toLowerCase();
    if (SUFFIX_ALPHA.equals(suffix)) {
      return 0;
    } else if (SUFFIX_BETA.equals(suffix)) {
      return 1;
    } else if (SUFFIX_GAMMA.equals(suffix)) {
      return 2;
    } else if (suffix.startsWith(SUFFIX_PRE_RELEASE)) {
      return 3;
    } else if (suffix.startsWith(SUFFIX_RELEASE_CANDIDATE)) {
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
  public static boolean isHigher(String higherSuffix, String lowerSuffix) {

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
   * @see net.sf.mmm.content.api.value.VersionIF#isHigherThan(net.sf.mmm.content.api.value.VersionIF)
   */
  public boolean isHigherThan(VersionIF other) {

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
   * @see VersionIF#toString()
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

  /**
   * This inner class is the manager for the value.
   */
  public static class Manager extends AbstractValueManager<VersionImpl> {

    /**
     * The constructor.
     */
    public Manager() {

      super();
    }

    /**
     * @see net.sf.mmm.value.api.ValueManager#getName()
     */
    public String getName() {

      return VALUE_NAME;
    }

    /**
     * @see net.sf.mmm.value.api.ValueManager#parse(java.lang.String)
     */
    public VersionImpl parse(String valueAsString) throws ValueParseException {

      return new VersionImpl(valueAsString);
    }

    /**
     * @see net.sf.mmm.value.api.ValueManager#getValueType()
     */
    public Class<VersionImpl> getValueType() {

      return VersionImpl.class;
    }

  }

}
