/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.orientdb;

import net.sf.mmm.util.query.api.SelectStatement;
import net.sf.mmm.util.query.api.feature.FeatureLet;

/**
 * Extends a regular {@link SelectStatement} with advanced features like
 * {@link #let(net.sf.mmm.util.property.api.path.PropertyPath, String) LET clause}.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface OrientDbSelectStatement<E>
    extends SelectStatement<E, OrientDbSelectStatement<E>>, FeatureLet<OrientDbSelectStatement<E>> {

}
