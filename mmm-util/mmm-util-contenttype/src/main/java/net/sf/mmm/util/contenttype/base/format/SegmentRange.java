/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SegmentRange extends Segment {

  private byte[] min;

  private byte[] max;

  /**
   * The constructor.
   */
  public SegmentRange() {

    super();
  }

  /**
   * @return the min
   */
  public byte[] getMin() {

    return this.min;
  }

  /**
   * @return the max
   */
  public byte[] getMax() {

    return this.max;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getLength() {

    return this.min.length;
  }

}
