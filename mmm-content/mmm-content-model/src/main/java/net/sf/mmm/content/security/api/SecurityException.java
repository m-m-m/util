/* $Id$ */
package net.sf.mmm.content.security.api;

import net.sf.mmm.nls.base.NlsException;

/**
 * This exception is used if a
 * {@link net.sf.mmm.content.api.ContentObject content-object} was accessed
 * violating a security constraint.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SecurityException extends NlsException {

  /** uid for serialization */
  private static final long serialVersionUID = 3257285846510940725L;

  /**
   * @see NlsException#NlsException(String, Object[])
   */
  public SecurityException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsException#NlsException(Throwable, String, Object[])
   */
  public SecurityException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
