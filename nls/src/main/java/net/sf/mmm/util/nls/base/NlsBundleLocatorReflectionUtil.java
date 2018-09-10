/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Set;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * Implementation of {@link NlsBundleLocator} using {@link ReflectionUtil}.
 *
 * @since 7.6.0
 */
public class NlsBundleLocatorReflectionUtil implements NlsBundleLocator {

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Iterable<Class<? extends NlsBundle>> findBundles() {

    ReflectionUtil reflectionUtil = ReflectionUtilImpl.getInstance();
    Set<String> classNames = reflectionUtil.findClassNames("", true, CLASSNAME_FILTER);
    return (Iterable) reflectionUtil.loadClasses(classNames, CLASS_FILTER);
  }

}
