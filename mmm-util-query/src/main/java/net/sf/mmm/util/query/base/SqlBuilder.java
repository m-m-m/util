/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.property.base.expression.Arg;
import net.sf.mmm.util.property.base.expression.ConjunctionExpression;
import net.sf.mmm.util.property.base.expression.SingleExpression;
import net.sf.mmm.util.property.base.expression.SqlOperator;
import net.sf.mmm.util.property.base.expression.SqlOperator.SqlOperatorLike;
import net.sf.mmm.util.query.api.Statement;
import net.sf.mmm.util.value.api.Range;

/**
 * This class represents the with context to build the SQL string and collect bind variables.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class SqlBuilder {

  private final SqlDialect dialect;

  private final StringBuilder sqlBuilder;

  private final List<Object> variablesInternal;

  private final List<Object> variables;

  /**
   * The constructor.
   *
   * @param dialect the {@link SqlDialect} to use.
   */
  public SqlBuilder(SqlDialect dialect) {
    super();
    this.dialect = dialect;
    this.sqlBuilder = new StringBuilder(32);
    this.variablesInternal = new ArrayList<>();
    this.variables = Collections.unmodifiableList(this.variablesInternal);
  }

  /**
   * @return the {@link Statement#getSql() SQL}.
   */
  public String getSql() {

    return this.sqlBuilder.toString();
  }

  /**
   * @return the {@link StringBuilder} for the {@link #getSql() SQL}.
   */
  public StringBuilder getBuffer() {

    return this.sqlBuilder;
  }

  /**
   * @return the {@link SqlDialect}.
   */
  public SqlDialect getDialect() {

    return this.dialect;
  }

  /**
   * @return the {@link List} with the bind variables.
   */
  public List<Object> getVariables() {

    return this.variables;
  }

  /**
   * @param expression the {@link Expression} to add.
   */
  public void addExpression(Expression expression) {

    if (expression instanceof ConjunctionExpression) {
      ConjunctionExpression conjunctionExpression = (ConjunctionExpression) expression;
      addConjunctionExpression(conjunctionExpression);
    } else {
      SingleExpression<?, ?> singleExpression = (SingleExpression<?, ?>) expression;
      addSingleExpression(singleExpression);
    }
  }

  /**
   * @param expression the {@link Expression} to add.
   */
  protected void addSingleExpression(SingleExpression<?, ?> expression) {

    addArg(expression.getArg1());
    SqlOperator<?, ?> operator = expression.getOperator();
    Arg<?> arg2 = expression.getArg2();
    if (arg2 == Arg.NULL) {
      if (operator == SqlOperator.EQUAL) {
        this.sqlBuilder.append(this.dialect.isNull());
      } else if (operator == SqlOperator.NOT_EQUAL) {
        this.sqlBuilder.append(this.dialect.isNotNull());
      }
    } else {
      this.sqlBuilder.append(this.dialect.operator(operator));
      addArg(arg2);
      if (operator instanceof SqlOperatorLike) {
        char escape = ((SqlOperatorLike) operator).getEscape();
        if (escape != '\0') {
          this.sqlBuilder.append(this.dialect.escape(escape));
        }
      }
    }
  }

  /**
   * @param expression the {@link Expression} to add.
   */
  protected void addConjunctionExpression(ConjunctionExpression expression) {

    Conjunction conjunction = expression.getConjunction();
    this.sqlBuilder.append(this.dialect.startConjunction(conjunction));
    String conjunctionSql = null;
    // builder.sql.append(this.dialect.startExpressions()));
    for (Expression term : expression.getTerms()) {
      if (conjunctionSql == null) {
        conjunctionSql = this.dialect.conjuction(conjunction);
      } else {
        this.sqlBuilder.append(conjunctionSql);
      }
      addExpression(term);
    }
    this.sqlBuilder.append(this.dialect.endConjunction(conjunction));
  }

  /**
   * @param arg the {@link Arg} to add.
   */
  public void addArg(Arg<?> arg) {

    PropertyPath<?> path = arg.getPath();
    if (path == null) {
      Object value = arg.getValue();
      if (value == null) {
        this.sqlBuilder.append(this.dialect.literalNull());
      } else if (value instanceof Boolean) {
        if (((Boolean) value).booleanValue()) {
          this.sqlBuilder.append(this.dialect.literalTrue());
        } else {
          this.sqlBuilder.append(this.dialect.literalFalse());
        }
      } else if (value instanceof Range) {
        Range<?> range = (Range<?>) value;
        addVariable(range.getMin());
        this.sqlBuilder.append(this.dialect.and());
        addVariable(range.getMax());
      } else {
        addVariable(value);
      }
    } else {
      addPath(path);
    }
  }

  /**
   * @param value the value to add as bind variable.
   */
  public void addVariable(Object value) {

    this.sqlBuilder.append(this.dialect.variable(this.variablesInternal.size()));
    this.variablesInternal.add(value);
  }

  /**
   * @param path the {@link PropertyPath} to add.
   */
  protected void addPath(PropertyPath<?> path) {

    this.sqlBuilder.append(this.dialect.ref(path.getName()));
  }

  /**
   * @param paths the {@link PropertyPath}s to add.
   */
  public void addPaths(List<PropertyPath<?>> paths) {

    String separator = null;
    for (PropertyPath<?> path : paths) {
      if (separator == null) {
        separator = this.dialect.separator();
      } else {
        this.sqlBuilder.append(separator);
      }
      addPath(path);
    }
  }

  /**
   * @param paths the {@link PropertyPath}s to add.
   */
  protected void addPaths(PropertyPath<?>... paths) {

    addPaths(Arrays.asList(paths));
  }

  @Override
  public String toString() {

    return this.sqlBuilder.toString();
  }

  /**
   * @param groupByList the {@link List} of {@link PropertyPath}s to add as {@code GROUP BY} clause. It may be
   *        {@code null}.
   */
  public void addGroupBy(List<PropertyPath<?>> groupByList) {

    if ((groupByList == null) || (groupByList.isEmpty())) {
      return;
    }
    this.sqlBuilder.append(this.dialect.groupBy());
    addPaths(groupByList);
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  public void addSelectFrom(String source) {

    this.sqlBuilder.append(this.dialect.select());
    addFrom(source);
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  public void addDeleteFrom(String source) {

    this.sqlBuilder.append(this.dialect.delete());
    addFrom(source);
  }

  /**
   * Adds {@link SqlDialect#from()}.
   */
  public void addFrom() {

    this.sqlBuilder.append(this.dialect.from());
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  public void addFrom(String source) {

    this.sqlBuilder.append(this.dialect.from());
    addSource(source);
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  public void addInsertInto(String source) {

    this.sqlBuilder.append(this.dialect.insertInto());
    addSource(source);
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  protected void addSource(String source) {

    this.sqlBuilder.append(this.dialect.ref(source));
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  public void addUpdate(String source) {

    this.sqlBuilder.append(this.dialect.update());
    addSource(source);
  }

}