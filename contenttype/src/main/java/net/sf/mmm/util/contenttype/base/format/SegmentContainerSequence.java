/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A {@link SegmentContainerSequence} is a {@link SegmentContainer} that matches
 * if all the {@link #getSegment(int) contained} {@link Segment}s match the
 * stream in the order of occurrence. It is used to concat smaller
 * {@link Segment}s to compose bigger parts up to the final format pattern.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "sequence")
public class SegmentContainerSequence extends SegmentContainer {

  /** The XML tag name for this object. */
  public static final String XML_TAG = "sequence";

  /**
   * The constructor.
   */
  public SegmentContainerSequence() {

    super();
  }

  @Override
  protected String getTagName() {

    return XML_TAG;
  }

}
