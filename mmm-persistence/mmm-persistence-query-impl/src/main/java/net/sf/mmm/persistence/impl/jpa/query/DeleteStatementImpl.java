/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;

import net.sf.mmm.persistence.api.query.DeleteStatement;

/**
 * This is the implementation of {@link DeleteStatement}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public class DeleteStatementImpl extends AbstractExecutionStatementImpl implements DeleteStatement {

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param context is the {@link AbstractJpqlContext} to use as template.
   */
  public DeleteStatementImpl(String jpqlStatement, AbstractJpqlContext context) {

    super(jpqlStatement, context);
  }

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param entityManager - see {@link #getEntityManager()}.
   * @param parameters - see {@link #getParameters()}.
   */
  public DeleteStatementImpl(String jpqlStatement, EntityManager entityManager, List<Object> parameters) {

    super(jpqlStatement, entityManager, parameters);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int executeDelete() {

    return getOrCreateQuery().executeUpdate();
  }

}
