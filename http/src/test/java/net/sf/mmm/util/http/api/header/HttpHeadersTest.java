/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.io.BufferedReader;
import java.io.StringReader;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test of {@link HttpHeaders}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class HttpHeadersTest extends Assertions {

  private static final String TEST_HTTP_MESSAGE = "GET /maven/apidocs/ HTTP/1.1" + HttpHeader.NEWLINE //
      + "Host: m-m-m.github.io" + HttpHeader.NEWLINE //
      + "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0" + HttpHeader.NEWLINE //
      + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" + HttpHeader.NEWLINE //
      + "Accept-Language: de,en-US;q=0.7,en;q=0.3" + HttpHeader.NEWLINE //
      + "Accept-Encoding: gzip, deflate, br" + HttpHeader.NEWLINE //
      + "Connection: keep-alive" + HttpHeader.NEWLINE //
      + "Upgrade-Insecure-Requests: 1" + HttpHeader.NEWLINE //
      + "Cache-Control: max-age=0" + HttpHeader.NEWLINE //
      + HttpHeader.NEWLINE;

  /**
   * Test of {@link HttpHeaders#parse(BufferedReader)}
   *
   * @throws Exception if something goes wrong.
   */
  @Test
  public void testParse() throws Exception {

    StringReader reader = new StringReader(TEST_HTTP_MESSAGE);
    BufferedReader br = new BufferedReader(reader);
    br.readLine();
    HttpHeaders headers = HttpHeaders.parse(br);
    assertThat(headers.getHost()).isEqualTo("m-m-m.github.io");
    HttpHeaderUserAgent userAgent = headers.getUserAgent();
    assertThat(userAgent.getBrowser()).isEqualTo("Firefox");
    assertThat(userAgent.getBrowserVersion()).isEqualTo("50.0");
    assertThat(userAgent.getOs().getSystemName()).isEqualTo("Windows NT");
    assertThat(userAgent.getOs().getSystemVersion()).isEqualTo("6.1");
    assertThat(userAgent.getOs().is64Bit()).isTrue();
  }

}
