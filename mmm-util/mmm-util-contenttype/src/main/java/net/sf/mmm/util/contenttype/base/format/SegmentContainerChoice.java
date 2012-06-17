/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

/**
 * A {@link SegmentContainerChoice} is a {@link SegmentContainer} that matches
 * if any of the {@link #getSegment(int) contained} {@link Segment}s match.<br/>
 * In typical cases it will contain multiple {@link SegmentContainerSequence}
 * instances that represent alternative patterns in the stream.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SegmentContainerChoice extends SegmentContainer {

  /** The XML tag name for this object. */
  public static final String XML_TAG = "choice";

  /**
   * The constructor.
   */
  public SegmentContainerChoice() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected long aggregateMaximumLength(long aggregated, long next) {

    // maximim(aggregated, next)
    if (aggregated > next) {
      return aggregated;
    } else {
      return next;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected long aggregateMinimumLength(long aggregated, long next) {

    // minimim(aggregated, next)
    if (aggregated < next) {
      return aggregated;
    } else {
      return next;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getTagName() {

    return XML_TAG;
  }

}
