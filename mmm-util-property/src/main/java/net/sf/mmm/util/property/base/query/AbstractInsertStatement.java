/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

import net.sf.mmm.util.property.api.query.InsertStatement;

/**
 * This is the abstract base-implementation of {@link InsertStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractInsertStatement<E, SELF extends AbstractInsertStatement<E, SELF>>
    extends AbstractStoreStatement<E, SELF> implements InsertStatement<E, SELF> {

  /**
   * The constructor.
   *
   * @param dialect the {@link SqlDialect}.
   */
  public AbstractInsertStatement(SqlDialect dialect) {
    super(dialect);
  }

  @Override
  protected void build(SqlBuilder builder) {

    builder.addInsertInto(getSource());
    super.build(builder);
  }

}
