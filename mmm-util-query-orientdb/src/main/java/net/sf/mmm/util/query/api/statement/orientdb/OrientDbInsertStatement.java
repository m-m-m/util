/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.statement.orientdb;

import net.sf.mmm.util.query.api.statement.InsertStatement;

/**
 * Extends a regular {@link InsertStatement} for OrientDB with advanced features.
 *
 * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.Bean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface OrientDbInsertStatement<E> extends InsertStatement<E, OrientDbInsertStatement<E>> {

}
