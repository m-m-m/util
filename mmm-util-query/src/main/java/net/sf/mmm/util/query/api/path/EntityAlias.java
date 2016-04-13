/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.query.base.path.Alias;

/**
 * This is the interface for an {@link EntityAlias} used in an
 * {@link net.sf.mmm.util.query.base.statement.AbstractStatement SQL statement} e.g. inside a a
 * {@link net.sf.mmm.util.query.base.statement.SqlDialect#from() FROM} block.
 *
 * @param <E> the generic type of the {@link #getType() type} of this {@link EntityAlias}.
 *
 * @see net.sf.mmm.util.query.base.path.Alias
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface EntityAlias<E> extends PathRoot<E> {

  /**
   * @return the actual source of this {@link EntityAlias}. Typically the name of a table, entity, class, etc. Shall NOT
   *         be {@code null}.
   */
  String getSource();

  /**
   * @return the alias name of this {@link EntityAlias} (what is "alias" in "SELECT FROM Foo AS alias"). May be
   *         {@code null} to omit or use canonical alias.
   */
  String getName();

  /**
   * @return the optional {@link Class} reflecting the entity of this source. May be {@code null}
   */
  Class<E> getType();

  /**
   * @return optional the {@link BeanFactory#createPrototype(Class) prototype} of this source. May be {@code null}.
   */
  E getPrototype();

  /**
   * @param aliasName the new {@link #getName() alias name}.
   * @return a copy of this {@link Alias} with the given {@link #getName() alias name}.
   */
  Alias<E> as(String aliasName);

}
