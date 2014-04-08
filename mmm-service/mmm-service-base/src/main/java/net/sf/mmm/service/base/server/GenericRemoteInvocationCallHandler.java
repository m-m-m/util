/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.server;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import net.sf.mmm.service.api.RemoteInvocationCall;
import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the abstract base class for a generic handler to {@link #invoke(RemoteInvocationCall) invoke}
 * {@link RemoteInvocationCall}s from the client on the server-side.
 *
 * @param <CALL> is the generic type of the {@link RemoteInvocationCall}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class GenericRemoteInvocationCallHandler<CALL extends RemoteInvocationCall> implements
    AttributeReadId<String> {

  /** @see #getId() */
  private final String id;

  /** @see #isSecured() */
  private Boolean secured;

  /** @see #isLogin() */
  private Boolean login;

  /** @see #getGenericService() */
  private final AbstractGenericRemoteInvocationService<?, ?, ?, ?> genericService;

  /**
   * The constructor.
   *
   * @param id - see {@link #getId()}.
   * @param genericService - see {@link #getGenericService()}.
   */
  public GenericRemoteInvocationCallHandler(String id, AbstractGenericRemoteInvocationService<?, ?, ?, ?> genericService) {

    super();
    this.id = id;
    this.genericService = genericService;
  }

  /**
   * @return the {@link AbstractGenericRemoteInvocationService generic remote invocation service}.
   */
  public AbstractGenericRemoteInvocationService<?, ?, ?, ?> getGenericService() {

    return this.genericService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return this.id;
  }

  /**
   * @see RemoteInvocationSecurityDetector#isLogin(java.lang.reflect.AnnotatedElement)
   * @return <code>true</code> if login operation, <code>false</code> otherwise.
   */
  public boolean isLogin() {

    if (this.login == null) {
      this.login = Boolean.valueOf(this.genericService.getSecurityDetector().isLogin(getServiceMethod()));
    }
    return this.login.booleanValue();
  }

  /**
   * @return the {@link Method} of the remote invocation.
   */
  protected abstract Method getServiceMethod();

  /**
   * @see RemoteInvocationSecurityDetector#isSecured(java.lang.reflect.AnnotatedElement)
   * @return <code>true</code> if secured operation, <code>false</code> otherwise.
   */
  public boolean isSecured() {

    if (this.secured == null) {
      this.secured = Boolean.valueOf(this.genericService.getSecurityDetector().isSecured(getServiceMethod()));
    }
    return this.secured.booleanValue();
  }

  /**
   * Invokes the given {@link RemoteInvocationCall}.
   *
   * @param call is the {@link RemoteInvocationCall} to execute.
   * @return the result of the invocation.
   * @throws Exception if anything goes wrong.
   */
  public abstract Object invoke(CALL call) throws Exception;

  /**
   * Performs a (pre-)validation of the given {@link RemoteInvocationCall}. Will typically be called outside a
   * transaction. Complex business and state validation that requires a transaction has to be performed inside
   * the actual {@link #invoke(RemoteInvocationCall) invocation}.
   *
   * @param call is the {@link RemoteInvocationCall} to validate.
   * @throws Exception if the given <code>call</code> is invalid.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void validate(CALL call) throws Exception {

    Set<? extends ConstraintViolation<?>> violations = doValidate(call);
    handleConstraintViolations((Set) violations);
  }

  /**
   * Called from {@link #validate(RemoteInvocationCall)} to delegate to JSR303 (javax.validation).
   *
   * @param call is the {@link RemoteInvocationCall} to validate.
   * @return the {@link Set} of {@link ConstraintViolation}s. Will be {@link Set#isEmpty() empty} if valid.
   */
  protected abstract Set<? extends ConstraintViolation<?>> doValidate(CALL call);

  /**
   * Handles the result of a {@link #validate(RemoteInvocationCall) validation}.
   *
   * @param violations is the {@link Set} of {@link ConstraintViolation}s.
   */
  protected void handleConstraintViolations(Set<ConstraintViolation<?>> violations) {

    if (violations.size() > 0) {
      throw new ConstraintViolationException(violations);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.id;
  }

}
