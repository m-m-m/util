/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.security.api;

import java.security.Principal;

/**
 * This is the interface for an {@link AuthorizationManager} that is is related
 * to the data object an operation is performed on. E.g. a user could be allowed
 * to modify customers but only if they are located in Germany.<br>
 * <b>ATTENTION:</b><br/>
 * Data related authorization should be avoided whenever possible. It makes
 * things complicated and can be a performance problem as it prevents from
 * caching strategies. Following the KISS ME (Keep It Small and Simple, Make it
 * Easy) principle you should try to avoid this.
 * 
 * @param <USER> is the generic type of the users to authorize.
 * @param <OPERATION> is the generic type of the operations to perform.
 * @param <OBJECT> is the generic type of the object to perform the operation
 *        on.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataRelatedAuthorizationManager<USER extends Principal, OPERATION, OBJECT> extends
    AuthorizationManager<USER, OPERATION> {

  /**
   * This method determines if the given <code>user</code> is permitted to
   * perform the given <code>operation</code> on the given <code>object</code>.
   * 
   * @see #verifyPermission(Principal, Object, Object)
   * 
   * @param user is the user to authorize.
   * @param operation is the operation the user intends to perform.
   * @param object is the object the user intends to perform the operation on.
   * @return <code>true</code> if the <code>user</code> is permitted to perform
   *         the given <code>operation</code>, <code>false</code> otherwise.
   */
  boolean isPermitted(USER user, OPERATION operation, OBJECT object);

  /**
   * This method verifies that the given <code>user</code>
   * {@link #isPermitted(Principal, Object, Object) is permitted} to perform the
   * given <code>operation</code> on the given <code>object</code>.
   * 
   * @param user is the user to authorize.
   * @param operation is the operation the user intends to perform.
   * @param object is the object the user intends to perform the operation on.
   * @throws SecurityException if the given <code>user</code> is NOT permitted
   *         to perform the given <code>operation</code>.
   */
  void verifyPermission(USER user, OPERATION operation, OBJECT object) throws SecurityException;

}
