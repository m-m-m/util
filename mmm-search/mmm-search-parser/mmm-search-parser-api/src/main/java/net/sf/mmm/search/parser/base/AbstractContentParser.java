/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.base;

import net.sf.mmm.search.parser.api.ContentParser;

/**
 * This is the abstract base implemetation of a {@link ContentParser} that
 * supports the {@link LimitBufferSize} feature.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParser implements ContentParser, LimitBufferSize {

  /** @see #getMaximumBufferSize() */
  private int maximumBufferSize;

  /**
   * The constructor
   */
  public AbstractContentParser() {

    super();
    this.maximumBufferSize = DEFAULT_MAX_BUFFER_SIZE;
  }

  /**
   * @see net.sf.mmm.search.parser.base.LimitBufferSize#getMaximumBufferSize()
   */
  public int getMaximumBufferSize() {

    return this.maximumBufferSize;
  }

  /**
   * @see net.sf.mmm.search.parser.base.LimitBufferSize#setMaximumBufferSize(int)
   */
  public void setMaximumBufferSize(int maxBytes) {

    this.maximumBufferSize = maxBytes;
  }

}
