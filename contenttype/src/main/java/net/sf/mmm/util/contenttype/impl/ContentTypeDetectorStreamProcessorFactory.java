/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.impl;

import java.util.List;

import net.sf.mmm.util.contenttype.base.ContentTypeBean;
import net.sf.mmm.util.contenttype.base.DecisionTreeNode;
import net.sf.mmm.util.io.api.spi.DetectorStreamProcessor;
import net.sf.mmm.util.io.base.AbstractDetectorStreamProcessorFactory;

/**
 * This is the implementation of {@link net.sf.mmm.util.io.api.spi.DetectorStreamProcessorFactory} for
 * {@link ContentTypeDetectorStreamProcessor}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentTypeDetectorStreamProcessorFactory extends AbstractDetectorStreamProcessorFactory {

  /** The top-level {@link DecisionTreeNode}. */
  private final DecisionTreeNode rootNode;

  private final int lookaheadCount;

  /**
   * The constructor.
   *
   * @param rootNode is the top-level {@link DecisionTreeNode}.
   * @param lookaheadCount - see {@link #getLookaheadCount()}.
   */
  public ContentTypeDetectorStreamProcessorFactory(DecisionTreeNode rootNode, int lookaheadCount) {

    super();
    this.rootNode = rootNode;
    this.lookaheadCount = lookaheadCount;
  }

  @Override
  public int getLookaheadCount() {

    return this.lookaheadCount;
  }

  @Override
  public DetectorStreamProcessor createProcessor() {

    List<ContentTypeBean> contentTypes = null;
    return new ContentTypeDetectorStreamProcessor(contentTypes);
  }

}
