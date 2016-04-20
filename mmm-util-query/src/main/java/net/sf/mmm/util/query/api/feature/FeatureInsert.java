/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.statement.InsertStatement;

/**
 * {@link StatementFactoryFeature} for a {@link net.sf.mmm.util.query.api.statement.StatementFactory} supporting a
 * {@link InsertStatement}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatureInsert extends StatementFactoryFeature {

  /**
   * Creates a regular {@link InsertStatement} ({@code DELETE FROM alias.source [AS alias.name] ...}).
   *
   * @param <E> the generic type of the entity to create (insert).
   * @param alias the {@link EntityAlias} to create (insert).
   * @return the new {@link InsertStatement}.
   */
  <E> InsertStatement<E, ?> insertInto(EntityAlias<E> alias);

}
