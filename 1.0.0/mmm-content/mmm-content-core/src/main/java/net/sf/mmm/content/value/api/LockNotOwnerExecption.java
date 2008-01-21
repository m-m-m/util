/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

import net.sf.mmm.content.NlsBundleContentCore;

/**
 * This is the exception thrown if a {@link Lock} should be modified that is
 * owned by a different user.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LockNotOwnerExecption extends LockException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1279286143554262402L;

  /**
   * The constructor.
   * 
   * @param owner is the owner of the lock.
   * @param user is the current user that wanted to modify the lock.
   * @param object is the locked object.
   */
  public LockNotOwnerExecption(String owner, String user, String object) {

    super(NlsBundleContentCore.ERR_LOCK_NOT_OWNER, owner, user, object);
  }

}
