/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.base.query.statement;

import net.sf.mmm.orient.api.query.statement.OrientDbInsertStatement;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.base.statement.AbstractInsertStatement;

/**
 * Implementation of {@link OrientDbInsertStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public class OrientDbInsertStatementImpl<E> extends AbstractInsertStatement<E, OrientDbInsertStatement<E>>
    implements OrientDbInsertStatement<E> {

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getDialect()}.
   * @param alias - see {@link #getAlias()}.
   */
  public OrientDbInsertStatementImpl(OrientDbDialect dialect, EntityAlias<E> alias) {
    super(dialect, alias);
  }

  @Override
  protected long doExecute(String sql, Integer limit) {

    // TODO Auto-generated method stub
    return 0;
  }

}
