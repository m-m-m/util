/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

import java.io.Serializable;

/**
 * This is the generic transfer-object for an invocation of a {@link net.sf.mmm.service.api.RemoteInvocationService}. It
 * contains the data for a single method call.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public class RemoteInvocationServiceCall implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = 595577849271182509L;

  /** @see #getServiceInterfaceName() */
  private String serviceInterfaceName;

  /** @see #getMethodName() */
  private String methodName;

  /** @see #getSignature() */
  private String signature;

  /** @see #getArguments() */
  private Serializable[] arguments;

  /**
   * The constructor for (de)serialization.
   */
  protected RemoteInvocationServiceCall() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param serviceInterfaceName - see {@link #getServiceInterfaceName()}.
   * @param methodName - see {@link #getMethodName()}.
   * @param signature - see {@link #getSignature()}.
   * @param arguments - see {@link #getArguments()}.
   */
  public RemoteInvocationServiceCall(String serviceInterfaceName, String methodName, String signature, Serializable[] arguments) {

    super();
    this.serviceInterfaceName = serviceInterfaceName;
    this.methodName = methodName;
    this.signature = signature;
    this.arguments = arguments;
  }

  /**
   * @return the {@link Class#getName() qualified classname} of the {@link net.sf.mmm.service.api.RemoteInvocationService}
   *         interface to invoke.
   */
  public String getServiceInterfaceName() {

    return this.serviceInterfaceName;
  }

  /**
   * @return the {@link java.lang.reflect.Method#getName() name of the method} to invoke.
   */
  public String getMethodName() {

    return this.methodName;
  }

  /**
   * @return a technical identifier for the method signature to distinguish methods with the same
   *         {@link #getMethodName() name}.
   */
  public String getSignature() {

    return this.signature;
  }

  /**
   * @return the array containing the arguments of the method invocation.
   */
  public Serializable[] getArguments() {

    return this.arguments;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return toString(false);
  }

  /**
   * @see #toString()
   * 
   * @param hideArguments - <code>true</code> if the {@link #getArguments() arguments} should be hidden e.g.
   *        to prevent data security issues when logging, <code>false</code> if the {@link #getArguments()
   *        arguments} shall be included.
   * @return the string representation.
   */
  public String toString(boolean hideArguments) {

    StringBuilder buffer = new StringBuilder(this.serviceInterfaceName);
    buffer.append('.');
    buffer.append(this.methodName);
    buffer.append('(');
    String separator = "";
    if (this.arguments != null) {
      for (Serializable arg : this.arguments) {
        buffer.append(separator);
        separator = ", ";
        if (hideArguments) {
          buffer.append("...");
        } else {
          buffer.append(arg);
        }
      }
    }
    buffer.append(')');
    return buffer.toString();
  }

}
