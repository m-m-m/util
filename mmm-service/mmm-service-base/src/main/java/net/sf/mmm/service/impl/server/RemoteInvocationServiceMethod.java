/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.server;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.reflect.api.AccessFailedException;

/**
 * This is a simple container for a {@link Method} of a {@link RemoteInvocationService}.
 * 
 * @author hohwille
 * @since 1.0.0
 * @param <SERVICE> is the generic type of the {@link #getServiceInterface() service interface}.
 */
class RemoteInvocationServiceMethod<SERVICE extends RemoteInvocationService> {

  /** @see #getServiceInterface() */
  private final Class<SERVICE> serviceInterface;

  /** @see #getServiceImplementation() */
  private final SERVICE serviceImplementation;

  /** @see #getMethod() */
  private final Method method;

  /** @see #getSignature() */
  private final int signature;

  /**
   * The constructor.
   * 
   * @param serviceInterface - see {@link #getServiceInterface()}.
   * @param serviceImplementation - see {@link #getServiceImplementation()}.
   * @param method - see {@link #getMethod()}.
   */
  public RemoteInvocationServiceMethod(Class<SERVICE> serviceInterface, SERVICE serviceImplementation, Method method) {

    super();
    this.serviceInterface = serviceInterface;
    this.serviceImplementation = serviceImplementation;
    this.method = method;
    this.signature = RemoteInvocationServiceCall.getSignature(method.getParameterTypes());
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
  public Method getMethod() {

    return this.method;
  }

  /**
   * @return the {@link RemoteInvocationServiceCall#getSignature() signature}.
   */
  public int getSignature() {

    return this.signature;
  }

  /**
   * This method gets an identifier for this service-method.
   * 
   * @return the ID.
   */
  public String getId() {

    return getId(this.serviceInterface.getName(), this.method.getName(), this.signature);
  }

  /**
   * This method gets an identifier for the method specified by the given <code>call</code>.
   * 
   * @param call is the {@link RemoteInvocationServiceCall}.
   * @return the ID.
   */
  public static String getId(RemoteInvocationServiceCall call) {

    return getId(call.getServiceInterfaceName(), call.getMethodName(), call.getSignature());
  }

  /**
   * This method gets an identifier for the method with the given parameters.
   * 
   * @param serviceName is the {@link Class#getName() name} of the {@link RemoteInvocationService}.
   * @param methodName is the {@link Method#getName() method name}.
   * @param signature is the {@link RemoteInvocationServiceCall#getSignature() signature}.
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
   * This method {@link Method#invoke(Object, Object...) invokes} this {@link RemoteInvocationServiceMethod}
   * for the given <code>arguments</code>.
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
      if ((result != null) && !(result instanceof Serializable)) {
        throw new ObjectMismatchException(result.getClass(), Serializable.class, getId(), "return-type");
      }
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
  public String toString() {

    return getId();
  }

}
