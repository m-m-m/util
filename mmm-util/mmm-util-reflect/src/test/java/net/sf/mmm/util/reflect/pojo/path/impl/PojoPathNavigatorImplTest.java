/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.impl;

import net.sf.mmm.util.reflect.pojo.path.api.PojoPathNavigator;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathNavigatorTest;

/**
 * This is the test-case for {@link PojoPathNavigatorImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PojoPathNavigatorImplTest extends PojoPathNavigatorTest {

  protected PojoPathNavigator createNavigator() {

    PojoPathNavigatorImpl navigator = new PojoPathNavigatorImpl();
    navigator.initialize();
    return navigator;
  }

}
