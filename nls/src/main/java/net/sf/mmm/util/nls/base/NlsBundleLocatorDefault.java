/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * Implementation of {@link NlsBundleLocator} using {@link ReflectionUtil}.
 *
 * @since 7.6.0
 */
public class NlsBundleLocatorDefault implements NlsBundleLocator {

  private static final Logger LOG = LoggerFactory.getLogger(NlsBundleLocatorDefault.class);

  @Override
  public Iterable<Class<? extends NlsBundle>> findBundles() {

    try {
      return new NlsBundleLocatorClasspathScanner().findBundles();
    } catch (Throwable e) {
      LOG.debug("mmm-util-resource (ClasspathScanner) not available.", e);
    }
    try {
      return new NlsBundleLocatorReflectionUtil().findBundles();
    } catch (Throwable e) {
      LOG.debug("mmm-util-reflect (ReflectionUtil) not available.", e);
    }
    try {
      return new NlsBundleLocatorSpring().findBundles();
    } catch (Throwable e) {
      LOG.debug("spring-core (PathMatchingResourcePatternResolver) not available.", e);
    }
    throw new IllegalStateException("None of mmm-util-resource, mmm-util-reflect, or spring-core on your classpath. Giving up!");
  }

}
