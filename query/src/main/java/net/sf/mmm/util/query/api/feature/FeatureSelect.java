/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.path.EntityAlias;
import net.sf.mmm.util.query.api.statement.SelectStatement;

/**
 * {@link StatementFactoryFeature} for a {@link net.sf.mmm.util.query.api.statement.StatementFactory} supporting a
 * {@link SelectStatement}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public abstract interface FeatureSelect extends StatementFactoryFeature {

  /**
   * Creates a regular {@link SelectStatement} ({@code SELECT FROM alias.source [AS alias.name] ...}).
   *
   * @param <E> the generic type of the entity to select.
   * @param alias the {@link EntityAlias} to select from.
   * @return the new {@link SelectStatement}.
   */
  <E> SelectStatement<E, ?> selectFrom(EntityAlias<E> alias);

  /**
   * Creates {@link SelectStatement} for a tuple of the given {{@code paths} (
   * {@code SELECT path1, path2, ... FROM alias.source [AS alias.name] ...}).
   *
   * @param alias the {@link EntityAlias} to select from.
   * @param paths the {@link PropertyPath}s to select.
   * @return the new {@link SelectStatement}.
   */
  SelectStatement<Object[], ?> selectFrom(EntityAlias<?> alias, PropertyPath<?>... paths);

  /**
   * Creates {@link SelectStatement} for a projection to a transfer-object having an according constructor. In JPA this
   * is called a constructor query (
   * {@code SELECT NEW package.Classname(arg1, arg2, ...) FROM alias.source [AS alias.name] ...}). If not natively
   * supported this is implemented as a projection based on a {@link #selectFrom(EntityAlias, PropertyPath...) tuple
   * selection}.
   *
   * @param <E> the generic type of the transfer-object to select.
   * @param alias the {@link EntityAlias} to select from.
   * @param toClass the transfer-object to use. Will be instantiated via the {@link java.lang.reflect.Constructor}
   *        matching to the {@link PropertyPath#getValue() value} types of the {@code constructorArgs}.
   * @param constructorArgs the selection paths (or aggregate expressions).
   * @return the new {@link SelectStatement}.
   */
  <E> SelectStatement<E, ?> selectFrom(EntityAlias<?> alias, Class<E> toClass, PropertyPath<?>... constructorArgs);

}
