/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import net.sf.mmm.util.nls.api.NlsBundle;

/**
 * Extends {@link ResourceBundleControlUtf8} with support for {@link NlsBundle} to prevent exception overhead.
 * If {@link NlsBundle}s are used to create {@link net.sf.mmm.util.nls.api.NlsMessage}s and
 * {@link net.sf.mmm.util.nls.api.NlsMessage#getLocalizedMessage(Locale) localization} takes place for a
 * {@link java.util.Locale} where no {@link ResourceBundle} is available then every lookup will cause a
 * {@link java.util.MissingResourceException}. As this is causing massive overhead when many localizations
 * take place, we use this control class with the following strategy:<br>
 * If {@link #newBundle(String, Locale, String, ClassLoader, boolean)} is called with
 * {@link java.util.Locale#ROOT} and there is no regular {@link ResourceBundle} available, but an
 * {@link NlsBundle} with the given {@code baseName} it is
 * {@link NlsBundleHelper#toResourceBundle(Class) converted} to a {@link ResourceBundle} and returned.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class ResourceBundleControlUtf8WithNlsBundleSupport extends ResourceBundleControlUtf8 {

  /** The singleton instance. */
  public static final ResourceBundleControlUtf8WithNlsBundleSupport INSTANCE = new ResourceBundleControlUtf8WithNlsBundleSupport();

  /**
   * The constructor.
   */
  public ResourceBundleControlUtf8WithNlsBundleSupport() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
      throws IllegalAccessException, InstantiationException, IOException {

    ResourceBundle bundle = super.newBundle(baseName, locale, format, loader, reload);
    if ((bundle == null) && (Locale.ROOT.equals(locale))) {
      // if we got here and there is no ResourceBundle for ROOT locale, we assume that we came from NlsBundle
      try {
        Class<?> bundleClass = Class.forName(baseName + NlsBundle.INTERFACE_NAME_SUFFIX, true, loader);
        if (NlsBundle.class.isAssignableFrom(bundleClass)) {
          // caller will cache the bundle so this is typically only done once
          bundle = NlsBundleHelper.getInstance().toResourceBundle((Class) bundleClass);
        }
      } catch (ClassNotFoundException e) {
        // ignore
      }
    }
    return bundle;
  }

}
