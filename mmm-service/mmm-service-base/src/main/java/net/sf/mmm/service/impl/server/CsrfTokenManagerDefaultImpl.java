/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.server;

import java.util.UUID;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.api.DefaultCsrfToken;
import net.sf.mmm.service.base.server.AbstractCsrfTokenManager;
import net.sf.mmm.service.base.server.CsrfTokenManager;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This is the default implementation of {@link CsrfTokenManager}. It generates a {@link java.util.UUID}
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(CsrfTokenManager.CDI_NAME)
public class CsrfTokenManagerDefaultImpl extends AbstractCsrfTokenManager {

  /**
   * The constructor.
   */
  public CsrfTokenManagerDefaultImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CsrfToken generateInitialToken() {

    HttpSession session = getSession();
    if (session == null) {
      throw new IllegalStateException("Failed to get or create HTTP session!");
    }
    String key = CsrfToken.class.getName();
    CsrfToken token = (CsrfToken) session.getAttribute(key);
    if (token == null) {
      token = generateNewToken();
      session.setAttribute(key, token);
    }
    return token;
  }

  /**
   * @return a newly generated {@link CsrfToken}.
   */
  private CsrfToken generateNewToken() {

    return new DefaultCsrfToken(UUID.randomUUID().toString());
  }

  /**
   * @return the {@link HttpSession} or <code>null</code> if no request context is available.
   */
  protected HttpSession getSession() {

    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes != null) {
      HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
      return request.getSession(true);
    }
    return null;
  }
}
