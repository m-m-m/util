/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;

import net.sf.mmm.util.nls.api.NlsResourceBundle;

/**
 * This is a {@link HttpServlet} that dynamically generates some JavaScript containing the content of
 * {@link net.sf.mmm.util.nls.api.NlsBundle}s and {@link java.util.ResourceBundle}s for the users locale.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsResourceBundleJavaScriptServlet extends AbstractNlsResourceBundleJavaScriptServlet {

  /** UID for serialization. */
  private static final long serialVersionUID = 2060146216723588926L;

  /** @see #writeBundles(PrintWriter, Locale) */
  private NlsResourceBundleProvider bundleProvider;

  /**
   * The constructor.
   */
  public NlsResourceBundleJavaScriptServlet() {

    super();
  }

  /**
   * @param bundleProvider is the bundleProvider to set
   */
  @Inject
  public void setBundleProvider(NlsResourceBundleProvider bundleProvider) {

    this.bundleProvider = bundleProvider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void writeBundles(PrintWriter writer, Locale locale) throws IOException {

    Collection<NlsResourceBundle> bundleList = this.bundleProvider.getBundles();
    for (NlsResourceBundle bundle : bundleList) {
      String name = bundle.getName();
      ResourceBundle rb = ResourceBundle.getBundle(name, locale);
      writeBundle(writer, name, rb);
    }
  }

}
