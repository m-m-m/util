/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.util.contenttype.base.DecisionState;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.io.api.spi.DetectorStreamBuffer;

/**
 * This is the base class of a {@link Segment} that represents a format pattern (or pieces of such) to detect
 * a {@link net.sf.mmm.util.contenttype.api.ContentType}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "segment")
@XmlAccessorType(XmlAccessType.NONE)
public abstract class Segment {

  /** @see #getParent() */
  private SegmentContainer parent;

  /**
   * The constructor.
   */
  public Segment() {

    super();
  }

  /**
   * This method gets the parent {@link SegmentContainer} containing this segment.
   * 
   * @return the parent {@link SegmentContainer} or <code>null</code> if this is the top-level sequence.
   */
  public SegmentContainer getParent() {

    return this.parent;
  }

  /**
   * This method sets the {@link #getParent() parent} of this {@link Segment}.
   * 
   * @param parent is the new parent.
   */
  protected void setParent(SegmentContainer parent) {

    this.parent = parent;
  }

  /**
   * This method gets the minimum length of this {@link Segment}.
   * 
   * @return the minimum length.
   */
  public abstract long getMinimumLength();

  /**
   * This method gets the maximum length of this {@link Segment}. Simple {@link Segment}s like
   * {@link SegmentConstant} have a fixed length. In this case the result of this method is the same as
   * {@link #getMinimumLength()}. Otherwise, the {@link Segment} is variable and this method returns a value
   * greater than {@link #getMinimumLength()}. Use {@link Long#MAX_VALUE} for an unbound segment like
   * {@link SegmentAny}.
   * 
   * @return the maximum length.
   */
  public long getMaximumLength() {

    return getMinimumLength();
  }

  /**
   * This method gets the XML tag-name of this {@link Segment}.
   * 
   * @return the tag-name.
   */
  protected abstract String getTagName();

  /**
   * This method gets an identifier of this {@link Segment} for the source used in potential error messages.
   * 
   * @return the source identifier.
   */
  protected String getSourceIdentifier() {

    return getTagName();
  }

  /**
   * This method validates this segment recursively to ensure the correctness of the configured format.
   * 
   * @param source describes the source of the validation.
   */
  public final void validate(StringBuilder source) {

    int length = source.length();
    source.append('/');
    source.append(getSourceIdentifier());
    doValidate(source);
    // reset source for recursive invocation...
    source.setLength(length);
  }

  /**
   * This method validates this segment recursively to ensure the correctness of the configured format.
   * 
   * @param source describes the source of the validation.
   */
  protected void doValidate(StringBuilder source) {

    validateNonRecursive(source);
  }

  /**
   * This method validates this segment to ensure the correctness of the configured format. <br>
   * <b>ATTENTION:</b><br>
   * This method does NOT perform a recursive validation of potential {@link SegmentContainer#getSegment(int)
   * sub-segments}.
   * 
   * @param source describes the source of the validation.
   */
  protected void validateNonRecursive(StringBuilder source) {

    if (getMinimumLength() < 0) {
      source.append(".length");
      throw new NlsIllegalArgumentException(Long.valueOf(getMinimumLength()), source.toString());
    }
    if (getMaximumLength() < getMinimumLength()) {
      source.append(".maxLength");
      throw new NlsIllegalArgumentException(Long.valueOf(getMaximumLength()), source.toString());
    }
  }

  /**
   * 
   * @param state
   * @param stateList
   * @param buffer
   * @param metadata
   * @param eos
   * @return
   */
  // public abstract boolean detect(DecisionState state, List<DecisionState> stateList,
  public boolean detect(DecisionState state, List<DecisionState> stateList, DetectorStreamBuffer buffer,
      Map<String, Object> metadata, boolean eos) {

    return false;
  }

}
