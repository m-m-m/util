/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

/**
 * {@link StatementFeature} for a {@link net.sf.mmm.util.query.api.statement.Statement} that modifies data.
 *
 * @see net.sf.mmm.util.query.api.statement.ModifyStatement
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatureModify extends StatementFeature {

  /**
   * Execute the SQL and return the amount of affected rows
   *
   * @return amount of affected rows
   */
  long execute();

}
