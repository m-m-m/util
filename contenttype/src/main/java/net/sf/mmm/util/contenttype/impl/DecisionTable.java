/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.impl;

import java.util.Map;

import net.sf.mmm.util.contenttype.base.ContentTypeBean;
import net.sf.mmm.util.contenttype.base.format.Segment;
import net.sf.mmm.util.contenttype.base.format.SegmentConstant;
import net.sf.mmm.util.contenttype.base.format.SegmentContainerSequence;
import net.sf.mmm.util.io.api.spi.DetectorStreamBuffer;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DecisionTable extends AbstractDecision {

  /** @see #detect(DetectorStreamBuffer, Map, boolean) */
  private final DecisionTreeNodeImpl[] table;

  /**
   * The constructor.
   */
  public DecisionTable() {

    super();
    this.table = new DecisionTreeNodeImpl[256];
  }

  @Override
  public DecisionTreeNodeImpl detect(DetectorStreamBuffer buffer, Map<String, Object> metadata, boolean eos) {

    int next = buffer.next();
    return this.table[next];
  }

  @Override
  public AbstractDecision merge(ContentTypeBean contentType, SegmentContainerSequence segment, int segmentIndex) {

    Segment s = segment.getSegment(segmentIndex);
    if (s instanceof SegmentConstant) {

    }
    return this;
  }
}
