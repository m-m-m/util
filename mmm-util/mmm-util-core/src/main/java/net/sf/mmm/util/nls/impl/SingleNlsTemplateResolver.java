/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsTemplateResolver} interface for a single
 * {@link AbstractResourceBundle}.
 * 
 * @see net.sf.mmm.util.nls.api.NlsMessage
 * @see java.util.ResourceBundle
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SingleNlsTemplateResolver extends AbstractSmartNlsTemplateResolver {

  /** the original bundle */
  private final AbstractResourceBundle nlsBundle;

  /**
   * The constructor.
   * 
   * @param internationalBundle is the NLS bundle.
   */
  public SingleNlsTemplateResolver(AbstractResourceBundle internationalBundle) {

    super();
    this.nlsBundle = internationalBundle;
  }

  /**
   * {@inheritDoc}
   */
  public NlsTemplate resolveTemplate(String internationalizedMessage) {

    return resolveTemplate(this.nlsBundle, internationalizedMessage);
  }

}
