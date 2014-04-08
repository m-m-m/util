/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.date.api.Iso8601UtilLimited;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.nls.api.NlsArgumentParser;
import net.sf.mmm.util.nls.api.NlsMessageFormatterFactory;
import net.sf.mmm.util.nls.base.AbstractNlsDependencies;
import net.sf.mmm.util.nls.base.NlsArgumentFormatter;
import net.sf.mmm.util.nls.base.NlsDependencies;
import net.sf.mmm.util.nls.impl.formatter.NlsArgumentFormatterImpl;

/**
 * This is the implementation of the {@link NlsDependencies} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named(NlsDependencies.CDI_NAME)
public class NlsDependenciesImpl extends AbstractNlsDependencies {

  /** @see #getArgumentParser() */
  private NlsArgumentParser argumentParser;

  /** @see #getArgumentFormatter() */
  private NlsArgumentFormatter argumentFormatter;

  /** @see #getMessageFormatterFactory() */
  private NlsMessageFormatterFactory messageFormatterFactory;

  /** @see #getIso8601Util() */
  private Iso8601UtilLimited iso8601Util;

  /**
   * The constructor.
   */
  public NlsDependenciesImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsArgumentParser getArgumentParser() {

    return this.argumentParser;
  }

  /**
   * @param argumentParser is the {@link NlsArgumentParser} to {@link Inject}.
   */
  @Inject
  public void setArgumentParser(NlsArgumentParser argumentParser) {

    getInitializationState().requireNotInitilized();
    this.argumentParser = argumentParser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsArgumentFormatter getArgumentFormatter() {

    return this.argumentFormatter;
  }

  /**
   * @param argumentFormatter is the {@link NlsArgumentFormatter} to {@link Inject}.
   */
  @Inject
  public void setArgumentFormatter(NlsArgumentFormatter argumentFormatter) {

    getInitializationState().requireNotInitilized();
    this.argumentFormatter = argumentFormatter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsMessageFormatterFactory getMessageFormatterFactory() {

    return this.messageFormatterFactory;
  }

  /**
   * @param messageFormatterFactory is the {@link NlsMessageFormatterFactory} to {@link Inject}.
   */
  @Inject
  public void setMessageFormatterFactory(NlsMessageFormatterFactory messageFormatterFactory) {

    getInitializationState().requireNotInitilized();
    this.messageFormatterFactory = messageFormatterFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iso8601UtilLimited getIso8601Util() {

    return this.iso8601Util;
  }

  /**
   * @param iso8601Util is the {@link Iso8601UtilLimited} to {@link Inject}.
   */
  @Inject
  public void setIso8601Util(Iso8601UtilLimited iso8601Util) {

    getInitializationState().requireNotInitilized();
    this.iso8601Util = iso8601Util;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.argumentParser == null) {
      throw new ResourceMissingException(NlsArgumentParser.class.getName());
    }
    if (this.iso8601Util == null) {
      this.iso8601Util = Iso8601UtilImpl.getInstance();
    }
    if (this.argumentFormatter == null) {
      NlsArgumentFormatterImpl impl = new NlsArgumentFormatterImpl();
      impl.initialize();
      this.argumentFormatter = impl;
    }
    if (this.messageFormatterFactory == null) {
      NlsMessageFormatterFactoryImpl impl = new NlsMessageFormatterFactoryImpl();
      impl.setDependencies(this);
      impl.initialize();
      this.messageFormatterFactory = impl;
    }
  }

}
