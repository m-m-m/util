/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.impl.statement.jpql;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.statenent.jpql.JpqlSelectStatement;
import net.sf.mmm.util.query.api.statenent.jpql.JpqlStatementFactory;
import net.sf.mmm.util.query.base.statement.AbstractStatementFactory;
import net.sf.mmm.util.query.base.statement.jpql.JpqlDialect;
import net.sf.mmm.util.query.base.statement.jpql.JpqlSelectStatementImpl;

/**
 * This is the interface
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
public class JpqlStatementFactoryImpl extends AbstractStatementFactory implements JpqlStatementFactory {

  @PersistenceContext
  private EntityManager entityManager;

  private JpqlDialect dialect;

  /**
   * The constructor.
   */
  public JpqlStatementFactoryImpl() {
    this(null);
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

  @Override
  public <E> JpqlSelectStatement<E> selectFrom(EntityAlias<E> alias) {

    return new JpqlSelectStatementImpl<>(this.entityManager, this.dialect, alias);
  }

  @Override
  public JpqlSelectStatement<Object[]> selectFrom(EntityAlias<?> alias, PropertyPath<?>... paths) {

    return new JpqlSelectStatementImpl<>(this.entityManager, this.dialect, alias, paths);
  }

  @Override
  public <E> JpqlSelectStatement<E> selectFrom(EntityAlias<?> alias, Class<E> toClass,
      PropertyPath<?>... constructorArgs) {

    return new JpqlSelectStatementImpl<>(this.entityManager, this.dialect, alias, toClass, constructorArgs);
  }

}
