/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.DataResourceFactory;

/**
 * This is the test-case for the class {@link DataResourceFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BrowsableResourceFactorySpringTest extends BrowsableResourceFactoryTest {

  @Override
  public BrowsableResourceFactory getBrowsableResourceFactory() {

    return SpringContainerPool.getInstance().get(BrowsableResourceFactory.class);
  }

}
