/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

/**
 * A {@link SegmentContainer} is like a list of {@link Segment}s. As it is also a {@link Segment} itself a
 * {@link SegmentContainer} can contain other {@link SegmentContainer}s. This class is abstract - see the available
 * subclasses for additional information.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class SegmentContainer extends Segment {

  @XmlElements(value = { @XmlElement(name = SegmentConstant.XML_TAG, type = SegmentConstant.class),
  @XmlElement(name = SegmentKey.XML_TAG, type = SegmentKey.class),
  @XmlElement(name = SegmentValue.XML_TAG, type = SegmentValue.class),
  @XmlElement(name = SegmentEndOfStream.XML_TAG, type = SegmentEndOfStream.class),
  @XmlElement(name = SegmentAny.XML_TAG, type = SegmentAny.class),
  @XmlElement(name = SegmentRange.XML_TAG, type = SegmentRange.class),
  @XmlElement(name = SegmentContainerSequence.XML_TAG, type = SegmentContainerSequence.class),
  @XmlElement(name = SegmentContainerRepeat.XML_TAG, type = SegmentContainerRepeat.class),
  @XmlElement(name = SegmentContainerChoice.XML_TAG, type = SegmentContainerChoice.class) })
  private List<Segment> segments;

  /**
   * The constructor.
   */
  public SegmentContainer() {

    super();
  }

  /**
   * This method gets the contained child {@link Segment} with the given {@code index}.
   *
   * @see java.util.List#get(int)
   *
   * @param index is the index of the requested child {@link Segment}. See {@link #getSegmentCount()}.
   * @return the requested child {@link Segment}.
   */
  public Segment getSegment(int index) {

    if (this.segments == null) {
      throw new IndexOutOfBoundsException(Integer.toString(index));
    }
    return this.segments.get(index);
  }

  /**
   * This method determines the number of {@link #getSegment(int) contained child segments}.
   *
   * @return the {@link Segment} count.
   */
  public int getSegmentCount() {

    if (this.segments == null) {
      return 0;
    }
    return this.segments.size();
  }

  @Override
  public long getMinimumLength() {

    long aggregated = 0;
    boolean first = true;
    for (Segment segment : this.segments) {
      long next = segment.getMinimumLength();
      if (first) {
        first = false;
        aggregated = next;
      } else {
        aggregated = aggregateMinimumLength(aggregated, next);
      }
    }
    return aggregated;
  }

  /**
   * This method aggregates the current {@code aggregated} {@link #getMaximumLength() minimum length} with the given
   * {@code next} value. The default implementation is a simple summation.
   *
   * @param aggregated is the current {@code aggregated} result, initially {@link #getMinimumLength()} of the first
   *        {@link #getSegment(int) child}.
   * @param next is the next length to aggregate.
   * @return the new aggregated result.
   */
  protected long aggregateMinimumLength(long aggregated, long next) {

    return aggregated + next;
  }

  @Override
  public long getMaximumLength() {

    long aggregated = 0;
    boolean first = true;
    for (Segment segment : this.segments) {
      long next = segment.getMaximumLength();
      if (next == Long.MAX_VALUE) {
        return Long.MAX_VALUE;
      }
      if (first) {
        first = false;
        aggregated = next;
      } else {
        aggregated = aggregateMaximumLength(aggregated, next);
      }
    }
    return aggregated;
  }

  /**
   * This method aggregates the current {@code aggregated} {@link #getMaximumLength() maximum length} with the given
   * {@code next} value. The default implementation is a simple summation. <br>
   * <b>ATTENTION:</b><br>
   * Please note that {@link Long#MAX_VALUE} is already handled by {@link #getMaximumLength()}. In that case this method
   * is NOT invoked anymore.
   *
   * @param aggregated is the current {@code aggregated} result, initially {@link #getMaximumLength()} of the first
   *        {@link #getSegment(int) child}.
   * @param next is the next length to aggregate.
   * @return the new aggregated result.
   */
  protected long aggregateMaximumLength(long aggregated, long next) {

    return aggregated + next;
  }

  @Override
  protected void doValidate(StringBuilder source) {

    super.validate(source);
    for (Segment segment : this.segments) {
      segment.validate(source);
      SegmentContainer parent = segment.getParent();
      if (parent == null) {
        segment.setParent(this);
      } else {
        assert (parent == this);
      }
    }
  }

  @Override
  protected void validateNonRecursive(StringBuilder source) {

    // do not call super.validateNonRecursive() to avoid pointless summation of
    // lengths...
  }

}
