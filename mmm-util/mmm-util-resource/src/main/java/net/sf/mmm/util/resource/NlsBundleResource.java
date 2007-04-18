/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource;

import net.sf.mmm.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the configuration
 * subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleResource extends AbstractResourceBundle {

  /**
   * The constructor. 
   */
  public NlsBundleResource() {

    super();
  }

  /**
   * Exception message if a resource was requested that is NOT available.
   * 
   * @see ResourceNotAvailableException
   */
  public static final String ERR_RESOURCE_NOT_AVAILABLE = "The resource \"{0}\" is not available in your classpath!";

}
