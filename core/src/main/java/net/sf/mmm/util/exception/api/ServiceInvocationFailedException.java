/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import java.util.UUID;

import net.sf.mmm.util.exception.NlsBundleUtilExceptionRoot;

/**
 * {@link NlsRuntimeException} thrown if a service invocation failed.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.5.0
 */
public class ServiceInvocationFailedException extends NlsRuntimeException {

  private static final long serialVersionUID = 1L;

  private final String code;

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message}.
   * @param code the {@link #getCode() code}.
   * @param uuid {@link UUID} the {@link #getUuid() UUID}.
   * @param service the name (e.g. {@link Class#getName() qualified name}) of the service that failed.
   */
  public ServiceInvocationFailedException(String message, String code, UUID uuid, String service) {

    this(null, message, code, uuid, service);
  }

  /**
   * The constructor.
   *
   * @param cause the {@link #getCause() cause} of this exception.
   * @param message the {@link #getMessage() message}.
   * @param code the {@link #getCode() code}.
   * @param uuid {@link UUID} the {@link #getUuid() UUID}.
   * @param service the name (e.g. {@link Class#getName() qualified name}) of the service that failed.
   */
  public ServiceInvocationFailedException(Throwable cause, String message, String code, UUID uuid, String service) {

    super(cause, createBundle(NlsBundleUtilExceptionRoot.class).errorServiceInvocationFailed(service, message), uuid);
    if (code == null) {
      this.code = "ServiceInvoke";
    } else {
      this.code = code;
    }
  }

  @Override
  public String getCode() {

    return this.code;
  }

  @Override
  public boolean isForUser() {

    return true;
  }

}
