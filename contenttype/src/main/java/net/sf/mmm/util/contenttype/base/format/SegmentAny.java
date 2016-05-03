/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

/**
 * This class represents a {@link Segment} that can contain any data. This data
 * is ignored. But following segments still need to match. A {@link SegmentAny}
 * can have a {@link #getMaximumLength() variable length}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SegmentAny extends SegmentVariableLength {

  /** The XML tag name for this object. */
  public static final String XML_TAG = "any";

  /**
   * The constructor.
   */
  public SegmentAny() {

    super();
  }

  @Override
  protected String getTagName() {

    return XML_TAG;
  }

}
