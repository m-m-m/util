/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.statement.orientdb;

import net.sf.mmm.util.query.api.feature.FeatureUpsert;
import net.sf.mmm.util.query.api.statement.UpdateStatement;

/**
 * Extends a regular {@link UpdateStatement} for OrientDB with advanced features like {@link #upsert() UPSERT}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface OrientDbUpdateStatement<E>
    extends UpdateStatement<E, OrientDbUpdateStatement<E>>, FeatureUpsert<OrientDbUpdateStatement<E>> {

}
