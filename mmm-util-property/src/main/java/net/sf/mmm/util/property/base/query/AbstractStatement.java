/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

import java.util.List;

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

  private Expression where;

  private long limit;

  private long offset;

  /**
   * The constructor.
   *
   * @param dialect the {@link SqlDialect}.
   */
  public AbstractStatement(SqlDialect dialect) {
    super();
    this.dialect = dialect;
    this.limit = Long.MAX_VALUE;
    this.offset = 0;
  }

  @Override
  public SqlDialect getSqlDialect() {

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
   * @see net.sf.mmm.util.property.api.query.feature.FeatureWhere#where(Expression...)
   * @param expressions the {@link Expression}s to add.
   * @return this query instance for fluent API calls.
   */
  protected SELF where(Expression... expressions) {

    Expression expression = combine(this.where, Conjunction.AND, expressions);
    if (expression.isConstant() && !expression.evaluate()) {
      throw new IllegalArgumentException("Expression can never match!");
    }
    this.where = expression;
    return self();
  }

  /**
   * @see net.sf.mmm.util.property.api.query.feature.FeatureLimit#limit(long)
   * @param newLimit the maximum number of matches.
   * @return this query instance for fluent API calls.
   */
  protected SELF limit(long newLimit) {

    this.limit = newLimit;
    return self();
  }

  /**
   * @see net.sf.mmm.util.property.api.query.feature.FeaturePaging#offset(long)
   * @param newOffset the number of records to skip.
   * @return this query instance for fluent API calls.
   */
  protected SELF offset(long newOffset) {

    this.offset = newOffset;
    return self();
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
  protected final void build(SqlBuilder builder) {

    buildStart(builder);
    buildMain(builder);
    buildEnd(builder);
  }

  /**
   * @param builder the {@link SqlBuilder} with the query context to build the SQL and bind variables.
   */
  protected void buildStart(SqlBuilder builder) {

  }

  /**
   * @param builder the {@link SqlBuilder} with the query context to build the SQL and bind variables.
   */
  protected void buildMain(SqlBuilder builder) {

    builder.addWhere(this.where);
  }

  /**
   * @param builder the {@link SqlBuilder} with the query context to build the SQL and bind variables.
   */
  protected void buildEnd(SqlBuilder builder) {

    builder.addPaging(this.offset, this.limit);
  }

  @Override
  public List<Object> getVariables() {

    return getBuilder().getVariables();
  }

  @Override
  public String getSql() {

    return getBuilder().toString();
  }

  @Override
  public String toString() {

    return getBuilder().toString();
  }

}
