/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * This class represents a {@link Segment} that can have a
 * {@link #getMaximumLength() variable length}. <br>
 * By default the length is unbound ([0, {@link Long#MAX_VALUE}]). If only the
 * "length" attribute is specified in XML, then both {@link #getMinimumLength()}
 * and {@link #getMaximumLength()} will return that value. If additionally the
 * "maxLength" attribute is specified, then {@link #getMinimumLength()} will
 * return the value of "length" and {@link #getMaximumLength()} will return the
 * value of "maxLength".
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class SegmentVariableLength extends Segment {

  /** @see #getMinimumLength() */
  @XmlAttribute(name = "length", required = true)
  private long length;

  /** @see #getMaximumLength() */
  @XmlAttribute(name = "maxLength", required = false)
  private long maximumLength;

  /**
   * The constructor.
   */
  public SegmentVariableLength() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param length - see {@link #getMinimumLength()}.
   * @param maximumLength - see {@link #getMaximumLength()}.
   */
  public SegmentVariableLength(long length, long maximumLength) {

    super();
    this.length = length;
    this.maximumLength = maximumLength;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getMinimumLength() {

    return this.length;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getMaximumLength() {

    if (this.maximumLength == 0) {
      if (this.length == 0) {
        return Long.MAX_VALUE;
      } else {
        return this.length;
      }
    }
    return this.maximumLength;
  }

  /**
   * @param maximumLength is the maximumLength to set
   */
  public void setMaximumLength(long maximumLength) {

    this.maximumLength = maximumLength;
  }

}
