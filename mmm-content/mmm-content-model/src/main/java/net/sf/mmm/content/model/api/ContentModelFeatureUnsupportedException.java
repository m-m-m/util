/* $Id$ */
package net.sf.mmm.content.model.api;

import net.sf.mmm.nls.base.NlsException;

/**
 * This is the exception thrown if a feature of the content model was invoked
 * that is not supported.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelFeatureUnsupportedException extends ContentModelException {

  /** uid for serialization */
  private static final long serialVersionUID = 3258689914253423670L;

  /**
   * @see NlsException#NlsException(String, Object[])
   * 
   */
  public ContentModelFeatureUnsupportedException(String internaitionalizedMessage,
      Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsException#NlsException(Throwable, String, Object[])
   * 
   */
  public ContentModelFeatureUnsupportedException(Throwable nested,
      String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
