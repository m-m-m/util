/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import net.sf.mmm.framework.base.provider.SimpleSingletonProvider;

/**
 * This is a provider for a
 * {@link Executors#privilegedThreadFactory() privileged} {@link ThreadFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PrivilegedThreadFactoryProvider extends SimpleSingletonProvider<ThreadFactory> {

  /**
   * The constructor.
   */
  public PrivilegedThreadFactoryProvider() {

    super(ThreadFactory.class, Executors.privilegedThreadFactory());
  }

}
