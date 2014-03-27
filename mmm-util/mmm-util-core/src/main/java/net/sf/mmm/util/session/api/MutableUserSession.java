/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.session.api;

import java.util.Locale;

/**
 * This interface extends {@link UserSession} with write operations to modify the user settings.<br/>
 * <b>ATTENTION:</b><br/>
 * It is NOT always guaranteed, that {@link UserSessionAccess#getSession()} will provide an instance that can
 * be cast to {@link MutableUserSession}. Further, only central place(s) in the code should modify the
 * {@link UserSession} to ensure consistency.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface MutableUserSession extends UserSession {

  /**
   * @param locale is the new value of {@link #getLocale()}.
   */
  void setLocale(Locale locale);

}
