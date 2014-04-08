/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.api.DefaultCsrfToken;
import net.sf.mmm.service.base.server.AbstractCsrfTokenManager;
import net.sf.mmm.service.base.server.CsrfTokenManager;

/**
 * This is a dummy implementation of {@link CsrfTokenManager} as replacement for testing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CsrfTokenManagerDummyImpl extends AbstractCsrfTokenManager {

  /** @see #generateInitialToken() */
  public static final CsrfToken DUMMY_TOKEN = new DefaultCsrfToken("test-secret");

  /**
   * The constructor.
   *
   */
  public CsrfTokenManagerDummyImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CsrfToken generateInitialToken() {

    return DUMMY_TOKEN;
  }

}
