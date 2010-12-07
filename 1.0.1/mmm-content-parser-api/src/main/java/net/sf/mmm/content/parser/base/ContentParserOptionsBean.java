/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.base;

import java.util.Locale;

import net.sf.mmm.content.parser.api.ContentParserOptions;
import net.sf.mmm.util.io.api.EncodingUtil;

/**
 * This is the implementation of {@link ContentParserOptions} as simple java
 * bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentParserOptionsBean implements ContentParserOptions {

  /** @see #getMaximumBufferSize() */
  private int maximumBufferSize;

  /** @see #getEncoding() */
  private String encoding;

  /** @see #isDisableUtfDetection() */
  private boolean disableUtfDetection;

  /**
   * The constructor.
   */
  public ContentParserOptionsBean() {

    super();
    this.maximumBufferSize = MAXIMUM_BUFFER_SIZE_DEFAULT;
    this.encoding = EncodingUtil.SYSTEM_DEFAULT_ENCODING;
    if (this.encoding.toLowerCase(Locale.US).startsWith("utf")) {
      this.encoding = EncodingUtil.ENCODING_ISO_8859_15;
    }
    this.disableUtfDetection = false;
  }

  /**
   * {@inheritDoc}
   */
  public int getMaximumBufferSize() {

    return this.maximumBufferSize;
  }

  /**
   * @param maximumBufferSize is the maximumBufferSize to set
   */
  public void setMaximumBufferSize(int maximumBufferSize) {

    this.maximumBufferSize = maximumBufferSize;
  }

  /**
   * {@inheritDoc}
   */
  public String getEncoding() {

    return this.encoding;
  }

  /**
   * @param encoding is the encoding to set
   */
  public void setEncoding(String encoding) {

    this.encoding = encoding;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDisableUtfDetection() {

    return this.disableUtfDetection;
  }

  /**
   * @param disableUnicodeDetection is the disableUnicodeDetection to set
   */
  public void setDisableUtfDetection(boolean disableUnicodeDetection) {

    this.disableUtfDetection = disableUnicodeDetection;
  }

}
