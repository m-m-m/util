/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;

/**
 * A {@link SegmentContainerRepeat} is a {@link SegmentContainerSequence} that can be repeated. The occurrences of the
 * actual sequence is bound by {@link #getMinimumOccurrence()} and {@link #getMaximumOccurrence()}. <br>
 * The following example shows a metadata definition of key/value pairs ("KEY=VALUE;") that are terminated by '\0'.
 * There has to be at least 1 and at maximum 99 of such key/value pairs to match.
 *
 * <pre>
 * ...
 * &lt;repeat minOccure="1" maxOccure="99">
 * &lt;key>
 * &lt;constant ascii="="/>
 * &lt;value>
 * &lt;constant ascii=";"/>
 * &lt;/repeat>
 * &lt;constant hex="00"/>
 * ..
 * </pre>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SegmentContainerRepeat extends SegmentContainerSequence {

  /** The XML tag name for this object. */
  @SuppressWarnings("hiding")
  public static final String XML_TAG = "repeat";

  private static final String XML_ATTRIBUTE_MINIMUM_OCCURRENCE = "min";

  private static final String XML_ATTRIBUTE_MAXIMUM_OCCURRENCE = "max";

  @XmlAttribute(name = "XML_ATTRIBUTE_MINIMUM_OCCURRENCE")
  private int minimumOccurrence;

  private int maximumOccurrence;

  /**
   * The constructor.
   */
  public SegmentContainerRepeat() {

    super();
  }

  @Override
  protected String getTagName() {

    return XML_TAG;
  }

  /**
   *
   * @return the minimum repeat count.
   */
  public int getMinimumOccurrence() {

    return this.minimumOccurrence;
  }

  /**
   * This method gets the maximum occurrence of this repeated sequence.
   *
   * @return the maximum occurrence.
   */
  public int getMaximumOccurrence() {

    return this.maximumOccurrence;
  }

  @Override
  public long getMinimumLength() {

    return super.getMinimumLength() * getMinimumOccurrence();
  }

  @Override
  public long getMaximumLength() {

    if (this.maximumOccurrence == Long.MAX_VALUE) {
      return Long.MAX_VALUE;
    }
    return super.getMaximumLength() * getMaximumOccurrence();
  }

  @Override
  protected void validateNonRecursive(StringBuilder source) {

    super.validateNonRecursive(source);
    if (this.minimumOccurrence < 0) {
      source.append('.');
      source.append(XML_ATTRIBUTE_MINIMUM_OCCURRENCE);
      throw new NlsIllegalArgumentException(Long.valueOf(this.minimumOccurrence), source.toString());
    }
    if (this.maximumOccurrence < this.minimumOccurrence) {
      source.append('.');
      source.append(XML_ATTRIBUTE_MAXIMUM_OCCURRENCE);
      throw new NlsIllegalArgumentException(Long.valueOf(this.maximumOccurrence), source.toString());
    }
  }

}
