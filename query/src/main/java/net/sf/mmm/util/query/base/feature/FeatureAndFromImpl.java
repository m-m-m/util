/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.feature;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.query.api.feature.FeatureAndFrom;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.base.statement.SqlBuilder;

/**
 * Implementation of {@link AbstractFeature} for {@link FeatureWhere}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class FeatureAndFromImpl extends AbstractFeature implements FeatureAndFrom<FeatureAndFromImpl> {

  private final List<EntityAlias<?>> aliasList;

  /**
   * The constructor.
   */
  public FeatureAndFromImpl() {
    super(SORT_INDEX_AND_FROM);
    this.aliasList = new ArrayList<>();
  }

  @Override
  public FeatureAndFromImpl andFrom(EntityAlias<?>... aliases) {

    for (EntityAlias<?> alias : aliases) {
      getStatement().addAlias(alias);
      this.aliasList.add(alias);
    }
    return this;
  }

  @Override
  public void build(SqlBuilder builder) {

    for (EntityAlias<?> alias : this.aliasList) {
      builder.append(getDialect().separator());
      builder.append(getDialect().alias(alias, false));
    }
  }

}
