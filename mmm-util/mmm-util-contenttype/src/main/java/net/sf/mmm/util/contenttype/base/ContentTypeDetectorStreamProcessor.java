/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base;

import java.io.IOException;
import java.util.Map;

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

  /** The current state of detection or <code>null</code>. */
  private DecisionTreeNode node;

  /**
   * The constructor.
   * 
   * @param rootNode is the the top-level {@link DecisionTreeNode}.
   */
  public ContentTypeDetectorStreamProcessor(DecisionTreeNode rootNode) {

    super();
    this.node = rootNode;
  }

  /**
   * {@inheritDoc}
   */
  public void process(DetectorStreamBuffer buffer, Map<String, Object> metadata, boolean eos)
      throws IOException {

    if (this.node != null) {
      this.node = this.node.detect(buffer, metadata, eos);
    }
  }

}
