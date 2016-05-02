/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.feature;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.feature.FeatureLet;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.api.variable.Variable;
import net.sf.mmm.util.query.base.statement.SqlBuilder;
import net.sf.mmm.util.query.base.statement.SqlDialect;

/**
 * Implementation of {@link AbstractFeature} for {@link FeatureWhere}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class FeatureLetImpl extends AbstractFeature implements FeatureLet<FeatureLetImpl> {

  private final List<LetExpression<?>> letExpressionList;

  /**
   * The constructor.
   */
  public FeatureLetImpl() {
    super(SORT_INDEX_LET);
    this.letExpressionList = new ArrayList<>();
  }

  @Override
  public <V> Variable<V> let(PropertyPath<V> path, String variable) {

    Variable<V> var = Variable.valueOf(variable);
    let(var, path);
    return var;
  }

  @Override
  public FeatureLetImpl let(String variable, PropertyPath<?> path) {

    this.letExpressionList.add(new LetExpression<>(variable, asPath(path)));
    return this;
  }

  @Override
  public <V> FeatureLetImpl let(Variable<V> variable, PropertyPath<V> path) {

    this.letExpressionList.add(new LetExpression<>(variable, asPath(path)));
    return this;
  }

  @Override
  public void build(SqlBuilder builder) {

    if (this.letExpressionList.isEmpty()) {
      return;
    }
    SqlDialect dialect = getDialect();
    builder.append(dialect.let());
    String separator = null;
    for (LetExpression<?> expression : this.letExpressionList) {
      if (separator == null) {
        separator = dialect.separator();
      } else {
        builder.append(separator);
      }
      builder.addVariable(expression.variable);
      builder.append(dialect.assignment());
      builder.addPath(expression.path);
    }
  }

  /**
   * A single expression of an {@code SET} clause.
   *
   * @param <V> the generic type of the value to set.
   */
  protected static class LetExpression<V> {

    private final Variable<V> variable;

    private final Path<V> path;

    /**
     * The constructor.
     *
     * @param variable - see {@link #getVariable()}
     * @param path - see {@link #getPath()}.
     */
    public LetExpression(Variable<V> variable, Path<V> path) {
      super();
      this.variable = variable;
      this.path = path;
    }

    /**
     * The constructor.
     *
     * @param variable - see {@link #getVariable()}
     * @param path - see {@link #getPath()}.
     */
    public LetExpression(String variable, Path<V> path) {
      this(Variable.valueOf(variable, path), path);
    }

    /**
     * @return the {@link Variable}.
     */
    public Variable<V> getVariable() {

      return this.variable;
    }

    /**
     * @return the path
     */
    public PropertyPath<V> getPath() {

      return this.path;
    }

  }

}
