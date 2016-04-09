/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.query;

import net.sf.mmm.util.property.api.query.feature.FeatureModify;

/**
 * Extends {@link Statement} for modifying data.
 *
 * @see InsertStatement
 * @see UpdateStatement
 * @see DeleteStatement
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 * @param <SELF> the generic type of this query itself (this) for fluent API calls.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface ModifyStatement<E, SELF extends ModifyStatement<E, SELF>>
    extends Statement<E, SELF>, FeatureModify {

}
