/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.query.feature;

import net.sf.mmm.util.property.api.path.PropertyPath;

/**
 * This is the abstract interface for a query {@link net.sf.mmm.util.property.api.query.Statement} allowing a
 * {@link #groupBy(PropertyPath) GROUP BY clause}.
 *
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatureGroupBy<SELF extends FeatureGroupBy<SELF>> {

  /**
   * Adds the given {@link PropertyPath} to the {@code GROUP BY} clause.
   *
   * @param path the {@link PropertyPath} to group by.
   * @return this query instance for fluent API calls.
   */
  SELF groupBy(PropertyPath<?> path);

}
