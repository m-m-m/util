/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;


/**
 * This is the abstract interface for a fragment of a JPQL-query.
 * 
 * @see net.sf.mmm.persistence.api.query.JpqlBuilder
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface JpqlBase<E> {

  /** JPQL-keyword SELECT. */
  String JPQL_SELECT = "SELECT ";

  /** JPQL-keyword FROM. */
  String JPQL_FROM = " FROM ";

  /** JPQL-keyword DISTINCT. */
  String JPQL_DISTINCT = "DISTINCT ";

  /** JPQL-keyword JOIN. */
  String JPQL_JOIN = " JOIN ";

  /** JPQL-keyword LEFT JOIN. */
  String JPQL_LEFT_JOIN = " LEFT JOIN ";

  /** JPQL-keyword LEFT OUTER JOIN. */
  String JPQL_LEFT_OUTER_JOIN = " LEFT OUTER JOIN ";

  /** JPQL-keyword INNER JOIN. */
  String JPQL_INNER_JOIN = " INNER JOIN ";

  /** JPQL-keyword FETCH. */
  String JPQL_FETCH = "FETCH ";

  /** JPQL-keyword for start of IN(...) expression. */
  String JPQL_IN_START = " IN(";

  /** JPQL-keyword for end of IN(...) expression. */
  String JPQL_IN_END = ") ";

  /** JPQL-keyword ORDER BY. */
  String JPQL_ORDER_BY = " ORDER BY ";

  /** JPQL-keyword GROUP BY. */
  String JPQL_GROUP_BY = " GROUP BY ";

  /** JPQL-keyword HAVING. */
  String JPQL_HAVING = " HAVING ";

  /** JPQL-keyword new. */
  String JPQL_NEW = "new ";

  /** JPQL-keyword WHERE. */
  String JPQL_WHERE = " WHERE ";

  /** JPQL-conjunction for AND. */
  String JPQL_CONJUNCTION_AND = " AND ";

  /** JPQL-conjunction for AND. */
  String JPQL_CONJUNCTION_OR = " OR ";

  /** JPQL-keyword for NOT (negation). */
  String JPQL_NOT = "NOT ";

  /** JPQL-operator LIKE. */
  String JPQL_OPERATOR_LIKE = "LIKE";

  /** JPQL-operator LIKE. */
  String JPQL_OPERATOR_NOT_LIKE = "NOT " + JPQL_OPERATOR_LIKE;

  /** JPQL-operator equal (=). */
  String JPQL_OPERATOR_EQUAL = "=";

  /** JPQL-operator not equal (<>). */
  String JPQL_OPERATOR_NOT_EQUAL = "<>";

  /** JPQL-operator greater (>). */
  String JPQL_OPERATOR_GREATER = ">";

  /** JPQL-operator greater-or-equal (>=). */
  String JPQL_OPERATOR_GREATER_OR_EQUAL = ">=";

  /** JPQL-operator less (<). */
  String JPQL_OPERATOR_LESS = "<";

  /** JPQL-operator less-or-equal (<=). */
  String JPQL_OPERATOR_LESS_OR_EQUAL = "<=";

  /** JPQL-operator BETWEEN. <b>ATTENTION:</b> Has to be used in the form "BETWEEN :min AND :max". */
  String JPQL_OPERATOR_BETWEEN = " BETWEEN ";

  /** JPQL-operator NOT BETWEEN. <b>ATTENTION:</b> Has to be used in the form "NOT BETWEEN :min AND :max". */
  String JPQL_OPERATOR_NOT_BETWEEN = " NOT BETWEEN ";

  /** JPQL-condition IS MEMBER. */
  String JPQL_CONDITION_IS_MEMBER = " IS MEMBER";

  /** JPQL-condition IS NOT MEMBER. */
  String JPQL_CONDITION_IS_NOT_MEMBER = " IS NOT MEMBER";

  /** JPQL-condition IS NULL. */
  String JPQL_CONDITION_IS_NULL = " IS NULL";

  /** JPQL-condition IS NOT NULL. */
  String JPQL_CONDITION_IS_NOT_NULL = " IS NOT NULL";

  /** JPQL-condition IS EMPTY. */
  String JPQL_CONDITION_IS_EMPTY = " IS EMPTY";

  /** JPQL-condition IS NOT EMPTY. */
  String JPQL_CONDITION_IS_NOT_EMPTY = " IS NOT EMPTY";

  /** JPQL-keyword for indexed parameter. */
  String JPQL_PARAMETER = "?";

  /**
   * @return the {@link Class} reflecting the main {@link net.sf.mmm.util.entity.api.GenericEntity entity}.
   *         This is typically the result type of this query, e.g. in case of a regular
   *         {@link JpqlFragment#select() select}.
   */
  Class<E> getEntityType();

  /**
   * This method gets the {@link JpqlPropertySupport#ALIAS alias} of the {@link #getEntityType() (main)
   * entity}. For example in the query "SELECT foo FROM MyEntity foo ..." this method will return "foo".
   * 
   * @return the alias of the {@link #getEntityType() (main) entity}.
   */
  String getEntityAlias();

}
