/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api;

import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * This is the interface for a {@link Query} that can only match a single result.
 *
 * @param <E> the generic type of the {@link #execute() result}.
 * @see #executeRequired()
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface SingleQuery<E> extends Query<E> {

  /**
   * @throws ObjectNotFoundException if the query had no match.
   * @return the result of {@link #execute()} but never {@code null}.
   */
  E executeRequired() throws ObjectNotFoundException;

}
