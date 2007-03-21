/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path.condition;

import net.sf.mmm.configuration.base.AbstractConfiguration;

/**
 * This is the implementation of the {@link Condition} interface that always
 * {@link #accept(AbstractConfiguration, String) matches}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NoCondition implements Condition {

  /**
   * The constructor
   */
  public NoCondition() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public boolean accept(AbstractConfiguration configuration, String namespaceUri) {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean canBeEstablished(AbstractConfiguration configuration, String namespaceUri) {
  
    return true;
  }
  
  /**
   * {@inheritDoc}
   */
  public AbstractConfiguration establish(AbstractConfiguration configuration, String namespaceUri) {

    return configuration;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
  
    return "";
  }

}
