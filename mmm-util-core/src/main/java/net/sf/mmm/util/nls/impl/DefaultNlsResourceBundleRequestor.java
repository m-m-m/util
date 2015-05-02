/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.ResourceBundle;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsResourceBundleRequestor;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;

/**
 * This is the default implementation of {@link NlsResourceBundleRequestor}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@Named(NlsResourceBundleRequestor.CDI_NAME)
@Singleton
public class DefaultNlsResourceBundleRequestor implements NlsResourceBundleRequestor {

  /**
   * The constructor.
   */
  public DefaultNlsResourceBundleRequestor() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void requestBundlesAsynchron(Runnable callback, String... bundleNames) {

    assert isAvailable(bundleNames);
    callback.run();
  }

  /**
   * This method verifies that the given <code>bundleNames</code> are available {@link ResourceBundle}s.
   * 
   * @param bundleNames are the {@link ResourceBundle#getBundle(String) bundle names}.
   * @return <code>true</code>.
   */
  private boolean isAvailable(String... bundleNames) {

    for (String name : bundleNames) {
      ResourceBundle.getBundle(name, AbstractNlsMessage.LOCALE_ROOT);
    }
    return true;
  }

}
