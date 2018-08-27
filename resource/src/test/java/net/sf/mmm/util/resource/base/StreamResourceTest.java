/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.ByteArrayInputStream;
import java.net.URL;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * This is the test-case for {@link StreamResource}.
 *
 * @author hohwille
 * @since 7.4.0
 */
public class StreamResourceTest extends Assertions {

  /**
   * Test of {@link StreamResource}.
   * 
   * @throws Exception if something goes wrong.
   */
  @Test
  public void testStreamResource() throws Exception {

    // given
    byte[] buffer = "Hello World!".getBytes();
    ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
    String name = "message.txt";
    // when
    StreamResource streamResource = new StreamResource(stream, name, buffer.length);
    // then
    assertThat(streamResource.openStream()).isSameAs(stream);
    assertThat(streamResource.getName()).isEqualTo(name);
    assertThat(streamResource.getPath()).isEqualTo(name);
    assertThat(streamResource.getSize()).isEqualTo(buffer.length);
    assertThat(streamResource.getSchemePrefix()).isEqualTo("stream:");
    assertThat(streamResource.isData()).isTrue();
    assertThat(streamResource.isAvailable()).isTrue();
    assertThat(streamResource.getUri()).isEqualTo("stream:" + name);
    URL url = streamResource.getUrl();
    assertThat(url.toString()).isEqualTo("stream:" + name);
    assertThat(url.openStream()).isSameAs(stream);
  }

}
