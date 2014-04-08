/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.server;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of {@link CsrfTokenManager}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractCsrfTokenManager extends AbstractLoggableComponent implements CsrfTokenManager {

  /**
   * The constructor.
   */
  public AbstractCsrfTokenManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CsrfToken generateUpdateToken(CsrfToken currentToken) {

    // by default no one-time tokens are required and initial token is sufficient
    return currentToken;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validateToken(CsrfToken token) throws SecurityException {

    if (!isValidToken(token)) {
      throw new SecurityException("Invalid XSRF token!");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValidToken(CsrfToken token) {

    return generateInitialToken().equals(token);
  }

}
