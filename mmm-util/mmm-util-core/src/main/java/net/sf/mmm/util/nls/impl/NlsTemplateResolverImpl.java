/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsTemplateResolver} interface. Supply all
 * your {@link AbstractResourceBundle}s at construction.<br>
 * <b>IMPORTANT:</b><br>
 * This class is located in an implementation package. However this is an
 * important class to be used by end-users. Try to centralize such usage in your
 * project code or even swap it out to the configuration of your favorite IoC
 * container framework.
 * 
 * @see net.sf.mmm.util.nls.api.NlsMessage
 * @see java.util.ResourceBundle
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsTemplateResolverImpl extends AbstractSmartNlsTemplateResolver {

  /** the original bundles */
  private final AbstractResourceBundle[] nlsBundles;

  /**
   * The constructor.
   * 
   * @param internationalBundles are the NLS bundle.
   */
  public NlsTemplateResolverImpl(AbstractResourceBundle... internationalBundles) {

    super();
    this.nlsBundles = internationalBundles;
  }

  /**
   * {@inheritDoc}
   */
  public NlsTemplate resolveTemplate(String internationalizedMessage) {

    NlsTemplate result = null;
    for (AbstractResourceBundle bundle : this.nlsBundles) {
      result = resolveTemplate(bundle, internationalizedMessage);
      if (result != null) {
        break;
      }
    }
    return result;
  }

}
