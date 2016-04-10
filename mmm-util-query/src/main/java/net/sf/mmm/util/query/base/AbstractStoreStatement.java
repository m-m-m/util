/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.StoreStatement;
import net.sf.mmm.util.query.base.feature.FeatureSetImpl;

/**
 * This is the abstract base-implementation of {@link StoreStatement}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractStoreStatement<E, SELF extends StoreStatement<E, SELF>>
    extends AbstractStatement<E, SELF> implements StoreStatement<E, SELF> {

  /**
   * The constructor.
   *
   * @param dialect the {@link SqlDialect}.
   */
  public AbstractStoreStatement(SqlDialect dialect) {
    super(dialect);
  }

  @Override
  public <V> SELF set(PropertyPath<V> path, V value) {

    feature(FeatureSetImpl.class).set(path, value);
    return self();
  }

  @Override
  public <V> SELF set(PropertyPath<V> path, PropertyPath<V> valuePath) {

    feature(FeatureSetImpl.class).set(path, valuePath);
    return self();
  }

}
