/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import javax.inject.Named;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * This is a {@link javax.servlet.http.HttpServlet} that dynamically generates some JavaScript containing the
 * content of {@link net.sf.mmm.util.nls.api.NlsBundle}s and {@link java.util.ResourceBundle}s for the users
 * locale.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// @Named(AbstractNlsResourceBundleJavaScriptServlet.URL_PATH)
@Named("NlsResourceBundleJavaScriptServlet")
@WebServlet(AbstractNlsResourceBundleJavaScriptServlet.URL_PATH)
public class NlsResourceBundleJavaScriptServlet extends AbstractNlsResourceBundleJavaScriptServlet implements
    Controller {

  /** UID for serialization. */
  private static final long serialVersionUID = 2060146216723588926L;

  /**
   * The constructor.
   */
  public NlsResourceBundleJavaScriptServlet() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

    super.service(request, response);
    return null;
  }

}
