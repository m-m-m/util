/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.session.api;

import java.util.Locale;

import org.slf4j.LoggerFactory;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.session.base.UserSessionProvider;
import net.sf.mmm.util.session.base.UserSessionProviderDefaultImpl;

/**
 * This class gives static {@link #getSession() access} to the current {@link UserSession}. By default the
 * implementation is based on spring but this small facade was created to support environments where no springframework
 * is available such as GWT (that does not even support {@link ThreadLocal}). In GWT contexts a GWT compatible
 * implementation is provided automatically.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public final class UserSessionAccess {

  /**
   * Construction forbidden.
   */
  private UserSessionAccess() {

    super();
  }

  private static UserSessionProvider provider;

  /**
   * @return the {@link UserSessionProvider} instance.
   */
  private static UserSessionProvider getProvider() {

    if (provider == null) {
      synchronized (UserSessionAccess.class) {
        UserSessionProviderDefaultImpl impl = new UserSessionProviderDefaultImpl();
        impl.initialize();
      }
    }
    return provider;
  }

  /**
   * @param sessionProvder is the {@link UserSessionProvider} to set.
   */
  private static void setProvider(UserSessionProvider sessionProvder) {

    if (provider != null) {
      LoggerFactory.getLogger(UserSessionAccess.class)
          .warn("ATTENTION: replacing existing UserSessionProvider {} with {}!", provider, sessionProvder);
    }
    synchronized (UserSessionAccess.class) {
      provider = sessionProvder;
    }
  }

  /**
   * @see UserSessionProvider#getCurrentSession()
   * @return the current {@link UserSession}.
   */
  public static UserSession getSession() {

    return getProvider().getCurrentSession();
  }

  /**
   * @return the login of the current user (e.g. "john.doe"). Will be {@code null} if called outside the scope of a
   *         current user session.
   */
  public static String getUserLogin() {

    return getSession().getLogin();
  }

  /**
   * @return {@link #getSession()}.{@link UserSession#getLocale() getLocale()}.
   */
  public static Locale getUserLocale() {

    return getSession().getLocale();
  }

  /**
   * The abstract base implementation of {@link UserSessionProvider}. It automatically registers itself in
   * {@link UserSessionAccess} on {@link #initialize() initialization}.
   */
  public abstract static class AbstractUserSessionProvider extends AbstractLoggableComponent
      implements UserSessionProvider {

    /**
     * The constructor.
     */
    public AbstractUserSessionProvider() {

      super();
    }

    @Override
    protected void doInitialized() {

      super.doInitialized();
      setProvider(this);
    }

  }

}
