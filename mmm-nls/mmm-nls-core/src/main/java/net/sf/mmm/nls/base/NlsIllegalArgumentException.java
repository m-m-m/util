/* $Id$ */
package net.sf.mmm.nls.base;

import net.sf.mmm.nls.api.NlsMessage;

/**
 * A {@link NlsIllegalArgumentException} is analog to an
 * {@link IllegalArgumentException} but with native language support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsIllegalArgumentException extends NlsRuntimeException {

  /** UID for serialization */
  private static final long serialVersionUID = -1537683835966488723L;

  /**
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   */
  public NlsIllegalArgumentException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
   */
  public NlsIllegalArgumentException(Throwable nested, String internaitionalizedMessage,
      Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsRuntimeException#NlsRuntimeException(NlsMessage)
   */
  public NlsIllegalArgumentException(NlsMessage internationalizedMessage) {

    super(internationalizedMessage);
  }

  /**
   * @see NlsRuntimeException#NlsRuntimeException(Throwable, NlsMessage)
   */
  public NlsIllegalArgumentException(Throwable nested, NlsMessage internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

}
