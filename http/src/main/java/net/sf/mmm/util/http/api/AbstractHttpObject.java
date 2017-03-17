/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import net.sf.mmm.util.exception.api.ReadOnlyException;
import net.sf.mmm.util.lang.base.AbstractStringWritable;

/**
 * This is the abstract base implementation for implementations related to {@link Http} such as {@link HttpMessage},
 * {@link net.sf.mmm.util.http.api.header.HttpHeader}, or {@link net.sf.mmm.util.http.api.header.HttpHeaders}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class AbstractHttpObject extends AbstractStringWritable {

  private boolean immutable;

  /**
   * The constructor.
   */
  public AbstractHttpObject() {
    super();
  }

  /**
   * @return {@code true} if this object is immutable and modifications will cause a {@link ReadOnlyException},
   *         {@code false} otherwise.
   */
  public boolean isImmutable() {

    return this.immutable;
  }

  /**
   * Sets this object as {@link #isImmutable() immutable}. Once it is immutable it can not be made mutable again.
   *
   * @return this object itself for fluent API calls.
   */
  public AbstractHttpObject setImmutable() {

    this.immutable = true;
    return this;
  }

  /**
   * Called to ensure that this object is mutable (not {@link #isImmutable() immutable}).
   *
   * @throws ReadOnlyException if this object is {@link #isImmutable()}.
   */
  protected void requireMutable() throws ReadOnlyException {

    requireMutable(null);
  }

  /**
   * Called to ensure that this object is mutable (not {@link #isImmutable() immutable}).
   *
   * @param attribute the optional attribute (name) that is to be modified.
   * @throws ReadOnlyException if this object is {@link #isImmutable()}.
   */
  protected void requireMutable(Object attribute) throws ReadOnlyException {

    if (this.immutable) {
      throw new ReadOnlyException(this, attribute);
    }
  }

}
