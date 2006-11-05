/* $Id$ */
package net.sf.mmm.content.api;

import net.sf.mmm.nls.base.NlsException;

/**
 * This is the abstract base exception of all errors about the content.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class ContentException extends NlsException {

  /**
   * @see NlsException#NlsException(String, Object[])
   */
  public ContentException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsException#NlsException(Throwable, String, Object[])
   */
  public ContentException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
