/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.session.base;

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

  /**
   * The constructor.
   */
  public UserSessionProviderDefaultImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserSession getCurrentSession() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLogin() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return null;
    }
    return authentication.getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Locale getLocale() {

    return LocaleContextHolder.getLocale();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLocale(Locale locale) {

    LocaleContextHolder.setLocale(locale);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFallback() {

    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      return false;
    }
    return true;
  }

}
