/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.statement.Statement;
import net.sf.mmm.util.query.api.variable.Variable;
import net.sf.mmm.util.query.base.argument.ConstantArgument;
import net.sf.mmm.util.query.base.expression.ConjunctionExpression;
import net.sf.mmm.util.query.base.expression.SingleExpression;
import net.sf.mmm.util.query.base.expression.SqlOperator;
import net.sf.mmm.util.query.base.expression.SqlOperator.SqlOperatorLike;
import net.sf.mmm.util.query.base.path.Alias;
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

  private final List<Object> parametersInternal;

  private final List<Object> parameters;

  private final List<PropertyPath<?>> paths;

  private final List<Variable<?>> variables;

  private final List<Alias<?>> sources;

  /**
   * The constructor.
   *
   * @param dialect the {@link SqlDialect} to use.
   */
  public SqlBuilder(SqlDialect dialect) {
    super();
    this.dialect = dialect;
    this.sqlBuilder = new StringBuilder(32);
    this.parametersInternal = new ArrayList<>();
    this.parameters = Collections.unmodifiableList(this.parametersInternal);
    this.paths = new ArrayList<>();
    this.variables = new ArrayList<>();
    this.sources = new ArrayList<>();
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
  public List<Object> getParameters() {

    return this.parameters;
  }

  /**
   * @return the {@link List} of all collected {@link PropertyPath}s.
   */
  List<PropertyPath<?>> getPaths() {

    return this.paths;
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
    Argument<?> arg2 = expression.getArg2();
    if (arg2 == ConstantArgument.NULL) {
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
   * @param arg the {@link ConstantArgument} to add.
   */
  public void addArg(Argument<?> arg) {

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
        addParameter(range.getMin());
        this.sqlBuilder.append(this.dialect.and());
        addParameter(range.getMax());
      } else {
        addParameter(value);
      }
    } else {
      addPath(path);
    }
  }

  /**
   * @param value the value to add as bind variable.
   */
  public void addParameter(Object value) {

    this.sqlBuilder.append(this.dialect.parameter(this.parametersInternal.size()));
    this.parametersInternal.add(value);
  }

  /**
   * @param path the {@link PropertyPath} to add.
   */
  public void addPath(PropertyPath<?> path) {

    this.sqlBuilder.append(this.dialect.ref(path.getName()));
    this.paths.add(path);
  }

  /**
   * @param pathList the {@link PropertyPath}s to add.
   */
  public void addPaths(List<PropertyPath<?>> pathList) {

    String separator = null;
    for (PropertyPath<?> path : pathList) {
      if (separator == null) {
        separator = this.dialect.separator();
      } else {
        this.sqlBuilder.append(separator);
      }
      addPath(path);
    }
  }

  /**
   * @param pathArray the {@link PropertyPath}s to add.
   */
  public void addPaths(PropertyPath<?>... pathArray) {

    addPaths(Arrays.asList(pathArray));
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
   * @param alias the {@link Alias}.
   */
  public void addSelectFrom(Alias<?> alias) {

    this.sqlBuilder.append(this.dialect.select());
    addFrom(alias);
  }

  /**
   * @param alias the {@link Alias}.
   */
  public void addDeleteFrom(Alias<?> alias) {

    this.sqlBuilder.append(this.dialect.delete());
    addFrom(alias);
  }

  /**
   * Adds {@link SqlDialect#from()}.
   */
  public void addFrom() {

    this.sqlBuilder.append(this.dialect.from());
  }

  /**
   * @param alias the {@link Alias}.
   */
  public void addFrom(Alias<?> alias) {

    this.sqlBuilder.append(this.dialect.from());
    addAlias(alias);
  }

  /**
   * @param alias the {@link Alias}.
   */
  public void addInsertInto(Alias<?> alias) {

    this.sqlBuilder.append(this.dialect.insertInto());
    addAlias(alias);
  }

  /**
   * @param alias the {@link Alias}.
   */
  protected void addAlias(Alias<?> alias) {

    this.sqlBuilder.append(this.dialect.ref(alias.getSource()));
    this.sqlBuilder.append(this.dialect.as(alias.getName()));
    this.sources.add(alias);
  }

  /**
   * @param alias the {@link Alias}.
   */
  public void addUpdate(Alias<?> alias) {

    this.sqlBuilder.append(this.dialect.update());
    addAlias(alias);
  }

  /**
   * @param variable the {@link Variable} to add.
   */
  public void addVariable(Variable<?> variable) {

    this.sqlBuilder.append(this.dialect.variable(variable));
    this.variables.add(variable);
  }

}