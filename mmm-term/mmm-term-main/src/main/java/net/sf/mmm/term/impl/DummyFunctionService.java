/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl;

import net.sf.mmm.term.api.FunctionException;
import net.sf.mmm.term.base.AbstractFunctionService;
import net.sf.mmm.term.impl.function.FctAbsolute;
import net.sf.mmm.term.impl.function.FctAddNumeric;
import net.sf.mmm.term.impl.function.FctAddString;
import net.sf.mmm.term.impl.function.FctAnd;
import net.sf.mmm.term.impl.function.FctDivide;
import net.sf.mmm.term.impl.function.FctEqual;
import net.sf.mmm.term.impl.function.FctGreater;
import net.sf.mmm.term.impl.function.FctGreaterEqual;
import net.sf.mmm.term.impl.function.FctLength;
import net.sf.mmm.term.impl.function.FctLess;
import net.sf.mmm.term.impl.function.FctLessEqual;
import net.sf.mmm.term.impl.function.FctMaximum;
import net.sf.mmm.term.impl.function.FctMinimum;
import net.sf.mmm.term.impl.function.FctModulo;
import net.sf.mmm.term.impl.function.FctMultiply;
import net.sf.mmm.term.impl.function.FctNot;
import net.sf.mmm.term.impl.function.FctNotEqual;
import net.sf.mmm.term.impl.function.FctOr;
import net.sf.mmm.term.impl.function.FctSubtract;
import net.sf.mmm.term.impl.function.FunctionElse;
import net.sf.mmm.term.impl.function.FunctionSwitch;
import net.sf.mmm.util.nls.NlsRuntimeException;

/**
 * This is a dummy implementation of the
 * {@link net.sf.mmm.term.api.FunctionService} interface. It can be used for
 * testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DummyFunctionService extends AbstractFunctionService {

  /**
   * The constructor.
   */
  public DummyFunctionService() {

    super();
    initialize();
  }

  /**
   * This method initializes the functions provided by this service.
   */
  protected void initialize() {

    try {
      registerFunction(new FunctionSwitch());
      registerFunction(new FunctionElse());
      GenericFunction f = new GenericFunction(FctAddNumeric.class);
      f.addImplementation(FctAddString.class);
      registerFunction(f);
      registerFunction(new GenericFunction(FctAbsolute.class));
      registerFunction(new GenericFunction(FctAnd.class));
      registerFunction(new GenericFunction(FctDivide.class));
      registerFunction(new GenericFunction(FctEqual.class));
      registerFunction(new GenericFunction(FctGreater.class));
      registerFunction(new GenericFunction(FctGreaterEqual.class));
      registerFunction(new GenericFunction(FctLength.class));
      registerFunction(new GenericFunction(FctLess.class));
      registerFunction(new GenericFunction(FctLessEqual.class));
      registerFunction(new GenericFunction(FctMaximum.class));
      registerFunction(new GenericFunction(FctMinimum.class));
      registerFunction(new GenericFunction(FctModulo.class));
      registerFunction(new GenericFunction(FctMultiply.class));
      registerFunction(new GenericFunction(FctNot.class));
      registerFunction(new GenericFunction(FctNotEqual.class));
      registerFunction(new GenericFunction(FctOr.class));
      registerFunction(new GenericFunction(FctSubtract.class));
    } catch (FunctionException e) {
      // TODO
      throw new NlsRuntimeException("Function-service initialization failed!", e) {};
    }
  }

}
