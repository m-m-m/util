/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.feature;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.feature.FeatureSet;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.base.argument.ConstantArgument;
import net.sf.mmm.util.query.base.statement.SqlBuilder;
import net.sf.mmm.util.query.base.statement.SqlDialect;

/**
 * Implementation of {@link AbstractFeature} for {@link FeatureWhere}.
 *
 * @author hohwille
 * @since 8.4.0
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

  /**
   * @return the {@link List} of {@link SetExpression}s.
   */
  public List<SetExpression<?>> getSetExpressionList() {

    return this.setExpressionList;
  }

  @Override
  public <V> FeatureSetImpl set(PropertyPath<V> path, V value) {

    this.setExpressionList.add(new SetExpression<>(asPath(path), value));
    return this;
  }

  @Override
  public <V> FeatureSetImpl set(PropertyPath<V> path, PropertyPath<V> valuePropertyPath) {

    this.setExpressionList.add(new SetExpression<>(asPath(path), asPath(valuePropertyPath)));
    return this;
  }

  @Override
  public void build(SqlBuilder builder) {

    if (this.setExpressionList.isEmpty()) {
      return;
    }
    SqlDialect dialect = getDialect();
    builder.append(dialect.set());
    String separator = null;
    for (SetExpression<?> expression : this.setExpressionList) {
      if (separator == null) {
        separator = dialect.separator();
      } else {
        builder.append(separator);
      }
      builder.addPath(expression.path);
      builder.append(dialect.assignment());
      builder.append(expression.assignment);
    }
  }

  /**
   * A single expression of an {@code SET} clause.
   *
   * @param <V> the generic type of the value to set.
   */
  protected static class SetExpression<V> {

    private final Path<V> path;

    private final Argument<V> assignment;

    /**
     * The constructor.
     *
     * @param path - see {@link #getPath()}.
     * @param assignment - see {@link #getAssignment()}.
     */
    public SetExpression(Path<V> path, Argument<V> assignment) {
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
    public SetExpression(Path<V> path, V assignment) {
      this(path, new ConstantArgument<>(assignment));
    }

    /**
     * @return the {@link Path}.
     */
    public Path<V> getPath() {

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
