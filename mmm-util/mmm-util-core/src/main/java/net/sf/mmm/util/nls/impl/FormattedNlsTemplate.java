/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsMessageFormatter;
import net.sf.mmm.util.nls.base.AbstractNlsTemplate;
import net.sf.mmm.util.nls.base.NlsDependencies;
import net.sf.mmm.util.nls.impl.formatter.NlsMessageFormatterImpl;

/**
 * This class is an abstract implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsTemplate} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class FormattedNlsTemplate extends AbstractNlsTemplate {

  /** @see #createFormatter(String, Locale) */
  private NlsDependencies nlsDependencies;

  /**
   * The constructor.
   * 
   * @param nlsDependencies are the {@link NlsDependencies} to use.
   */
  public FormattedNlsTemplate(NlsDependencies nlsDependencies) {

    super();
    this.nlsDependencies = nlsDependencies;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NlsMessageFormatter createFormatter(String messageTemplate, Locale locale) {

    return new NlsMessageFormatterImpl(messageTemplate, this.nlsDependencies);
  }

  /**
   * This method gets the {@link NlsDependencies} to use.
   * 
   * @return the {@link NlsDependencies}.
   */
  protected NlsDependencies getNlsDependencies() {

    return this.nlsDependencies;
  }

}
