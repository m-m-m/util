/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is the interface for a factory that can dynamically {@link #createBundle(Class) create} instance of
 * {@link NlsBundle}-interfaces.
 * 
 * @see NlsAccess#getBundleFactory()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface NlsBundleFactory {

  /**
   * This method dynamically creates an instance for the given <code>bundleInterface</code>.
   * 
   * @param <BUNDLE> is the generic type of the requested {@link NlsBundle}.
   * @param bundleInterface the interface of the requested {@link NlsBundle}. Has to be a sub-interface of
   *        {@link NlsBundle} with according methods.
   * @return an instance of the requested {@link NlsBundle} interface.
   */
  <BUNDLE extends NlsBundle> BUNDLE createBundle(Class<BUNDLE> bundleInterface);

}
