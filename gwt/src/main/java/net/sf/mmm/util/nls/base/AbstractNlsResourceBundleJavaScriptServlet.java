/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.mmm.util.component.base.AbstractLoggableHttpServlet;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * This is a {@link javax.servlet.http.HttpServlet} that dynamically generates some JavaScript containing the
 * content of {@link net.sf.mmm.util.nls.api.NlsBundle}s and {@link java.util.ResourceBundle}s for the users
 * locale.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsResourceBundleJavaScriptServlet extends AbstractLoggableHttpServlet {

  /** UID for serialization. */
  private static final long serialVersionUID = -2997745885840819492L;

  /** The URL path under which the bundle can be loaded. */
  public static final String URL_PATH = "js/mmm-nls-bundle.js";

  /** The query string for {@link #URL_PATH} to query the bundle name as GET-parameter. */
  public static final String URL_PARAM_NAME = "name";

  /** The query string for {@link #URL_PATH} to query the bundle name as GET-parameter. */
  public static final String URL_PARAM_NAME_QUERY = "?" + URL_PARAM_NAME + "=";

  /**
   * The constructor.
   */
  public AbstractNlsResourceBundleJavaScriptServlet() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String name = req.getParameter(URL_PARAM_NAME);
    try {
      if ((name == null) || (name.isEmpty())) {
        throw new ObjectNotFoundException("HTTP-Parameter", URL_PARAM_NAME);
      }
      Locale locale = req.getLocale();
      ResourceBundle bundle = ResourceBundle.getBundle(name, locale);
      writeBundle(resp.getWriter(), name, bundle);
    } catch (RuntimeException e) {
      getLogger().warn("Failed to provide bundle: " + name, e);
      throw e;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    doGet(req, resp);
  }

  /**
   * This method writes the given {@link ResourceBundle} to the {@code writer}.
   * 
   * @param writer is the {@link PrintWriter} to use.
   * @param name is the {@link ResourceBundle#getBundle(String) bundle name}.
   * @param bundle is the {@link ResourceBundle} for the users locale to write to the given
   *        {@code writer}.
   */
  protected void writeBundle(PrintWriter writer, String name, ResourceBundle bundle) {

    writer.print("var ");
    writer.print(escapeBundleName(name));
    writer.println(" = {");
    Enumeration<String> keyEnum = bundle.getKeys();
    while (keyEnum.hasMoreElements()) {
      String key = keyEnum.nextElement();
      Object object = bundle.getObject(key);
      if (object instanceof String) {
        writer.print(escapeBundleKey(key));
        writer.print(":\"");
        writer.print(object.toString());
        writer.print("\",");
      }
    }
    writer.println("};");
  }

  /**
   * @param key is the key to escape for JavaScript.
   * @return the escaped key.
   */
  protected String escapeBundleKey(String key) {

    // When using mmm you either use NlsBundle and keys are java method names
    // or they are Java constants from AbstractResourceBundle. Hence there is nothing to do here...
    //
    return key;
  }

  /**
   * @param name is the {@link ResourceBundle#getBundle(String) bundle name} to escape for JavaScript.
   * @return the escaped name. As JavaScript does not accept periods (.) in variable names they are replaced
   *         with the dollar sign ($).
   */
  protected String escapeBundleName(String name) {

    return name.replace('.', '$');
  }

}
