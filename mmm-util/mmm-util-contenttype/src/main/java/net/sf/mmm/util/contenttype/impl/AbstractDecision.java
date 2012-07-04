/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.impl;

import java.util.Map;

import net.sf.mmm.util.contenttype.base.ContentTypeBean;
import net.sf.mmm.util.contenttype.base.DecisionTreeNode;
import net.sf.mmm.util.contenttype.base.format.SegmentContainerSequence;
import net.sf.mmm.util.io.api.spi.DetectorStreamBuffer;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDecision {

  /**
   * The constructor.
   */
  public AbstractDecision() {

    super();
  }

  /**
   * @see net.sf.mmm.util.contenttype.base.DecisionTreeNode#detect(DetectorStreamBuffer,
   *      Map, boolean)
   * 
   * @param buffer is the {@link DetectorStreamBuffer} containing the next bytes
   *        to process.
   * @param metadata is a {@link Map} with metadata. New metadata read from the
   *        stream is added to this {@link Map} if NOT already defined. If
   *        metadata in this {@link Map} is already present before it is
   *        detected, the <code>buffer</code> shall be modified to reflect this
   *        metadata value.
   * @param eos - <code>true</code> if the end of the stream has been reached
   *        and the given <code>buffer</code> contains the remaining data.
   * @return the {@link DecisionTreeNode} representing the current state of
   *         detection or <code>null</code> if the stream does NOT match any
   *         known filetype.
   */
  public abstract DecisionTreeNodeImpl detect(DetectorStreamBuffer buffer,
      Map<String, Object> metadata, boolean eos);

  public abstract AbstractDecision merge(ContentTypeBean contentType, SegmentContainerSequence segment,
      int segmentIndex);

}
