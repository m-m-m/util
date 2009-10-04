/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.base;

import javax.annotation.Resource;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.content.parser.api.ContentParser} that supports the
 * {@link LimitBufferSize} feature.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParser extends AbstractContentParserBase {

  /** @see #doInitialize() */
  private ContentParserRegistrar contentParserRegistrar;

  /**
   * The constructor.
   */
  public AbstractContentParser() {

    super();
  }

  /**
   * @param contentParserRegistrar is the contentParserRegistrar to set
   */
  @Resource
  public void setContentParserRegistrar(ContentParserRegistrar contentParserRegistrar) {

    getInitializationState().requireNotInitilized();
    this.contentParserRegistrar = contentParserRegistrar;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.contentParserRegistrar != null) {
      this.contentParserRegistrar.addParser(this, getRegistryKeys());
    }
  }

}
