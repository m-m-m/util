/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This class acts a a generic base to capture a generic {@link Type} bound to generic {@literal <T>}.
 *
 * @param <T> the generic {@link Type} to capture and {@link #resolve()}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public abstract class GenericTypeCapture<T> {

  /**
   * The constructor.
   */
  public GenericTypeCapture() {
    super();
  }

  /**
   * @return the {@link Type} of the generic {@literal <T>}.
   */
  protected Type resolve() {

    Type superType = getClass().getGenericSuperclass();
    assert (superType instanceof ParameterizedType);
    return ((ParameterizedType) superType).getActualTypeArguments()[0];
  }

}
