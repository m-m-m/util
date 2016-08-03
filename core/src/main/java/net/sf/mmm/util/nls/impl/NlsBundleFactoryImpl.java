/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import net.sf.mmm.util.nls.api.NlsBundleFactory;
import net.sf.mmm.util.nls.base.AbstractNlsBundleFactory;

/**
 * This is the implementation of {@link NlsBundleFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class NlsBundleFactoryImpl extends AbstractNlsBundleFactory {

  /**
   * The constructor.
   */
  public NlsBundleFactoryImpl() {

    super();
  }

  /**
   * The constructor.
   *
   * @param classLoader is the {@link ClassLoader} to use.
   */
  public NlsBundleFactoryImpl(ClassLoader classLoader) {

    super(classLoader);
  }

}
