/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.statement.DeleteStatement;

/**
 * This is the abstract interface for a {@link net.sf.mmm.util.query.api.statement.StatementFactory} supporting a
 * {@link DeleteStatement}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatureDelete extends StatementFactoryFeature {

  /**
   * Creates a regular {@link DeleteStatement} ({@code DELETE FROM alias.source [AS alias.name] ...}).
   *
   * @param <E> the generic type of the entity to delete from.
   * @param alias the {@link EntityAlias} to delete from.
   * @return the new {@link DeleteStatement}.
   */
  <E> DeleteStatement<E, ?> deleteFrom(EntityAlias<E> alias);

}
