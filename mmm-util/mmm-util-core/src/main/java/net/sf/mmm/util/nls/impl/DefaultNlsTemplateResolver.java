/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsTemplate;

/**
 * This is the default implementation of the {@link net.sf.mmm.util.nls.api.NlsTemplateResolver}. It locates
 * all {@link ResourceBundle}s declared in the
 * {@link net.sf.mmm.util.nls.api.NlsTemplateResolver#CLASSPATH_NLS_BUNDLE bundle-declaration-files}.
 * 
 * @see AbstractResourceBundleNlsTemplateResolver
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Named
@Singleton
public class DefaultNlsTemplateResolver extends AbstractResourceBundleNlsTemplateResolver {

  /** @see #getResourceBundleFinder() */
  private NlsResourceBundleLocator resourceBundleFinder;

  /** @see #resolveTemplate(String) */
  private NlsReversedResourceBundle[] nlsBundles;

  /**
   * The constructor.
   */
  public DefaultNlsTemplateResolver() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.resourceBundleFinder == null) {
      NlsResourceBundleLocatorImpl impl = new NlsResourceBundleLocatorImpl();
      impl.initialize();
      this.resourceBundleFinder = impl;
    }
    List<ResourceBundle> bundles = this.resourceBundleFinder.findBundles();

    int bundleCount = bundles.size();
    this.nlsBundles = new NlsReversedResourceBundle[bundleCount];
    for (int i = 0; i < bundleCount; i++) {
      this.nlsBundles[i] = new NlsReversedResourceBundleImpl(bundles.get(i));
    }
    NlsAccess.setTemplateResolver(this);
  }

  /**
   * This method gets the {@link NlsResourceBundleLocator}.
   * 
   * @return the {@link NlsResourceBundleLocator}.
   */
  public NlsResourceBundleLocator getResourceBundleFinder() {

    return this.resourceBundleFinder;
  }

  /**
   * @param resourceBundleFinder is the resourceBundleFinder to set
   */
  @Inject
  public void setResourceBundleFinder(NlsResourceBundleLocator resourceBundleFinder) {

    getInitializationState().requireNotInitilized();
    this.resourceBundleFinder = resourceBundleFinder;
  }

  /**
   * {@inheritDoc}
   */
  public NlsTemplate resolveTemplate(String internationalizedMessage) {

    return resolveTemplate(internationalizedMessage, this.nlsBundles);
  }

}
