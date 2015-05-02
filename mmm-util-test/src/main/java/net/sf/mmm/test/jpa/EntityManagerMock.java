/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test.jpa;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

/**
 * This is a mock implementation of {@link EntityManager}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EntityManagerMock implements EntityManager {

  /**
   * The constructor.
   */
  public EntityManagerMock() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Query createQuery(CriteriaUpdate updateQuery) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Query createQuery(CriteriaDelete deleteQuery) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isJoinedToTransaction() {

    // mock
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EntityGraph<?> createEntityGraph(String graphName) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EntityGraph<?> getEntityGraph(String graphName) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void flush() {

    // mock
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(Object entity) {

    // mock
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Query createQuery(String qlString) {

    return createQuery(qlString, Object.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Query createNamedQuery(String name) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Query createNativeQuery(String sqlString, Class resultClass) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> TypedQuery<T> createNamedQuery(String arg0, Class<T> arg1) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Query createNativeQuery(String sqlString) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Query createNativeQuery(String sqlString, String resultSetMapping) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> TypedQuery<T> createQuery(CriteriaQuery<T> arg0) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> TypedQuery<T> createQuery(String query, Class<T> resultType) {

    return new TypedQueryMock<T>(query, resultType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void detach(Object arg0) {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T find(Class<T> entityClass, Object primaryKey) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T find(Class<T> arg0, Object arg1, Map<String, Object> arg2) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2, Map<String, Object> arg3) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CriteriaBuilder getCriteriaBuilder() {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getDelegate() {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EntityManagerFactory getEntityManagerFactory() {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FlushModeType getFlushMode() {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LockModeType getLockMode(Object arg0) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Metamodel getMetamodel() {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getProperties() {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void persist(Object entity) {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T merge(T entity) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void remove(Object entity) {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T getReference(Class<T> entityClass, Object primaryKey) {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFlushMode(FlushModeType flushMode) {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void lock(Object entity, LockModeType lockMode) {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(Object entity) {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void joinTransaction() {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isOpen() {

    // mock
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EntityTransaction getTransaction() {

    // mock
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void lock(Object arg0, LockModeType arg1, Map<String, Object> arg2) {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(Object arg0, Map<String, Object> arg1) {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(Object arg0, LockModeType arg1) {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(Object arg0, LockModeType arg1, Map<String, Object> arg2) {

    // mock

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProperty(String arg0, Object arg1) {

    // mock
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T unwrap(Class<T> arg0) {

    // mock
    return null;
  }

}
