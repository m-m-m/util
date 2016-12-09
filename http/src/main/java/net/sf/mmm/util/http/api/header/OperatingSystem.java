/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The {@link OperatingSystem} information for {@link HttpHeaderUserAgent#getOs()}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class OperatingSystem {

  /** The {@link #getFamily() OS family} <em>Android</em>. */
  public static final String FAMILY_ANDROID = "Android";

  /** The {@link #getFamily() OS family} <em>Linux</em>. */
  public static final String FAMILY_LINUX = "Linux";

  /** The {@link #getFamily() OS family} <em>BSD</em> (Berkeley Software Distribution). */
  public static final String FAMILY_BSD = "BSD";

  /** The {@link #getFamily() OS family} <em>Windows</em>. */
  public static final String FAMILY_WINDOWS = "Windows";

  /** The {@link #getFamily() OS family} <em>Windows Phone</em>. */
  public static final String FAMILY_WINDOWS_PHONE = "Windows Phone";

  /** The {@link #getFamily() OS family} <em>Mac OS</em> (Apple Macintosh). */
  public static final String FAMILY_MAC_OS = "Mac OS";

  /** The {@link #getFamily() OS family} <em>iOS</em> (Apple Mobile OS for iPhone, iPad, etc.). */
  public static final String FAMILY_IOS = "iOS";

  /** The {@link #getFamily() OS family} <em>tvOS</em> (for AppleTV). */
  public static final String FAMILY_TV_OS = "tvOS";

  /** The {@link #getFamily() OS family} <em>Xbox</em>. */
  public static final String FAMILY_XBOX = "Xbox";

  /** The {@link #getFamily() OS family} <em>Nintendo</em>. */
  public static final String FAMILY_NINTENDO = "Nintendo";

  /** The {@link #getFamily() OS family} <em>PlayStation</em>. */
  public static final String FAMILY_PLAYSTATION = "PlayStation";

  /** The {@link #getFamily() OS family} <em>Fire OS</em> (from Amazon for Kindle, Fire Phone, or Fire TV). */
  public static final String FAMILY_KINDLE = "Fire OS";

  /** The {@link #getFamily() OS family} <em>Bada</em> (from Samsung Electronics for Smartphone). */
  public static final String FAMILY_BADA = "Bada";

  /** The {@link #getFamily() OS family} <em>AIX</em> (Advanced Interactive eXecutive). */
  public static final String FAMILY_AIX = "AIX";

  /** The {@link #getFamily() OS family} <em>Sailfish</em> (Linux based mobile OS). */
  public static final String FAMILY_SAILFISH = "Sailfish";

  /** The {@link #getFamily() OS family} <em>XMB</em> (XrossMediaBar). */
  public static final String FAMILY_XMB = "XMB";

  /** The {@link #getFamily() OS family} <em>Bot</em> for a bot or crawler. */
  public static final String FAMILY_BOT = "Bot";

  /** The {@link #getVariant() variant} for <em>Ubuntu</em> {@link #FAMILY_LINUX Linux}. */
  public static final String VARIANT_UBUNTU = "Ubuntu";

  /** The {@link #getVariant() variant} for <em>SUSE</em> {@link #FAMILY_LINUX Linux}. */
  public static final String VARIANT_SUSE = "SUSE";

  /** The {@link #getVariant() variant} for <em>Debian</em> {@link #FAMILY_LINUX Linux}. */
  public static final String VARIANT_DEBIAN = "Debian";

  /** The {@link #getVariant() variant} for <em>Gentoo</em> {@link #FAMILY_LINUX Linux}. */
  public static final String VARIANT_GENTOO = "Gentoo";

  /** The {@link #getVariant() variant} for <em>RedHat</em> {@link #FAMILY_LINUX Linux}. */
  public static final String VARIANT_REDHAT = "Red Hat";

  /**
   * The value for segments such as {@link #getFamily() OS family} or {@link #getVersion() version} if they are unknown.
   */
  public static final String UNKNOWN = "unknown";

  private static final List<String> ARCHITECTURES = Arrays.asList("x86_64", "x64", "x86", "wow64", "win64", "ppc", "arm");

  private static final List<String> VARIANTS = Arrays.asList(VARIANT_UBUNTU, VARIANT_DEBIAN, VARIANT_REDHAT, VARIANT_SUSE, VARIANT_GENTOO);

  private final String spec;

  private String family;

  private String variant;

  private String version;

  private String architecture;

  private String locale;

  /**
   * The constructor.
   *
   * @param spec the {@link #getSpec() OS specification}.
   */
  public OperatingSystem(String spec) {
    super();
    this.spec = spec;
    this.family = UNKNOWN;
    this.variant = UNKNOWN;
    this.version = UNKNOWN;
    this.architecture = UNKNOWN;
    this.locale = UNKNOWN;
    String[] tokens = spec.split(";");
    for (String token : tokens) {
      String tokenLower = token.toLowerCase(Locale.US);
      detectArchitecture(token, tokenLower);
      detectOs(token, tokenLower);
    }
  }

  /**
   * TODO: javadoc
   *
   * @param token
   * @param tokenLower
   */
  private void detectOs(String token, String tokenLower) {

    // for (String osFam : FAMILIES.keySet()) {
    // int familyIndex = tokenLower.indexOf(osFam);
    // if (familyIndex >= 0) {
    // String fam = FAMILIES.get(osFam);
    // boolean familyDetected = detectFamily(fam);
    // if (familyDetected) {
    // int familyEndIndex = familyIndex + osFam.length();
    // if (familyEndIndex < token.length()) {
    // char c = token.charAt(familyEndIndex);
    // if ((c == '/') || c == ' ') {
    // familyEndIndex++;
    // detectOsDetails(token.substring(familyEndIndex));
    // }
    // }
    // }
    // }
    // }
  }

  private void detectOsDetails(String details) {

    String[] segments = details.split(" ");
    if (segments.length == 0) {
      return;
    } else if (segments.length == 1) {

    } else {

    }
  }

  private void detectArchitecture(String token, String tokenLower) {

    for (String arch : ARCHITECTURES) {
      int archIndex = tokenLower.indexOf(arch);
      if (archIndex >= 0) {
        this.architecture = token.substring(archIndex, archIndex + arch.length());
        return;
      }
    }
  }

  /**
   * @param fam
   * @return
   */
  private boolean detectFamily(String osFamily) {

    boolean update;
    if (this.family == null) {
      update = false;
    } else if (this.family.startsWith(osFamily)) {
      update = false;
    } else if (osFamily.startsWith(this.family)) {
      update = true;
    } else if (FAMILY_XBOX.equals(osFamily)) {
      update = true;
    } else if (FAMILY_ANDROID.equals(osFamily)) {
      update = true;
    } else {
      update = false;
    }
    if (update) {
      this.family = osFamily;
      return true;
    } else {
      return false;
    }
  }

  /**
   * @return the list of OS {@link #getFamily() families}.
   */
  private static Map<String, String> createOsFamilies() {

    String[] families = new String[] { FAMILY_ANDROID, FAMILY_LINUX, FAMILY_BSD, FAMILY_MAC_OS, FAMILY_WINDOWS, FAMILY_IOS, FAMILY_WINDOWS_PHONE };
    int size = families.length;
    Map<String, String> map = new HashMap<>(size);
    for (int i = 0; i < size; i++) {
      String key = families[i].toLowerCase(Locale.US);
      map.put(key, families[i]);
    }
    return map;
  }

  /**
   * The constructor.
   *
   * @param spec - see {@link #getSpec()}.
   * @param family - see {@link #getFamily()}.
   * @param variant - see {@link #getVariant()}.
   * @param version - see {@link #getVersion()}.
   * @param architecture - see {@link #getArchitecture()}.
   * @param locale - see {@link #getLocale()}.
   */
  public OperatingSystem(String spec, String family, String variant, String version, String architecture, String locale) {
    super();
    this.spec = spec;
    this.family = family;
    this.variant = variant;
    this.version = version;
    this.architecture = architecture;
    this.locale = locale;
  }

  public static OperatingSystem of(String family, String variant, String version, String architecture, String locale) {

    StringBuilder buffer = new StringBuilder(32);
    return of(appendSegment(family, buffer), appendSegment(variant, buffer), appendSegment(version, buffer), appendSegment(architecture, buffer),
        appendSegment(locale, buffer), buffer.toString());
  }

  private static String appendSegment(String segment, StringBuilder buffer) {

    if ((segment == null) || segment.isEmpty()) {
      return UNKNOWN;
    }
    if (!segment.equals(UNKNOWN)) {
      if (buffer.length() > 0) {
        buffer.append("; ");
      }
      buffer.append(segment);
    }
    return segment;
  }

  public static OperatingSystem of(String family, String variant, String version, String architecture, String locale, String spec) {

    return new OperatingSystem(spec, family, variant, version, architecture, locale);
  }

  /**
   * @param userAgent the full user agent string (see {@link HttpHeaderUserAgent#getValue()}).
   * @return
   */
  static OperatingSystem of(String userAgent) {

    int specStart = userAgent.indexOf('(');
    if (specStart > 0) {
      int specEnd = userAgent.indexOf(')', specStart);
      if (specEnd > 0) {
        return new OperatingSystem(userAgent.substring(specStart + 1, specEnd));
      }
    }
    if (userAgent.startsWith("AppleTV")) {
      return new OperatingSystem("");
    }
    return null;
  }

  /**
   * @return the family
   */
  public String getFamily() {

    return this.family;
  }

  /**
   * @return the variant
   */
  public String getVariant() {

    return this.variant;
  }

  /**
   * @return the version
   */
  public String getVersion() {

    return this.version;
  }

  /**
   * @return the architecture
   */
  public String getArchitecture() {

    return this.architecture;
  }

  /**
   * @return the locale
   */
  public String getLocale() {

    return this.locale;
  }

  /**
   * @return the spec
   */
  public String getSpec() {

    return this.spec;
  }

  @Override
  public String toString() {

    return this.spec;
  }

}
