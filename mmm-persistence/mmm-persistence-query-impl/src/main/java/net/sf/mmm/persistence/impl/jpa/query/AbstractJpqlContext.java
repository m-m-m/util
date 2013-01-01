/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * This class is the container for the context and state required to build a JPQL query.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public abstract class AbstractJpqlContext {

  /** @see #getEntityManager() */
  private final EntityManager entityManager;

  /** The {@link List} of parameters. */
  private List<Object> parameters;

  /**
   * The constructor.
   * 
   * @param entityManager - see {@link #getEntityManager()}.
   */
  public AbstractJpqlContext(EntityManager entityManager) {

    this(entityManager, new LinkedList<Object>());
  }

  /**
   * The copy constructor.
   * 
   * @param template is the {@link AbstractJpqlContext} to use as template.
   */
  public AbstractJpqlContext(AbstractJpqlContext template) {

    this(template.entityManager, template.parameters);
  }

  /**
   * The constructor.
   * 
   * @param entityManager - see {@link #getEntityManager()}.
   * @param parameters - see {@link #getParameters()}.
   */
  protected AbstractJpqlContext(EntityManager entityManager, List<Object> parameters) {

    super();
    this.entityManager = entityManager;
    this.parameters = parameters;
  }

  /**
   * @return the instance of {@link EntityManager} used to build the JPQL statement.
   */
  public EntityManager getEntityManager() {

    return this.entityManager;
  }

  /**
   * @return the {@link List} of {@link javax.persistence.Query#getParameter(int) parameters}.
   */
  public List<Object> getParameters() {

    return this.parameters;
  }

  /**
   * This method applies the parameters to the given {@link Query}.
   * 
   * @param query is the {@link Query} where to apply the parameters.
   */
  public void applyParameters(Query query) {

    int i = 0;
    for (Object parameter : this.parameters) {
      query.setParameter(i++, parameter);
    }
  }

  /**
   * @see EntityManager#createQuery(String, Class)
   * 
   * @param jpqlStatement is the JPQL statement.
   * @return the {@link Query} representing the JPQL statement.
   */
  public Query createQuery(String jpqlStatement) {

    Query query = this.entityManager.createQuery(jpqlStatement);
    applyParameters(query);
    return query;
  }

  /**
   * @see EntityManager#createQuery(String, Class)
   * 
   * @param <R> is the generic type of the result.
   * 
   * @param jpqlStatement is the JPQL statement.
   * @param resultType is the type of the result.
   * @return the {@link TypedQuery} representing the JPQL statement.
   */
  public <R> TypedQuery<R> createTypedQuery(String jpqlStatement, Class<R> resultType) {

    TypedQuery<R> query = this.entityManager.createQuery(jpqlStatement, resultType);
    applyParameters(query);
    return query;
  }
}
