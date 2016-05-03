/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleFactory;

import com.google.gwt.core.client.GWT;

/**
 * This is the GWT compatible implementation of {@link NlsBundleFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsBundleFactoryImpl extends AbstractComponent implements NlsBundleFactory {

  /** The generated GWT implementation to delegate to. */
  private final NlsBundleFactory delegate;

  /**
   * The constructor.
   */
  public NlsBundleFactoryImpl() {

    super();
    this.delegate = GWT.create(NlsBundleFactory.class);
  }

  @Override
  public <BUNDLE extends NlsBundle> BUNDLE createBundle(Class<BUNDLE> bundleInterface) {

    return this.delegate.createBundle(bundleInterface);
  }

}
