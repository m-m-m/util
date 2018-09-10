/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.session.base;

import java.security.Principal;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import net.sf.mmm.util.session.api.MutableUserSession;
import net.sf.mmm.util.session.api.UserSession;
import net.sf.mmm.util.session.api.UserSessionAccess.AbstractUserSessionProvider;

/**
 * This is the default implementation of {@link UserSessionProvider}. It simply delegates to according classes
 * provided by the spring framework.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class UserSessionProviderDefaultImpl extends AbstractUserSessionProvider implements MutableUserSession {

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
  public Authentication getUser() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return null;
    }
    return authentication;
  }

  @Override
  public void setUser(Principal user) {

    // we do not want to get developers used to do evil things. This is just a backdoor needed for specific
    // client environments such as GWT.
    throw new UnsupportedOperationException();
  }

  @Override
  public String getLogin() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return null;
    }
    return authentication.getName();
  }

  @Override
  public Locale getLocale() {

    return LocaleContextHolder.getLocale();
  }

  @Override
  public void setLocale(Locale locale) {

    LocaleContextHolder.setLocale(locale);
  }

  @Override
  public boolean isFallback() {

    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      return false;
    }
    return true;
  }

}
