/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.nls.api.NlsArgumentParser;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.impl.NlsFormatterManagerImpl;

/**
 * This is the abstract base implementation of the {@link NlsTemplateResolver}
 * interface.<br>
 * You should extend this class rather than directly implementing the
 * {@link NlsTemplateResolver} interface to gain compatibility with further
 * releases.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsTemplateResolver extends AbstractLoggable implements
    NlsTemplateResolver {

  /** @see #getArgumentParser() */
  private NlsArgumentParser argumentParser;

  /**
   * The constructor.<br>
   */
  public AbstractNlsTemplateResolver() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.argumentParser == null) {
      NlsFormatterManagerImpl impl = new NlsFormatterManagerImpl();
      impl.initialize();
      this.argumentParser = impl;
    }
  }

  /**
   * @return the argumentParser
   */
  protected NlsArgumentParser getArgumentParser() {

    return this.argumentParser;
  }

  /**
   * @param argumentParser is the argumentParser to set
   */
  @Inject
  public void setArgumentParser(NlsArgumentParser argumentParser) {

    getInitializationState().requireNotInitilized();
    this.argumentParser = argumentParser;
  }

}
