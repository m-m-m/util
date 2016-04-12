/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.feature;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.feature.FeatureOrderBy;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.base.statement.SqlBuilder;
import net.sf.mmm.util.query.base.statement.SqlDialect;

/**
 * Implementation of {@link AbstractFeature} for {@link FeatureWhere}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class FeatureOrderByImpl extends AbstractFeature implements FeatureOrderBy<FeatureOrderByImpl> {

  private final List<OrderByExpression> orderByList;

  /**
   * The constructor.
   */
  public FeatureOrderByImpl() {
    super(SORT_INDEX_ORDER_BY);
    this.orderByList = new ArrayList<>();
  }

  @Override
  public FeatureOrderByImpl orderBy(PropertyPath<?> path, SortOrder order) {

    this.orderByList.add(new OrderByExpression(path, order));
    return this;
  }

  @Override
  public void build(SqlBuilder builder) {

    if (this.orderByList.isEmpty()) {
      return;
    }
    StringBuilder sqlBuilder = builder.getBuffer();
    SqlDialect dialect = builder.getDialect();
    sqlBuilder.append(dialect.orderBy());
    String separator = null;
    for (OrderByExpression orderBy : this.orderByList) {
      if (separator == null) {
        separator = dialect.separator();
      } else {
        sqlBuilder.append(separator);
      }
      sqlBuilder.append(dialect.ref(orderBy.getPath().getName()));
      sqlBuilder.append(dialect.order(orderBy.getOrder()));
    }
  }

  /**
   * A single expression of an {@code ORDER BY} clause.
   */
  protected static class OrderByExpression {

    private final PropertyPath<?> path;

    private final SortOrder order;

    /**
     * The constructor.
     *
     * @param path the {@link PropertyPath}.
     * @param order the {@link SortOrder}.
     */
    public OrderByExpression(PropertyPath<?> path, SortOrder order) {
      super();
      this.path = path;
      this.order = order;
    }

    /**
     * @return the {@link PropertyPath}.
     */
    public PropertyPath<?> getPath() {

      return this.path;
    }

    /**
     * @return the {@link SortOrder}.
     */
    public SortOrder getOrder() {

      return this.order;
    }

  }

}
