/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration;

import net.sf.mmm.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the configuration
 * subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleConfigCore extends AbstractResourceBundle {

  /**
   * The constructor.
   */
  public NlsBundleConfigCore() {

    super();
  }

  /**
   * exception message if a node was tried to be changed that is not
   * {@link net.sf.mmm.configuration.api.MutableConfiguration#isEditable() editable}.
   * 
   * @see net.sf.mmm.configuration.api.ConfigurationNotEditableException
   */
  public static final String ERR_NODE_NOT_EDITABLE = "The configuration \"{0}\" is not editable!";

  /**
   * exception message if a configuration could not be read.
   * 
   * @see net.sf.mmm.configuration.base.ConfigurationReadException
   */
  public static final String ERR_READ = "Could not read configuration from \"{0}\"!";

  /**
   * exception message if a configuration could not be written.
   * 
   * @see net.sf.mmm.configuration.base.ConfigurationWriteException
   */
  public static final String ERR_WRITE = "Could not write configuration to \"{0}\"!";

  /**
   * exception message if a namespace was used on a node that does not support
   * namespaces.
   * 
   * @see net.sf.mmm.configuration.api.NamespacesUnsupportedException
   */
  public static final String ERR_NAMESPACES_UNSUPPORTED = "The configuration \"{0}\" with implementation \"{1}\" does not support namespaces!";

  /**
   * exception message if a cyclic inclusion was detected.
   * 
   * @see net.sf.mmm.configuration.base.AbstractConfigurationDocument
   */
  public static final String ERR_INCLUDE_CYCLE = "Cyclic inclusion: can not include \"{0}\" from \"{1}\"!";

  /**
   * general exception message for exception cascade.
   * 
   * @see net.sf.mmm.configuration.api.NamespacesUnsupportedException
   */
  public static final String ERR_ILLEGAL_NAME = "Illegal child name \"{0}\" in configuration \"{1}\"!";

  /**
   * general exception message for exception cascade.
   * 
   * @see net.sf.mmm.configuration.api.NamespacesUnsupportedException
   */
  public static final String ERR_GENERAL = "Error in configuration \"{0}\"!";

}
