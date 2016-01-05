/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the generic interface for a <em>builder</em>. A builder is an object following the builder-pattern in order
 * to {@link #build() build} some object using a simple and fluent API.
 *
 * @param <T> the type of the object to build.
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface Builder<T> {

  /**
   * Creates a new instance of the object to build. If the {@link Builder} is reused, any additional changes to the
   * {@link Builder} shall NOT have any effect on instances previously returned by this method.
   *
   * @return the object to build.
   */
  T build();

}
