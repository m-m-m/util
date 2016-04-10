/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.jpql;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.query.api.jpql.JpqlSelectStatement;
import net.sf.mmm.util.query.api.orientdb.OrientDbSelectStatement;
import net.sf.mmm.util.query.base.AbstractSelectStatement;
import net.sf.mmm.util.query.base.feature.FeatureHavingImpl;
import net.sf.mmm.util.query.base.feature.FeaturePagingImpl;

/**
 * Implementation of {@link OrientDbSelectStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public class JpqlSelectStatementImpl<E> extends AbstractSelectStatement<E, JpqlSelectStatement<E>>
    implements JpqlSelectStatement<E> {

  private final EntityManager entityManager;

  private final Class<E> entityClass;

  private final String source;

  /**
   * The constructor.
   *
   * @param entityManager the {@link EntityManager} instance.
   * @param entityClass the {@link Class} reflecting the {@link Entity}.
   */
  public JpqlSelectStatementImpl(EntityManager entityManager, Class<E> entityClass) {
    super(JpqlDialect.INSTANCE);
    this.entityManager = entityManager;
    this.entityClass = entityClass;
    String entityName = entityClass.getSimpleName();
    Entity entity = entityClass.getAnnotation(Entity.class);
    if (entity != null) {
      String name = entity.name();
      if (!name.isEmpty()) {
        entityName = name;
      }
    }
    this.source = entityName;
  }

  @Override
  protected String getSource() {

    return this.source;
  }

  private Query query(String prefix) {

    Query query = this.entityManager.createQuery(prefix + getSql());
    assignVariables(query);
    return query;
  }

  private TypedQuery<E> typedQuery(String prefix) {

    TypedQuery<E> query = this.entityManager.createQuery(prefix + getSql(), this.entityClass);
    assignVariables(query);
    return query;
  }

  private void assignVariables(Query query) {

    FeaturePagingImpl paging = feature(FeaturePagingImpl.class);
    query.setFirstResult((int) paging.getOffset());
    query.setMaxResults(paging.getLimit());
    List<Object> variables = getVariables();
    for (int i = 0; i < variables.size(); i++) {
      query.setParameter(i + 1, variables.get(i));
    }
  }

  @Override
  public List<E> fetch() {

    return typedQuery(getSqlDialect().select()).getResultList();
  }

  @Override
  public E fetchFirst() {

    return typedQuery(getSqlDialect().select()).getSingleResult();
  }

  @Override
  public E fetchOne() {

    return typedQuery(getSqlDialect().select()).getSingleResult();
  }

  @Override
  public long fetchCount() {

    Long count = (Long) query(getSqlDialect().selectCountAll()).getSingleResult();
    return count.longValue();
  }

  @Override
  public JpqlSelectStatement<E> having(Expression... expressions) {

    feature(FeatureHavingImpl.class).having(expressions);
    return self();
  }

}
