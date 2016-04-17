/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.statenent.jpql;

import net.sf.mmm.util.query.api.feature.FeatureHaving;
import net.sf.mmm.util.query.api.statement.SelectStatement;

/**
 * Extends a regular {@link SelectStatement} with advanced features like
 * {@link #having(net.sf.mmm.util.query.api.expression.Expression...) HAVING clause}.
 *
 * @param <E> the generic type of the queried object (an {@link javax.persistence.Entity}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface JpqlSelectStatement<E>
    extends SelectStatement<E, JpqlSelectStatement<E>>, FeatureHaving<JpqlSelectStatement<E>> {

}
