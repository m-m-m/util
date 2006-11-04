/* $Id$ */
package net.sf.mmm.configuration.api;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.configuration.api.Configuration configuration} error
 * occurs.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationException extends NlsRuntimeException {

  /** uid for serialization */
  private static final long serialVersionUID = -7724733961978667640L;

  /**
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   *      
   */
  public ConfigurationException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
   *      
   */
  public ConfigurationException(Throwable nested, String internaitionalizedMessage,
      Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
