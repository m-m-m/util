/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.property.api.query.StoreStatement;
import net.sf.mmm.util.property.base.expression.Arg;

/**
 * This is the abstract base-implementation of {@link StoreStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractStoreStatement<E, SELF extends AbstractStoreStatement<E, SELF>>
    extends AbstractStatement<E, SELF> implements StoreStatement<E, SELF> {

  private final List<SetExpression<?>> setExpressionList;

  /**
   * The constructor.
   *
   * @param dialect the {@link SqlDialect}.
   */
  public AbstractStoreStatement(SqlDialect dialect) {
    super(dialect);
    this.setExpressionList = new ArrayList<>();
  }

  @Override
  public <V> SELF set(PropertyPath<V> path, V value) {

    this.setExpressionList.add(new SetExpression<>(path, value));
    return self();
  }

  @Override
  public <V> SELF set(PropertyPath<V> path, PropertyPath<V> valuePath) {

    this.setExpressionList.add(new SetExpression<>(path, valuePath));
    return self();
  }

  @Override
  protected void buildMain(SqlBuilder builder) {

    builder.addSet(this.setExpressionList);
    super.buildMain(builder);
  }

  /**
   * A single expression of an {@code SET} clause.
   *
   * @param <V> the generic type of the value to set.
   */
  protected static class SetExpression<V> {

    private final PropertyPath<V> path;

    private final Arg<V> assignment;

    /**
     * The constructor.
     *
     * @param path - see {@link #getPath()}.
     * @param assignment - see {@link #getAssignment()}.
     */
    public SetExpression(PropertyPath<V> path, PropertyPath<V> assignment) {
      super();
      this.path = path;
      this.assignment = new Arg<>(assignment);
    }

    /**
     * The constructor.
     *
     * @param path - see {@link #getPath()}.
     * @param assignment - see {@link #getAssignment()}.
     */
    public SetExpression(PropertyPath<V> path, V assignment) {
      super();
      this.path = path;
      this.assignment = new Arg<>(assignment);
    }

    /**
     * @return the path
     */
    public PropertyPath<V> getPath() {

      return this.path;
    }

    /**
     * @return the assignment
     */
    public Arg<V> getAssignment() {

      return this.assignment;
    }

  }

}
