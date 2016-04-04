/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.query;

import net.sf.mmm.util.property.api.query.feature.FeatureFetch;
import net.sf.mmm.util.property.api.query.feature.FeatureGroupBy;
import net.sf.mmm.util.property.api.query.feature.FeatureOrderBy;
import net.sf.mmm.util.property.api.query.feature.FeaturePaging;
import net.sf.mmm.util.property.api.query.feature.FeatureWhere;

/**
 * Extends {@link Statement} for regular {@code SELECT} statements to retrieve results (unlike {@link InsertStatement
 * INSERT}, {@link UpdateStatement UPDATE} or deletequery).
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface SelectStatement<E, SELF extends SelectStatement<E, SELF>> extends Statement<E, SELF>,
    FeatureFetch<E>, FeatureWhere<SELF>, FeatureOrderBy<SELF>, FeatureGroupBy<SELF>, FeaturePaging<SELF> {

}
