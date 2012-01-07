/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class Container extends Segment {

  /** @see #getSegment(int) */
  @XmlElements(value = { @XmlElement(name = "constant", type = SegmentConstant.class),
      @XmlElement(name = "key", type = SegmentKey.class),
      @XmlElement(name = "value", type = SegmentValue.class),
      @XmlElement(name = "eos", type = SegmentEndOfStream.class),
      @XmlElement(name = "any", type = SegmentAny.class),
      @XmlElement(name = "range", type = SegmentRange.class),
      @XmlElement(name = "sequence", type = ContainerSequence.class),
      @XmlElement(name = "choice", type = ContainerChoice.class) })
  private List<Segment> segments;

  /**
   * The constructor.
   */
  public Container() {

    super();
  }

  /**
   * 
   * @param index
   * @return
   */
  public Segment getSegment(int index) {

    if (this.segments == null) {
      throw new IndexOutOfBoundsException(Integer.toString(index));
    }
    return this.segments.get(index);
  }

  public int getSegmentCount() {

    if (this.segments == null) {
      return 0;
    }
    return this.segments.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getLength() {

    long length = 0;
    for (Segment segment : this.segments) {
      length = length + segment.getLength();
    }
    return length;
  }

}
