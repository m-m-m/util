/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.IocContainer;

/**
 * This is just an ugly static pool used to simplify testing. It might cause
 * memory holes and should never be used in productive code.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class SpringContainerPool {

  /** @see #getInstance() */
  private static SpringContainer instance;

  /**
   * The constructor.
   */
  private SpringContainerPool() {

    super();
  }

  /**
   * This method gets the singleton instance of the {@link IocContainer}.
   * 
   * @return the requested container.
   */
  public static IocContainer getInstance() {

    if (instance == null) {
      instance = new SpringContainer();
    }
    return instance;
  }

  /**
   * This method disposes the {@link #getInstance() singleton-instance} (if it
   * exists).
   */
  public static void dispose() {

    if (instance != null) {
      instance.dispose();
      instance = null;
    }
  }

}
