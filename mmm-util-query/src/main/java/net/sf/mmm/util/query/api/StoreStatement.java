/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api;

import net.sf.mmm.util.query.api.feature.FeatureSet;

/**
 * Extends {@link Statement} for storing data.
 *
 * @see InsertStatement
 * @see UpdateStatement
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface StoreStatement<E, SELF extends StoreStatement<E, SELF>>
    extends ModifyStatement<E, SELF>, FeatureSet<SELF> {

}
