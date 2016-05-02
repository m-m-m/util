/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.feature;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.variable.Variable;

/**
 * {@link StatementFeature} for a {@link net.sf.mmm.util.query.api.statement.Statement} with support for
 * {@link #let(PropertyPath, String) LET} block.
 *
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface FeatureLet<SELF extends FeatureLet<SELF>> extends StatementFeature {

  /**
   * @param <V> the generic type of the value represented by the {@link Variable} and {@link PropertyPath}.
   * @param variable the context {@link Variable} to bind.
   * @param path the {@link PropertyPath} to bind to the {@link Variable}.
   * @return this query instance for fluent API calls.
   */
  <V> SELF let(Variable<V> variable, PropertyPath<V> path);

  /**
   * @param variable the context {@link Variable} to bind.
   * @param path the {@link PropertyPath} to bind to the {@link Variable}.
   * @return this query instance for fluent API calls.
   */
  SELF let(String variable, PropertyPath<?> path);

  /**
   * @param <V> the generic type of the value represented by the {@link Variable} and {@link PropertyPath}.
   * @param path the {@link PropertyPath} to bind to the {@link Variable}.
   * @param variable the context {@link Variable} to bind.
   * @return the {@link Variable} created for the given {@link String}.
   */
  <V> Variable<V> let(PropertyPath<V> path, String variable);

}
