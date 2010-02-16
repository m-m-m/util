/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.framework.api.IocContainer;

/**
 * This is just an ugly static pool used to simplify testing. It might cause
 * memory holes and should never be used in productive code.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class SpringContainerPool {

  /** @see #getContainer(String) */
  private static final Map<String, SpringContainer> CONTAINER_MAP = new HashMap<String, SpringContainer>();

  /**
   * The constructor.
   */
  private SpringContainerPool() {

    super();
  }

  /**
   * This method gets the {@link IocContainer} for the given
   * <code>configPath</code>.
   * 
   * @param configPath is the location to the XML-configuration in the
   *        class-path.
   * @return the requested container.
   */
  public static IocContainer getContainer(String configPath) {

    SpringContainer container = CONTAINER_MAP.get(configPath);
    if (container == null) {
      container = new SpringContainer(configPath);
      CONTAINER_MAP.put(configPath, container);
    }
    return container;
  }

  /**
   * This method disposes the {@link IocContainer} for the given
   * <code>configPath</code> (if it exists because it was created via
   * {@link #getContainer(String)}).
   * 
   * @param configPath is the location to the XML-configuration in the
   *        class-path.
   */
  public static void disposeContainer(String configPath) {

    SpringContainer container = CONTAINER_MAP.remove(configPath);
    if (container != null) {
      container.dispose();
    }
  }

}
