/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

/**
 * This is the abstract interface for a {@link net.sf.mmm.util.query.api.statement.Statement} with support for
 * {@link #limit(int)}.
 *
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatureLimit<SELF extends FeatureLimit<SELF>> extends StatementFeature {

  /**
   * Set the limit for the query matches. Should be called only once per query.
   *
   * @param limit the limit for the maximum number of matches for the query.
   * @return this query instance for fluent API calls.
   */
  SELF limit(int limit);

}
