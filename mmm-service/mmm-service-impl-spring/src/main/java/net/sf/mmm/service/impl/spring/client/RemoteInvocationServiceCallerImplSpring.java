/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.spring.client;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.inject.Inject;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.service.api.client.RemoteInvocationServiceResultCallback;
import net.sf.mmm.service.base.RemoteInvocationGenericService;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.service.base.client.AbstractRemoteInvocationServiceCaller;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is the implementation of {@link net.sf.mmm.service.api.client.RemoteInvocationServiceCaller} using
 * spring-remoting.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationServiceCallerImplSpring extends AbstractRemoteInvocationServiceCaller {

  /** @see #setServiceClient(RemoteInvocationGenericService) */
  private RemoteInvocationGenericService serviceClient;

  /**
   * The constructor.
   */
  public RemoteInvocationServiceCallerImplSpring() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performRequest(RemoteInvocationGenericServiceRequest request,
      RemoteInvocationServiceResultCallback<?>[] callbacks) {

    RemoteInvocationGenericServiceResponse response = this.serviceClient.callServices(request);
    handleResponse(request, callbacks, response);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <SERVICE extends RemoteInvocationService> SERVICE getServiceClient(final Class<SERVICE> serviceInterface) {

    InvocationHandler handler = new InvocationHandler() {

      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // TODO: Handling for equals + hashCode?

        Serializable[] arguments = new Serializable[args.length];
        for (int i = 0; i < args.length; i++) {
          try {
            arguments[i] = (Serializable) args[i];
          } catch (ClassCastException e) {
            throw new NlsIllegalArgumentException(args[i], serviceInterface.getName() + "." + method.getName() + "@arg"
                + i, e);
          }
        }
        int signature = RemoteInvocationServiceCall.getSignature(method.getParameterTypes());
        RemoteInvocationServiceCall call = new RemoteInvocationServiceCall(serviceInterface.getName(),
            method.getName(), signature, arguments);
        addCall(call, method.getReturnType());
        return null;
      }
    };
    SERVICE serviceProxy = serviceInterface.cast(Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
        new Class[] { serviceInterface }, handler));
    return serviceProxy;
  }

  /**
   * @return the serviceClient
   */
  protected RemoteInvocationGenericService getServiceClient() {

    return this.serviceClient;
  }

  /**
   * @param serviceClient is the client-stub for {@link RemoteInvocationGenericService} to inject.
   */
  @Inject
  public void setServiceClient(RemoteInvocationGenericService serviceClient) {

    this.serviceClient = serviceClient;
  }

}
