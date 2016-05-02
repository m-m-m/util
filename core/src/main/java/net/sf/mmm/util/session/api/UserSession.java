/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.session.api;

import java.security.Principal;
import java.util.Locale;

/**
 * This is the interface for a simple user session.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface UserSession {

  /**
   * @return the current user logged in or {@code null} as {@link #isFallback() fallback}.
   */
  Principal getUser();

  /**
   * @return the login of the current user or {@code null} as {@link #isFallback() fallback}.
   */
  String getLogin();

  /**
   * @return the {@link Locale} of the current user, or the {@link Locale#getDefault() system default locale}
   *         as {@link #isFallback() fallback}.
   */
  Locale getLocale();

  /**
   * This method determines if this {@link UserSession} was {@link UserSessionAccess#getSession() received} or
   * is currently called outside the scope of a current user session. On the server side a {@link UserSession}
   * typically exists in the context of a servlet container or JEE application server. If the context is not
   * available this method will return {@code true}. This may be because the user is currently not logged in (anonymous access) or the context is not available for technical reasons (e.g. misconfiguration).
   *
   * @return {@code true} if {@link UserSessionAccess#getSession() received} or called outside the scope
   *         of a current user session, {@code false} otherwise.
   */
  boolean isFallback();

}
