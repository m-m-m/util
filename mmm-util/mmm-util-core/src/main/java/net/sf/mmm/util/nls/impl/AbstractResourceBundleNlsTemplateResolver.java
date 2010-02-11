/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.base.AbstractNlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This is an abstract base implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsTemplateResolver} interface.<br>
 * The localization is done by a reverse lookup of the message key from the
 * given {@link #resolveTemplate(String) internationalized message} using
 * {@link AbstractResourceBundle}. With that key and the name of the according
 * {@link java.util.ResourceBundle} it is possible to create the according
 * {@link NlsTemplate}.<br>
 * The template does the localization by a forward
 * {@link java.util.ResourceBundle#getBundle(String, java.util.Locale) lookup}
 * for a regular {@link java.util.ResourceBundle bundle} with the same
 * {@link Class#getName() name}.<br>
 * This implementation allows localization of chained
 * {@link net.sf.mmm.util.nls.api.NlsObject}s by injecting itself into the
 * {@link net.sf.mmm.util.nls.api.NlsFormatterManager} used by the
 * {@link #resolveTemplate(String) resolved} {@link NlsTemplate templates}.<br>
 * 
 * @see net.sf.mmm.util.nls.api.NlsMessage
 * @see java.util.ResourceBundle
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractResourceBundleNlsTemplateResolver extends AbstractNlsTemplateResolver {

  /**
   * The constructor.
   */
  public AbstractResourceBundleNlsTemplateResolver() {

    super();
  }

  /**
   * This method {@link #resolveTemplate(String) resolves} the
   * {@link NlsTemplate} from the given {@link AbstractResourceBundle}.
   * 
   * @param resourceBundle is the resource-bundle that potentially declared the
   *        <code>internationalizedMessage</code>.
   * @param internationalizedMessage is the message for which the
   *        {@link NlsTemplate} is required.
   * @return the according {@link NlsTemplate} or <code>null</code> if the
   *         <code>internationalizedMessage</code> is NOT declared in
   *         <code>resourceBundle</code>.
   */
  protected NlsTemplate resolveTemplate(AbstractResourceBundle resourceBundle,
      String internationalizedMessage) {

    String key = resourceBundle.getKey(internationalizedMessage);
    if (key != null) {
      String name = resourceBundle.getClass().getName();
      return new NlsTemplateImpl(name, key, getArgumentParser());
    }
    return null;
  }

}
