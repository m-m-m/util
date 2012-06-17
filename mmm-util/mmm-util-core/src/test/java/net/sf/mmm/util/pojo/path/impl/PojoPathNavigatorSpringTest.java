/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.impl;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.pojo.path.api.PojoPathNavigator;
import net.sf.mmm.util.pojo.path.api.PojoPathNavigatorTest;

/**
 * This is the test-case for {@link PojoPathNavigator} using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PojoPathNavigatorSpringTest extends PojoPathNavigatorTest {

  protected PojoPathNavigator createNavigator() {

    PojoPathNavigator navigator = SpringContainerPool.getInstance().getComponent(PojoPathNavigator.class);
    return navigator;
  }

}
