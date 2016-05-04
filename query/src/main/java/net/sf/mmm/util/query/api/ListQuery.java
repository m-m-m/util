/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api;

import java.util.Iterator;
import java.util.List;

/**
 * This is the interface for a regular {@link Query} that returns the {@link List} of matching objects.
 *
 * @param <E> the generic type of the {@link List}-{@link List#get(int) elements}.
 *
 * @see net.sf.mmm.util.query.api.statement.SelectStatement#query()
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ListQuery<E> extends Query<List<E>>, Iterable<E> {

  /**
   * {@link #execute() Executes} the query and returns the matching objects as {@link Iterator}. This method may be
   * overridden for database technology implementations that have native support for {@link Iterator} (unlike JPA). In
   * such case this method is way more efficient if you only need to iterate the results to a specific point without the
   * need to know the {@link List#size() number of matches}. If possible you shall prefer this method over
   * {@link #execute()}.
   *
   * @return an {@link Iterator} of the {@link #execute() results}.
   */
  @Override
  default Iterator<E> iterator() {

    return execute().iterator();
  }

}
