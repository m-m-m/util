/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.api.session;

/**
 * This is the interface for the session of an application client.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ClientSession {

  /**
   * @return the login of the authenticated user or <code>null</code> if not available (anonymous user).
   */
  String getUserLogin();

  /**
   * @return the full name of the authenticated user. Will fallback to {@link #getUserLogin()} if not
   *         available.
   */
  String getUserName();

}
