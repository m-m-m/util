/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

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
import net.sf.mmm.util.property.base.query.AbstractSelectStatement.OrderByExpression;
import net.sf.mmm.util.property.base.query.AbstractStoreStatement.SetExpression;
import net.sf.mmm.util.value.api.Range;

/**
 * This class represents the with context to build the SQL string and collect bind variables.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class SqlBuilder {

  private final SqlDialect dialect;

  private final StringBuilder sql;

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
    this.sql = new StringBuilder(32);
    this.variablesInternal = new ArrayList<>();
    this.variables = Collections.unmodifiableList(this.variablesInternal);
  }

  /**
   * @return the sql
   */
  public String getSql() {

    return this.sql.toString();
  }

  /**
   * @return the {@link List} with the bind variables.
   */
  public List<Object> getVariables() {

    return this.variables;
  }

  /**
   * @param where the {@link Expression} to add as {@code WHERE} clause. May be {@code null}.
   */
  public void addWhere(Expression where) {

    if (where == null) {
      return;
    }
    if (where.isConstant()) {
      if (!where.evaluate()) {
        throw new IllegalStateException();
      }
    } else {
      this.sql.append(this.dialect.where());
      addExpression(where);
    }
  }

  /**
   * @param expression the {@link Expression} to add.
   */
  protected void addExpression(Expression expression) {

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
        this.sql.append(this.dialect.isNull());
      } else if (operator == SqlOperator.NOT_EQUAL) {
        this.sql.append(this.dialect.isNotNull());
      }
    } else {
      this.sql.append(this.dialect.operator(operator));
      addArg(arg2);
      if (operator instanceof SqlOperatorLike) {
        char escape = ((SqlOperatorLike) operator).getEscape();
        if (escape != '\0') {
          this.sql.append(this.dialect.escape(escape));
        }
      }
    }
  }

  /**
   * @param expression the {@link Expression} to add.
   */
  protected void addConjunctionExpression(ConjunctionExpression expression) {

    Conjunction conjunction = expression.getConjunction();
    this.sql.append(this.dialect.startConjunction(conjunction));
    String conjunctionSql = null;
    // builder.sql.append(this.dialect.startExpressions()));
    for (Expression term : expression.getTerms()) {
      if (conjunctionSql == null) {
        conjunctionSql = this.dialect.conjuction(conjunction);
      } else {
        this.sql.append(conjunctionSql);
      }
      addExpression(term);
    }
    this.sql.append(this.dialect.endConjunction(conjunction));
  }

  /**
   * @param arg the {@link Arg} to add.
   */
  protected void addArg(Arg<?> arg) {

    PropertyPath<?> path = arg.getPath();
    if (path == null) {
      Object value = arg.getValue();
      if (value == null) {
        this.sql.append(this.dialect.literalNull());
      } else if (value instanceof Boolean) {
        if (((Boolean) value).booleanValue()) {
          this.sql.append(this.dialect.literalTrue());
        } else {
          this.sql.append(this.dialect.literalFalse());
        }
      } else if (value instanceof Range) {
        Range<?> range = (Range<?>) value;
        addVariable(range.getMin());
        this.sql.append(this.dialect.and());
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
  protected void addVariable(Object value) {

    this.sql.append(this.dialect.variable(this.variablesInternal.size()));
    this.variablesInternal.add(value);
  }

  /**
   * @param path the {@link PropertyPath} to add.
   */
  protected void addPath(PropertyPath<?> path) {

    this.sql.append(this.dialect.ref(path.getName()));
  }

  /**
   * @param paths the {@link PropertyPath}s to add.
   */
  protected void addPaths(List<PropertyPath<?>> paths) {

    String separator = null;
    for (PropertyPath<?> path : paths) {
      if (separator == null) {
        separator = this.dialect.separator();
      } else {
        this.sql.append(separator);
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

    return this.sql.toString();
  }

  /**
   * @param groupByList the {@link List} of {@link PropertyPath}s to add as {@code GROUP BY} clause. It may be
   *        {@code null}.
   */
  public void addGroupBy(List<PropertyPath<?>> groupByList) {

    if ((groupByList == null) || (groupByList.isEmpty())) {
      return;
    }
    this.sql.append(this.dialect.groupBy());
    addPaths(groupByList);
  }

  /**
   * @param orderByList the {@link List} of {@link OrderByExpression}s to add as {@code  ORDER BY} clause. It may be
   *        {@code null}.
   */
  public void addOrderBy(List<OrderByExpression> orderByList) {

    if ((orderByList == null) || (orderByList.isEmpty())) {
      return;
    }
    this.sql.append(this.dialect.orderBy());
    String separator = null;
    for (OrderByExpression orderBy : orderByList) {
      if (separator == null) {
        separator = this.dialect.separator();
      } else {
        this.sql.append(separator);
      }
      this.sql.append(this.dialect.ref(orderBy.getPath().getName()));
      this.sql.append(this.dialect.order(orderBy.getOrder()));
    }
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  public void addSelectFrom(String source) {

    this.sql.append(this.dialect.select());
    addFrom(source);
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  public void addDeleteFrom(String source) {

    this.sql.append(this.dialect.delete());
    addFrom(source);
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  public void addFrom(String source) {

    this.sql.append(this.dialect.from());
    addSource(source);
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  public void addInsertInto(String source) {

    this.sql.append(this.dialect.insertInto());
    addSource(source);
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  protected void addSource(String source) {

    this.sql.append(this.dialect.ref(source));
  }

  /**
   * @param source the name of the source (e.g. table, document, class, etc.)
   */
  public void addUpdate(String source) {

    this.sql.append(this.dialect.update());
    addSource(source);
  }

  /**
   * @param offset the number of rows to skip.
   * @param limit the maximum number of rows to retrieve.
   */
  public void addPaging(long offset, long limit) {

    if (offset != 0) {
      String sqlOffset = this.dialect.offset();
      if (!sqlOffset.isEmpty()) {
        this.sql.append(sqlOffset);
        addVariable(Long.valueOf(offset));
      }
    }
    if (limit != Long.MAX_VALUE) {
      String sqlLimit = this.dialect.limit();
      if (!sqlLimit.isEmpty()) {
        this.sql.append(sqlLimit);
        addVariable(Long.valueOf(limit));
      }
    }
  }

  /**
   * @param setExpressionList the {@link List} of {@link SetExpression}s.
   */
  public void addSet(List<SetExpression<?>> setExpressionList) {

    if ((setExpressionList == null) || (setExpressionList.isEmpty())) {
      return;
    }
    this.sql.append(this.dialect.set());
    String separator = null;
    for (SetExpression<?> setExp : setExpressionList) {
      if (separator == null) {
        separator = this.dialect.separator();
      } else {
        this.sql.append(separator);
      }
      this.sql.append(this.dialect.ref(setExp.getPath().getName()));
      this.sql.append(this.dialect.setAssignment());
      addArg(setExp.getAssignment());
    }
  }
}