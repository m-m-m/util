/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * This class represents a {@link Segment} that has a constant value. The stream
 * data has to contain these {@link #getBytes() bytes} in order to match the
 * according {@link net.sf.mmm.util.contenttype.api.ContentType}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SegmentConstant extends Segment {

  /** The constant bytes. */
  private byte[] bytes;

  /** @see #getAscii() */
  @XmlAttribute(name = "ascii", required = false)
  private String ascii;

  /** @see #getUtf8() */
  @XmlAttribute(name = "utf8", required = false)
  private String utf8;

  /** @see #getHex() */
  @XmlAttribute(name = "hex", required = false)
  private String hex;

  /**
   * The constructor.
   */
  public SegmentConstant() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param bytes are the bytes.
   */
  public SegmentConstant(byte... bytes) {

    super();
    this.bytes = bytes;
  }

  /**
   * @return the bytes
   */
  public byte[] getBytes() {

    return this.bytes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getLength() {

    return this.bytes.length;
  }

  /**
   * @return the ascii
   */
  public String getAscii() {

    return this.ascii;
  }

  /**
   * @return the utf8
   */
  public String getUtf8() {

    return this.utf8;
  }

  /**
   * @return the hex
   */
  public String getHex() {

    return this.hex;
  }

}
