/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.feature;

import net.sf.mmm.util.query.api.feature.FeatureUpsert;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.base.statement.SqlBuilder;

/**
 * Implementation of {@link AbstractFeature} for {@link FeatureWhere}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class FeatureUpsertImpl extends AbstractFeature implements FeatureUpsert<FeatureUpsertImpl> {

  private boolean upsert;

  /**
   * The constructor.
   */
  public FeatureUpsertImpl() {
    super(SORT_INDEX_UPSERT);
  }

  @Override
  public FeatureUpsertImpl upsert() {

    this.upsert = true;
    return this;
  }

  @Override
  public void build(SqlBuilder builder) {

    if (this.upsert) {
      builder.getBuffer().append(getDialect().upsert());
    }
  }

}
