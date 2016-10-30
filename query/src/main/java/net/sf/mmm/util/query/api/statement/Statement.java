/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.statement;

import java.util.List;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.Command;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.base.statement.SqlDialect;

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
 * @since 8.4.0
 */
public abstract interface Statement<E, SELF extends Statement<E, SELF>> extends Command {

  /**
   * @return the {@link EntityAlias} to work on (select from, insert into, etc.). For regular {@link Statement}s this
   *         will be {@link EntityAlias}{@code <E>} but for special queries such as
   *         {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, net.sf.mmm.util.property.api.path.PropertyPath...)
   *         tuple} or
   *         {@link net.sf.mmm.util.query.api.statement.StatementFactory#selectFrom(EntityAlias, Class, net.sf.mmm.util.query.api.path.Path...)
   *         constructor} queries this is not bound to the generic {@code <E>}.
   */
  EntityAlias<?> getAlias();

  /**
   * @return an {@link java.util.Collections#unmodifiableList(List) unmodifiable} {@link List} with the bind variables.
   */
  List<Object> getParameters();

  /**
   * @return the current SQL {@link String} of this {@link Statement}. May only be an SQL fragment as the final
   *         operation such as {@link SelectStatement#fetch()} will complete the statement. Has to be rebuild after
   *         every modification to the {@link Statement}.
   */
  @Override
  String getSql();

  /**
   * @return the {@link SqlDialect} to use.
   */
  SqlDialect getDialect();

  /**
   * @see #getSql()
   *
   * @return the same as {@link #getSql()}.
   */
  @Override
  String toString();

}
