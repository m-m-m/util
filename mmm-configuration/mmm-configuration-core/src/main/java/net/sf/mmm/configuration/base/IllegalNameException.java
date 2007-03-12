/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import net.sf.mmm.configuration.NlsBundleConfigCore;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationException;

/**
 * This is the exception thrown if the {@link Configuration#getName() name} for
 * a {@link Configuration} is illegal.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IllegalNameException extends ConfigurationException {

  /** UID for serialization */
  private static final long serialVersionUID = -6453342129988661990L;

  /**
   * The constructor
   * 
   * @param name
   *        is the configuration {@link Configuration#getName() name} that is
   *        illegal. May be <code>null</code>.
   * @param location
   *        is the configuration where the error occurred.
   */
  public IllegalNameException(String name, Configuration location) {

    super(NlsBundleConfigCore.ERR_ILLEGAL_NAME, name, location);
  }

  /**
   * The constructor
   * 
   * @param name
   *        is the configuration {@link Configuration#getName() name} that is
   *        illegal. May be <code>null</code>.
   * @param location
   *        is the configuration where the error occurred.
   */
  public IllegalNameException(String name, String location) {

    super(NlsBundleConfigCore.ERR_ILLEGAL_NAME, name, location);
  }

}
