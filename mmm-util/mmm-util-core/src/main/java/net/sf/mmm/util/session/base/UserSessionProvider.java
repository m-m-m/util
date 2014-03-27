/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.session.base;

import net.sf.mmm.util.session.api.UserSession;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface UserSessionProvider {

  /**
   * Gets the current {@link UserSession} according to the current environment and client or server context.
   *
   * @return the current {@link UserSession}.
   */
  UserSession getCurrentSession();

}
