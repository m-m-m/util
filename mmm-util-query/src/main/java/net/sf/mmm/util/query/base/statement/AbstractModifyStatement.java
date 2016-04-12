/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import net.sf.mmm.util.query.api.statement.ModifyStatement;
import net.sf.mmm.util.query.base.path.Alias;

/**
 * This is the abstract base-implementation of {@link ModifyStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractModifyStatement<E, SELF extends ModifyStatement<E, SELF>>
    extends AbstractStatement<E, SELF> implements ModifyStatement<E, SELF> {

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getSqlDialect()}.
   * @param alias - see {@link #getAlias()}.
   */
  public AbstractModifyStatement(SqlDialect dialect, Alias<E> alias) {
    super(dialect, alias);
  }

}
