/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.path.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.path.api.PathFactory;
import net.sf.mmm.util.path.api.PathProvider;
import net.sf.mmm.util.path.base.AbstractPathFactory;

/**
 * This is the default implementation of the {@link PathFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named(PathFactory.CDI_NAME)
public class PathFactoryImpl extends AbstractPathFactory {

  /** @see #getProviders() */
  private List<PathProvider> providers;

  /**
   * The constructor.
   */
  public PathFactoryImpl() {

    super();
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.providers == null) {
      this.providers = new ArrayList<>();
      ClassPathProvider classPathProvider = new ClassPathProvider();
      classPathProvider.initialize();
      this.providers.add(classPathProvider);
    }
    for (PathProvider provider : this.providers) {
      registerProvider(provider);
    }
  }

  /**
   * @return the {@link List} of {@link PathProvider}s.
   */
  protected List<PathProvider> getProviders() {

    return this.providers;
  }

  /**
   * This method sets the {@link List} of {@link PathProvider}s to {@link #registerProvider(PathProvider)
   * register}.
   *
   * @param providers is the list of providers.
   */
  @Inject
  public void setProviders(List<PathProvider> providers) {

    getInitializationState().requireNotInitilized();
    this.providers = providers;
  }

}
