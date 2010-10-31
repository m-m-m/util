/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterManagerImpl;

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
public abstract class AbstractNlsTemplateResolver extends AbstractLoggableComponent implements
    NlsTemplateResolver {

  /** @see #getNlsDependencies() */
  private NlsDependencies nlsDependencies;

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
    if (this.nlsDependencies == null) {
      NlsFormatterManagerImpl impl = new NlsFormatterManagerImpl();
      impl.initialize();
      this.nlsDependencies = impl.getNlsDependencies();
    }
  }

  /**
   * @return the {@link NlsDependencies}.
   */
  protected NlsDependencies getNlsDependencies() {

    return this.nlsDependencies;
  }

  /**
   * @param nlsDependencies are the {@link NlsDependencies} to set.
   */
  @Inject
  public void setNlsDependencies(NlsDependencies nlsDependencies) {

    getInitializationState().requireNotInitilized();
    this.nlsDependencies = nlsDependencies;
  }

}
