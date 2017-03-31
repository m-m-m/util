/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.io.BufferedReader;
import java.io.StringReader;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test of {@link HttpHeadersImpl}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class HttpHeadersTest extends Assertions {

  private static final String TEST_HTTP_REQUEST = "GET /maven/apidocs/ HTTP/1.1" + HttpHeader.CRLF //
      + "Host: m-m-m.github.io" + HttpHeader.CRLF //
      + "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0" + HttpHeader.CRLF //
      + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" + HttpHeader.CRLF //
      + "Accept-Language: de,en-US;q=0.7,en;q=0.3" + HttpHeader.CRLF //
      + "Accept-Encoding: gzip, deflate, br" + HttpHeader.CRLF //
      + "Connection: keep-alive" + HttpHeader.CRLF //
      + "Upgrade-Insecure-Requests: 1" + HttpHeader.CRLF //
      + "Cache-Control: max-age=0" + HttpHeader.CRLF //
      + HttpHeader.CRLF;

  private static final String TEST_HTTP_RESPONSE = "HTTP/1.1 200 OK" + HttpHeader.CRLF //
      + "Date: Sat, 01 Jan 2000 12:13:14 GMT" + HttpHeader.CRLF //
      + "Server: Apache/2.2.14 (Win32)" + HttpHeader.CRLF //
      + "Last-Modified: Sat, 01 Jan 2000 01:02:03 GMT" + HttpHeader.CRLF //
      + "ETag: \"10000000565a5-2c-3e94b66c2e680\"" + HttpHeader.CRLF //
      + "Accept-Ranges: bytes" + HttpHeader.CRLF //
      + "Content-Length: 42000" + HttpHeader.CRLF //
      + "Content-Type: application/octet-stream" + HttpHeader.CRLF //
      + "Content-Disposition: attachment; filename=\"data.img\"" + HttpHeader.CRLF //
      + "Connection: Closed" + HttpHeader.CRLF //
      + HttpHeader.CRLF;

  /**
   * Test of {@link HttpHeadersImpl#parse(BufferedReader)}
   *
   * @throws Exception if something goes wrong.
   */
  @Test
  public void testParse() throws Exception {

    StringReader reader = new StringReader(TEST_HTTP_REQUEST);
    BufferedReader br = new BufferedReader(reader);
    br.readLine();
    HttpHeadersImpl headers = HttpHeadersImpl.parse(br);
    assertThat(headers.getHost()).isEqualTo("m-m-m.github.io");
    HttpHeaderUserAgent userAgent = HttpHeaderUserAgent.get(headers);
    assertThat(userAgent.getBrowser()).isEqualTo("Firefox");
    assertThat(userAgent.getBrowserVersion()).isEqualTo("50.0");
    assertThat(userAgent.getOs().getSystemName()).isEqualTo("Windows NT");
    assertThat(userAgent.getOs().getSystemVersion()).isEqualTo("6.1");
    assertThat(userAgent.getOs().is64Bit()).isTrue();
  }

}
