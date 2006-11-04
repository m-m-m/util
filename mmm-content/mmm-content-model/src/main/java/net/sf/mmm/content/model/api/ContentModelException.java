/* $Id$ */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.nls.base.NlsException;

/**
 * This exception is used if something went wrong with the content model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelException extends ContentException {

  /** uid for serialization */
  private static final long serialVersionUID = 3544669568611071536L;

  /**
   * @see NlsException#NlsException(String, Object[])
   * 
   */
  public ContentModelException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsException#NlsException(Throwable, String, Object[])
   * 
   */
  public ContentModelException(Throwable nested, String internaitionalizedMessage,
      Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
