/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * This class represents a {@link Segment} for a metadata value. The value of
 * this segment will be added to the metadata during detection associated with
 * the {@link #getKey() key}. If a {@link SegmentValue} has a
 * {@link #getMaximumLength() variable length} then it is terminated as soon as
 * the next {@link Segment} is matched (what is typically a
 * {@link SegmentConstant}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SegmentValue extends SegmentVariableLength {

  /** The XML tag name for this object. */
  public static final String XML_TAG = "value";

  /** @see #getKey() */
  @XmlAttribute(name = "key", required = false)
  private String key;

  /**
   * The constructor.
   */
  public SegmentValue() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getTagName() {

    return XML_TAG;
  }

  /**
   * This method gets the key to associate this value in the
   * {@link net.sf.mmm.util.io.api.DetectorStream#getMetadata() detected
   * metadata}. <br/>
   * The key may also be <code>null</code> to indicate a variable key depending
   * on the streamed data. Then the last {@link SegmentKey} that matched before
   * is used to determine the key. If there is no such {@link SegmentKey} the
   * format specification is invalid.
   * 
   * @return the key or <code>null</code>.
   */
  public String getKey() {

    return this.key;
  }

}
