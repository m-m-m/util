/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.impl.NlsFormatterDefault;
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
public abstract class AbstractNlsTemplateResolver implements NlsTemplateResolver {

  /** The formatter manager to use. */
  private final NlsFormatterManager formatterManager;

  /**
   * The constructor.<br>
   */
  public AbstractNlsTemplateResolver() {

    super();
    this.formatterManager = createFormatterManager();
  }

  /**
   * This method creates the actual {@link NlsFormatterManager} used by this
   * implementation. It is called once from the constructor. You may override it
   * to add custom features.
   * 
   * @return the {@link NlsFormatterManager} to use.
   */
  protected NlsFormatterManager createFormatterManager() {

    NlsFormatter<Object> defaultFormatter = new NlsFormatterDefault(this);
    return new NlsFormatterManagerImpl(defaultFormatter);
  }

  /**
   * This method gets the {@link NlsFormatterManager} to use by the created
   * templates.
   * 
   * @return the formatterManager
   */
  public NlsFormatterManager getFormatterManager() {

    return this.formatterManager;
  }

}
