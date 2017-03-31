/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import net.sf.mmm.util.query.api.path.CollectionPath;
import net.sf.mmm.util.query.api.path.EntityAlias;

/**
 * {@link StatementFeature} for a {@link net.sf.mmm.util.query.api.statement.Statement} with support for #innerJoin
 * block.
 *
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.5.0
 */
public abstract interface FeatureAndFrom<SELF extends FeatureAndFrom<SELF>> extends StatementFeature {

  /**
   * @param aliases the {@link CollectionPath} to select from (in addition to
   *        {@link net.sf.mmm.util.query.api.statement.Statement#getAlias()}).
   * @return this query instance for fluent API calls.
   */
  SELF andFrom(EntityAlias<?>... aliases);

}
