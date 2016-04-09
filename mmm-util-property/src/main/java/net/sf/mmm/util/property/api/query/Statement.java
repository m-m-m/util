/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.query;

import java.util.List;

import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.property.base.query.SqlDialect;

/**
 * This interface allows to build and execute type-safe query statements using a fluent API based on
 * {@link PropertyPath} and {@link Expression}.
 *
 * @see SelectStatement
 * @see InsertStatement
 * @see UpdateStatement
 * @see DeleteStatement
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface Statement<E, SELF extends Statement<E, SELF>> {

  /**
   * @return an {@link java.util.Collections#unmodifiableList(List) unmodifiable} {@link List} with the bind variables.
   */
  List<Object> getVariables();

  /**
   * @return the current SQL {@link String} of this {@link Statement}. May only be an SQL fragment as the final
   *         operation such as {@link SelectStatement#fetch()} will complete the statement. Has to be rebuild after
   *         every modification to the {@link Statement}.
   */
  String getSql();

  /**
   * @return the {@link SqlDialect} to use.
   */
  SqlDialect getSqlDialect();

  /**
   * @see #getSql()
   *
   * @return the same as {@link #getSql()}.
   */
  @Override
  String toString();

}
