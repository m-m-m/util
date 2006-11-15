/* $Id$ */
package net.sf.mmm.content.security.api;

import net.sf.mmm.content.NlsResourceBundle;
import net.sf.mmm.content.api.ContentObject;

/**
 * This exception is thrown if a content-user tried to perfom an action on an
 * {@link ContentObject content-object} without having the appropriate
 * permissions.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PermissionDeniedException extends SecurityException {

  /** uid for serialization */
  private static final long serialVersionUID = 3256727294587712817L;

  /**
   * The constructor.
   * 
   * @param subject
   *        is the user that wanted to perform the permitted operation.
   * @param action
   *        is the action that was permitted.
   * @param object
   *        is the content-object on which the action was permitted.
   */
  public PermissionDeniedException(String subject, String action, String object) {

    super(NlsResourceBundle.ERR_PERMISSION_DENIED, subject, action, object);
  }

  /**
   * This method gets the user that wanted to perform the permitted operation.
   * 
   * @return the subject.
   */
  public String getSubject() {

    return (String) getNlsMessage().getArgument(0);
  }

  /**
   * This method gets the action that was permitted.
   * 
   * @return the action.
   */
  public String getAction() {

    return (String) getNlsMessage().getArgument(1);
  }

  /**
   * This method gets the content-object on which the action was permitted.
   * 
   * @return the object.
   */
  public String getObject() {

    return (String) getNlsMessage().getArgument(1);
  }
}
