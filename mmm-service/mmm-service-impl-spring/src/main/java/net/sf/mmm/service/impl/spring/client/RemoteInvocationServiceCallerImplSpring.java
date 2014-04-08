/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.spring.client;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.service.api.rpc.RemoteInvocationService;
import net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcCall;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcResponse;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcService;
import net.sf.mmm.service.base.rpc.client.AbstractRemoteInvocationServiceCaller;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is the implementation of {@link net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller} using
 * an {@link #setServiceClient(GenericRemoteInvocationRpcService) injected client-stub} (provided by
 * spring-remoting) and {@link Proxy java dynamic proxies}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named(RemoteInvocationServiceCaller.CDI_NAME)
public class RemoteInvocationServiceCallerImplSpring extends AbstractRemoteInvocationServiceCaller {

  /** @see #setServiceClient(GenericRemoteInvocationRpcService) */
  private GenericRemoteInvocationRpcService serviceClient;

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
  protected void performRequest(GenericRemoteInvocationRpcRequest request, RequestBuilder builder) {

    GenericRemoteInvocationRpcResponse response = this.serviceClient.callServices(request);
    handleResponse(request, builder, response);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <SERVICE extends RemoteInvocationService> SERVICE getServiceClient(final Class<SERVICE> serviceInterface) {

    // TODO: cache the dynamic proxies per service interface?
    InvocationHandler handler = new InvocationHandler() {

      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // handle Object standard methods like equals/hashCode/toString/...
        if (method.getDeclaringClass().equals(Object.class)) {
          return method.invoke(proxy, args);
        }

        Serializable[] arguments = new Serializable[args.length];
        for (int i = 0; i < args.length; i++) {
          try {
            arguments[i] = (Serializable) args[i];
          } catch (ClassCastException e) {
            throw new NlsIllegalArgumentException(args[i], serviceInterface.getName() + "." + method.getName() + "@arg"
                + i, e);
          }
        }
        int signature = GenericRemoteInvocationRpcCall.getSignature(method.getParameterTypes());
        GenericRemoteInvocationRpcCall call = new GenericRemoteInvocationRpcCall(serviceInterface.getName(),
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
  protected GenericRemoteInvocationRpcService getServiceClient() {

    return this.serviceClient;
  }

  /**
   * @param serviceClient is the client-stub for {@link GenericRemoteInvocationRpcService} to inject.
   */
  @Inject
  public void setServiceClient(GenericRemoteInvocationRpcService serviceClient) {

    this.serviceClient = serviceClient;
  }

}
