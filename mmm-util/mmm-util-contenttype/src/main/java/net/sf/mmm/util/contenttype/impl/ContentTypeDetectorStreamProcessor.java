/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.mmm.util.contenttype.base.ContentTypeBean;
import net.sf.mmm.util.contenttype.base.DecisionTreeNode;
import net.sf.mmm.util.contenttype.base.format.Segment;
import net.sf.mmm.util.contenttype.base.format.SegmentContainerSequence;
import net.sf.mmm.util.io.api.spi.DetectorStreamBuffer;
import net.sf.mmm.util.io.api.spi.DetectorStreamProcessor;

/**
 * This is an implementation of {@link DetectorStreamProcessor} that allows to
 * detect a {@link ContentTypeBean} using {@link DecisionTreeNode}.<br/>
 * <b>ATTENTION:</b><br/>
 * This implementation is stateful and therefore NOT thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentTypeDetectorStreamProcessor implements DetectorStreamProcessor {

  private final List<ContentTypeBean> contentTypes;

  private List<ContentTypeDetectorStreamProcessor.DecisionState> stateList;

  /**
   * The constructor.
   * 
   * @param rootNode is the the top-level {@link DecisionTreeNode}.
   */
  public ContentTypeDetectorStreamProcessor(List<ContentTypeBean> contentTypes) {

    super();
    this.contentTypes = contentTypes;
  }

  /**
   * {@inheritDoc}
   */
  public void process(DetectorStreamBuffer buffer, Map<String, Object> metadata, boolean eos)
      throws IOException {

    if (this.stateList == null) {
      this.stateList = new LinkedList<ContentTypeDetectorStreamProcessor.DecisionState>();
      for (ContentTypeBean contentType : this.contentTypes) {
        detectInitially(contentType);
      }
    } else {

    }
  }

  protected void detectInitially(ContentTypeBean contentType) {

    SegmentContainerSequence sequence = contentType.getFormat();
    int length = sequence.getSegmentCount();
    for (int i = 0; i < length; i++) {
      Segment child = sequence.getSegment(i);

    }
  }

  private static class DecisionState {

    private ContentTypeBean contentType;

    private Segment segment;

    private int segmentIndex;

  }

}
