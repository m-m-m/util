/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.rpc.server;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;

import net.sf.mmm.service.api.rpc.RemoteInvocationService;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcCall;
import net.sf.mmm.service.base.server.AbstractGenericRemoteInvocationService;
import net.sf.mmm.service.base.server.GenericRemoteInvocationCallHandler;
import net.sf.mmm.util.reflect.api.AccessFailedException;

/**
 * This is the implementation of {@link GenericRemoteInvocationCallHandler} for an
 * {@link GenericRemoteInvocationRpcCall}. It therefore contains a {@link Method} of a
 * {@link RemoteInvocationService}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <SERVICE> is the generic type of the {@link #getServiceInterface() service interface}.
 */
public class GenericRemoteInvocationRpcCallHandler<SERVICE extends RemoteInvocationService> extends
    GenericRemoteInvocationCallHandler<GenericRemoteInvocationRpcCall> {

  /** @see #getServiceInterface() */
  private final Class<SERVICE> serviceInterface;

  /** @see #getServiceImplementation() */
  private final SERVICE serviceImplementation;

  /** @see #getServiceMethod() */
  private final Method method;

  /** @see #getSignature() */
  private final int signature;

  /**
   * The constructor.
   *
   * @param serviceInterface - see {@link #getServiceInterface()}.
   * @param serviceImplementation - see {@link #getServiceImplementation()}.
   * @param method - see {@link #getServiceMethod()}.
   * @param genericService - see {@link #getGenericService()}.
   */
  public GenericRemoteInvocationRpcCallHandler(Class<SERVICE> serviceInterface, SERVICE serviceImplementation,
      AbstractGenericRemoteInvocationService<?, ?, ?, ?> genericService, Method method) {

    this(serviceInterface, serviceImplementation, genericService, method, GenericRemoteInvocationRpcCall
        .getSignature(method.getParameterTypes()));
  }

  /**
   * The constructor.
   *
   * @param serviceInterface - see {@link #getServiceInterface()}.
   * @param serviceImplementation - see {@link #getServiceImplementation()}.
   * @param method - see {@link #getServiceMethod()}.
   * @param genericService - see {@link #getGenericService()}.
   * @param signature - see {@link #getSignature()}.
   */
  private GenericRemoteInvocationRpcCallHandler(Class<SERVICE> serviceInterface, SERVICE serviceImplementation,
      AbstractGenericRemoteInvocationService<?, ?, ?, ?> genericService, Method method, int signature) {

    super(getId(serviceInterface.getName(), method.getName(), signature), genericService);
    this.serviceInterface = serviceInterface;
    this.serviceImplementation = serviceImplementation;
    this.method = method;
    this.signature = signature;
  }

  /**
   * @return the serviceInterface
   */
  public Class<SERVICE> getServiceInterface() {

    return this.serviceInterface;
  }

  /**
   * @return the service
   */
  public SERVICE getServiceImplementation() {

    return this.serviceImplementation;
  }

  /**
   * @return the method
   */
  @Override
  public Method getServiceMethod() {

    return this.method;
  }

  /**
   * @return the {@link GenericRemoteInvocationRpcCall#getSignature() signature}.
   */
  public int getSignature() {

    return this.signature;
  }

  /**
   * This method gets an identifier for this service-method.
   *
   * @return the ID.
   */
  @Override
  public String getId() {

    return getId(this.serviceInterface.getName(), this.method.getName(), this.signature);
  }

  /**
   * This method gets an identifier for the method specified by the given <code>call</code>.
   *
   * @param call is the {@link GenericRemoteInvocationRpcCall}.
   * @return the ID.
   */
  public static String getId(GenericRemoteInvocationRpcCall call) {

    return getId(call.getServiceInterfaceName(), call.getMethodName(), call.getSignature());
  }

  /**
   * This method gets an identifier for the method with the given parameters.
   *
   * @param serviceName is the {@link Class#getName() name} of the {@link RemoteInvocationService}.
   * @param methodName is the {@link Method#getName() method name}.
   * @param signature is the {@link GenericRemoteInvocationRpcCall#getSignature() signature}.
   * @return the ID.
   */
  static String getId(String serviceName, String methodName, int signature) {

    StringBuilder buffer = new StringBuilder(serviceName);
    buffer.append('.');
    buffer.append(methodName);
    buffer.append('@');
    buffer.append(signature);
    return buffer.toString();
  }

  /**
   * This method {@link Method#invoke(Object, Object...) invokes} this
   * {@link GenericRemoteInvocationRpcCallHandler} for the given <code>arguments</code>.
   *
   * @param arguments are the method parameters.
   * @return the result.
   * @throws Throwable in case of any error.
   */
  public Serializable invoke(Serializable[] arguments) throws Throwable {

    Object result;
    try {
      Object[] args = arguments;
      result = this.method.invoke(this.serviceImplementation, args);
      return (Serializable) result;
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, this.method);
    } catch (InvocationTargetException e) {
      throw e.getCause();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Set<? extends ConstraintViolation<?>> doValidate(GenericRemoteInvocationRpcCall call) {

    // MethodValidator methodValidator = getGenericService().getValidator().unwrap(MethodValidator.class);
    // return methodValidator.validateAllParameters(this.serviceImplementation, this.method,
    // call.getArguments());
    return Collections.emptySet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object invoke(GenericRemoteInvocationRpcCall call) throws Exception {

    Object[] args = call.getArguments();
    return this.method.invoke(this.serviceImplementation, args);
  }

}
