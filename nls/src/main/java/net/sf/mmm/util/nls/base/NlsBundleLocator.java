/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.nls.api.NlsBundle;

/**
 * Locator to {@link #findBundles() find all} {@link NlsBundle} interfaces.
 *
 * @since 7.6.0
 */
public interface NlsBundleLocator {

  /** Filter to find classes by name. */
  Filter<String> CLASSNAME_FILTER = new Filter<String>() {

    @Override
    public boolean accept(String classname) {

      return AbstractNlsBundleFactory.NLS_BUNDLE_CLASS_NAME_PATTERN.matcher(classname).matches();
    }
  };

  /** Filter to find classes by super-interface. */
  Filter<Class<?>> CLASS_FILTER = new Filter<Class<?>>() {

    @Override
    public boolean accept(Class<?> javaClass) {

      return NlsBundle.class.isAssignableFrom(javaClass);
    }
  };

  /**
   * @return an {@link Iterable} with all available {@link NlsBundle} interfaces on the classpath.
   */
  Iterable<Class<? extends NlsBundle>> findBundles();

}
