/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.session.api;

import java.security.Principal;
import java.util.Locale;

/**
 * This interface extends {@link UserSession} with write operations to modify the user settings. <br>
 * <b>ATTENTION:</b><br>
 * It is NOT always guaranteed, that {@link UserSessionAccess#getSession()} will provide an instance that can be cast to
 * {@link MutableUserSession}. Further, only central place(s) in the code should modify the {@link UserSession} to
 * ensure consistency.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface MutableUserSession extends UserSession {

  /**
   * @param locale is the new value of {@link #getLocale()}.
   */
  void setLocale(Locale locale);

  /**
   * Sets the {@link #getUser() current user}. <br>
   * <b>ATTENTION:</b><br>
   * This method is not intended for end-users to avoid getting used to strange design. This is just a back-door
   * required for specific limited (client) environments where the user has to be set explicitly. Typically you will
   * just use a security solution such as spring-security and {@link UserSession} will give you abstracted access on it
   * throughout the application. Setting the user after successful login is to be done in a single place of the
   * application and should directly happen via the underlying technology.
   *
   * @param user is the new value of {@link #getUser()}
   * @throws UnsupportedOperationException if not supported.
   */
  void setUser(Principal user) throws UnsupportedOperationException;

}
