/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import net.sf.mmm.configuration.NlsBundleConfigCore;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationException;

/**
 * This is the exception thrown if a general error occurred in a
 * {@link Configuration}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class GeneralConfigurationException extends ConfigurationException {

  /** UID for serialization */
  private static final long serialVersionUID = -6453342129988661990L;

  /**
   * The constructor
   * 
   * @param nested
   *        is the {@link #getCause() cause} of this exception.
   * @param location
   *        is the configuration where the error occurred.
   */
  public GeneralConfigurationException(Throwable nested, Configuration location) {

    super(nested, NlsBundleConfigCore.ERR_GENERAL, location);
  }

  /**
   * The constructor
   * 
   * @param nested
   *        is the {@link #getCause() cause} of this exception.
   * @param location
   *        is the configuration where the error occurred.
   */
  public GeneralConfigurationException(Throwable nested, String location) {

    super(nested, NlsBundleConfigCore.ERR_GENERAL, location);
  }

}
