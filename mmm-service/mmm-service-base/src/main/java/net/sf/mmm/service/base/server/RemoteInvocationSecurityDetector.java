/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.server;

import java.lang.reflect.AnnotatedElement;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a detector that can decide if a
 * {@link net.sf.mmm.service.api.RemoteInvocationCall} {@link #isSecured(AnnotatedElement) is secured} or
 * {@link #isLogin(AnnotatedElement) a login operation}.
 * 
 * @see CsrfTokenManager#generateInitialToken()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface RemoteInvocationSecurityDetector {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.service.base.server.RemoteInvocationSecurityDetector";

  /**
   * The name of {@link net.sf.mmm.service.api.rpc.RemoteInvocationService} {@link java.lang.reflect.Method}
   * representing a {@link #isLogin(AnnotatedElement) login operation}.
   */
  String LOGIN_METHOD_NAME = "login";

  /**
   * The {@link Class#getSimpleName() name} of {@link net.sf.mmm.service.api.command.RemoteInvocationCommand}
   * representing a {@link #isLogin(AnnotatedElement) login operation}.
   */
  String LOGIN_COMMAND_NAME = "LoginCommand";

  /**
   * Determines if a {@link net.sf.mmm.service.api.RemoteInvocationCall} is <em>secured</em>. Here secured
   * means that it can not be invoked before the {@link #isLogin(AnnotatedElement) login operation} has been
   * invoked and a {@link net.sf.mmm.service.api.CsrfToken} has been
   * {@link CsrfTokenManager#generateInitialToken() generated}. <br>
   * A typical implementation shall honor common standards such as JSR 250 so
   * {@link net.sf.mmm.service.api.RemoteInvocationCall} will be considered as secured if annotated with
   * {@link javax.annotation.security.RolesAllowed} and will not be secured if annotated with
   * {@link javax.annotation.security.PermitAll}.
   * 
   * @param operation is the {@link AnnotatedElement} to check. In case of a
   *        {@link net.sf.mmm.service.api.command.RemoteInvocationCommand} this will be the command class, in
   *        case of a {@link net.sf.mmm.service.api.rpc.RemoteInvocationService} operation this will be the
   *        {@link java.lang.reflect.Method}.
   * @return <code>true</code> if secured, <code>false</code> otherwise (no security required).
   */
  boolean isSecured(AnnotatedElement operation);

  /**
   * Determines if a {@link net.sf.mmm.service.api.RemoteInvocationCall} represents the login operation. It is
   * technically possible to have multiple login operations. However, for simplicity this should be avoided if
   * possible. A regular implementation should honor the {@link net.sf.mmm.service.api.Login} annotation as
   * well as conventions such as a {@link #LOGIN_METHOD_NAME} and {@link #LOGIN_COMMAND_NAME}.
   * 
   * @param operation is the {@link AnnotatedElement} to check. In case of a
   *        {@link net.sf.mmm.service.api.command.RemoteInvocationCommand} this will be the command class, in
   *        case of a {@link net.sf.mmm.service.api.rpc.RemoteInvocationService} operation this will be the
   *        {@link java.lang.reflect.Method}.
   * @return <code>true</code> if the given <code>command</code> represents the login operation,
   *         <code>false</code> otherwise.
   */
  boolean isLogin(AnnotatedElement operation);

}
