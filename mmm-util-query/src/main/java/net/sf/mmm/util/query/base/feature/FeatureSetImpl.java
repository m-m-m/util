/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.feature;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.feature.FeatureSet;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.base.argument.ConstantArgument;
import net.sf.mmm.util.query.base.statement.SqlBuilder;
import net.sf.mmm.util.query.base.statement.SqlDialect;

/**
 * Implementation of {@link AbstractFeature} for {@link FeatureWhere}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class FeatureSetImpl extends AbstractFeature implements FeatureSet<FeatureSetImpl> {

  private final List<SetExpression<?>> setExpressionList;

  /**
   * The constructor.
   */
  public FeatureSetImpl() {
    super(SORT_INDEX_SET);
    this.setExpressionList = new ArrayList<>();
  }

  @Override
  public <V> FeatureSetImpl set(PropertyPath<V> path, V value) {

    this.setExpressionList.add(new SetExpression<>(path, value));
    return this;
  }

  @Override
  public <V> FeatureSetImpl set(PropertyPath<V> path, PropertyPath<V> valuePropertyPath) {

    this.setExpressionList.add(new SetExpression<>(path, asArg(valuePropertyPath)));
    return this;
  }

  @Override
  public void build(SqlBuilder builder) {

    if (this.setExpressionList.isEmpty()) {
      return;
    }
    StringBuilder sqlBuilder = builder.getBuffer();
    SqlDialect dialect = builder.getDialect();
    sqlBuilder.append(dialect.set());
    String separator = null;
    for (SetExpression<?> expression : this.setExpressionList) {
      if (separator == null) {
        separator = dialect.separator();
      } else {
        sqlBuilder.append(separator);
      }
      sqlBuilder.append(dialect.ref(expression.path.getName()));
      sqlBuilder.append(dialect.assignment());
      builder.addArg(expression.assignment);
    }
  }

  /**
   * A single expression of an {@code SET} clause.
   *
   * @param <V> the generic type of the value to set.
   */
  protected static class SetExpression<V> {

    private final PropertyPath<V> path;

    private final Argument<V> assignment;

    /**
     * The constructor.
     *
     * @param path - see {@link #getPath()}.
     * @param assignment - see {@link #getAssignment()}.
     */
    public SetExpression(PropertyPath<V> path, Argument<V> assignment) {
      super();
      this.path = path;
      this.assignment = assignment;
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
      this.assignment = new ConstantArgument<>(assignment);
    }

    /**
     * @return the {@link PropertyPath}.
     */
    public PropertyPath<V> getPath() {

      return this.path;
    }

    /**
     * @return the {@link Argument} to assign.
     */
    public Argument<V> getAssignment() {

      return this.assignment;
    }

  }

}
