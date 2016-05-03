/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.ResourceBundle;

import net.sf.mmm.util.nls.api.NlsTemplate;

/**
 * This is an implementation of the {@link net.sf.mmm.util.nls.api.NlsTemplateResolver} interface. Supply all
 * your {@link java.util.Locale#ROOT root} {@link ResourceBundle}s at construction. <br>
 * <b>IMPORTANT:</b><br>
 * This class is located in an implementation package. If you want to use it directly, try to centralize such
 * usage in your project code or even swap it out to the configuration of your favorite IoC container
 * framework.
 * 
 * @see DefaultNlsTemplateResolver
 * @see net.sf.mmm.util.nls.api.NlsMessage
 * @see java.util.ResourceBundle
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsTemplateResolverImpl extends AbstractResourceBundleNlsTemplateResolver {

  /** the original bundles */
  private final NlsReversedResourceBundle[] nlsBundles;

  /**
   * The constructor.
   * 
   * @param internationalBundles are the {@link java.util.Locale#ROOT root} {@link ResourceBundle}s.
   */
  public NlsTemplateResolverImpl(ResourceBundle... internationalBundles) {

    super();
    this.nlsBundles = new NlsReversedResourceBundle[internationalBundles.length];
    for (int i = 0; i < internationalBundles.length; i++) {
      this.nlsBundles[i] = new NlsReversedResourceBundleImpl(internationalBundles[i]);
    }
  }

  /**
   * The constructor.
   * 
   * @param reversedBundles are the {@link NlsReversedResourceBundle} instances.
   */
  public NlsTemplateResolverImpl(NlsReversedResourceBundle... reversedBundles) {

    super();
    this.nlsBundles = reversedBundles;
  }

  @Override
  public NlsTemplate resolveTemplate(String internationalizedMessage) {

    return resolveTemplate(internationalizedMessage, this.nlsBundles);
  }

}
