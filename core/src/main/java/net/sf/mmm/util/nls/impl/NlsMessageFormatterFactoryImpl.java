/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.nls.api.NlsMessageFormatter;
import net.sf.mmm.util.nls.api.NlsMessageFormatterFactory;
import net.sf.mmm.util.nls.base.NlsDependencies;
import net.sf.mmm.util.nls.impl.formatter.NlsMessageFormatterImpl;

/**
 * This is the implementation of the {@link NlsMessageFormatterFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named
public class NlsMessageFormatterFactoryImpl extends AbstractComponent implements NlsMessageFormatterFactory {

  /** @see #getDependencies() */
  private NlsDependencies dependencies;

  /**
   * The constructor.
   */
  public NlsMessageFormatterFactoryImpl() {

    super();
  }

  @Override
  public NlsMessageFormatter create(String message) {

    return new NlsMessageFormatterImpl(message, this.dependencies);
  }

  /**
   * @return the {@link NlsDependencies}.
   */
  protected NlsDependencies getDependencies() {

    return this.dependencies;
  }

  /**
   * @param nlsDependencies is the nlsDependencies to set
   */
  @Inject
  public void setDependencies(NlsDependencies nlsDependencies) {

    getInitializationState().requireNotInitilized();
    this.dependencies = nlsDependencies;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.dependencies == null) {
      NlsDependenciesImpl impl = new NlsDependenciesImpl();
      impl.setMessageFormatterFactory(this);
      impl.initialize();
      this.dependencies = impl;
    }
  }

}
