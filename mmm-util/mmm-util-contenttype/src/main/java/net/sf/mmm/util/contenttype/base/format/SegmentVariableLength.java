/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * This class represents a {@link Segment} that can have a
 * {@link #getMaximumLength() variable length}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class SegmentVariableLength extends Segment {

  /** @see #getLength() */
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
   * {@inheritDoc}
   */
  @Override
  public long getLength() {

    return this.length;
  }

  /**
   * This method gets the maximum length of this {@link Segment}. By default
   * this method returns the same as {@link #getLength()}. However, it can also
   * return a greater value if the length is variable.
   * 
   * @return the maximum length.
   */
  public long getMaximumLength() {

    if (this.maximumLength == 0) {
      return this.length;
    }
    return this.maximumLength;
  }

}
