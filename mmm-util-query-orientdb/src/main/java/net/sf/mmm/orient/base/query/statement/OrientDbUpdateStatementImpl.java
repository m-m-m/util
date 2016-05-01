/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.base.query.statement;

import net.sf.mmm.orient.api.query.statement.OrientDbUpdateStatement;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.base.feature.FeatureUpsertImpl;
import net.sf.mmm.util.query.base.statement.AbstractUpdateStatement;

/**
 * Implementation of {@link OrientDbUpdateStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public class OrientDbUpdateStatementImpl<E> extends AbstractUpdateStatement<E, OrientDbUpdateStatement<E>>
    implements OrientDbUpdateStatement<E> {

  /**
   * The constructor.
   *
   * @param dialect - see {@link #getDialect()}.
   * @param alias - see {@link #getAlias()}.
   */
  public OrientDbUpdateStatementImpl(OrientDbDialect dialect, EntityAlias<E> alias) {
    super(dialect, alias);
  }

  @Override
  public OrientDbUpdateStatement<E> upsert() {

    feature(FeatureUpsertImpl.class).upsert();
    return self();
  }

  @Override
  protected long doExecute(String sql, Integer limit) {

    // TODO Auto-generated method stub
    return 0;
  }

}
