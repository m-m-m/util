/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.mmm.util.nls.api.NlsArgumentParser;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.impl.NlsFormatterManagerImpl;
import net.sf.mmm.util.nls.impl.NlsMessageImpl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsMessageFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsMessageFactoryImpl extends AbstractNlsMessageFactory {

  /** @see #getArgumentParser() */
  private NlsArgumentParser argumentParser;

  /**
   * The constructor.
   */
  public NlsMessageFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(NlsTemplate template, Map<String, Object> messageArguments) {

    return new NlsMessageImpl(template, messageArguments, this.argumentParser);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(String internationalizedMessage, Map<String, Object> messageArguments) {

    return new NlsMessageImpl(internationalizedMessage, messageArguments, this.argumentParser);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.argumentParser == null) {
      NlsFormatterManagerImpl formatterManager = new NlsFormatterManagerImpl();
      formatterManager.initialize();
      this.argumentParser = formatterManager;
    }
  }

  /**
   * @return the argumentParser
   */
  public NlsArgumentParser getArgumentParser() {

    return this.argumentParser;
  }

  /**
   * @param argumentParser is the argumentParser to set
   */
  @Resource
  public void setArgumentParser(NlsArgumentParser argumentParser) {

    getInitializationState().requireNotInitilized();
    this.argumentParser = argumentParser;
  }

}
