/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.security;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsBundleSecurityRoot extends NlsBundle {

  /**
   * @see net.sf.mmm.security.api.PermissionDeniedException
   *
   * @param user is the user who is not permitted.
   * @param operation is the operation that was not permitted.
   * @param object is the object the operation was denied on or <code>null</code> if not object specific.
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Permission denied for \"{user}\" performing \"{operation}\"{object,choice,(?==null)''(else)' on \"'{object}'\"'}!")
  NlsMessage errorPermissionDenied(@Named("user") Object user, @Named("operation") Object operation,
      @Named("object") Object object);

}
