/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleFactory;

/**
 * TODO: this class ...
 * 
 * @author hohwille
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
    this.bundleMap = new HashMap<Class<? extends NlsBundle>, NlsBundle>();
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
  public <BUNDLE extends NlsBundle> BUNDLE createBundle(Class<BUNDLE> bundleInterface) {

    BUNDLE bundle = (BUNDLE) this.bundleMap.get(bundleInterface);
    if (bundle == null) {
      throw new IllegalStateException("Undefined NlsBundle " + bundleInterface.getName());
    }
    return bundle;
  }

}
