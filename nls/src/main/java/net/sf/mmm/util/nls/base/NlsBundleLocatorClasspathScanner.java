/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.resource.api.ClasspathScanner;
import net.sf.mmm.util.resource.impl.AbstractClasspathScanner;

/**
 * Implementation of {@link NlsBundleLocator} using {@link ClasspathScanner}.
 *
 * @since 7.6.0
 */
public class NlsBundleLocatorClasspathScanner implements NlsBundleLocator {

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Iterable<Class<? extends NlsBundle>> findBundles() {

    ClasspathScanner classpathScanner = AbstractClasspathScanner.getInstance();
    return (Iterable) classpathScanner.getClasspathResourceClasses(CLASSNAME_FILTER, CLASS_FILTER);
  }

}
