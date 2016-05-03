/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.impl.NlsDependenciesImpl;

/**
 * This is the abstract base implementation of {@link NlsDependencies}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract class AbstractNlsDependencies extends AbstractLoggableComponent implements NlsDependencies {

  /** @see #getInstance() */
  private static AbstractNlsDependencies instance;

  /**
   * The constructor.
   */
  public AbstractNlsDependencies() {

    super();
  }

  /**
   * @return the instance
   */
  public static AbstractNlsDependencies getInstance() {

    if (instance == null) {
      NlsDependenciesImpl impl = new NlsDependenciesImpl();
      instance = impl;
      impl.setArgumentParser(AbstractNlsFormatterManager.getInstance());
      impl.initialize();
    }
    return instance;
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    synchronized (AbstractNlsDependencies.class) {
      if (instance == null) {
        instance = this;
      } else if (instance != this) {
        getLogger().warn("Duplicate instances {} and {} (getInstance() vs. IoC)", instance, this);
      }
    }
  }

}
