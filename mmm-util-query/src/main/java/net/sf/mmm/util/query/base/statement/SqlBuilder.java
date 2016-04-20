/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.lang.api.attribute.AttributeReadName;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.expression.Bracketing;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.api.statement.Statement;
import net.sf.mmm.util.query.api.variable.Variable;
import net.sf.mmm.util.query.base.argument.ConstantArgument;
import net.sf.mmm.util.query.base.expression.SimpleExpressionFormatter;
import net.sf.mmm.util.query.base.expression.SqlOperator;
import net.sf.mmm.util.query.base.expression.SqlOperator.SqlOperatorLike;
import net.sf.mmm.util.value.api.Range;

/**
 * This class represents the with context to build the SQL string and collect bind variables.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class SqlBuilder extends SimpleExpressionFormatter {

  private final AbstractStatement<?, ?> statement;

  private final SqlDialect dialect;

  private final List<Object> parametersInternal;

  private final List<Object> parameters;

  private final List<Path<?>> paths;

  /**
   * The constructor.
   *
   * @param statement the {@link AbstractStatement} to build.
   */
  public SqlBuilder(AbstractStatement<?, ?> statement) {
    super();
    this.statement = statement;
    this.dialect = statement.getDialect();
    this.parametersInternal = new ArrayList<>();
    this.parameters = Collections.unmodifiableList(this.parametersInternal);
    this.paths = new ArrayList<>();
  }

  /**
   * @return the {@link Statement#getSql() SQL}.
   */
  public String getSql() {

    return getBuffer().toString();
  }

  /**
   * @return the statement
   */
  public AbstractStatement<?, ?> getStatement() {

    return this.statement;
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
  List<Path<?>> getPaths() {

    return this.paths;
  }

  @Override
  public void append(Argument<?> argument) {

    Path<?> path = argument.getValuePath();
    if (path != null) {
      addPath(path);
    } else {
      Object value = argument.getValue();
      if (value == null) {
        append(this.dialect.literalNull());
      } else if (value instanceof Boolean) {
        if (((Boolean) value).booleanValue()) {
          append(this.dialect.literalTrue());
        } else {
          append(this.dialect.literalFalse());
        }
      } else if (value instanceof Range) {
        Range<?> range = (Range<?>) value;
        addParameter(range.getMin());
        append(this.dialect.and());
        addParameter(range.getMax());
      } else {
        addParameter(value);
      }
    }
  }

  @Override
  public void append(SqlOperator<?, ?> operator, Argument<?> arg2) {

    StringBuilder buffer = getBuffer();
    if (arg2 == ConstantArgument.NULL) {
      if (operator == SqlOperator.EQUAL) {
        buffer.append(this.dialect.isNull());
        return;
      } else if (operator == SqlOperator.NOT_EQUAL) {
        buffer.append(this.dialect.isNotNull());
        return;
      }
    }
    if (arg2 == null) {
      if (operator == SqlOperator.EMPTY) {
        buffer.append(this.dialect.isEmpty());
        return;
      } else if (operator == SqlOperator.NOT_EMPTY) {
        buffer.append(this.dialect.isNotEmpty());
        return;
      }
    }
    buffer.append(this.dialect.operator(operator));
    append(arg2);
    if (operator instanceof SqlOperatorLike) {
      char escape = ((SqlOperatorLike) operator).getEscape();
      if (escape != '\0') {
        buffer.append(this.dialect.escape(escape));
      }
    }
  }

  @Override
  public String format(Conjunction conjunction) {

    return this.dialect.conjuction(conjunction);
  }

  /**
   * @param expression the {@link Expression} to add.
   */
  public void addExpression(Expression expression) {

    expression.format(this, Bracketing.MINIMAL);
  }

  /**
   * @param value the value to add as bind variable.
   */
  public void addParameter(Object value) {

    append(this.dialect.parameter(this.parametersInternal.size()));
    this.parametersInternal.add(value);
  }

  /**
   * @param path the {@link PropertyPath} to add.
   */
  public void addPath(Path<?> path) {

    boolean omitRootAlias = !this.statement.isSupportingAlias();
    String quote = this.dialect.quoteReference();
    append(quote);
    path.format(this::formatPathSegment, getBuffer());
    append(quote);
    this.paths.add(path);
  }

  protected String formatPathSegment(AttributeReadName segment) {

    boolean omitRootAlias = !this.statement.isSupportingAlias();
    if (omitRootAlias && (segment instanceof EntityAlias)) {
      return "";
    }
    if (segment instanceof ReadableProperty) {
      ReadableProperty<?> property = (ReadableProperty<?>) segment;
      return this.dialect.property(property);
    }
    return segment.getName();
  }

  /**
   * @param pathList the {@link Path}s to add.
   */
  public void addPaths(List<Path<?>> pathList) {

    String separator = null;
    for (Path<?> path : pathList) {
      if (separator == null) {
        separator = this.dialect.separator();
      } else {
        append(separator);
      }
      addPath(path);
    }
  }

  /**
   * @param pathArray the {@link PropertyPath}s to add.
   */
  public void addPaths(Path<?>... pathArray) {

    addPaths(Arrays.asList(pathArray));
  }

  /**
   * @param variable the {@link Variable} to add.
   */
  public void addVariable(Variable<?> variable) {

    append(this.dialect.variable(variable));
  }

}