/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.impl.function;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.pojo.path.api.PojoPathNamedFunction;
import net.sf.mmm.util.pojo.path.base.AbstractPojoPathFunction;

/**
 * This is the implementation of a {@link PojoPathNamedFunction} that performs a {@link Object#toString()} on
 * the input-object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named
public class PojoPathNamedFunctionToString extends AbstractPojoPathFunction<Object, String> implements
    PojoPathNamedFunction<Object, String> {

  /**
   * The constructor.
   */
  public PojoPathNamedFunctionToString() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<Object> getInputClass() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public Class<String> getValueClass() {

    return String.class;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return "toString";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String get(Object actual, String functionName, PojoPathContext context) {

    if (actual == null) {
      return null;
    } else {
      return actual.toString();
    }
  }

}
