/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.base.AbstractNlsBundleFactory;

/**
 * This is the implementation of {@link net.sf.mmm.util.nls.api.NlsBundleFactory}.
 * 
 * @author hohwille
 * @since 3.0.0
 */
@Named
@Singleton
public class NlsBundleFactoryImpl extends AbstractNlsBundleFactory {

  /** @see #getMessageFactory() */
  private NlsMessageFactory messageFactory;

  /**
   * The constructor.
   */
  public NlsBundleFactoryImpl() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param classLoader is the {@link ClassLoader} to use.
   */
  public NlsBundleFactoryImpl(ClassLoader classLoader) {

    super(classLoader);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialized() {

    super.doInitialized();
    NlsAccess.setBundleFactory(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NlsMessageFactory getMessageFactory() {

    if (this.messageFactory == null) {
      return super.getMessageFactory();
    }
    return this.messageFactory;
  }

  /**
   * @param messageFactory is the messageFactory to set
   */
  @Inject
  public void setMessageFactory(NlsMessageFactory messageFactory) {

    getInitializationState().requireNotInitilized();
    this.messageFactory = messageFactory;
  }

}
