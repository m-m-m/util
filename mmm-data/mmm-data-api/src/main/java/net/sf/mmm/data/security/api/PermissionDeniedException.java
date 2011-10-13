/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.security.api;

import net.sf.mmm.data.NlsBundleDataApi;

/**
 * This exception is thrown if a {@link ContentUser} tried to perform an
 * {@link ContentAction action} on an
 * {@link net.sf.mmm.data.api.ContentObject ContentObject} without having the
 * appropriate {@link ContentRule permissions}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PermissionDeniedException extends SecurityException {

  /** UID for serialization */
  private static final long serialVersionUID = 3256727294587712817L;

  /**
   * The constructor.
   * 
   * @param subject is the user that wanted to perform the permitted operation.
   * @param operation is the action that failed due to lack of permissions.
   * @param object is the object on which the <code>operation</code> should have
   *        been performed.
   */
  public PermissionDeniedException(String subject, String operation, String object) {

    super(NlsBundleDataApi.ERR_PERMISSION_DENIED, toMap(KEY_USER, subject, KEY_OPERATION,
        operation, KEY_OBJECT, object));
  }
}
