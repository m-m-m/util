/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is the interface of a component to {@link #requestBundlesAsynchron(Runnable, String...) request}
 * {@link NlsResourceBundle}s. In regular contexts there is no need in requesting resource bundles and all
 * methods of this component will have no effect. However, on special client-environments like GWT you may
 * need to request them explicitly if you do NOT use {@link NlsBundle} and want to make use of code-splitting
 * and lazy loading.
 *
 * @see NlsMessageFactory#createDirect(String, String, java.util.Map)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public interface NlsResourceBundleRequestor {

  /**
   * This method requests a number of {@link java.util.ResourceBundle}s by their
   * {@link java.util.ResourceBundle#getBundle(String) base-name}. In a GWT environment this will cause that
   * these {@link java.util.ResourceBundle} are loaded from the server (if not already available) in the
   * {@link java.util.Locale} of the user so they are available for dictionaries. After the last bundle has
   * been loaded the given {@code callback} will be invoked. In other environments this method will do nothing
   * but synchronously invoking the {@code callback}.
   *
   * @param callback is the {@link Runnable} that is {@link Runnable#run() called} when all bundles are loaded
   *        and available.
   * @param bundleNames are the {@link java.util.ResourceBundle#getBundle(String) base-names} of the requested
   *        {@link java.util.ResourceBundle}s.
   */
  void requestBundlesAsynchron(Runnable callback, String... bundleNames);

}
