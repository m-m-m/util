/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;

import net.sf.mmm.persistence.api.query.UpdateStatement;

/**
 * This is the implementation of {@link UpdateStatement}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UpdateStatementImpl extends AbstractExecutionStatementImpl implements UpdateStatement {

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param context is the {@link AbstractJpqlContext} to use as template.
   */
  public UpdateStatementImpl(String jpqlStatement, AbstractJpqlContext context) {

    super(jpqlStatement, context);
  }

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param entityManager - see {@link #getEntityManager()}.
   * @param parameters - see {@link #getParameters()}.
   */
  public UpdateStatementImpl(String jpqlStatement, EntityManager entityManager, List<Object> parameters) {

    super(jpqlStatement, entityManager, parameters);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int executeUpdate() {

    return getOrCreateQuery().executeUpdate();
  }

}
