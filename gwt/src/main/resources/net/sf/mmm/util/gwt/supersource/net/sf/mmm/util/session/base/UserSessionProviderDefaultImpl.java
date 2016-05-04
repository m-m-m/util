/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.session.base;

import java.security.Principal;
import java.util.Locale;

import net.sf.mmm.util.session.api.MutableUserSession;
import net.sf.mmm.util.session.api.UserSession;
import net.sf.mmm.util.session.api.UserSessionAccess.AbstractUserSessionProvider;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This is the default implementation of {@link UserSessionProvider}. It simply delegates to according classes
 * provided by the springframework.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class UserSessionProviderDefaultImpl extends AbstractUserSessionProvider implements MutableUserSession {

  private  Principal user;

  /**
   * The constructor.
   */
  public UserSessionProviderDefaultImpl() {

    super();
  }

  @Override
  public UserSession getCurrentSession() {

    return this;
  }

  @Override
  public Principal getUser() {

    return this.user;
  }

  @Override
  public void setUser(Principal user) {

    this.user = user;
  }

  @Override
  public String getLogin() {

    if (this.user == null) {
      return null;
    }
    return this.user.getName();
  }

  @Override
  public Locale getLocale() {

    return Locale.getDefault();
  }

  @Override
  public void setLocale(Locale locale) {

    Locale.setDefault(locale);
  }

  @Override
  public boolean isFallback() {

    if (this.user == null) {
      return true;
    }
    return false;
  }

}
