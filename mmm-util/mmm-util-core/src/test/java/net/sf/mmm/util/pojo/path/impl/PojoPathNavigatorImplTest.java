/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.pojo.path.api.PojoPathNamedFunction;
import net.sf.mmm.util.pojo.path.api.PojoPathNavigator;
import net.sf.mmm.util.pojo.path.api.PojoPathNavigatorTest;
import net.sf.mmm.util.pojo.path.impl.function.PojoPathNamedFunctionToString;

/**
 * This is the test-case for {@link PojoPathNavigatorImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PojoPathNavigatorImplTest extends PojoPathNavigatorTest {

  protected PojoPathNavigator createNavigator() {

    PojoPathNavigatorImpl navigator = new PojoPathNavigatorImpl();
    PojoPathFunctionManagerImpl functionManager = new PojoPathFunctionManagerImpl();
    List<PojoPathNamedFunction> functions = new ArrayList<PojoPathNamedFunction>();
    PojoPathNamedFunctionToString functionToString = new PojoPathNamedFunctionToString();
    functionToString.initialize();
    functions.add(functionToString);
    functionManager.setFunctions(functions);
    navigator.setFunctionManager(functionManager);
    navigator.initialize();
    return navigator;
  }
}
