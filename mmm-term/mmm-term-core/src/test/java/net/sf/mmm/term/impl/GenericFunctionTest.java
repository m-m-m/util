/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl;

import junit.framework.TestCase;

import net.sf.mmm.term.api.OperatorPriority;
import net.sf.mmm.term.impl.function.FctAdd;
import net.sf.mmm.term.impl.function.FctAddNumeric;
import net.sf.mmm.term.impl.function.FctAddString;
import net.sf.mmm.util.value.api.ValueException;

/**
 * This is the test case for {@link GenericFunction}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class GenericFunctionTest extends TestCase {

  public GenericFunctionTest() {

    super();
  }

  private void checkCalculation(GenericFunction f, Object arg1, Object arg2, Object result)
      throws ValueException {

    assertEquals(result, f.calculate(arg1, arg2));
    assertEquals(result, f.calculate(null, new Constant(arg1), new Constant(arg2)));
  }

  public void testAdd() throws Exception {

    GenericFunction add = new GenericFunction("add", FctAdd.SYMBOL, OperatorPriority.MEDIUM);
    add.addImplementation(FctAddNumeric.class);
    add.addImplementation(FctAddString.class);
    checkCalculation(add, 5, 3, 8);
    checkCalculation(add, -1, 1, 0);
    checkCalculation(add, -1, 1, 0);
    checkCalculation(add, Integer.MAX_VALUE, 1, (Integer.MAX_VALUE + 1L));
    checkCalculation(add, 1.0, -1.125, -0.125);
    checkCalculation(add, "Hi", 42, "Hi42");
    checkCalculation(add, new Short("5"), new Byte("42"), new Byte("47"));
  }

}
