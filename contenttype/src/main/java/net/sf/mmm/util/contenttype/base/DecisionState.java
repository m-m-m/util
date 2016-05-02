/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base;

import net.sf.mmm.util.contenttype.base.format.Segment;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DecisionState {

  /** @see #getSegment() */
  private Segment segment;

  /** @see #getStreamOffset() */
  private long streamOffset;

  /** @see #getKey() */
  private String key;

  /**
   * @return the segment
   */
  public Segment getSegment() {

    return this.segment;
  }

  /**
   * @param segment is the segment to set
   */
  public void setSegment(Segment segment) {

    this.segment = segment;
  }

  /**
   * @return the streamOffset
   */
  public long getStreamOffset() {

    return this.streamOffset;
  }

  /**
   * 
   * 
   * @param bytes is the number of bytes to add to the
   *        {@link #getStreamOffset() stream offset}.
   */
  public void addStreamOffset(int bytes) {

    this.streamOffset = this.streamOffset + bytes;
  }

  /**
   * @return the key
   */
  public String getKey() {

    return this.key;
  }

  /**
   * @param key is the key to set
   */
  public void setKey(String key) {

    this.key = key;
  }

}
