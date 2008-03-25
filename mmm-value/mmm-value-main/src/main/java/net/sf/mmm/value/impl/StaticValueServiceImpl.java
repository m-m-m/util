/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import net.sf.mmm.util.value.api.ValueException;
import net.sf.mmm.value.impl.type.DateValueManager;

/**
 * This is an implementation of the {@link net.sf.mmm.value.api.ValueService}
 * interface used for testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StaticValueServiceImpl extends ValueServiceImpl {

  /**
   * The constructor.
   */
  public StaticValueServiceImpl() {

    super();
    try {
      addValueType(Boolean.class);
      addValueType(Integer.class);
      addValueType(Long.class);
      addValueType(Float.class);
      addValueType(Double.class);
      addValueType(String.class);
      addValueManager(new DateValueManager());
    } catch (ValueException e) {
      throw new IllegalStateException("ValueService initialization is illegal!", e);
    }

  }

}
