/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.query;

import net.sf.mmm.util.property.api.query.feature.FeatureHaving;

/**
 * Extends a regular {@link SelectStatement} with advanced features like
 * {@link #having(net.sf.mmm.util.property.api.expression.Expression...) HAVING clause}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface JpqlSelectStatement<E, SELF extends JpqlSelectStatement<E, SELF>>
    extends SelectStatement<E, SELF>, FeatureHaving<SELF> {

}
