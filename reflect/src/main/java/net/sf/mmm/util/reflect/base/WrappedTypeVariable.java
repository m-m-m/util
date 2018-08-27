/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.reflect.TypeVariable;

/**
 * Internal implementation of {@link GenericTypeVariable} to wrap a dynamic {@link TypeVariable}.
 *
 * @author hohwille
 * @since 7.1.0
 */
class WrappedTypeVariable extends GenericTypeVariable<Object> {

  /**
   * The constructor.
   *
   * @param typeVariable the {@link TypeVariable} to wrap.
   */
  public WrappedTypeVariable(TypeVariable<?> typeVariable) {
    super(typeVariable);
  }

}
