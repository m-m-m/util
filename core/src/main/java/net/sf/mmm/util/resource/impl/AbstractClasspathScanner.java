/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.resource.api.ClasspathScanner;

/**
 * Abstract base implementation of {@link ClasspathScanner}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.3.0
 */
public abstract class AbstractClasspathScanner extends AbstractLoggableComponent implements ClasspathScanner {

  private static ClasspathScanner instance;

  /**
   * The constructor.
   */
  public AbstractClasspathScanner() {

    super();
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    if (instance == null) {
      instance = this;
    }
  }

  /**
   * This method gets the singleton instance of this {@link ClasspathScanner}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static ClasspathScanner getInstance() {

    if (instance == null) {
      synchronized (AbstractClasspathScanner.class) {
        if (instance == null) {
          ClasspathScannerImpl impl = new ClasspathScannerImpl();
          impl.initialize();
        }
      }
    }
    return instance;
  }

}
