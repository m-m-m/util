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
   * @see net.sf.mmm.configuration.base.path.condition.Condition#accept(net.sf.mmm.configuration.base.AbstractConfiguration, String)
   */
  public boolean accept(AbstractConfiguration configuration, String namespaceUri) {

    return true;
  }

  /**
   * @see net.sf.mmm.configuration.base.path.condition.Condition#canBeEstablished(net.sf.mmm.configuration.base.AbstractConfiguration, java.lang.String)
   */
  public boolean canBeEstablished(AbstractConfiguration configuration, String namespaceUri) {
  
    return true;
  }
  
  /**
   * @see net.sf.mmm.configuration.base.path.condition.Condition#establish(net.sf.mmm.configuration.base.AbstractConfiguration, String)
   */
  public AbstractConfiguration establish(AbstractConfiguration configuration, String namespaceUri) {

    return configuration;
  }
  
  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
  
    return "";
  }

}
