/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.lang.api.SystemInformation;
import net.sf.mmm.util.lang.base.SystemInformationImpl;
import net.sf.mmm.util.version.api.NameVersionComment;
import net.sf.mmm.util.version.base.GenericNameVersion;
import net.sf.mmm.util.version.base.GenericNameVersionComment;

/**
 * This class represents the HTTP {@link #HEADER_PRAGMA Pragma} header according to
 * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.32">RFC 2616 (section 14.32)</a>. It is used
 * to control the caching of data.
 *
 * @see HttpHeaderCacheControl
 *
 * @author hohwille
 * @since 8.5.0
 */
public class HttpHeaderUserAgent extends AbstractHttpHeader implements HttpRequestHeader {

  /** An empty instance of {@link HttpHeaderUserAgent} that acts as factory. */
  public static final HttpHeaderUserAgent FACTORY = new HttpHeaderUserAgent();

  /** The {@link #getName() name} of this {@link HttpHeader}. */
  public static final String HEADER = HttpRequestHeader.HEADER_USER_AGENT;

  /** The {@link #getBrowser() browser} <em>Firefox</em>. */
  public static final String BROWSER_FIREFOX = "Firefox";

  /** The {@link #getBrowser() browser} <em>Chrome</em>. */
  public static final String BROWSER_CHROME = "Chrome";

  /**
   * The {@link #getBrowser() browser} <em>Internet Explorer</em>.
   *
   * @see #BROWSER_EDGE
   * @see #BROWSER_INTERNET_EXPLORER_MOBILE
   */
  public static final String BROWSER_INTERNET_EXPLORER = "Internet Explorer";

  /** The {@link #getBrowser() browser} <em>Internet Explorer</em>. */
  public static final String BROWSER_INTERNET_EXPLORER_MOBILE = "IEMobile";

  /**
   * The {@link #getBrowser() browser} <em>Edge</em> (new browser from Microsoft, successor of
   * {@link #BROWSER_INTERNET_EXPLORER IE}).
   */
  public static final String BROWSER_EDGE = "Edge";

  /** The {@link #getBrowser() browser} <em>Safari</em>. */
  public static final String BROWSER_SAFARI = "Safari";

  /** The {@link #getBrowser() browser} <em>Opera</em>. */
  public static final String BROWSER_OPERA = "Opera";

  /** The {@link #getBrowser() browser} <em>Chromium</em>. */
  public static final String BROWSER_CHROMIUM = "Chromium";

  /** The {@link #getBrowser() browser} <em>Epiphany</em>. */
  public static final String BROWSER_EPIPHANY = "Epiphany";

  /** The {@link #getBrowser() browser} <em>Iceweasel</em>. */
  public static final String BROWSER_ICEWEASEL = "Iceweasel";

  /** The {@link #getBrowser() browser} <em>Maxthon</em>. */
  public static final String BROWSER_MAXTHON = "Maxthon";

  /** The {@link #getBrowser() browser} <em>Conkeror</em>. */
  public static final String BROWSER_KONQUEROR = "Konqueror";

  /** The {@link #getBrowser() browser} <em>Kindle</em> (Amazon eBook reader). */
  public static final String BROWSER_KINDLE = "Kindle";

  /** The "{@link #getBrowser() browser}" for an unknown <em>{@link #isCrawler() craweler}</em>. */
  public static final String BROWSER_CRAWLER = "Crawler";

  /**
   * The value for segments such as {@link #getBrowser()}, {@link #getBrowserVersion()} or properties of the
   * {@link #getOs() Operating System} if they are unknown.
   */
  public static final String UNKNOWN = SystemInformation.UNKNOWN;

  private static final Logger LOG = LoggerFactory.getLogger(HttpHeaderUserAgent.class);

  private static final Set<String> BROWSER_BLACKLIST = new HashSet<>(
      Arrays.asList("mozilla", "gecko", "khtml", "vendorid", "profile", "configuration", "version"));

  private static final Map<String, String> BROWSERS = createBrowserMap();

  private static final Map<String, String> ARCHITECTURES = createArchitectureMap();

  private static final Map<String, String> SYSTEM_TYPES = createSystemTypes();

  private static final Map<String, String> LINUX_VARIANTS = createLinuxVariants();

  private static final Set<String> CRAWLER_WHITELIST = new HashSet<>(Arrays.asList("mj12bot", "googlebot", "adsbot-google-mobile", "bingbot", "bingpreview",
      "yahoo! slurp", "simplepie", "sitelockspider", "curl", "okhttp", "blexbot", "yandexbot", "scoutjet", "ips-agent"));

  private static final Pattern PATTERN_VERSION = Pattern.compile("[0-9]+(.[0-9]+)*");

  private static final GenericNameVersionComment OS_UNKNOWN = new GenericNameVersionComment(UNKNOWN, UNKNOWN, UNKNOWN);

  private static final String IEMOBILE = "iemobile";

  private static final String MOZILLA = "Mozilla";

  private static final String CRAWLER_INDICATOR = "compatible;";

  private List<GenericNameVersionComment> segments;

  private String browser;

  private String browserVersion;

  private SystemInformation os;

  private Boolean crawler;

  /**
   * The internal factory constructor.
   */
  private HttpHeaderUserAgent() {
    super();
  }

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value} of this {@link HttpHeader}.
   */
  private HttpHeaderUserAgent(String value) {
    super(value);
  }

  private static Map<String, String> createBrowserMap() {

    String[] browsers = new String[] { BROWSER_CHROME, BROWSER_CHROMIUM, BROWSER_EDGE, BROWSER_EPIPHANY, BROWSER_FIREFOX, BROWSER_ICEWEASEL, BROWSER_KONQUEROR,
        BROWSER_MAXTHON, BROWSER_OPERA, BROWSER_SAFARI };
    int size = browsers.length;
    Map<String, String> map = new HashMap<>(size + 5);
    map.put("opr", BROWSER_OPERA);
    map.put("msie", BROWSER_INTERNET_EXPLORER);
    map.put(IEMOBILE, BROWSER_INTERNET_EXPLORER);
    map.put("conkeror", BROWSER_KONQUEROR);
    map.put("mobile safari", BROWSER_SAFARI);
    for (int i = 0; i < size; i++) {
      String key = browsers[i].toLowerCase(Locale.US);
      map.put(key, browsers[i]);
    }
    return map;
  }

  private static Map<String, String> createLinuxVariants() {

    String[] osVariants = new String[] { "Ubuntu", "Debian", "SuSE", "Gentoo", "Red Hat", "Fedora", "CentOs", "Raspian", "Bananian", "Mandriva", "SLES" };
    int size = osVariants.length;
    Map<String, String> map = new HashMap<>(size);
    for (int i = 0; i < size; i++) {
      String key = osVariants[i].toLowerCase(Locale.US);
      map.put(key, osVariants[i]);
    }
    return map;
  }

  private static Map<String, String> createArchitectureMap() {

    Map<String, String> map = new HashMap<>();
    map.put("x86_64", "x86_64");
    map.put("x64", "x68_64");
    map.put("amd64", "x86_64");
    map.put("wow64", "x86_64");
    map.put("win64", "x86_64");
    map.put("x68", "x68");
    map.put("i286", "i286");
    map.put("i386", "i386");
    map.put("i486", "i486");
    map.put("i586", "i586");
    map.put("i686", "i686");
    map.put("ppc", "ppc");
    map.put("ppc64", "ppc64");
    map.put("arm", "arm");
    map.put("arm64", "arm64");
    map.put("sparc", "sparc");
    map.put("mips", "mips");
    return map;
  }

  private static Map<String, String> createSystemTypes() {

    String[] systemTypes = new String[] { SystemInformation.SYSTEM_TYPE_ANDROID, SystemInformation.SYSTEM_TYPE_LINUX, SystemInformation.SYSTEM_TYPE_BSD,
        SystemInformation.SYSTEM_TYPE_MAC_OS, SystemInformation.SYSTEM_TYPE_WINDOWS, SystemInformation.SYSTEM_TYPE_IOS,
        SystemInformation.SYSTEM_TYPE_WINDOWS_PHONE, SystemInformation.SYSTEM_TYPE_BADA, SystemInformation.SYSTEM_TYPE_FIRE_OS,
        SystemInformation.SYSTEM_TYPE_NINTENDO, SystemInformation.SYSTEM_TYPE_PLAYSTATION, SystemInformation.SYSTEM_TYPE_SAILFISH,
        SystemInformation.SYSTEM_TYPE_TV_OS, SystemInformation.SYSTEM_TYPE_XBOX };
    int size = systemTypes.length;
    Map<String, String> map = new HashMap<>(size + 5);
    map.put("iphone os", SystemInformation.SYSTEM_TYPE_IOS);
    map.put("rim tablet os", "RIM Tablet OS");
    for (int i = 0; i < size; i++) {
      String key = systemTypes[i].toLowerCase(Locale.US);
      map.put(key, systemTypes[i]);
    }
    return map;
  }

  @Override
  public String getName() {

    return HEADER;
  }

  @Override
  public boolean isRequestHeader() {

    return true;
  }

  @Override
  public boolean isResponseHeader() {

    return false;
  }

  /**
   * @return {@code true} if this {@link #getValue() user agent} was detected as a search engine crawler or bot and does
   *         not belong to a regular {@link #getBrowser() browser}, {@code false} otherwise (may still be a crawler but
   *         was not detected). In case of a crawler (result is {@code true}) the {@link #getOs() OS info} will be fake
   *         in most cases.
   */
  public boolean isCrawler() {

    if (this.crawler == null) {
      getBrowser();
      if (this.crawler == null) {
        this.crawler = Boolean.FALSE;
      }
    }
    return this.crawler.booleanValue();
  }

  /**
   * @return the {@link GenericNameVersionComment} segments.
   */
  public List<GenericNameVersionComment> getSegments() {

    if (this.segments == null) {
      this.segments = Collections.unmodifiableList(GenericNameVersionComment.ofAll(getValue()));
    }
    return this.segments;
  }

  /**
   * @return the name or identifier of the browser.
   */
  public String getBrowser() {

    if (this.browser == null) {
      detectOsAndBrowser();
    }
    return this.browser;
  }

  private void detectCrawlerByIndicator(int segmentCount) {

    if (segmentCount > 0) {
      GenericNameVersionComment segment = this.segments.get(0);
      if (segment.getName().equals(MOZILLA)) {
        String comment = segment.getComment();
        if ((comment != null) && (comment.startsWith(CRAWLER_INDICATOR))) {
          String crawlerString = comment.substring(CRAWLER_INDICATOR.length());
          completeCrawlerDetection(crawlerString, -1);
        }
      }
    }
  }

  /**
   * @return the version of the browser (e.g. "50" or "9.0").
   */
  public String getBrowserVersion() {

    if (this.browserVersion == null) {
      detectOsAndBrowser();
    }
    return this.browserVersion;
  }

  /**
   * @return the {@link SystemInformation} of the Operating System (OS) running the browser.
   */
  public SystemInformation getOs() {

    if (this.os == null) {
      detectOsAndBrowser();
    }
    return this.os;
  }

  private void detectOsAndBrowser() {

    assert (this.os == null);
    assert (this.browser == null);
    assert (this.browserVersion == null);
    String userAgent = getValue();
    String userAgentLowerCase = userAgent.toLowerCase(Locale.US);
    GenericNameVersionComment osInfo = detectOs(userAgent, userAgentLowerCase);
    String systemType = osInfo.getComment();
    String systemArchitecture = detectArchitecture(userAgentLowerCase, systemType);
    detectCrawler(userAgent, userAgentLowerCase);
    if (SystemInformation.SYSTEM_TYPE_WINDOWS_PHONE.equals(systemType)) {
      int ieMobileStartIndex = userAgentLowerCase.indexOf(IEMOBILE);
      if (ieMobileStartIndex > 0) {
        this.browser = BROWSER_INTERNET_EXPLORER_MOBILE;
        String ieMobile = userAgent.substring(ieMobileStartIndex);
        GenericNameVersion ieMobileNameVersion = GenericNameVersion.ofFirst(ieMobile);
        if (ieMobileNameVersion == null) {
          this.browserVersion = UNKNOWN;
        } else {
          this.browserVersion = ieMobileNameVersion.getVersion();
        }
      }
    }
    if (this.browser == null) {
      detectBrowser();
    }
    if (isLimitedDevice(userAgentLowerCase)) {
      this.os = new SystemInformationImpl(osInfo.getName(), osInfo.getVersion(), systemArchitecture, systemType, true);
    } else {
      this.os = new SystemInformationImpl(osInfo.getName(), osInfo.getVersion(), systemArchitecture, systemType);
    }
  }

  private void detectCrawler(String userAgent, String userAgentLowerCase) {

    for (String crawlerLowerCase : CRAWLER_WHITELIST) {
      int crawlerIndex = userAgentLowerCase.indexOf(crawlerLowerCase);
      if (crawlerIndex >= 0) {
        completeCrawlerDetection(substring(userAgent, crawlerIndex, '(', ')', ';'), crawlerLowerCase.length());
      }
    }
  }

  private void completeCrawlerDetection(String crawlerString, int length) {

    this.crawler = Boolean.TRUE;
    this.browserVersion = UNKNOWN;
    if (length > 0) {
      this.browser = crawlerString.substring(0, length);
    } else {
      this.browser = BROWSER_CRAWLER;
    }
    GenericNameVersionComment crawlerSegment = GenericNameVersionComment.ofFirst(crawlerString);
    if (crawlerSegment != null) {
      String name = crawlerSegment.getName();
      if (name.startsWith(this.browser)) {
        this.browser = name;
        this.browserVersion = crawlerSegment.getVersion();
      }
    }
  }

  private GenericNameVersionComment detectOs(String userAgent, String userAgentLowerCase) {

    GenericNameVersionComment osInfo = OS_UNKNOWN;
    String osVariant = null;
    for (GenericNameVersionComment segment : getSegments()) {
      String comment = segment.getComment();
      osInfo = detectOsFromComment(osInfo, comment);
      if (osVariant == null) {
        osVariant = LINUX_VARIANTS.get(segment.getName().toLowerCase(Locale.US));
        if ((osVariant != null) && osInfo.getVersion().equals(UNKNOWN)) {
          String osVersion = getVersion(segment);
          if (osVersion != null) {
            osInfo = getOsInfoForOsVariant(osInfo, osVariant, SystemInformation.SYSTEM_TYPE_LINUX).withVersion(osVersion);
          }
        }
      }
    }
    if (osInfo == OS_UNKNOWN) {
      Matcher matcher = NameVersionComment.COMMENT_PATTERN.matcher(userAgent);
      while (matcher.find()) {
        String comment = matcher.group(1);
        osInfo = detectOsFromComment(osInfo, comment);
      }
    }
    if (osVariant == null) {
      osInfo = detectOsVariant(userAgentLowerCase, osInfo);
    }
    return osInfo;
  }

  private void detectBrowser() {

    int segmentCount = getSegments().size();
    for (int i = segmentCount - 1; i >= 0; i--) {
      GenericNameVersionComment segment = this.segments.get(i);
      String nameLowerCase = segment.getName().toLowerCase(Locale.US);
      String browserName = BROWSERS.get(nameLowerCase);
      if (BROWSER_SAFARI.equals(browserName) && (i > 1)) {
        GenericNameVersionComment previousSegment = this.segments.get(i - 1);
        if (previousSegment.getName().equals(BROWSER_CHROME)) {
          this.browser = BROWSER_CHROME;
          this.browserVersion = previousSegment.getVersion();
          break;
        }
      }
      if (browserName != null) {
        this.browser = browserName;
        this.browserVersion = segment.getVersion();
        break;
      } else if (!BROWSER_BLACKLIST.contains(nameLowerCase)) {
        this.browser = segment.getName();
        this.browserVersion = segment.getVersion();
        break;
      }
    }
    if (this.browser == null) {
      detectCrawlerByIndicator(segmentCount);
      if (this.browser == null) {
        this.browser = UNKNOWN;
        this.browserVersion = UNKNOWN;
      }
    }
  }

  private boolean isLimitedDevice(String userAgentLowerCase) {

    return userAgentLowerCase.contains("mobile") || BROWSER_KINDLE.equals(this.browser);
  }

  private static String substring(String string, int start, char stop1, char stop2, char stop3) {

    int end = string.length();
    end = findChar(string, start, stop1, end);
    end = findChar(string, start, stop2, end);
    end = findChar(string, start, stop3, end);
    return string.substring(start, end);
  }

  private static int findChar(String string, int start, char stop2, int end) {

    int stopIndex = string.indexOf(stop2, start);
    if ((stopIndex >= 0) && (stopIndex < end)) {
      return stopIndex;
    }
    return end;
  }

  private GenericNameVersionComment detectOsFromComment(GenericNameVersionComment osSegment, String comment) {

    GenericNameVersionComment osInfo = osSegment;
    if ((comment != null) && (osInfo == OS_UNKNOWN)) {
      String[] tokens = comment.split(";");
      String[] tokensLowerCase = new String[tokens.length];
      int tokenIndex = 0;
      for (int i = 0; i < tokens.length; i++) {
        tokens[i] = tokens[i].trim();
        int likeIndex = tokens[i].indexOf(" like ");
        if (likeIndex > 0) {
          tokens[i] = tokens[i].substring(0, likeIndex);
        }
        tokensLowerCase[i] = tokens[i].toLowerCase(Locale.US);
        if (tokensLowerCase[i].startsWith("xbox")) {
          tokenIndex = i;
        }
      }
      while (tokenIndex < tokens.length) {
        String token = tokens[tokenIndex];
        String tokenLower = tokensLowerCase[tokenIndex];
        GenericNameVersionComment newOsInfo = detectOsFromToken(token, tokenLower, osInfo);
        if (newOsInfo != null) {
          if (osInfo == null) {
            osInfo = newOsInfo;
          } else {
            osInfo = mergeOsInfo(osInfo, newOsInfo);
          }
        }
        tokenIndex++;
      }
    }
    return osInfo;
  }

  private GenericNameVersionComment detectOsVariant(String userAgentLowerCase, GenericNameVersionComment osInfo) {

    for (String variant : LINUX_VARIANTS.keySet()) {
      if (userAgentLowerCase.contains(variant)) {
        String systemVariant = LINUX_VARIANTS.get(variant);
        return getOsInfoForOsVariant(osInfo, systemVariant, SystemInformation.SYSTEM_TYPE_LINUX);
      }
    }
    return osInfo;
  }

  private GenericNameVersionComment getOsInfoForOsVariant(GenericNameVersionComment osInfo, String osVariant, String expectedOsType) {

    String osType = osInfo.getComment();
    if (!UNKNOWN.equals(osType) && !expectedOsType.equals(osType)) {
      LOG.debug("Changed OS type from {} to {} because variant {} was detected", osType, expectedOsType, osVariant);
    }
    return new GenericNameVersionComment(osVariant + " " + osInfo.getName(), osInfo.getVersion(), expectedOsType);
  }

  private GenericNameVersionComment mergeOsInfo(GenericNameVersionComment osInfo, GenericNameVersionComment newOsInfo) {

    if (osInfo == OS_UNKNOWN) {
      return newOsInfo;
    }
    if (osInfo.getComment().equals(SystemInformation.SYSTEM_TYPE_LINUX) && newOsInfo.getComment().equals(SystemInformation.SYSTEM_TYPE_ANDROID)) {
      return newOsInfo;
    }
    if (osInfo.getVersion().equals(UNKNOWN) && !newOsInfo.getVersion().equals(UNKNOWN) && osInfo.getComment().equals(newOsInfo.getComment())) {
      return newOsInfo;
    }
    return osInfo;
  }

  private GenericNameVersionComment detectOsFromToken(String token, String tokenLower, GenericNameVersionComment osInfo) {

    int winNtStartIndex = token.indexOf("WinNT");
    if (winNtStartIndex >= 0) {
      String systemVersion = UNKNOWN;
      String details = token.substring(winNtStartIndex + 5, token.length()).trim();
      String[] fragments = details.split(" ");
      int versionIndex = findVersion(fragments);
      if (versionIndex >= 0) {
        systemVersion = getVersion(fragments[versionIndex]);
      }
      return new GenericNameVersionComment("Windows NT", systemVersion, SystemInformation.SYSTEM_TYPE_WINDOWS);
    }
    for (String type : SYSTEM_TYPES.keySet()) {
      int typeStartIndex = tokenLower.indexOf(type);
      if (typeStartIndex >= 0) {
        String systemType = SYSTEM_TYPES.get(type);
        String systemVersion = UNKNOWN;
        int typeEndIndex = typeStartIndex + type.length();
        StringBuilder systemName = new StringBuilder(token.subSequence(typeStartIndex, typeEndIndex));
        if ((typeEndIndex < token.length()) && (token.charAt(typeEndIndex) == '_')) {
          typeEndIndex++;
        }
        String details = token.substring(typeEndIndex, token.length()).trim();
        String[] fragments = details.split(" ");
        int versionIndex = findVersion(fragments);
        if (versionIndex >= 0) {
          systemVersion = getVersion(fragments[versionIndex]);
        } else {
          versionIndex = fragments.length;
        }
        for (int i = 0; i < versionIndex; i++) {
          if (!isEmpty(fragments[i]) && !ARCHITECTURES.containsKey(fragments[i])) {
            systemName.append(' ');
            systemName.append(fragments[i]);
          }
        }
        return new GenericNameVersionComment(systemName.toString(), systemVersion, systemType);
      } else if (osInfo != OS_UNKNOWN) {
        String version = getVersion(token);
        if (version != null) {
          return osInfo.withVersion(version);
        }
      }
    }
    return null;
  }

  private int findVersion(String[] strings) {

    for (int i = 0; i < strings.length; i++) {
      if (isVersion(strings[i])) {
        int next = i + 1;
        int length = strings[i].length();
        if ((length <= 2) && (next < strings.length) && isVersion(strings[next]) && (length < strings[next].length())) {
          return next;
        }
        return i;
      }
    }
    return -1;
  }

  private boolean isVersion(String string) {

    return PATTERN_VERSION.matcher(string).matches();
  }

  private String getVersion(String string) {

    if (string == null) {
      return null;
    }
    if (isVersion(string)) {
      return string;
    }
    return null;
  }

  private String getVersion(GenericNameVersionComment segment) {

    String version = getVersion(segment.getVersion());
    if (version != null) {
      return version;
    }
    version = getVersion(segment.getComment());
    if (version != null) {
      return version;
    }
    return segment.getVersion();
  }

  private String detectArchitecture(String userAgentLowerCase, String systemType) {

    for (String arch : ARCHITECTURES.keySet()) {
      int archIndex = userAgentLowerCase.indexOf(arch);
      if (archIndex >= 0) {
        char c = ' ';
        if (archIndex > 0) {
          c = userAgentLowerCase.charAt(archIndex - 1);
        }
        if (Character.isWhitespace(c) || c == ',' || c == ';' || c == '_') {
          return ARCHITECTURES.get(arch);
        }
      }
    }
    return UNKNOWN;
  }

  @Override
  protected AbstractHttpHeader withValue(String value) {

    return ofValue(value);
  }

  /**
   * @param headerValue the header {@link #getValues() value}.
   * @return the parsed {@link HttpHeaderUserAgent} or {@code null} if the given value is {@code null} or
   *         {@link String#isEmpty() empty}.
   */
  public static HttpHeaderUserAgent ofValue(String headerValue) {

    String value = trim(headerValue);
    if (value == null) {
      return null;
    }
    return new HttpHeaderUserAgent(value);
  }

  /**
   * @param headers the {@link HttpHeaders} to get this header from. May be {@code null}.
   * @return the {@link HttpHeaderUserAgent} form the given {@link HttpHeaders} or {@code null} if not present.
   */
  public static HttpHeaderUserAgent get(HttpHeaders headers) {

    if (headers == null) {
      return null;
    }
    HttpHeader header = headers.getHeader(HEADER);
    return (HttpHeaderUserAgent) header;
  }

}
