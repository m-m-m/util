/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.property.api.query.Statement;
import net.sf.mmm.util.property.base.expression.Expressions;

/**
 * This is the abstract base-implementation of {@link Statement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractStatement<E, SELF extends AbstractStatement<E, SELF>> implements Statement<E, SELF> {

  private SqlDialect dialect;

  private SqlBuilder sqlContext;

  /**
   * The constructor.
   *
   * @param dialect the {@link SqlDialect}.
   */
  public AbstractStatement(SqlDialect dialect) {
    super();
    this.dialect = dialect;
  }

  /**
   * @return the {@link SqlDialect} to use.
   */
  public SqlDialect getDialect() {

    return this.dialect;
  }

  /**
   * @return the name of the source (table, object, class, etc.) to select from.
   */
  protected abstract String getSource();

  /**
   * @return this query instance for fluent API calls.
   */
  @SuppressWarnings("unchecked")
  protected SELF self() {

    this.sqlContext = null;
    return (SELF) this;
  }

  /**
   * Combines the given {@link Expression} with the given array of new {@code Expression}s using the given
   * {@link Conjunction}.
   *
   * @param expression the current {@link Expression} or {@code null}.
   * @param conjunction the {@link Conjunction} used to combine.
   * @param newExpressions the array with the new {@link Expression}s to combine.
   * @return the combined {@link Expression}.
   */
  protected Expression combine(Expression expression, Conjunction conjunction, Expression... newExpressions) {

    if ((newExpressions == null) || (newExpressions.length == 0)) {
      return expression;
    }
    if (expression == null) {
      if (newExpressions.length == 1) {
        return newExpressions[0];
      } else {
        return Expressions.combine(conjunction, newExpressions);
      }
    } else {
      return expression.combine(conjunction, newExpressions);
    }
  }

  /**
   * @return the {@link SqlBuilder}.
   */
  protected SqlBuilder getBuilder() {

    if (this.sqlContext == null) {
      this.sqlContext = createSqlBuilder();
      build(this.sqlContext);
    }
    return this.sqlContext;
  }

  /**
   * @return a new instance of {@code SqlContext}.
   */
  protected SqlBuilder createSqlBuilder() {

    return new SqlBuilder(this.dialect);
  }

  /**
   * @param builder the {@link SqlBuilder} with the query context to build the SQL and bind variables.
   */
  protected abstract void build(SqlBuilder builder);

  @Override
  public String toString() {

    return getBuilder().toString();
  }

}
