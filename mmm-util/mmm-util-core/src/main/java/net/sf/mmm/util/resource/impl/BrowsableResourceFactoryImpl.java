/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.spi.DataResourceProvider;
import net.sf.mmm.util.resource.base.AbstractBrowsableResourceFactory;
import net.sf.mmm.util.resource.impl.spi.ClasspathResourceProvider;
import net.sf.mmm.util.resource.impl.spi.FileResourceProvider;
import net.sf.mmm.util.resource.impl.spi.UrlResourceProvider;

/**
 * This is the default implementation of the {@link net.sf.mmm.util.resource.api.BrowsableResourceFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named
public class BrowsableResourceFactoryImpl extends AbstractBrowsableResourceFactory {

  /** @see #getProviders() */
  private List<DataResourceProvider<? extends DataResource>> providers;

  /**
   * The constructor.
   */
  public BrowsableResourceFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.providers == null) {
      this.providers = new ArrayList<DataResourceProvider<? extends DataResource>>();
      ClasspathResourceProvider classpathResourceProvider = new ClasspathResourceProvider();
      classpathResourceProvider.initialize();
      this.providers.add(classpathResourceProvider);
      FileResourceProvider fileResourceProvider = new FileResourceProvider();
      fileResourceProvider.initialize();
      this.providers.add(fileResourceProvider);
      UrlResourceProvider urlResourceProvider = new UrlResourceProvider();
      urlResourceProvider.initialize();
      this.providers.add(urlResourceProvider);
    }
    for (DataResourceProvider<? extends DataResource> provider : this.providers) {
      registerProvider(provider);
    }
  }

  /**
   * @return the providers
   */
  protected List<DataResourceProvider<? extends DataResource>> getProviders() {

    return this.providers;
  }

  /**
   * This method sets the {@link List} of {@link DataResourceProvider providers} to
   * {@link #registerProvider(DataResourceProvider) register}.
   * 
   * @param providers is the list of providers.
   */
  @Inject
  public void setProviders(List<DataResourceProvider<? extends DataResource>> providers) {

    this.providers = providers;
  }

}
