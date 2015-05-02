/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base;

import java.util.Map;

import net.sf.mmm.util.contenttype.api.ContentType;
import net.sf.mmm.util.io.api.spi.DetectorStreamBuffer;

/**
 * A {@link DecisionTreeNode} represents the state of the detection of a
 * {@link ContentType}. Out of the {@link ContentTypeBean#getFormat() format
 * descriptions} of all {@link ContentType}s a decision tree is generated for
 * fast and efficient detection of the {@link ContentType}. Therefore each
 * {@link DecisionTreeNode} represents a current state of detection and is
 * {@link #getContentType() associated} with the {@link ContentType} best
 * matching the current data. When new {@link DetectorStreamBuffer data} is
 * available, the {@link #detect(DetectorStreamBuffer, Map, boolean) detection}
 * can be continued. Step by step a more detailed {@link DecisionTreeNode} is
 * returned until the final {@link ContentType} is discovered or it is ensured
 * that the data does not match any of the known {@link ContentType}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DecisionTreeNode {

  /**
   * This method gets the {@link ContentType} associated with this node (as far
   * as detected). If nothing has yet been detected, the root
   * {@link ContentType} shall be returned.
   * 
   * @return the associated {@link ContentType}.
   */
  ContentType getContentType();

  /**
   * This method processes the given <code>buffer</code> to proceed with the
   * detection of the content-type.
   * 
   * @see net.sf.mmm.util.io.api.spi.DetectorStreamProcessor#process(DetectorStreamBuffer,
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
  DecisionTreeNode detect(DetectorStreamBuffer buffer, Map<String, Object> metadata, boolean eos);

}
