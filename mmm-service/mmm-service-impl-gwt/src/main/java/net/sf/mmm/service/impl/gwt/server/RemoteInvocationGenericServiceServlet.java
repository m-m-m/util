/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.gwt.server;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.mmm.service.base.gwt.RemoteInvocationGenericServiceGwt;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcService;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcResponse;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This is the {@link RemoteServiceServlet} that implements the {@link RemoteInvocationGenericServiceGwt}
 * using <code>spring-webmvc</code> in order to allow context and dependency-injection (CDI). The actual
 * implementation for {@link #callServices(GenericRemoteInvocationRpcRequest)} is delegated to an
 * {@link #setGenericService(GenericRemoteInvocationRpcService) injected} implementation - by default
 * {@link net.sf.mmm.service.impl.rpc.server.GenericRemoteInvocationRpcServiceImpl} but can be changed by overriding
 * the spring XML configuration.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named("RemoteInvocationGenericServiceServlet")
public class RemoteInvocationGenericServiceServlet extends RemoteServiceServlet implements
    RemoteInvocationGenericServiceGwt, Controller, ServletContextAware {

  /** UID for serialization. */
  private static final long serialVersionUID = -7334030062127266096L;

  /** @see #getGenericService() */
  private GenericRemoteInvocationRpcService genericService;

  /** @see #getServletContext() */
  private ServletContext servletContext;

  /**
   * The constructor.
   */
  public RemoteInvocationGenericServiceServlet() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericRemoteInvocationRpcResponse callServices(GenericRemoteInvocationRpcRequest request) {

    return this.genericService.callServices(request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ServletContext getServletContext() {

    return this.servletContext;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setServletContext(ServletContext servletContext) {

    this.servletContext = servletContext;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {

    super.doPost(request, response);
    return null;
  }

  /**
   * @param genericService is the genericService to set
   */
  @Inject
  @Named(GenericRemoteInvocationRpcService.CDI_NAME)
  public void setGenericService(GenericRemoteInvocationRpcService genericService) {

    this.genericService = genericService;
  }

  /**
   * @return the genericService
   */
  protected GenericRemoteInvocationRpcService getGenericService() {

    return this.genericService;
  }

}
