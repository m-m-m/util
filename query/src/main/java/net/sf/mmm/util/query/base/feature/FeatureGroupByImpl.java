/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.feature;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.feature.FeatureGroupBy;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.base.statement.SqlBuilder;

/**
 * Implementation of {@link AbstractFeature} for {@link FeatureWhere}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class FeatureGroupByImpl extends AbstractFeature implements FeatureGroupBy<FeatureGroupByImpl> {

  private final List<Path<?>> groupByList;

  /**
   * The constructor.
   */
  public FeatureGroupByImpl() {
    super(SORT_INDEX_GROUP_BY);
    this.groupByList = new ArrayList<>();
  }

  @Override
  public FeatureGroupByImpl groupBy(PropertyPath<?> path) {

    this.groupByList.add(asPath(path));
    return this;
  }

  @Override
  public void build(SqlBuilder builder) {

    if (this.groupByList.isEmpty()) {
      return;
    }
    builder.append(getDialect().groupBy());
    builder.addPaths(this.groupByList);
  }
}
