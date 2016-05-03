/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.impl;

import java.util.Map;

import net.sf.mmm.util.contenttype.api.ContentType;
import net.sf.mmm.util.contenttype.base.DecisionTreeNode;
import net.sf.mmm.util.io.api.spi.DetectorStreamBuffer;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DecisionTreeNodeImpl implements DecisionTreeNode {

  /** @see #getContentType() */
  private ContentType contentType;

  private Map<Byte, DecisionTreeNodeImpl> byte2childNodeMap;

  /**
   * The constructor.
   */
  public DecisionTreeNodeImpl() {

    super();
  }

  @Override
  public ContentType getContentType() {

    return this.contentType;
  }

  /**
   * @param contentType is the contentType to set
   */
  public void setContentType(ContentType contentType) {

    this.contentType = contentType;
  }

  @Override
  public DecisionTreeNode detect(DetectorStreamBuffer buffer, Map<String, Object> metadata, boolean eos) {

    // TODO Auto-generated method stub
    return null;
  }

}
