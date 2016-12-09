/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import net.sf.mmm.util.lang.api.SystemInformation;

/**
 * The test of {@link HttpHeaderUserAgent}.
 *
 * @author hohwille
 */
public class HttpHeaderUserAgentTest extends Assertions {

  private HttpHeaderUserAgent checkUserAgent(SoftAssertions assertion, String agent, String browser, String version, String osName, String osType,
      String osVersion, String architecture) {

    HttpHeaderUserAgent userAgent = HttpHeaderUserAgent.ofValue(agent);
    assertion.assertThat(userAgent.getBrowser()).as("Browser of " + agent).isEqualTo(browser);
    assertion.assertThat(userAgent.getBrowserVersion()).as("BrowserVersion of " + agent).isEqualTo(version);
    SystemInformation os = userAgent.getOs();
    assertion.assertThat(os.getSystemType()).as("SystemType of " + agent).isEqualTo(osType);
    assertion.assertThat(os.getSystemName()).as("SystemName of " + agent).isEqualTo(osName);
    assertion.assertThat(os.getSystemVersion()).as("SystemVersion of " + agent).isEqualTo(osVersion);
    assertion.assertThat(os.getSystemArchitecture()).as("SystemArchitecture of " + agent).isEqualTo(architecture);
    return userAgent;
  }

  private HttpHeaderUserAgent checkCrawler(SoftAssertions assertion, String agent, String browser, String version, String osName, String osType,
      String osVersion, String architecture) {

    HttpHeaderUserAgent userAgent = checkUserAgent(assertion, agent, browser, version, osName, osType, osVersion, architecture);
    assertion.assertThat(userAgent.isCrawler()).as("Crawler of " + agent).isTrue();
    return userAgent;
  }

  private HttpHeaderUserAgent checkDevice(SoftAssertions assertion, String agent, String browser, String version, String osName, String osType,
      String osVersion, String architecture) {

    HttpHeaderUserAgent userAgent = checkUserAgent(assertion, agent, browser, version, osName, osType, osVersion, architecture);
    assertion.assertThat(userAgent.getOs().isLimitedDevice()).as("os.limitedDevice of " + agent).isTrue();
    return userAgent;
  }

  /**
   * Test of mobile devices (smart phones) with various operating systems. User agent strings from arbitrary sources.
   */
  @Test
  public void testMobileDevices() {

    try (AutoCloseableSoftAssertions assertion = new AutoCloseableSoftAssertions()) {
      checkDevice(assertion,
          "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3", "Safari",
          "7534.48.3", "iPhone OS", "iOS", "5_0", "arm");
    }
  }

  /**
   * Test of game consoles.
   *
   * Agent strings from <a href="https://deviceatlas.com/blog/list-of-user-agent-strings">deviceatlas</a>.
   */
  @Test
  public void testGameConsoles() {

    try (AutoCloseableSoftAssertions assertion = new AutoCloseableSoftAssertions()) {
      checkDevice(assertion, "Mozilla/5.0 (Nintendo WiiU) AppleWebKit/536.30 (KHTML, like Gecko) NX/3.0.4.2.12 NintendoBrowser/4.3.1.11264.US",
          "NintendoBrowser", "4.3.1.11264.US", "Nintendo WiiU", "Nintendo", "unknown", "unknown");
      checkDevice(assertion,
          "Mozilla/5.0 (Windows Phone 10.0; Android 4.2.1; Xbox; Xbox One) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Mobile Safari/537.36 Edge/13.10586",
          "Edge", "13.10586", "Xbox One", "Xbox", "unknown", "unknown");
      checkDevice(assertion, "Mozilla/5.0 (PlayStation 4 3.11) AppleWebKit/537.73 (KHTML, like Gecko)", "AppleWebKit", "537.73", "PlayStation 4", "PlayStation",
          "3.11", "unknown");
      checkDevice(assertion, "Mozilla/5.0 (PlayStation Vita 3.61) AppleWebKit/537.73 (KHTML, like Gecko) Silk/3.2", "Silk", "3.2", "PlayStation Vita",
          "PlayStation", "3.61", "unknown");
      checkDevice(assertion, "Mozilla/5.0 (Nintendo 3DS; U; ; en) Version/1.7412.EU", "unknown", "unknown", "Nintendo 3DS", "Nintendo", "unknown", "unknown");
    }
  }

  /**
   * Test of browsers on desktop PCs on various operating systems. User agent strings from arbitrary sources.
   */
  @Test
  public void testDesktopBrowsers() {

    try (AutoCloseableSoftAssertions assertion = new AutoCloseableSoftAssertions()) {
      checkUserAgent(assertion,
          "Mozilla/5.0 (X11; U; Linux i686; it-it) AppleWebKit/531.2+ (KHTML, like Gecko) Version/5.0 Safari/531.2+ Debian/squeeze (2.30.6-1) Epiphany/2.30.6",
          "Epiphany", "2.30.6", "Debian Linux", "Linux", "2.30.6-1", "i686");
      checkUserAgent(assertion,
          "Mozilla/5.0 (X11; Linux x86_64; Debian) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.52 Safari/537.36 OPR/15.0.1147.100", "Opera",
          "15.0.1147.100", "Debian Linux", "Linux", "unknown", "x86_64");
      checkUserAgent(assertion, "Mozilla/5.0 (X11; U; Linux x86_64; de; rv:1.9.0.19) Gecko/2010033022 Iceweasel/3.0.6 (Debian-3.0.6-3)", "Iceweasel", "3.0.6",
          "Debian Linux", "Linux", "unknown", "x86_64");
      checkUserAgent(assertion, "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.1 (KHTML, like Gecko) Debian Chrome/41.0.2272.118 Safari/535.1", "Chrome",
          "41.0.2272.118", "Debian Linux", "Linux", "unknown", "i686");
      checkUserAgent(assertion,
          "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246", "Edge", "12.246",
          "Windows NT", "Windows", "10.0", "x86_64");
      checkUserAgent(assertion, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/601.3.9 (KHTML, like Gecko) Version/9.0.2 Safari/601.3.9",
          "Safari", "601.3.9", "Mac OS X", "Mac OS", "10_11_2", "unknown");
      checkUserAgent(assertion, "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:15.0) Gecko/20100101 Firefox/15.0.1", "Firefox", "15.0.1", "Ubuntu Linux", "Linux",
          "unknown", "x86_64");
    }
  }

  /**
   * Agent strings from <a href="https://deviceatlas.com/blog/list-of-user-agent-strings">deviceatlas</a>.
   */
  @Test
  public void testEBookReaders() {

    try (AutoCloseableSoftAssertions assertion = new AutoCloseableSoftAssertions()) {
      checkDevice(assertion,
          "Mozilla/5.0 (X11; U; Linux armv7l like Android; en-us) AppleWebKit/531.2+ (KHTML, like Gecko) Version/5.0 Safari/533.2+ Kindle/3.0+", "Kindle",
          "3.0+", "Linux armv7l", "Linux", "unknown", "arm");
      checkDevice(assertion,
          "Mozilla/5.0 (Linux; U; en-US) AppleWebKit/528.5+ (KHTML, like Gecko, Safari/528.5+) Version/4.0 Kindle/3.0 (screen 600x800; rotate)", "Kindle",
          "3.0", "Linux", "Linux", "unknown", "unknown");

    }
  }

  /**
   * Agent strings from
   * <a href="http://html5-mobile.de/blog/wichtigsten-user-agents-mobile-devices-jquery-mobile">html5-mobile</a>.
   */
  @Test
  public void testTables() {

    try (AutoCloseableSoftAssertions assertion = new AutoCloseableSoftAssertions()) {
      checkDevice(assertion,
          "Mozilla/5.0 (Linux; U; Android 2.3.4; en-us; Kindle Fire Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
          "Safari", "533.1", "Android", "Android", "2.3.4", "unknown");
      checkDevice(assertion, "Mozilla/5.0 (PlayBook; U; RIM Tablet OS 1.0.0; en-US) AppleWebKit/534.8+ (KHTML, like Gecko) Version/0.0.1 Safari/534.8+",
          "Safari", "534.8+", "RIM Tablet OS", "RIM Tablet OS", "1.0.0", "unknown");
    }
  }

  /**
   * Agent strings from <a href="https://deviceatlas.com/blog/list-of-web-crawlers-user-agents">deviceatlas</a>.
   *
   * @see HttpHeaderUserAgent#isCrawler()
   */
  @Test
  public void testCrawlers() {

    try (AutoCloseableSoftAssertions assertion = new AutoCloseableSoftAssertions()) {
      checkCrawler(assertion, "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)", "Googlebot", "2.1", "unknown", "unknown", "unknown",
          "unknown");
      checkCrawler(assertion, "Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)", "bingbot", "2.0", "unknown", "unknown", "unknown",
          "unknown");
      checkCrawler(assertion, "Mozilla/5.0 (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)", "Yahoo! Slurp", "unknown", "unknown",
          "unknown", "unknown", "unknown");
      checkCrawler(assertion, "Mozilla/2.0 (compatible; Ask Jeeves/Teoma)", "Crawler", "unknown", "unknown", "unknown", "unknown", "unknown");
      checkCrawler(assertion,
          "Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12F70 Safari/600.1.4 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
          "Googlebot", "2.1", "iPhone OS", "iOS", "8_3", "arm");
      checkCrawler(assertion, "Mozilla/5.0 (compatible; MJ12bot/v1.4.5; http://www.majestic12.co.uk/bot.php?+)", "MJ12bot", "v1.4.5", "unknown", "unknown",
          "unknown", "unknown");
      checkCrawler(assertion, "SimplePie/1.3.1 (Feed Parser; http://simplepie.org; Allow like Gecko) Build/20121030175911", "SimplePie", "1.3.1", "unknown",
          "unknown", "unknown", "unknown");
      checkCrawler(assertion,
          "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53 BingPreview/1.0b",
          "BingPreview", "1.0b", "iPhone OS", "iOS", "7_0", "arm");
      checkCrawler(assertion,
          "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53 (compatible; bingbot/2.0; http://www.bing.com/bingbot.htm)",
          "bingbot", "2.0", "iPhone OS", "iOS", "7_0", "arm");
      checkCrawler(assertion,
          "SAMSUNG-SGH-E250/1.0 Profile/MIDP-2.0 Configuration/CLDC-1.1 UP.Browser/6.2.3.3.c.1.101 (GUI) MMP/2.0 (compatible; Googlebot-Mobile/2.1; +http://www.google.com/bot.html)",
          "Googlebot-Mobile", "2.1", "unknown", "unknown", "unknown", "unknown");
      checkCrawler(assertion, "DoCoMo/2.0 N905i(c100;TB;W24H16) (compatible; Googlebot-Mobile/2.1; +http://www.google.com/bot.html)", "Googlebot-Mobile", "2.1",
          "unknown", "unknown", "unknown", "unknown");
      checkCrawler(assertion,
          "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)",
          "bingbot", "2.0", "iPhone OS", "iOS", "7_0", "arm");
      checkCrawler(assertion,
          "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1 (compatible; AdsBot-Google-Mobile; +http://www.google.com/mobile/adsbot.html)",
          "AdsBot-Google-Mobile", "unknown", "iPhone OS", "iOS", "9_1", "arm");
      checkCrawler(assertion, "SiteLockSpider [en] (WinNT; I ;Nav)", "SiteLockSpider", "unknown", "Windows NT", "Windows", "unknown", "unknown");
      checkCrawler(assertion, "okhttp/2.5.0", "okhttp", "2.5.0", "unknown", "unknown", "unknown", "unknown");
      checkCrawler(assertion, "curl/7.35.0", "curl", "7.35.0", "unknown", "unknown", "unknown", "unknown");
      checkCrawler(assertion, "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:14.0; ips-agent) Gecko/20100101 Firefox/14.0.1", "ips-agent", "unknown", "Ubuntu Linux",
          "Linux", "unknown", "i686");
      checkCrawler(assertion, "Googlebot-Image/1.0", "Googlebot-Image", "1.0", "unknown", "unknown", "unknown", "unknown");
      checkCrawler(assertion, "Mozilla/5.0 (compatible; BLEXBot/1.0; +http://webmeup-crawler.com/)", "BLEXBot", "1.0", "unknown", "unknown", "unknown",
          "unknown");
      checkCrawler(assertion, "Mozilla/5.0 (compatible; YandexBot/3.0; +http://yandex.com/bots)", "YandexBot", "3.0", "unknown", "unknown", "unknown",
          "unknown");
      checkCrawler(assertion, "Mozilla/5.0 (compatible; ScoutJet; +http://www.scoutjet.com/)", "ScoutJet", "unknown", "unknown", "unknown", "unknown",
          "unknown");

    }
  }

  /**
   * Test of user agents from exotic devices.
   */
  @Test
  public void testExoticDevices() {

    try (AutoCloseableSoftAssertions assertion = new AutoCloseableSoftAssertions()) {
      checkUserAgent(assertion, "SonyEricssonT68/R201A", "SonyEricssonT68", "R201A", "unknown", "unknown", "unknown", "unknown");

      checkUserAgent(assertion,
          "Mozilla/5.0 (Mobile; Windows Phone 8.1; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; NOKIA; Lumia 920) like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537",
          "IEMobile", "11.0", "Windows Phone", "Windows Phone", "8.1", "arm");

      checkUserAgent(assertion, "BlackBerry8520/5.0.0.681 Profile/MIDP-2.1 Configuration/CLDC-1.1 VendorID/114", "BlackBerry8520", "5.0.0.681", "unknown",
          "unknown", "unknown", "unknown");

    }
  }

}
