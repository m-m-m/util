/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

/**
 * This class represents a {@link Segment} that indicates the end of the stream.
 * It matches if the stream data is entirely processed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SegmentEndOfStream extends Segment {

  /**
   * {@inheritDoc}
   */
  @Override
  public long getLength() {

    return 0;
  }

}
