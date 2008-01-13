/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsMessageFormatter;
import net.sf.mmm.util.nls.base.AbstractNlsTemplate;

/**
 * This class is an abstract implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsTemplate} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class FormattedNlsTemplate extends AbstractNlsTemplate {

  /**
   * The constructor.
   */
  public FormattedNlsTemplate() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NlsMessageFormatter createFormatter(String messageTemplate, Locale locale) {

    return new NlsMessageFormatterImpl(messageTemplate, getFormatterManager());
  }

  /**
   * This method gets the {@link NlsFormatterManager} to use.
   * 
   * @return the formatter manager.
   */
  protected NlsFormatterManager getFormatterManager() {

    return NlsFormatterManagerImpl.INSTANCE;
  }

}
