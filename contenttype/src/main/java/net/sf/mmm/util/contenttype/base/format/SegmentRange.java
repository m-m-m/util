/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;

/**
 * A {@link SegmentRange} is a {@link Segment} that matches a value of a fixed
 * {@link #getMinimumLength() length} like {@link SegmentConstant} but the value
 * can be in a specific range from {@link #getBytesMinimum()} to
 * {@link #getBytesMaximum()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SegmentRange extends Segment {

  /** The XML tag name for this object. */
  public static final String XML_TAG = "range";

  private static final String XML_ATTRIBUTE_HEX_MIN = "hexMin";

  private static final String XML_ATTRIBUTE_HEX_MAX = "hexMax";

  @XmlAttribute(name = XML_ATTRIBUTE_HEX_MIN)
  private String hexMin;

  @XmlAttribute(name = XML_ATTRIBUTE_HEX_MAX)
  private String hexMax;

  private  byte[] bytesMinimum;

  private  byte[] bytesMaximum;

  /**
   * The constructor.
   */
  public SegmentRange() {

    super();
  }

  @Override
  protected String getTagName() {

    return XML_TAG;
  }

  /**
   * @return the min
   */
  public byte[] getBytesMinimum() {

    if (this.bytesMinimum == null) {
      this.bytesMinimum = SegmentConstant.parseBytes(this.hexMin, XML_ATTRIBUTE_HEX_MIN);
    }
    return this.bytesMinimum;
  }

  /**
   * @return the max
   */
  public byte[] getBytesMaximum() {

    if (this.bytesMaximum == null) {
      this.bytesMaximum = SegmentConstant.parseBytes(this.hexMax, XML_ATTRIBUTE_HEX_MAX);
    }
    return this.bytesMaximum;
  }

  @Override
  public long getMinimumLength() {

    return getBytesMinimum().length;
  }

  @Override
  protected void validateNonRecursive(StringBuilder source) {

    super.validateNonRecursive(source);
    int length = getBytesMinimum().length;
    if (length <= 0) {
      source.append(".min.length");
      throw new NlsIllegalArgumentException(Integer.valueOf(length), source.toString());
    }
    if (length != getBytesMaximum().length) {

      throw new NlsIllegalArgumentException(Integer.valueOf(length), "min.length != max.length["
          + getBytesMaximum().length + "]");
    }
  }

}
