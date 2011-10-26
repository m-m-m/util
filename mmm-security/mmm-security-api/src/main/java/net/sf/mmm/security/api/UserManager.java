/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.security.api;

import java.security.Principal;

/**
 * This is the interface for a manager of the users of an application. Many
 * end-point technologies such as {@link javax.servlet.Servlet} typically
 * authenticate a user identified by a login. However, a user is more than a
 * string. Further, the responsibility for the logic to identify a
 * {@link Principal user object} for the authenticated login (e.g.
 * {@link javax.servlet.http.HttpServletRequest#getRemoteUser()}) should
 * typically belong to the application, rather than an application-server (or
 * servlet-container) - see e.g.
 * {@link javax.servlet.http.HttpServletRequest#getUserPrincipal()}). Assume you
 * need to read the user from a database and want to do this is the transaction
 * managed by your business logic, then it makes sense to use an application
 * component for this case. This is what this interface is for.<br>
 * <b>ATTENTION:</b><br/>
 * However you should NOT bypass the authentication mechanism of
 * application-server and do that manually. However if you use a single-sign-on
 * authentication (e.g. kerberos) but your application is only accessible for a
 * subset of users managed by the SSO directory, the {@link UserManager} may
 * reject {@link #getUser(String) logins} that have been successfully been
 * authenticated by your application-server. However, it depends on the point of
 * view if you call this authentication or authorization.
 * 
 * @param <USER> is the generic type of the managed {@link #getUser(String)
 *        users}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UserManager<USER extends Principal> {

  /**
   * This method gets the user for the given <code>login</code>.
   * 
   * @param login is the login of the user (user ID).
   * @return the requested user.
   * @throws SecurityException if the given user does not exist or is invalid
   *         for arbitrary reasons.
   */
  USER getUser(String login) throws SecurityException;

}
