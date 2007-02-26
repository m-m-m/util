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
public class ConfigurationReadException extends ConfigurationException {

  /** uid for serialization */
  private static final long serialVersionUID = -3756630761535352289L;

  /**
   * The constructor.
   * 
   * @param access
   *        is the access where to read the configuration from.
   */
  public ConfigurationReadException(ConfigurationAccess access) {

    super(NlsBundleConfigCore.ERR_READ, access);
  }

  /**
   * The constructor.
   * 
   * @param location
   *        is the location (path, URL, etc.) where to read the configuration
   *        from.
   */
  public ConfigurationReadException(String location) {

    super(NlsBundleConfigCore.ERR_READ, location);
  }

  /**
   * The constructor.
   * 
   * @param access
   *        is the access where to read the configuration from.
   * @param nested
   *        is the throwable that caused this exception.
   */
  public ConfigurationReadException(ConfigurationAccess access, Throwable nested) {

    super(NlsBundleConfigCore.ERR_READ, access, nested);
  }

  /**
   * The constructor.
   * 
   * @param location
   *        is the location (path, URL, etc.) where to read the configuration
   *        from.
   * @param nested
   *        is the throwable that caused this exception.
   */
  public ConfigurationReadException(String location, Throwable nested) {

    super(NlsBundleConfigCore.ERR_READ, location, nested);
  }

}
