/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import java.util.List;

import javax.persistence.EntityManager;

import net.sf.mmm.persistence.api.query.JpqlStatement;

/**
 * This is the implementation of {@link JpqlStatement} using JPA.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractStatementImpl extends AbstractJpqlContext implements JpqlStatement {

  /** @see #getJpqlStatement() */
  private final String jpqlStatement;

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param context is the {@link AbstractJpqlContext} to use as template.
   */
  public AbstractStatementImpl(String jpqlStatement, AbstractJpqlContext context) {

    super(context);
    this.jpqlStatement = jpqlStatement;
  }

  /**
   * The constructor.
   * 
   * @param jpqlStatement - see {@link #getJpqlStatement()}.
   * @param entityManager - see {@link #getEntityManager()}.
   * @param parameters - see {@link #getParameters()}.
   */
  public AbstractStatementImpl(String jpqlStatement, EntityManager entityManager, List<Object> parameters) {

    super(entityManager, parameters);
    this.jpqlStatement = jpqlStatement;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getJpqlStatement() {

    return this.jpqlStatement;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.jpqlStatement;
  }

}
