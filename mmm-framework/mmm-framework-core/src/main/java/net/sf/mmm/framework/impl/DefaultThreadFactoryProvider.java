/* $Id$ */
package net.sf.mmm.framework.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import net.sf.mmm.framework.base.provider.SimpleSingletonProvider;

/**
 * This is a provider for a {@link Executors#defaultThreadFactory() default}
 * {@link ThreadFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DefaultThreadFactoryProvider extends SimpleSingletonProvider<ThreadFactory> {

  /**
   * The constructor.
   */
  public DefaultThreadFactoryProvider() {

    super(ThreadFactory.class, Executors.defaultThreadFactory());
  }

}
