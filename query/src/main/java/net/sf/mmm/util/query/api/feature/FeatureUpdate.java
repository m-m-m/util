/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.statement.UpdateStatement;

/**
 * This is the abstract interface for a {@link net.sf.mmm.util.query.api.statement.StatementFactory} supporting a
 * {@link UpdateStatement}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract interface FeatureUpdate extends StatementFactoryFeature {

  /**
   * Creates a regular {@link UpdateStatement} ({@code UPDATE alias.source [AS alias.name] ...}).
   *
   * @param <E> the generic type of the entity to update.
   * @param alias the {@link EntityAlias} to update.
   * @return the new {@link UpdateStatement}.
   */
  <E> UpdateStatement<E, ?> update(EntityAlias<E> alias);

}
