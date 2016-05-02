/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleFactory;

/**
 * This is the GWT compatible implementation of {@link NlsBundleFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsBundleFactoryGwt implements NlsBundleFactory {

  /** The map with the generated bundles. */
  private final Map<Class<? extends NlsBundle>, NlsBundle> bundleMap;

  /**
   * The constructor.
   */
  public AbstractNlsBundleFactoryGwt() {

    super();
    this.bundleMap = new HashMap<>();
  }

  /**
   * Registers the given <code>bundle</code> for the given <code>bundleInterface</code>.
   *
   * @param bundleInterface is the interface of the {@link NlsBundle}.
   * @param bundle is the actual {@link NlsBundle} instance.
   */
  protected void register(Class<? extends NlsBundle> bundleInterface, NlsBundle bundle) {

    NlsBundle old = this.bundleMap.put(bundleInterface, bundle);
    if (old != null) {
      throw new IllegalStateException("Duplicate bundle for " + bundleInterface);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <BUNDLE extends NlsBundle> BUNDLE createBundle(Class<BUNDLE> bundleInterface) {

    BUNDLE bundle = getBundle(bundleInterface);
    if (bundle == null) {
      throw new IllegalStateException("Undefined NlsBundle " + bundleInterface.getName());
    }
    return bundle;
  }

  /**
   * This method gets the {@link NlsBundle} for the given <code>bundleInterface</code> from the internal map.
   *
   * @param <BUNDLE> is the generic type of the {@link NlsBundle}.
   *
   * @param bundleInterface is the class reflecting the {@link NlsBundle} interface.
   * @return the {@link NlsBundle} implementation or <code>null</code> if NOT registered.
   */
  @SuppressWarnings("unchecked")
  protected <BUNDLE extends NlsBundle> BUNDLE getBundle(Class<BUNDLE> bundleInterface) {

    return (BUNDLE) this.bundleMap.get(bundleInterface);
  }

}
