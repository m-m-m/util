/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.test.ObjectHelper;
import net.sf.mmm.util.http.api.header.AbstractHttpHeader;
import net.sf.mmm.util.http.api.header.HttpHeader;
import net.sf.mmm.util.http.api.header.HttpHeaderContentDisposition;

/**
 * This is the test for {@link HttpHeaderContentDisposition}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class HttpHeaderContentDispositionTest extends Assertions {

  /** Test {@link HttpHeaderContentDisposition#ofValue(String)}. */
  @Test
  public void testOfString() {

    String contentDispositionHeaderValue = "attachment; filename=\"image.jpg\"";
    HttpHeaderContentDisposition contentDisposition = HttpHeaderContentDisposition.ofValue(contentDispositionHeaderValue);
    assertThat(contentDisposition.getName()).isEqualTo(HttpHeader.HEADER_CONTENT_DISPOSITION);
    assertThat(contentDisposition.getType()).isEqualTo(HttpHeaderContentDisposition.TYPE_ATTACHMENT);
    assertThat(contentDisposition.getFilename()).isEqualTo("image.jpg");
    assertThat(contentDisposition.getParameter(HttpHeaderContentDisposition.PARAMETER_FILENAME)).isEqualTo("image.jpg");
    assertThat(contentDisposition.isTypeAttachment()).isTrue();
    assertThat(contentDisposition.isTypeInline()).isFalse();
    assertThat(contentDisposition.getValue()).isEqualTo(contentDispositionHeaderValue);
    assertThat(contentDisposition.toString()).isEqualTo("Content-Disposition: attachment; filename=\"image.jpg\"");
    assertThat(contentDisposition.getModificationDate()).isNull();
    assertThat(contentDisposition.getCreationDate()).isNull();
    assertThat(contentDisposition.getReadDate()).isNull();

    AbstractHttpHeader header = AbstractHttpHeader.ofHeader(contentDisposition.toString());
    assertThat(header).isNotNull().isInstanceOf(HttpHeaderContentDisposition.class);
    ObjectHelper.checkEqualsAndHashCode(header, contentDisposition, true);

    contentDisposition = HttpHeaderContentDisposition.ofValue("filename=\"image.jpg\"");
    assertThat(contentDisposition.getType()).isNull();
    assertThat(contentDisposition.getFilename()).isEqualTo("image.jpg");
    assertThat(contentDisposition.isTypeAttachment()).isFalse();
    assertThat(contentDisposition.isTypeInline()).isFalse();

    contentDisposition = HttpHeaderContentDisposition.ofValue("filename=image.jpg");
    assertThat(contentDisposition.getType()).isNull();
    assertThat(contentDisposition.getFilename()).isEqualTo("image.jpg");
    assertThat(contentDisposition.getValue()).isEqualTo("; filename=\"image.jpg\"");

    contentDisposition = HttpHeaderContentDisposition
        .ofValue("inline;name=upload-field;filename=\"genome.jpeg\";modification-date=\"Wed, 12 Feb 1997 16:29:51 -0500\";");
    assertThat(contentDisposition.getType()).isEqualTo(HttpHeaderContentDisposition.TYPE_INLINE);
    assertThat(contentDisposition.getFilename()).isEqualTo("genome.jpeg");
    assertThat(contentDisposition.getParameter(HttpHeaderContentDisposition.PARAMETER_NAME)).isEqualTo("upload-field");
    assertThat(contentDisposition.getModificationDate()).isEqualTo(Instant.parse("1997-02-12T21:29:51Z"));
    assertThat(contentDisposition.isTypeInline()).isTrue();
    assertThat(contentDisposition.isTypeAttachment()).isFalse();
    assertThat(contentDisposition.getValue())
        .isEqualTo("inline; filename=\"genome.jpeg\"; modification-date=\"Wed, 12 Feb 1997 16:29:51 -0500\"; name=\"upload-field\"");
  }

}
