/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.path.ComparablePath;

/**
 * {@link StatementFeature} for a {@link net.sf.mmm.util.query.api.statement.Statement} allowing an
 * {@link #orderBy(ComparablePath, SortOrder) ORDER BY clause}.
 *
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.5.0
 */
public abstract interface FeatureOrderBy<SELF extends FeatureOrderBy<SELF>> extends StatementFeature {

  /**
   * Adds the given {@link PropertyPath} to the {@code ORDER BY} clause using default {@link SortOrder} (what should be
   * {@link SortOrder#ASCENDING ascending}).
   *
   * @param path the {@link PropertyPath} to order by.
   * @param order the {@link SortOrder}.
   * @return this query instance for fluent API calls.
   */
  default SELF orderBy(ComparablePath<?> path) {

    return orderBy(path, null);
  }

  /**
   * Adds the given {@link PropertyPath} to the {@code ORDER BY} clause.
   *
   * @param path the {@link PropertyPath} to order by.
   * @param order the {@link SortOrder}.
   * @return this query instance for fluent API calls.
   */
  SELF orderBy(ComparablePath<?> path, SortOrder order);

}
