/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.security.api;

import java.security.Principal;

/**
 * This is the interface for a component that manages the <em>authorization</em>
 * of an application. The term authorization means that it is
 * {@link #verifyPermission(Principal, Object) verified} that a given user is
 * permitted to perform a given operation.<br>
 * <b>ATTENTION:</b><br/>
 * Never mix positive (granting) and negative (denying) permission concepts. It
 * is strongly recommended to follow the principle that everything is forbidden
 * by default and configured permissions grant a user access to an operation.
 * 
 * @param <USER> is the generic type of the users to authorize.
 * @param <OPERATION> is the generic type of the operations to authorize.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AuthorizationManager<USER extends Principal, OPERATION> {

  /**
   * This method determines if the given <code>user</code> is permitted to
   * perform the given <code>operation</code>.
   * 
   * @see #verifyPermission(Principal, Object)
   * 
   * @param user is the user to authorize.
   * @param operation is the operation the user intends to perform.
   * @return <code>true</code> if the <code>user</code> is permitted to perform
   *         the given <code>operation</code>, <code>false</code> otherwise.
   */
  boolean isPermitted(USER user, OPERATION operation);

  /**
   * This method verifies that the given <code>user</code>
   * {@link #isPermitted(Principal, Object) is permitted} to perform the given
   * <code>operation</code>.
   * 
   * @param user is the user to authorize.
   * @param operation is the operation the user intends to perform.
   * @throws SecurityException if the given <code>user</code> is NOT permitted
   *         to perform the given <code>operation</code>.
   */
  void verifyPermission(USER user, OPERATION operation) throws SecurityException;

}
