/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement.orientdb;

import java.util.function.Function;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.statement.orientdb.OrientDbDeleteStatement;
import net.sf.mmm.util.query.api.statement.orientdb.OrientDbInsertStatement;
import net.sf.mmm.util.query.api.statement.orientdb.OrientDbSelectStatement;
import net.sf.mmm.util.query.api.statement.orientdb.OrientDbStatementFactory;
import net.sf.mmm.util.query.api.statement.orientdb.OrientDbUpdateStatement;
import net.sf.mmm.util.query.base.statement.AbstractStatementFactory;

/**
 * This is the implementation of {@link OrientDbStatementFactory}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class OrientDbStatementFactoryImpl extends AbstractStatementFactory implements OrientDbStatementFactory {

  private final OrientDbDialect dialect;

  private Function mapper;

  /**
   * The constructor.
   */
  public OrientDbStatementFactoryImpl() {
    this(OrientDbDialect.INSTANCE);
  }

  /**
   * The constructor.
   *
   * @param dialect the {@link OrientDbDialect} to use.
   */
  public OrientDbStatementFactoryImpl(OrientDbDialect dialect) {
    super();
    this.dialect = dialect;
  }

  @Override
  public <E> OrientDbSelectStatement<E> selectFrom(EntityAlias<E> alias) {

    return new OrientDbSelectStatementImpl<>(this.dialect, alias, this.mapper);
  }

  @Override
  public OrientDbSelectStatement<Object[]> selectFrom(EntityAlias<?> alias, PropertyPath<?>... paths) {

    return new OrientDbSelectStatementImpl<>(this.dialect, alias, this.mapper, paths);
  }

  @Override
  public <E> OrientDbSelectStatement<E> selectFrom(EntityAlias<?> alias, Class<E> toClass,
      PropertyPath<?>... constructorArgs) {

    return new OrientDbSelectStatementImpl<>(this.dialect, alias, this.mapper, toClass, constructorArgs);
  }

  @Override
  public <E> OrientDbUpdateStatement<E> update(EntityAlias<E> alias) {

    return new OrientDbUpdateStatementImpl<>(this.dialect, alias);
  }

  @Override
  public <E> OrientDbDeleteStatement<E> deleteFrom(EntityAlias<E> alias) {

    return new OrientDbDeleteStatementImpl<>(this.dialect, alias);
  }

  @Override
  public <E> OrientDbInsertStatement<E> insertInto(EntityAlias<E> alias) {

    return new OrientDbInsertStatementImpl<>(this.dialect, alias);
  }

}
