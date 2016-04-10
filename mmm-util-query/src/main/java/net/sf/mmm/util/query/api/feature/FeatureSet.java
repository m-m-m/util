/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import net.sf.mmm.util.property.api.path.PropertyPath;

/**
 * This is the abstract interface for a query {@link net.sf.mmm.util.query.api.Statement} allowing a
 * {@link #set(PropertyPath, Object) SET clause}.
 *
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatureSet<SELF extends FeatureSet<SELF>> extends StatementFeature {

  /**
   * Adds a value binding setting the given {@link PropertyPath} to the given {@code value}.
   *
   * @param <V> the generic type of the {@code value}.
   * @param path the {@link PropertyPath} to set.
   * @param value the literal value to set for each matching object.
   * @return this query instance for fluent API calls.
   */
  <V> SELF set(PropertyPath<V> path, V value);

  /**
   * Adds a value binding setting the given {@link PropertyPath} to the value of the given {@code valuePath}.
   *
   * @param <V> the generic type of the {@code value}.
   * @param path the {@link PropertyPath} to set.
   * @param valuePath the {@link PropertyPath} pointing to the value to set the given {@code path} to.
   * @return this query instance for fluent API calls.
   */
  <V> SELF set(PropertyPath<V> path, PropertyPath<V> valuePath);

  /**
   * Adds a value binding setting the given {@link PropertyPath} to {@code null}.
   *
   * @param path the {@link PropertyPath} to set.
   * @return this query instance for fluent API calls.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  default SELF setNull(PropertyPath<?> path) {

    Object value = null;
    PropertyPath<Object> p = (PropertyPath) path;
    return set(p, value);
  }

}
