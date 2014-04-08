/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.server;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.lang.api.concern.Security;

/**
 * This is the interface for the manager of {@link CsrfToken}s.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface CsrfTokenManager extends Security {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.service.base.server.XsrfTokenManager";

  /**
   * Checks if the given {@link CsrfToken} that has been sent from the client is valid. This method has to
   * correspond to {@link #generateInitialToken()} and {@link #generateUpdateToken(CsrfToken)}. In case a
   * remote invocation is invoked that is secured (requires authentication and typically also authorization),
   * the {@link CsrfToken} has to be checked. A value of <code>null</code> is never valid and will always
   * fail. Only in case of a secured invocation and the presence of {@link CsrfToken} this method is invoked.
   *
   * @param token is the {@link CsrfToken} send from the client. Will not be <code>null</code>.
   * @return <code>true</code> if the given <code>token</code> is valid, <code>false</code> otherwise (in case
   *         of an CSRF attack or some technical bug).
   */
  boolean isValidToken(CsrfToken token);

  /**
   * @see #isValidToken(CsrfToken)
   *
   * @param token is the token to validate.
   * @throws SecurityException if the token is not {@link #isValidToken(CsrfToken) valid}.
   */
  void validateToken(CsrfToken token) throws SecurityException;

  /**
   * This method generates a new {@link CsrfToken} for the initial "log-in" of a user. Here are some examples
   * of possible implementation strategies:
   * <ul>
   * <li>A token is generated that simply contains the session ID. If an attacker can force the browser to
   * send a request to your service and after the user has already logged into your application the browser
   * will send authentication data for the request (CSRF). However, the attacker can not easily access your
   * session ID due to the SOP (Same Origin Policy).</li>
   * <li>A token is generated with a unique ID that the client can not guess such as a {@link java.util.UUID}.
   * The token is also stored in the server-side HTTP session so it can be compared for
   * {@link #isValidToken(CsrfToken) validation}.</li>
   * <li>A token is generated from a combination of different aspects (e.g. user login, user agent,
   * server-secret, etc.) and digitally signed an encrypted. This approach can also be used in situations
   * where no server-side HTTP session exists (e.g. with only HTTP Basic Authentication) as the
   * {@link #isValidToken(CsrfToken) validation} can decrypt the token, split the aspects and verify them.</li>
   * </ul>
   * In case you create your own implementation be aware of the following things:
   * <ul>
   * <li>The IP address of the user can legally change from one request to another, e.g. if the user enters or
   * leaves a VPN. On the other hand attackers can easily fake IP addresses so relying on IP address often
   * reduces user comfort without gaining a very much higher level of {@link Security}.</li>
   * <li>Do not use {@link java.util.Random} to generate security tokens as this is too weak.</li>
   * </ul>
   *
   * @return the initial {@link CsrfToken}. Shall not be <code>null</code>.
   */
  CsrfToken generateInitialToken();

  /**
   * This method generates a new {@link CsrfToken} or updates an existing one-time {@link CsrfToken}.
   *
   * @see #generateInitialToken()
   *
   * @param currentToken is the current {@link CsrfToken} that has previously been generated and may be
   *        updated.
   * @return the <code>currentToken</code> (same instance) to keep the token or a new instance of
   *         {@link CsrfToken} to replace the current token and expect the next request from the client to
   *         provide that new token (e.g. to implement one-time tokens for highest level of protection).
   */
  CsrfToken generateUpdateToken(CsrfToken currentToken);

}
