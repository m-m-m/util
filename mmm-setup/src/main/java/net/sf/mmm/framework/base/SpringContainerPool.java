/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.framework.api.IocContainer;

/**
 * This is just an ugly static pool used to simplify testing. It will cause
 * memory holes and should never be used in productive code.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SpringContainerPool {

  /** @see #getContainer(String) */
  private static final Map<String, SpringContainer> containerMap = new HashMap<String, SpringContainer>();

  /**
   * The constructor.
   */
  private SpringContainerPool() {

    super();
  }

  /**
   * This method gets the container for the given <code>configPath</code>.
   * 
   * @param configPath is the location to the XML-configuration in the
   *        class-path.
   * @return the requested container.
   */
  public static IocContainer getContainer(String configPath) {

    SpringContainer container = containerMap.get(configPath);
    if (container == null) {
      container = new SpringContainer(configPath);
      containerMap.put(configPath, container);
    }
    return container;
  }

}
