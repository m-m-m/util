/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.jpa.query.impl.statement.jpql;

import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.mmm.jpa.query.api.statenent.jpql.JpqlDeleteStatement;
import net.sf.mmm.jpa.query.api.statenent.jpql.JpqlSelectStatement;
import net.sf.mmm.jpa.query.api.statenent.jpql.JpqlStatementFactory;
import net.sf.mmm.jpa.query.api.statenent.jpql.JpqlUpdateStatement;
import net.sf.mmm.jpa.query.base.statement.jpql.JpqlDialect;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.mapping.GenericPojoBeanMapper;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.base.statement.AbstractStatement;
import net.sf.mmm.util.query.base.statement.AbstractStatementFactory;

/**
 * This is the interface
 *
 * @author hohwille
 * @since 8.0.0
 */
public class JpqlStatementFactoryImpl extends AbstractStatementFactory implements JpqlStatementFactory {

  private EntityManager entityManager;

  private JpqlDialect dialect;

  private GenericPojoBeanMapper beanMapper;

  /**
   * The constructor.
   */
  public JpqlStatementFactoryImpl() {
    this(null, JpqlDialect.INSTANCE);
  }

  /**
   * The constructor.
   *
   * @param dialect the {@link JpqlDialect}.
   */
  public JpqlStatementFactoryImpl(JpqlDialect dialect) {
    this(null, dialect);
  }

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager}.
   */
  public JpqlStatementFactoryImpl(EntityManager entityManager) {
    this(entityManager, JpqlDialect.INSTANCE);
  }

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager}.
   * @param dialect the {@link JpqlDialect}.
   */
  public JpqlStatementFactoryImpl(EntityManager entityManager, JpqlDialect dialect) {
    super();
    this.entityManager = entityManager;
    this.dialect = dialect;
  }

  /**
   * @param entityManager is the {@link EntityManager} to inject.
   */
  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {

    this.entityManager = entityManager;
  }

  /**
   * @return the {@link EntityManager}.
   */
  protected EntityManager getEntityManager() {

    return this.entityManager;
  }

  /**
   * @param beanMapper is the {@link GenericPojoBeanMapper} to {@link Inject}.
   */
  @Inject
  public void setBeanMapper(GenericPojoBeanMapper beanMapper) {

    this.beanMapper = beanMapper;
  }

  /**
   * @return the {@link GenericPojoBeanMapper}.
   */
  protected GenericPojoBeanMapper getBeanMapper() {

    return this.beanMapper;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <E> JpqlSelectStatement<E> selectFrom(EntityAlias<E> alias) {

    Function<?, E> mapper = null;
    Class<E> resultType = alias.getResultType();
    Class<?> entityType = alias.getEntityType();
    E prototype = alias.getPrototype();
    if ((resultType != null) && (prototype != null) && (entityType != resultType)
        && (!resultType.isAssignableFrom(entityType))) {
      mapper = x -> (E) this.beanMapper.toBean(x, (Bean) prototype);
    }
    return new JpqlSelectStatementImpl<>(this.entityManager, this.dialect, alias, mapper);
  }

  @Override
  public JpqlSelectStatement<Object[]> selectFrom(EntityAlias<?> alias, PropertyPath<?>... paths) {

    return new JpqlSelectStatementImpl<>(this.entityManager, this.dialect, alias, null, paths);
  }

  @Override
  public <E> JpqlSelectStatement<E> selectFrom(EntityAlias<?> alias, Class<E> toClass,
      PropertyPath<?>... constructorArgs) {

    return new JpqlSelectStatementImpl<>(this.entityManager, this.dialect, alias, null, toClass, constructorArgs);
  }

  @Override
  public <E> JpqlDeleteStatement<E> deleteFrom(EntityAlias<E> alias) {

    return new JpqlDeleteStatementImpl<>(this.entityManager, this.dialect, alias);
  }

  @Override
  public <E> JpqlUpdateStatement<E> update(EntityAlias<E> alias) {

    return new JpqlUpdateStatementImpl<>(this.entityManager, this.dialect, alias);
  }

  static void assignParameters(Query query, AbstractStatement<?, ?> statement, Long firstResult,
      Integer maxResults) {

    if (firstResult != null) {
      query.setFirstResult(firstResult.intValue());
    }
    if (maxResults != null) {
      query.setMaxResults(maxResults.intValue());
    }
    List<Object> variables = statement.getParameters();
    for (int i = 0; i < variables.size(); i++) {
      query.setParameter(i + 1, variables.get(i));
    }
  }

}
