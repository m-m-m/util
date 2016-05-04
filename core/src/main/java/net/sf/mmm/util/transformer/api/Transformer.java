/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer.api;

/**
 * This is the interface for a transformer that {@link #transform(Object) transforms} a given value.
 *
 * @param <V> is the templated type of the value.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Transformer<V> {

  /**
   * This method transforms a given {@code original} value. This value must NOT be modified by this method. To change
   * the {@code original} value a new value has to be created and returned instead. If the value should NOT be changed,
   * the {@code original} value (the exact same instance) should be returned.
   *
   * @param original is the original value.
   * @return the transformed value. May only be {@code null} if explicitly documented by the implementation. The
   *         receiver of a {@link Transformer} should also document if {@code null} is a legal result.
   */
  V transform(V original);

}
