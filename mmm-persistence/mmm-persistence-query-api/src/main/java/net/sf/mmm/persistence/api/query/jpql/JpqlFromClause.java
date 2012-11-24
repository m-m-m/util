/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;

/**
 * This is the interface for a from-clause of a JQPL-query.
 * 
 * @see net.sf.mmm.persistence.api.query.JpqlBuilder
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface JpqlFromClause<E> extends JpqlGroupBySupport<E> {

  /**
   * This method adds an additional entity selection to this FROM clause (e.g.
   * "... FROM MainEntity main, AdditionalEntity additional ...").
   * 
   * @param entityType is the {@link Class} reflecting the additional
   *        {@link net.sf.mmm.util.entity.api.GenericEntity entity} to add to this from clause.
   * @param alias is the alias for the additional entity.
   * @return this instance.
   */
  JpqlFromClause<E> also(Class<?> entityType, String alias);

  /**
   * This method adds a join with an additional entity to this FROM clause. It is a shortcut for
   * {@link #join(String, String, JpqlJoinType)} using {@link JpqlJoinType#JOIN}.
   * 
   * @param valuedPathExpression is the <code>join_association_path_expression</code> - typically
   *        "&lt;otherAlias&gt;.&lt;collectionProperty&gt;".
   * @param alias is the alias for this join expression.
   * @return this instance.
   */
  JpqlFromClause<E> join(String valuedPathExpression, String alias);

  /**
   * This method adds a join with an additional entity to this FROM clause.
   * 
   * @param valuedPathExpression is the <code>join_association_path_expression</code> - typically
   *        "&lt;otherAlias&gt;.&lt;collectionProperty&gt;".
   * @param alias is the alias for this join expression.
   * @param type is the {@link JpqlJoinType}.
   * @return this instance.
   */
  JpqlFromClause<E> join(String valuedPathExpression, String alias, JpqlJoinType type);

  /**
   * This method adds a fetch join with an additional entity to this FROM clause ( {@link JpqlJoinType type} +
   * " FETCH " + valuedPathExpression).
   * 
   * @param valuedPathExpression is the <code>join_association_path_expression</code> - typically
   *        "&lt;otherAlias&gt;.&lt;collectionProperty&gt;".
   * @param type is the {@link JpqlJoinType}.
   * @return this instance.
   */
  JpqlFromClause<E> joinFetch(String valuedPathExpression, JpqlJoinType type);

  /**
   * This method adds a collection member declaration (e.g.
   * "... IN(&lt;otherAlias&gt;.&lt;collection&gt;) &lt;alias&gt; ...").
   * 
   * @param collectionValuedPathExpression is the collection path expression.
   * @param alias is the alias for this expression.
   * @return this instance.
   */
  JpqlFromClause<E> in(String collectionValuedPathExpression, String alias);

  /**
   * This method completes this clause and opens the WHERE clause.
   * 
   * @return the {@link JpqlWhereClause}.
   */
  JpqlWhereClause<E> where();

}
