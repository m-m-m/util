/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.statement.InsertStatement;

/**
 * This is the abstract base-implementation of {@link InsertStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.5.0
 */
public abstract class AbstractInsertStatement<E, SELF extends InsertStatement<E, SELF>>
    extends AbstractStoreStatement<E, SELF> implements InsertStatement<E, SELF> {

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getDialect()}.
   * @param alias - see {@link #getAlias()}.
   */
  public AbstractInsertStatement(SqlDialect dialect, EntityAlias<E> alias) {
    super(dialect, alias);
  }

  @Override
  public boolean isSupportingAlias() {

    return false;
  }

  @Override
  protected void buildStart(SqlBuilder builder) {

    builder.getBuffer().append(getDialect().insertInto());
    super.buildStart(builder);
  }

}
