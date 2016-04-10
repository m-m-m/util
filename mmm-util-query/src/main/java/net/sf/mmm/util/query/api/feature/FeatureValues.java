/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import net.sf.mmm.util.property.api.path.PropertyPath;

/**
 * This is the abstract interface for a query {@link net.sf.mmm.util.query.api.Statement} allowing a
 * {@link #value(PropertyPath, Object) VALUES clause}. For each invocation of {@link #value(PropertyPath, Object)
 * value(pI, vI)} a value binding is added so in the end the resulting SQL fragment will be:
 *
 * <pre>
 * (p1, p2, ..., pN) VALUES (v1, v2, ..., vN)
 * </pre>
 *
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatureValues<SELF extends FeatureValues<SELF>> extends StatementFeature {

  /**
   * Adds a value binding setting the given {@link PropertyPath} to the given {@code value}.
   *
   * @param <V> the generic type of the {@code value}.
   * @param path the {@link PropertyPath} for the value to assign that will be added to the bracket before the
   *        {@ocde VALUES} keyword.
   * @param value the literal value to assign that will be added to the bracket after the {@ocde VALUES} keyword.
   * @return this query instance for fluent API calls.
   */
  <V> SELF value(PropertyPath<V> path, V value);

}
