/* $Id$ */
package net.sf.mmm.util.reflect;

import net.sf.mmm.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the configuration
 * subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsResourceBundle extends AbstractResourceBundle {

  /**
   * The constructor
   */
  public NlsResourceBundle() {

    super();
  }

  /**
   * Exception message if a resource was requested that is NOT available.
   * 
   * @see ResourceNotAvailableException
   */
  public static final String ERR_RESOURCE_NOT_AVAILABLE = "The resource \"{0}\" is not available in your classpath!";

}
