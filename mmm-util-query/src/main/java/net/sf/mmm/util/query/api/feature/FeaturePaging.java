/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

/**
 * This is the abstract interface for a query {@link net.sf.mmm.util.query.api.statement.Statement} with paging support via
 * {@link #limit(int)} and {@link #offset(long)}.
 *
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeaturePaging<SELF extends FeaturePaging<SELF>> extends FeatureLimit<SELF> {

  /**
   * Set the offset for the query results. Should be called only once per query. The offset is useful for paging results
   * in combination with {@link #limit(int)}).
   *
   * @param offset the number of results to skip.
   * @return this query instance for fluent API calls.
   */
  SELF offset(long offset);

}
