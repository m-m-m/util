/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.property.api.query.SelectStatement;

/**
 * This is the abstract base-implementation of {@link SelectStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractSelectStatement<E, SELF extends AbstractSelectStatement<E, SELF>>
    extends AbstractStatement<E, SELF> implements SelectStatement<E, SELF> {

  private final List<OrderByExpression> orderByList;

  private final List<PropertyPath<?>> groupByList;

  /**
   * The constructor.
   *
   * @param dialect the {@link SqlDialect}.
   */
  public AbstractSelectStatement(SqlDialect dialect) {
    super(dialect);
    this.orderByList = new ArrayList<>();
    this.groupByList = new ArrayList<>();
  }

  @Override
  public SELF where(Expression... expressions) {

    return super.where(expressions);
  }

  @Override
  public SELF orderBy(PropertyPath<?> path, SortOrder order) {

    this.orderByList.add(new OrderByExpression(path, order));
    return self();
  }

  @Override
  public SELF groupBy(PropertyPath<?> path) {

    this.groupByList.add(path);
    return self();
  }

  @Override
  public SELF limit(long newLimit) {

    return super.limit(newLimit);
  }

  @Override
  public SELF offset(long newOffset) {

    return super.offset(newOffset);
  }

  @Override
  protected void buildMain(SqlBuilder builder) {

    builder.addFrom(getSource());
    super.buildMain(builder);
    builder.addGroupBy(this.groupByList);
    builder.addOrderBy(this.orderByList);
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
