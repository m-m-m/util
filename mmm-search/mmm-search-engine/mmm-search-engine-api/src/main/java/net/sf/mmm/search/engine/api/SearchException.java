/* $Id$ */
package net.sf.mmm.search.engine.api;

import net.sf.mmm.nls.api.NlsMessage;
import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * This is a technical exception that can be thrown by the {@link SearchEngine}
 * and related objects. The {@link SearchEngine} implementation is suggested to
 * be tolerant (e.g. accept malformed queries). Anyways in some situations (e.g.
 * {@link java.io.IOException}) it can be neccessary to throw an exception.
 * Therefore this is the suggested exception type to be used.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchException extends NlsRuntimeException {

  /** UID for serialization */
  private static final long serialVersionUID = -2903854338698104923L;

  /**
   * The constructor
   * 
   * @param internaitionalizedMessage
   * @param arguments
   */
  public SearchException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * The constructor
   * 
   * @param nested
   * @param internaitionalizedMessage
   * @param arguments
   */
  public SearchException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

  /**
   * The constructor
   * 
   * @param internationalizedMessage
   */
  public SearchException(NlsMessage internationalizedMessage) {

    super(internationalizedMessage);
  }

  /**
   * The constructor
   * 
   * @param nested
   * @param internationalizedMessage
   */
  public SearchException(Throwable nested, NlsMessage internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

}
