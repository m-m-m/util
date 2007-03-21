/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.access;

import net.sf.mmm.configuration.api.access.ConfigurationAccess;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.api.access.ConfigurationAccess} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationAccess implements ConfigurationAccess {

  /** @see #getContextPrefix() */
  private String prefix;

  /**
   * The constructor.
   */
  public AbstractConfigurationAccess() {

    super();
    this.prefix = null;
  }

  /**
   * @see #getContextPrefix()
   * 
   * @param contextPrefix
   */
  public void setContextPrefix(String contextPrefix) {

    this.prefix = contextPrefix;
  }

  /**
   * {@inheritDoc}
   */
  public String getContextPrefix() {

    return this.prefix;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getUniqueUri();
  }

}
