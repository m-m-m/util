/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;


/**
 * This is the interface for a having clause (<code>having_clause</code>) in JPQL.
 * 
 * @see net.sf.mmm.persistence.api.query.JpqlBuilder
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public interface JpqlHavingClause<E> extends JpqlConditionalExpression<E, JpqlHavingClause<E>> {

  // nothing to add...

}
