/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.statenent.jpql;

import net.sf.mmm.util.query.api.statement.UpdateStatement;

/**
 * Extension of a regular {@link UpdateStatement} for {@link net.sf.mmm.util.query.base.statement.jpql.Jpql JPQL}.
 *
 * @param <E> the generic type of the queried object (an {@link javax.persistence.Entity}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface JpqlUpdateStatement<E> extends UpdateStatement<E, JpqlUpdateStatement<E>> {

}
