/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

/**
 * {@link StatementFeature} for a {@link net.sf.mmm.util.query.api.statement.Statement} supporting an {@link #upsert()
 * USPERT} what is a mix of an insert and an update. If supported (e.g. by OrientDB) at all it is typically used by
 * {@link net.sf.mmm.util.query.api.statement.UpdateStatement}s.
 *
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatureUpsert<SELF extends FeatureUpsert<SELF>> extends StatementFeature {

  /**
   * Marks the statement as UPSERT meaning that it is an insert of the object does not exist and an update if the object
   * already exists. An UPSERT requires a {@link FeatureWhere#where(net.sf.mmm.util.query.api.expression.Expression...)
   * WHERE clause}.
   *
   * @return this query instance for fluent API calls.
   */
  SELF upsert();

}
