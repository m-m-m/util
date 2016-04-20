/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.statement.orientdb;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.feature.FeatureInsert;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.statement.StatementFactory;

/**
 * This is the interface extends {@link StatementFactory} for OrientDb.
 *
 * @author hohwille
 * @since 8.0.0
 */
@ComponentSpecification
public interface OrientDbStatementFactory extends StatementFactory, FeatureInsert {

  @Override
  <E> OrientDbSelectStatement<E> selectFrom(EntityAlias<E> alias);

  @Override
  OrientDbSelectStatement<Object[]> selectFrom(EntityAlias<?> alias, PropertyPath<?>... paths);

  @Override
  <E> OrientDbSelectStatement<E> selectFrom(EntityAlias<?> alias, Class<E> toClass,
      PropertyPath<?>... constructorArgs);

  @Override
  <E> OrientDbDeleteStatement<E> deleteFrom(EntityAlias<E> alias);

  @Override
  <E> OrientDbUpdateStatement<E> update(EntityAlias<E> alias);

  @Override
  <E> OrientDbInsertStatement<E> insertInto(EntityAlias<E> alias);

}
