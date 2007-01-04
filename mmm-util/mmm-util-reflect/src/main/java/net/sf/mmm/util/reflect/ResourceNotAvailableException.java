/* $Id$ */
package net.sf.mmm.util.reflect;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * This exception is thrown if a resource was requested that is NOT available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ResourceNotAvailableException extends NlsRuntimeException {

  /** UID for serialization */
  private static final long serialVersionUID = -356811274649703298L;

  /**
   * The constructor
   * 
   * @param absolutePath
   *        is the absolute path of the resource that could NOT be found.
   */
  public ResourceNotAvailableException(String absolutePath) {

    super(NlsResourceBundle.ERR_RESOURCE_NOT_AVAILABLE, absolutePath);
  }

}
