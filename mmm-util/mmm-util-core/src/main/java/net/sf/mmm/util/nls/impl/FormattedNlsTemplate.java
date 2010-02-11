/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsArgumentParser;
import net.sf.mmm.util.nls.api.NlsMessageFormatter;
import net.sf.mmm.util.nls.base.AbstractNlsTemplate;

/**
 * This class is an abstract implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsTemplate} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class FormattedNlsTemplate extends AbstractNlsTemplate {

  /** @see #createFormatter(String, Locale) */
  private NlsArgumentParser argumentParser;

  /**
   * The constructor.
   * 
   * @param argumentParser is the {@link #getArgumentParser() argument-parser}
   *        to use.
   */
  public FormattedNlsTemplate(NlsArgumentParser argumentParser) {

    super();
    this.argumentParser = argumentParser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NlsMessageFormatter createFormatter(String messageTemplate, Locale locale) {

    return new NlsMessageFormatterImpl(messageTemplate, getArgumentParser());
  }

  /**
   * This method gets the {@link NlsArgumentParser} to use.
   * 
   * @return the argumentParser
   */
  protected NlsArgumentParser getArgumentParser() {

    return this.argumentParser;
  }

}
