/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.base.AbstractNlsTemplateResolver;

/**
 * This is the dummy implementation of {@link net.sf.mmm.util.nls.api.NlsTemplateResolver} for GWT clients. As
 * localization has already been performed by GWT there is nothing to do here.
 * 
 * @see AbstractResourceBundleNlsTemplateResolver
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Named
@Singleton
public class DefaultNlsTemplateResolver extends AbstractNlsTemplateResolver {

  /**
   * The constructor.
   */
  public DefaultNlsTemplateResolver() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public NlsTemplate resolveTemplate(String internationalizedMessage) {

    // just a dummy, message is already localized by GWT.
    return null;
  }

}
