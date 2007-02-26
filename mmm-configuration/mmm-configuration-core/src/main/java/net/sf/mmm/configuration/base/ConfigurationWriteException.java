/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import net.sf.mmm.configuration.NlsBundleConfigCore;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;

/**
 * This is the exception thrown if a configuration file was not found.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationWriteException extends ConfigurationException {

  /** uid for serialization */
  private static final long serialVersionUID = 4441678946509285043L;

  /**
   * The constructor.
   * 
   * @param access
   *        is the access where to read the configuration from.
   */
  public ConfigurationWriteException(ConfigurationAccess access) {

    super(NlsBundleConfigCore.ERR_WRITE, access);
  }

  /**
   * The constructor.
   * 
   * @param access
   *        is the access where to read the configuration from.
   * @param nested
   *        is the throwable that caused this exception.
   */
  public ConfigurationWriteException(ConfigurationAccess access, Throwable nested) {

    super(NlsBundleConfigCore.ERR_WRITE, access, nested);
  }

}
